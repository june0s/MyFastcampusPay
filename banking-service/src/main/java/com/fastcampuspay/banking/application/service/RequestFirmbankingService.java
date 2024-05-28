package com.fastcampuspay.banking.application.service;

import com.fastcampuspay.banking.adapter.out.external.bank.BankAccount;
import com.fastcampuspay.banking.adapter.out.external.bank.ExternalFirmbankingRequest;
import com.fastcampuspay.banking.adapter.out.external.bank.FirmBankingResult;
import com.fastcampuspay.banking.adapter.out.external.bank.GetBankAccountRequest;
import com.fastcampuspay.banking.adapter.out.persistence.FirmbankingRequestJpaEntity;
import com.fastcampuspay.banking.adapter.out.persistence.FirmbankingRequestMapper;
import com.fastcampuspay.banking.adapter.out.persistence.RegisterBankAccountJpaEntity;
import com.fastcampuspay.banking.adapter.out.persistence.RegisterBankAccountMapper;
import com.fastcampuspay.banking.application.port.in.FirmbankingRequestCommand;
import com.fastcampuspay.banking.application.port.in.RegisterBankAccountUseCase;
import com.fastcampuspay.banking.application.port.in.RequestFirmbankingCommand;
import com.fastcampuspay.banking.application.port.in.RequestFirmbankingUseCase;
import com.fastcampuspay.banking.application.port.out.RegisterBankAccountPort;
import com.fastcampuspay.banking.application.port.out.RequestBankAccountInfoPort;
import com.fastcampuspay.banking.application.port.out.RequestExternalFirmbankingPort;
import com.fastcampuspay.banking.application.port.out.RequestFirmbankingPort;
import com.fastcampuspay.banking.domain.FirmbankingRequest;
import com.fastcampuspay.banking.domain.RegisteredBankAccount;
import com.fastcampuspay.common.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
@Transactional
public class RequestFirmbankingService implements RequestFirmbankingUseCase {

    private final RequestFirmbankingPort requestFirmbankingPort;
    private final FirmbankingRequestMapper mapper;
    private final RequestExternalFirmbankingPort requestExternalFirmbankingPort;

    @Override
    public FirmbankingRequest requestFirmbanking(FirmbankingRequestCommand command) {

        // Business logic
        // a -> b 계좌로 돈 보내기.
        // 1. 요청에 대해 정보 먼저 write, "요청" 상태로
        FirmbankingRequestJpaEntity requestedEntity = requestFirmbankingPort.createFirmbankingRequest(
                new FirmbankingRequest.FromBankName(command.getFromBankName()),
                new FirmbankingRequest.FromBankAccountNumber(command.getFromBankAccountNumber()),
                new FirmbankingRequest.ToBankName(command.getToBankName()),
                new FirmbankingRequest.ToBankAccountNumber(command.getToBankAccountNumber()),
                new FirmbankingRequest.MoneyAccount(command.getMoneyAmount()),
                new FirmbankingRequest.FirmbankingStatus(0)
        );

        // 2. 외부 은행에 펌뱅킹 요청
        FirmBankingResult result = requestExternalFirmbankingPort.requestFirmbanking(new ExternalFirmbankingRequest(
                command.getFromBankName(),
                command.getFromBankAccountNumber(),
                command.getToBankName(),
                command.getToBankAccountNumber())
        );

        // Transactional UUID
        UUID randomUUID = UUID.randomUUID();
        requestedEntity.setUuid(randomUUID.toString());

        // 3. 결과에 따라 1번에서 작성했던 FirmbankingRequest 정보를 update
        if (result.getResultCode() == 0) {
            // 성공
            requestedEntity.setFirmbankingStatus(1);
        } else {
            // 실패
            requestedEntity.setFirmbankingStatus(2);
        }

        // 4. 결과를 리턴하기 전에 바뀐 상태 값을 기준으로 다시 save
        FirmbankingRequestJpaEntity jpaEntity = requestFirmbankingPort.modifyFirmbankingRequest(requestedEntity);

        return mapper.mapToDomainEntity(jpaEntity, randomUUID);
    }
}
