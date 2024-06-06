package com.fastcampuspay.remittance.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Remittance { // 송금이 완료된 건에 대한 정보 클래스.
    @Getter
    private final String remittanceId;
    @Getter private final String remittanceFromMembershipId;
    @Getter private final String toBankName;
    @Getter private final String toBankAccountNumber;
    @Getter private final int remittanceType; // 0: membership 계좌(내부 고객), 1: bank 계좌(외부 은행 계좌)
    @Getter private final int amount; // 송금 요청 금액.
    @Getter private final String status;

    @Value
    public static class RemittanceId {
        public RemittanceId(String value) {
            this.remittanceId = value;
        }

        String remittanceId;
    }
    @Value
    public static class RemittanceFromMembershipId {
        public RemittanceFromMembershipId(String value) {
            this.remittanceFromMembershipId = value;
        }
        String remittanceFromMembershipId;
    }
    @Value
    public static class ToBankName {
        public ToBankName(String value) {
            this.toBankName = value;
        }
        String toBankName;
    }
    @Value
    public static class ToBankAccountNumber {
        public ToBankAccountNumber(String value) {
            this.toBankAccountNumber = value;
        }
        String toBankAccountNumber;
    }
    @Value
    public static class RemittanceType {
        public RemittanceType(int value) {
            this.remittanceType = value;
        }
        int remittanceType;
    }
    @Value
    public static class Amount {
        public Amount(int value) {
            this.amount = value;
        }
        int amount;
    }
    @Value
    public static class Status {
        public Status(String value) {
            this.status = value;
        }
        String status;
    }
    public static Remittance generateRemittance(
            RemittanceId remittanceId,
            RemittanceFromMembershipId remittanceFromMembershipId,
            ToBankName toBankName,
            ToBankAccountNumber toBankAccountNumber,
            RemittanceType remittanceType,
            Amount amount,
            Status status) {
        return new Remittance(
                remittanceId.getRemittanceId(),
                remittanceFromMembershipId.getRemittanceFromMembershipId(),
                toBankName.getToBankName(),
                toBankAccountNumber.getToBankAccountNumber(),
                remittanceType.getRemittanceType(),
                amount.getAmount(),
                status.getStatus());
    }
}
