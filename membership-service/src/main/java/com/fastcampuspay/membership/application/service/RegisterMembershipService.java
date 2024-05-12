package com.fastcampuspay.membership.application.service;

import com.fastcampuspay.membership.adapter.out.persistence.MembershipJpaEntity;
import com.fastcampuspay.membership.adapter.out.persistence.MembershipMapper;
import com.fastcampuspay.membership.application.port.in.RegisterMembershipCommand;
import com.fastcampuspay.membership.application.port.in.RegisterMembershipUseCase;
import com.fastcampuspay.membership.application.port.out.RegisterMembershipPort;
import com.fastcampuspay.membership.domain.Membership;
import common.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
public class RegisterMembershipService implements RegisterMembershipUseCase {

    private final RegisterMembershipPort registerMembershipPort;
    private final MembershipMapper membershipMapper;

    @Override
    public Membership registerMembership(RegisterMembershipCommand command) {
        // command -> DB 와 통신하고 결과 return
        final MembershipJpaEntity jpaEntity = registerMembershipPort.createMembership(
                new Membership.MembershipName(command.getName())
                , new Membership.MembershipEmail(command.getEmail())
                , new Membership.MembershipAddress(command.getAddress())
                , new Membership.MembershipIsValid(command.isValid())
                , new Membership.MembershipIsCorp(command.isCorp())
        );

        // biz logic -> DB
        // biz logic 입장에서는 external system 이다. port, adapter 를 통해서 나가야 한다.
        // entity -> Membership 객체 변환해야 한다. 편의를 위해 mapper 객체를 통해 변환하자.

        return membershipMapper.mapToDomainEntity(jpaEntity);
    }
}
