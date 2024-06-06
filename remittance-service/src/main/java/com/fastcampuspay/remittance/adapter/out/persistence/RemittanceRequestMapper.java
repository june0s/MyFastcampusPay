package com.fastcampuspay.remittance.adapter.out.persistence;

import com.fastcampuspay.remittance.domain.RemittanceRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RemittanceRequestMapper {
    public RemittanceRequest mapToDomainEntity(RemittanceRequestJpaEntity remittanceRequestJpaEntity, UUID uuid) {
        return RemittanceRequest.generateRemittanceRequest(
                new RemittanceRequest.RemittanceRequestId(remittanceRequestJpaEntity.getRemittanceRequestId() + ""),
                new RemittanceRequest.RemittanceFromMembershipId(remittanceRequestJpaEntity.getRemittanceFromMembershipId()),
                new RemittanceRequest.ToBankName(remittanceRequestJpaEntity.getToBankName()),
                new RemittanceRequest.ToBankAccountNumber(remittanceRequestJpaEntity.getToBankAccountNumber()),
                new RemittanceRequest.RemittanceType(remittanceRequestJpaEntity.getRemittanceType()),
                new RemittanceRequest.Amount(remittanceRequestJpaEntity.getAmount())
        );
    }
}
