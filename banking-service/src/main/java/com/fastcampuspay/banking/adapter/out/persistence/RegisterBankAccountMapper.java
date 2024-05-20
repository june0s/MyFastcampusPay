package com.fastcampuspay.banking.adapter.out.persistence;

import com.fastcampuspay.banking.domain.RegisteredBankAccount;
import org.springframework.stereotype.Component;

@Component
public class RegisterBankAccountMapper {
    public RegisteredBankAccount mapToDomainEntity(RegisterBankAccountJpaEntity registerBankAccountJpaEntity) {
        return RegisteredBankAccount.generateRegisteredBankAccount(
                new RegisteredBankAccount.RegisteredBankAccountId(registerBankAccountJpaEntity.getRegisteredBankAccountId()+"")
                , new RegisteredBankAccount.MembershipId(registerBankAccountJpaEntity.getMembershipId())
                , new RegisteredBankAccount.BankName(registerBankAccountJpaEntity.getBankName())
                , new RegisteredBankAccount.BankAccountNumber(registerBankAccountJpaEntity.getBankAccountNumber())
                , new RegisteredBankAccount.LinkedStatusIsValid(registerBankAccountJpaEntity.isLinkedStatusIsValid())
        );
    }
}
