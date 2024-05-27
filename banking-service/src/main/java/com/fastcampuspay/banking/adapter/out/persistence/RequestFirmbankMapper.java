package com.fastcampuspay.banking.adapter.out.persistence;

import com.fastcampuspay.banking.adapter.in.web.RequestFirmbankingRequest;
import com.fastcampuspay.banking.domain.FirmbankingRequest;
import com.fastcampuspay.banking.domain.RegisteredBankAccount;
import org.springframework.stereotype.Component;

@Component
public class RequestFirmbankMapper {
    public FirmbankingRequest mapToDomainEntity(RequestFirmbankingJpaEntity requestFirmbankingJpaEntity) {
        return FirmbankingRequest.generateFirmbankingRequest(
                new FirmbankingRequest.FirmbankingRequestId(requestFirmbankingJpaEntity.getRequestFirmbankingId() + "")
                , new FirmbankingRequest.FromBankName(requestFirmbankingJpaEntity.getFromBankName())
                , new FirmbankingRequest.FromBankAccountNumber(requestFirmbankingJpaEntity.getFromBankAccountNumber())
                , new FirmbankingRequest.ToBankName(requestFirmbankingJpaEntity.getToBankName())
                , new FirmbankingRequest.ToBankAccountNumber(requestFirmbankingJpaEntity.getToBankAccountNumber())
                , new FirmbankingRequest.MoneyAccount(requestFirmbankingJpaEntity.getMoneyAmount())
                , new FirmbankingRequest.FirmbankingStatus(requestFirmbankingJpaEntity.getFirmbankingStatus())
        );
    }
}
