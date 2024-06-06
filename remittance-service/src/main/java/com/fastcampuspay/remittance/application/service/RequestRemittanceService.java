package com.fastcampuspay.remittance.application.service;

import com.fastcampuspay.common.UseCase;
import com.fastcampuspay.remittance.adapter.out.persistence.RemittanceRequestMapper;
import com.fastcampuspay.remittance.application.port.in.RequestRemittanceCommand;
import com.fastcampuspay.remittance.application.port.in.RequestRemittanceUseCase;
import com.fastcampuspay.remittance.application.port.out.RequestRemittancePort;
import com.fastcampuspay.remittance.application.port.out.membership.MembershipPort;
import com.fastcampuspay.remittance.application.port.out.money.MoneyPort;
import com.fastcampuspay.remittance.domain.RemittanceRequest;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
public class RequestRemittanceService implements RequestRemittanceUseCase {

    private final RequestRemittancePort requestRemittancePort;
    private final RemittanceRequestMapper mapper;
    private final MembershipPort membershipPort;
    private final MoneyPort moneyPort;

    @Override
    public RemittanceRequest requestRemittance(RequestRemittanceCommand command) {

        // Business Logic
        // 0. 송금 요청 상태를 시작 상태로 기록 (persistence layer)

        // 1. from 멤버십 상태 확인 (membership svc call)

        // 2. 잔액 있는 지 확인 (money svc call)

        // 2-1 잔액이 충분하지 않으면, 충전 요청 (money svc call)

        // 3. 송금 타입(고객/은행)
        // 3-1 내부 고객일 경우
        // from 고객 머니 감액, to 고객 머니 증액 (money svc call)

        // 3-2 외부 은행 계좌
        // 외부 은행 계좌가 적절한 지 확인 (bank svc call)
        // 법인계좌 -> 외부 은행 계좌 펌뱅킹 요청 (bank svc call)

        // 4. 송금 요청 상태를 성공 상태로 기록 (persistence layer)
        // 4. 송금 기록 (peremittance layer) // 송금이 완료된 기록.

        // 4. 송금 요청 생성 (remittance svc call)

        // 5. 송금 요청 상태로 변경 (remittance svc call)

        // 6. 송금 요청 리턴 (remittance svc call)


        membershipPort.getMembershipStatus(command.getFromMembershipId());

        return null;
    }
}
