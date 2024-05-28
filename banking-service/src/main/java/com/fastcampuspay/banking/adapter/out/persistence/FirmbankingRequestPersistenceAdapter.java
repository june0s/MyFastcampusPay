package com.fastcampuspay.banking.adapter.out.persistence;

import com.fastcampuspay.banking.application.port.out.RequestFirmbankingPort;
import com.fastcampuspay.banking.domain.FirmbankingRequest;
import com.fastcampuspay.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@PersistenceAdapter
@RequiredArgsConstructor
public class FirmbankingRequestPersistenceAdapter implements RequestFirmbankingPort {

    private final SpringDataFirmbankingRequestRepository firmbankingRequestRepository;

    @Override
    public FirmbankingRequestJpaEntity createFirmbankingRequest(FirmbankingRequest.FromBankName fromBankName, FirmbankingRequest.FromBankAccountNumber fromBankAccountNumber, FirmbankingRequest.ToBankName toBankName, FirmbankingRequest.ToBankAccountNumber toBankAccountNumber, FirmbankingRequest.MoneyAccount moneyAccount, FirmbankingRequest.FirmbankingStatus firmbankingStatus) {
        return firmbankingRequestRepository.save(new FirmbankingRequestJpaEntity(
                        fromBankName.getFromBankName()
                        , fromBankAccountNumber.getFromBankAccountNumber()
                        , toBankName.getToBankName()
                        , toBankAccountNumber.getToBankAccountNumber()
                        , moneyAccount.getMoneyAccount()
                        , firmbankingStatus.getFirmbankingStatus()
                        , UUID.randomUUID()
                )
        );
    }

    @Override
    public FirmbankingRequestJpaEntity modifyFirmbankingRequest(FirmbankingRequestJpaEntity entity) {
        return firmbankingRequestRepository.save(entity);
    }
}
