package com.fastcampuspay.remittance.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestRemittanceRequest {
    private String fromMembershipId; // 송금인
    private String toMembershipId; // 수취인
    private String toBankName;
    private String toBankAccountNumber;
    private int remittanceType; // 0: membership 계좌(내부 고객), 1: bank 계좌(외부 은행 계좌)
    private int amount; // 송금 요청 금액.
}
