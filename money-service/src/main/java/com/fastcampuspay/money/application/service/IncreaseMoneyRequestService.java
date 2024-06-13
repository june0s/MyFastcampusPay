package com.fastcampuspay.money.application.service;

import com.fastcampuspay.common.CountDownLatchManager;
import com.fastcampuspay.common.RechargingMoneyTask;
import com.fastcampuspay.common.SubTask;
import com.fastcampuspay.common.UseCase;
import com.fastcampuspay.money.adapter.axon.command.MemberMoneyCreatedCommand;
import com.fastcampuspay.money.adapter.out.persistence.MemberMoneyJpaEntity;
import com.fastcampuspay.money.adapter.out.persistence.MoneyChangingRequestMapper;
import com.fastcampuspay.money.application.port.in.*;
import com.fastcampuspay.money.application.port.out.GetMembershipPort;
import com.fastcampuspay.money.application.port.out.IncreaseMoneyPort;
import com.fastcampuspay.money.application.port.out.MembershipStatus;
import com.fastcampuspay.money.application.port.out.SendRechargingMoneyTaskPort;
import com.fastcampuspay.money.domain.MemberMoney;
import com.fastcampuspay.money.domain.MoneyChangingRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@UseCase
@RequiredArgsConstructor
@Transactional
@Slf4j
public class IncreaseMoneyRequestService implements IncreaseMoneyRequestUseCase, CreateMemberMoneyUseCase {

    private final CountDownLatchManager countDownLatchManager;
    private final SendRechargingMoneyTaskPort sendRechargingMoneyTaskPort;
    private final GetMembershipPort getMembershipPort;
    private final IncreaseMoneyPort increaseMoneyPort;
    private final CreateMemberMoneyPort createMemberMoneyPort;
    private final MoneyChangingRequestMapper mapper;

    private final CommandGateway commandGateway;

    @Override
    public MoneyChangingRequest increaseMoneyRequest(IncreaseMoneyRequestCommand command) {
        // 머니의 충전(증액) 과정 정의해보자
        // 1. 고객 정보가 정상인지 확인 -> MembershipService
        final MembershipStatus membershipStatus = getMembershipPort.getMembership(command.getTargetMembershipId());
        if (!membershipStatus.isValid()) {
            return null;
        }
        // 2. 고객의 연동된 계좌가 있는 지, 고객의 연동된 계좌의 잔액이 충분한 지 확인 -> BankingService
        // 3. 법인 계좌 상태 정상인지 확인 -> BankingService
        // 4. 증액을 위한 "기록". 요청 상태로 MoneyChangingStatus.REQUESTED 로 변경 -> MoneyChangingService
        // 5. 펌뱅킹을 수행하고 (고객의 연동된 계좌 -> 패캠페이 법인 계좌) -> BankingService

        // 6-1. 결과가 정상적이라면. 성공으로 MoneyChangingStatus.SUCCESS 로 리턴
        // 성공 시에 멤버의 MemberMoney 값 증액 해야 한다!

        final MemberMoneyJpaEntity memberMoneyJpaEntity = increaseMoneyPort.increaseMoney(
                new MemberMoney.MembershipId(command.getTargetMembershipId())
                , command.getAmount()
        );

        if (memberMoneyJpaEntity != null) {
            // 성공 했다.
            return mapper.mapToDomainEntity(increaseMoneyPort.createChangingRequest(
                    new MoneyChangingRequest.TargetMembershipId(command.getTargetMembershipId()),
                    new MoneyChangingRequest.MoneyChangingType(0), // INCREASING
                    new MoneyChangingRequest.ChangingMoneyAmount(command.getAmount()),
                    new MoneyChangingRequest.MoneyChangingStatus(1), // SUCCESS
                    new MoneyChangingRequest.Uuid(UUID.randomUUID())
            ));
        }

        // 6-2. 결과가 비정상이라면, 실패로 MoneyChangingStatus.FAILED 로 리턴

        return null;
    }

