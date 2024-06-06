package com.fastcampuspay.remittance.adapter.out.persistence;

import com.fastcampuspay.remittance.domain.Remittance;
import com.fastcampuspay.remittance.domain.RemittanceRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RemittanceMapper {
    public Remittance mapToDomainEntity(RemittanceJpaEntity remittanceJpaEntity, UUID uuid) {
//        return Remittance.generateRemittance(
//
//        );
        return null;
    }
}
