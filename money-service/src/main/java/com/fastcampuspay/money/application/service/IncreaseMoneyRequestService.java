package com.fastcampuspay.money.application.service;

import com.fastcampuspay.money.adapter.out.persistence.MemberMoneyJpaEntity;
import com.fastcampuspay.money.adapter.out.persistence.MoneyChangingRequestMapper;
import com.fastcampuspay.money.application.port.in.IncreaseMoneyRequestCommand;
import com.fastcampuspay.money.application.port.in.IncreaseMoneyRequestUseCase;
import com.fastcampuspay.money.application.port.out.IncreaseMoneyPort;
import com.fastcampuspay.money.domain.MemberMoney;
import com.fastcampuspay.money.domain.MoneyChangingRequest;
import com.fastcampuspay.common.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
@Transactional
public class IncreaseMoneyRequestService implements IncreaseMoneyRequestUseCase {

    private final IncreaseMoneyPort increaseMoneyPort;
    private final MoneyChangingRequestMapper mapper;

    @Override
    public MoneyChangingRequest increaseMoneyRequest(IncreaseMoneyRequestCommand command) {
        // 머니의 충전(증액) 과정 정의해보자
        // 1. 고객 정보가 정상인지 확인 -> MembershipService
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
