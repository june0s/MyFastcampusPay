package com.fastcampuspay.membership.application.port.in;

import com.fastcampuspay.membership.domain.Membership;
import common.UseCase;

// 외부에서 들어오는(in) 요청.
//@UseCase
public interface RegisterMembershipUseCase {

    Membership registerMembership(RegisterMembershipCommand command);
}
