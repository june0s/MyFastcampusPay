package com.fastcampuspay.membership.adapter.out.persistence;

import com.fastcampuspay.common.PersistenceAdapter;
import com.fastcampuspay.membership.application.port.out.FindMembershipPort;
import com.fastcampuspay.membership.application.port.out.ModifyMembershipPort;
import com.fastcampuspay.membership.application.port.out.RegisterMembershipPort;
import com.fastcampuspay.membership.domain.Membership;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class MembershipPersistenceAdapter implements RegisterMembershipPort, FindMembershipPort, ModifyMembershipPort {

    private final SpringDataMembershipRepository membershipRepository;

    @Override
    public MembershipJpaEntity createMembership(Membership.MembershipName membershipName, Membership.MembershipEmail membershipEmail, Membership.MembershipAddress membershipAddress, Membership.MembershipIsValid membershipIsValid, Membership.MembershipIsCorp membershipIsCorp) {
        // adapter 가 db 와 어떻게 연동 되는 지 정의한다.
        return membershipRepository.save(
                new MembershipJpaEntity(
                        membershipName.getMembershipName()
                        , membershipAddress.getMembershipAddress()
                        , membershipEmail.getMembershipEmail()
                        , membershipIsValid.isMembershipIsValid()
                        , membershipIsCorp.isMembershipIsCorp()));
    }

    @Override
    public MembershipJpaEntity findMembership(Membership.MembershipId membershipId) {
        return membershipRepository.getById(Long.parseLong(membershipId.getMembershipId()));
    }

    @Override
    public MembershipJpaEntity modifyMembership(Membership.MembershipId membershipId, Membership.MembershipName membershipName, Membership.MembershipEmail membershipEmail, Membership.MembershipAddress membershipAddress, Membership.MembershipIsValid membershipIsValid, Membership.MembershipIsCorp membershipIsCorp) {
        MembershipJpaEntity entity = membershipRepository.getById(Long.parseLong(membershipId.getMembershipId()));
        entity.setName(membershipName.getMembershipName());
        entity.setAddress(membershipAddress.getMembershipAddress());
        entity.setEmail(membershipEmail.getMembershipEmail());
        entity.setValid(membershipIsValid.isMembershipIsValid());
        entity.setCorp(membershipIsCorp.isMembershipIsCorp());

        return membershipRepository.save(entity);
    }
}
