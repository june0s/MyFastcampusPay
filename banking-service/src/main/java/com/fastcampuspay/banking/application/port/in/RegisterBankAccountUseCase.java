package com.fastcampuspay.banking.application.port.in;

import com.fastcampuspay.banking.domain.RegisteredBankAccount;

// 외부에서 들어오는(in) 요청.
//@UseCase
public interface RegisterBankAccountUseCase {

    RegisteredBankAccount registerBankAccount(RequestFirmbankingCommand command);
}