    // 머니 충전 - 비동기
    @Override
    public MoneyChangingRequest increaseMoneyRequestAsync(IncreaseMoneyRequestCommand command) {

        // Subtask 전제
        // 각 서비스에 특정 membershipId 로 Validation 을 하기위한 Task.
        log.info("2. increaseMoneyRequestAsync");

        // 1. subtask, task
        final SubTask validMemberTask = SubTask.builder()
                .taskName("validMemberTask") // 멤버십 유효성 검사
                .membershipId(command.getTargetMembershipId())
                .taskType("membership")
                .status("ready")
                .build();

        // banking sub task
        // banking account validation
        final SubTask validBankingAccountTask = SubTask.builder()
                .taskName("validBankingAcountTask") // 뱅킹 계좌 유효성 검사.
                .membershipId(command.getTargetMembershipId())
                .taskType("banking")
                .status("ready")
                .build();
        // amount money firmbanking sub task -> 원래는 확인해야 하는데, ok 받았다고 가정 하자.

        final List<SubTask> subTaskList = new ArrayList<>();
        subTaskList.add(validMemberTask);
        subTaskList.add(validBankingAccountTask);
        final RechargingMoneyTask task = RechargingMoneyTask.builder()
                .taskId(UUID.randomUUID().toString())
                .taskName("Increase MoneyTask")
                .subTaskList(subTaskList)
                .moneyAmount(command.getAmount())
                .membershipId(command.getTargetMembershipId())
                .toBankName("fastcampus")
                .build();

        // 2. kafka cluster produce
        log.debug("sendRechargingMoneyTaskPort. task id: " + task.getTaskId());
        sendRechargingMoneyTaskPort.sendRechargingMoneyTaskPort(task);
        countDownLatchManager.addCountDownLatch(task.getTaskId());

        // 3. wait
        try {
            log.debug("+ [start] await: " + task.getTaskId() + " = = = = =");
            countDownLatchManager.getCountDownLatch(task.getTaskId()).await();
            log.debug("+ [end] await: " + task.getTaskId() + " = = = = =");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // 3-1. task-consumer
        // 등록된 sub-task, status 모두 ok -> task 결과를 produce

        // 4. task result consume
        // 받은 응답을 다시, countDownLatchManager 를 통해서 결과 데이터를 받아야 한다.
        log.info("7. [wakeup] result: " + countDownLatchManager.getDataForKey(task.getTaskId()) + " = =");
        final String result = countDownLatchManager.getDataForKey(task.getTaskId());
        if (result.equals("success")) {
            // 4-1 Consume ok, logic 실행.
            final MemberMoneyJpaEntity memberMoneyJpaEntity = increaseMoneyPort.increaseMoney(
                    new MemberMoney.MembershipId(command.getTargetMembershipId())
                    , command.getAmount()
            );

            if (memberMoneyJpaEntity != null) {
                // 성공 했다.
                return mapper.mapToDomainEntity(increaseMoneyPort.createChangingRequest(
                        new MoneyChangingRequest.TargetMembershipId(command.getTargetMembershipId()),
                        new MoneyChangingRequest.MoneyChangingType(0), // INCREASING
                        new MoneyChangingRequest.ChangingMoneyAmount(command.getAmount()),
                        new MoneyChangingRequest.MoneyChangingStatus(1), // SUCCESS
                        new MoneyChangingRequest.Uuid(UUID.randomUUID())
                ));
            }
        } else {
            // 4-2 Consume fail, logic 실행.
            return null;
        }

        // 5. consume ok -> logic 실행.

        return null;
    }

    @Override
    public void createMemberMoneyUseCase(CreateMoneyRequestCommand command) {
        // axon framework를 위한 axon framework 전용 멤버 머니를 만들기 위한 command 를 정의해보자.

        MemberMoneyCreatedCommand axonCommand = new MemberMoneyCreatedCommand(command.getMembershipId());

        // command 보내고, 이벤트 처리될 때까지 기다린다.
        commandGateway.send(axonCommand).whenComplete((result, exception) -> {
            // 이벤트 받으면 jpa 사용해서 db 에 저장한다.
            if (exception != null) {
                System.out.println("exception: " + exception.getMessage());
            } else {
                System.out.println("result: " + result);
                createMemberMoneyPort.createMemberMoney(
                        new MemberMoney.MembershipId(command.getMembershipId()),
                        new MemberMoney.MoneyAggregateIdentifier(result.toString()));
            }
        });
    }

//    @Override
//    public MoneyChangingRequest registerBankAccount(RequestFirmbankingCommand command) {
//
//        // 은행 계좌를 등록해야하는 서비스(비즈니스 로직)
//
//        // {멤버 서비스 확인?} 지금은 skip
////        command.getMembershipId();
//
//        // 1. 외부 실제 은행에 등록 가능한 계좌인지(정상인지) 확인
//        // 외부의 은행에 이 계좌가 정상인지 확인 해야한다.
//        // Biz logic -> External System
//        // Port -> Adapter -> External System
//        BankAccount bankAccount = requestBankAccountInfoPort.getBankAccountInfo(new GetBankAccountRequest(
//                command.getBankName()
//                , command.getBankAccountNumber()
//        ));
//        boolean valid = bankAccount.isValid();
//
//        // 2. 등록가능한 계좌라면, 등록한다. 성공하면, 등록에 성공한 등록 정보를 리턴.
//        // 2-1 등록가능하지 않은 계좌라면, 에러를 리턴
//        if (valid) {
//            // 등록 정보 저장
//            MoneyChangingRequestJpaEntity savedAccountInfo = increaseMoneyPort.createChangingRequest(
//                    new MoneyChangingRequest.MembershipId(command.getMembershipId())
//                    , new MoneyChangingRequest.BankName(command.getBankName())
//                    , new MoneyChangingRequest.BankAccountNumber(command.getBankAccountNumber())
//                    , new MoneyChangingRequest.LinkedStatusIsValid(command.isLinkedStatusIsValid())
//            );
//
//            return mapper.mapToDomainEntity(savedAccountInfo);
//        } else {
//            return null;
//        }
//    }
}
