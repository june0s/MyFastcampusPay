package com.fastcampuspay.remittance.adapter.out.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "request_remittance")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemittanceRequestJpaEntity {
    @Id
    @GeneratedValue
    private Long remittanceRequestId;

    private String remittanceFromMembershipId;
    private String toBankName;
    private String toBankAccountNumber;
    private int remittanceType; // 0: membership 계좌(내부 고객), 1: bank 계좌(외부 은행 계좌)
    private int amount; // 송금 요청 금액.
}
