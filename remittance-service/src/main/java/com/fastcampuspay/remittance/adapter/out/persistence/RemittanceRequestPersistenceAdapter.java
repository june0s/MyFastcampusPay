package com.fastcampuspay.remittance.adapter.out.persistence;

import com.fastcampuspay.common.PersistenceAdapter;
import com.fastcampuspay.remittance.application.port.in.FindRemittanceCommand;
import com.fastcampuspay.remittance.application.port.in.RequestRemittanceCommand;
import com.fastcampuspay.remittance.application.port.out.FindRemittancePort;
import com.fastcampuspay.remittance.application.port.out.RequestRemittancePort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@PersistenceAdapter
@RequiredArgsConstructor
public class RemittanceRequestPersistenceAdapter implements RequestRemittancePort, FindRemittancePort {

    private final SpringDataRemittanceRequestRepository remittanceRequestRepository;

    @Override
    public RemittanceRequestJpaEntity createRemittanceRequestHistory(RequestRemittanceCommand command) {
        return remittanceRequestRepository.save(
                RemittanceRequestJpaEntity.builder()
                       .fromMembershipId(command.getFromMembershipId())
                       .toMembershipId(command.getToMembershipId())
                       .toBankName(command.getToBankName())
                       .toBankAccountNumber(command.getToBankAccountNumber())
                       .remittanceType(command.getRemittanceType())
                       .amount(command.getAmount())
//                       .remittanceStatus("start")
                       .build()
        );
    }

    @Override
    public boolean saveRemittanceRequestHistory(RemittanceRequestJpaEntity entity) {
        remittanceRequestRepository.save(entity);
        return true;
    }

    @Override
    public List<RemittanceRequestJpaEntity> findRemittanceHistory(FindRemittanceCommand command) {
        return remittanceRequestRepository.findAllByFromMembershipId(command.getMembershipId());
    }
}
