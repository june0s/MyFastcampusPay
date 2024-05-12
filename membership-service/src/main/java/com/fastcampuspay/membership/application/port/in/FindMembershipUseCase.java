package com.fastcampuspay.membership.application.port.in;

import com.fastcampuspay.membership.domain.Membership;

// 외부에서 들어오는(in) 요청.
public interface FindMembershipUseCase {

    Membership findMembership(FindMembershipCommand command);
}
