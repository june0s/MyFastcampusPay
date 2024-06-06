package com.fastcampuspay.money.application.port.in;

import com.fastcampuspay.money.domain.MoneyChangingRequest;

// 외부에서 들어오는(in) 요청.
//@UseCase
public interface IncreaseMoneyRequestUseCase {

    MoneyChangingRequest increaseMoneyRequest(IncreaseMoneyRequestCommand command);
    MoneyChangingRequest increaseMoneyRequestAsync(IncreaseMoneyRequestCommand command);
}
