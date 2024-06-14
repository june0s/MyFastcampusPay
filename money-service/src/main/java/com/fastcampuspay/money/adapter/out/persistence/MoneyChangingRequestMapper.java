package com.fastcampuspay.money.adapter.out.persistence;

import com.fastcampuspay.money.domain.MoneyChangingRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MoneyChangingRequestMapper {
    public MoneyChangingRequest mapToDomainEntity(MoneyChangingRequestJpaEntity moneyChangingRequestJpaEntity) {
        return MoneyChangingRequest.generateMoneyChangingRequest(
                new MoneyChangingRequest.MoneyChangingRequestId(moneyChangingRequestJpaEntity.getMoneyChangingRequestId()+"")
                , new MoneyChangingRequest.TargetMembershipId(moneyChangingRequestJpaEntity.getTargetMembershipId())
                , new MoneyChangingRequest.MoneyChangingType(moneyChangingRequestJpaEntity.getMoneyChangingType())
                , new MoneyChangingRequest.ChangingMoneyAmount(moneyChangingRequestJpaEntity.getMoneyAmount())
                , new MoneyChangingRequest.MoneyChangingStatus(moneyChangingRequestJpaEntity.getChagingMoneyStatus())
                , new MoneyChangingRequest.Uuid(moneyChangingRequestJpaEntity.getUuid())
        );
    }

//    public MoneyChangingRequest mapTo(MemberMoneyJpaEntity entity) {
//        return MoneyChangingRequest.generateMoneyChangingRequest(
//          new MoneyChangingRequest.MoneyChangingRequestId(entity.getAggregateIdentifier()+"")
//          , new MoneyChangingRequest.TargetMembershipId(String.valueOf(entity.getMembershipId())
//          , new MoneyChangingRequest.MoneyChangingType(0)
//          , new MoneyChangingRequest.ChangingMoneyAmount(entity.getBalance())
//          , new MoneyChangingRequest.MoneyChangingStatus(1)
//          , new MoneyChangingRequest.Uuid(UUID.randomUUID())
//        );
//    }
}
