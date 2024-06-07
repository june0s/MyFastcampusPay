package com.fastcampuspay.remittance.application.service;

import com.fastcampuspay.common.UseCase;
import com.fastcampuspay.remittance.adapter.out.persistence.RemittanceRequestJpaEntity;
import com.fastcampuspay.remittance.adapter.out.persistence.RemittanceRequestMapper;
import com.fastcampuspay.remittance.application.port.in.RequestRemittanceCommand;
import com.fastcampuspay.remittance.application.port.in.RequestRemittanceUseCase;
import com.fastcampuspay.remittance.application.port.out.RequestRemittancePort;
import com.fastcampuspay.remittance.application.port.out.banking.BankingPort;
import com.fastcampuspay.remittance.application.port.out.membership.MembershipPort;
import com.fastcampuspay.remittance.application.port.out.membership.MembershipStatus;
import com.fastcampuspay.remittance.application.port.out.money.MoneyInfo;
import com.fastcampuspay.remittance.application.port.out.money.MoneyPort;
import com.fastcampuspay.remittance.domain.RemittanceRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RequestRemittanceService implements RequestRemittanceUseCase {

    private final RequestRemittancePort requestRemittancePort;
    private final RemittanceRequestMapper mapper;
    private final MembershipPort membershipPort;
    private final MoneyPort moneyPort;
    private final BankingPort bankingPort;

    @Override
    public RemittanceRequest requestRemittance(RequestRemittanceCommand command) {

        // Business Logic
        // 0. 송금 요청 상태를 시작 상태로 기록 (persistence layer)
        final RemittanceRequestJpaEntity entity = requestRemittancePort.createRemittanceRequestHistory(command);

        // 1. from 멤버십 상태 확인 (membership svc call)
        final MembershipStatus membershipStatus = membershipPort.getMembershipStatus(command.getFromMembershipId());

//        if (!membershipStatus.isValid()) {
//            return null;
//        }
        if (!membershipStatus.isValid()) {
            return null;
        }

        // 2. 잔액 있는 지 확인 (money svc call)
        final MoneyInfo moneyInfo = moneyPort.getMoneyInfo(command.getFromMembershipId());

        // 2-1 잔액이 충분하지 않으면, 충전 요청 (money svc call)
        if (moneyInfo.getBalance() < command.getAmount()) {
            int needChargeMoney = command.getAmount() - moneyInfo.getBalance();
            if (needChargeMoney < 10000) {
                needChargeMoney = 10000;
            }
            final int rechargeAmount = (int) (Math.ceil(needChargeMoney / 10000) * 10000);
            log.info("needChargeMoney : {}", needChargeMoney);
            log.info("rechargeAmount : {}", rechargeAmount);

            final boolean resultChargeMoney = moneyPort.requestMoneyRecharging(command.getFromMembershipId(), rechargeAmount);
            if (!resultChargeMoney) {
                return null;
            }
        }

        // 3. 송금 타입(고객/은행)
        // 3-1 내부 고객일 경우
        if (command.getRemittanceType() == 0) {
            // from 고객 머니 감액,
            final boolean fromRemittanceResult = moneyPort.requestMoneyDecrease(command.getFromMembershipId(), command.getAmount());

            // to 고객 머니 증액 (money svc call)
            final boolean toRemittanceResult = moneyPort.requestMoneyIncrease(command.getToMembershipId(), command.getAmount());

            if (!fromRemittanceResult || !toRemittanceResult) {
                return null;
            }

        } else if (command.getRemittanceType() == 1 ){ // 3-2 외부 은행 계좌
            // 외부 은행 계좌가 적절한 지 확인 (bank svc call)
            // 법인계좌 -> 외부 은행 계좌 펌뱅킹 요청 (bank svc call)
            final boolean remittanceResult = bankingPort.requestFirmbanking(command.getToBankName(), command.getToBankAccountNumber(), command.getAmount());
            if (!remittanceResult) {
                return null;
            }
        }

        // 4. 송금 요청 상태를 성공 상태로 기록 (persistence layer)
        entity.setRemittanceStatus("success");
        final boolean result = requestRemittancePort.saveRemittanceRequestHistory(entity);
        if (result) {
            return mapper.mapToDomainEntity(entity);
        }

        // 4. 송금 기록 (peremittance layer) // 송금이 완료된 기록.

        // 4. 송금 요청 생성 (remittance svc call)

        // 5. 송금 요청 상태로 변경 (remittance svc call)

        // 6. 송금 요청 리턴 (remittance svc call)


//        membershipPort.getMembershipStatus(command.getFromMembershipId());

        return null;
    }
}
