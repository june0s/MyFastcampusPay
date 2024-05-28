package com.fastcampuspay.banking.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.util.UUID;

// 접근 제어자를 PRIVATE 으로 설정. 클래스 내의 해당 멤버를 수정할 수 있다 - 아키텍처 적으로 좀 더 clean 하다고 함.
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FirmbankingRequest {

    @Getter private final String firmbankingRequestId;
    @Getter private final String fromBankName;
    @Getter private final String fromBankAccountNumber;
    @Getter private final String toBankName;
    @Getter private final String toBankAccountNumber;
    @Getter private final int moneyAccount;
    @Getter private final int firmbankingStatus; // 0: 요청, 1: 완료, 2: 실패
    @Getter private final UUID uuid;

    // FirmbankingRequest 클래스는 오염이 되면 안되는 클래스. 고객 정보. 핵심 도메인. 안전하게 관리해야 한다.
    // -> 멤버 변수 를 private final 로 설정하고, 클래스 접근 제어자를 PRIVATE 으로 설정. 새롭게 클래스 생성하는 것이 어려워진다.
    public static FirmbankingRequest generateFirmbankingRequest(
            FirmbankingRequestId firmbankingRequestId
            , FromBankName fromBankName
            , FromBankAccountNumber fromBankAccountNumber
            , ToBankName toBankName
            , ToBankAccountNumber toBankAccountNumber
            , MoneyAccount moneyAccount
            , FirmbankingStatus firmbankingStatus
            , UUID uuid
    ) {
        return new FirmbankingRequest(
                firmbankingRequestId.firmbankingRequestId
                , fromBankName.fromBankName
                , fromBankAccountNumber.fromBankAccountNumber
                , toBankName.toBankName
                , toBankAccountNumber.toBankAccountNumber
                , moneyAccount.moneyAccount
                , firmbankingStatus.firmbankingStatus
                , uuid
        );
    }

    // 각각의 멤버 변수를 value 로 관리할 수 있도록 static class 를 선언한다.
    // Membership 클래스에 직접 접근은 불가능하지만 MembershipId 라는 static 클래스를 통해서 membershipId 에 값을 설정할 수 있다.
    // 안전하게 사용하고 값을 보호할 수 있다고 함...
    @Value
    public static class FirmbankingRequestId {
        public FirmbankingRequestId(String value) {
            this.firmbankingRequestId = value;
        }
        String firmbankingRequestId;
    }
    @Value
    public static class FromBankName {
        public FromBankName(String value) {
            this.fromBankName = value;
        }
        String fromBankName;
    }
    @Value
    public static class FromBankAccountNumber {
        public FromBankAccountNumber(String value) {
            this.fromBankAccountNumber = value;
        }
        String fromBankAccountNumber;
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
    public static class MoneyAccount {
        public MoneyAccount(int value) {
            this.moneyAccount = value;
        }
        int moneyAccount;
    }
    @Value
    public static class FirmbankingStatus {
        public FirmbankingStatus(int value) {
            this.firmbankingStatus = value;
        }
        int firmbankingStatus;
    }
}
