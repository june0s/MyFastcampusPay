package com.fastcampuspay.money.application.port.in;

import com.fastcampuspay.money.domain.MemberMoney;

public interface CreateMemberMoneyPort {
    void createMemberMoney(MemberMoney.MembershipId membershipId, MemberMoney.MoneyAggregateIdentifier identifier);
}
