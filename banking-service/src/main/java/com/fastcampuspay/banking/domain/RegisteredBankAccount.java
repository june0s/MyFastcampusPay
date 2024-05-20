package com.fastcampuspay.banking.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

// 접근 제어자를 PRIVATE 으로 설정. 클래스 내의 해당 멤버를 수정할 수 있다 - 아키텍처 적으로 좀 더 clean 하다고 함.
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RegisteredBankAccount {

    @Getter private final String registeredBankAccountId;
    @Getter private final String membershipId;
    @Getter private final String bankName; // 은행명 -> enum
    @Getter private final String bankAccountNumber;
    @Getter private final boolean linkedStatusIsValid; // 연결 상태 정상 인지

    // Membership 클래스는 오염이 되면 안되는 클래스. 고객 정보. 핵심 도메인. 안전하게 관리해야 한다.
    // -> 멤버 변수 를 private final 로 설정하고, 클래스 접근 제어자를 PRIVATE 으로 설정. 새롭게 클래스 생성하는 것이 어려워진다.

    // membership 클래스는 generateMember 를 사용해, static 클래스의 membershipId 를 통해 membership 을 생성할 수도 있다.
    public static RegisteredBankAccount generateRegisteredBankAccount(RegisteredBankAccountId registeredBankAccountId
            , MembershipId membershipId
            , BankName bankName
            , BankAccountNumber bankAccountNumber
            , LinkedStatusIsValid linkedStatusIsValid) {
        return new RegisteredBankAccount(
                registeredBankAccountId.registeredBankAccountId
                , membershipId.membershipId
                , bankName.bankName
                , bankAccountNumber.bankAccountNumber
                , linkedStatusIsValid.linkedStatusIsValid
        );
    }

    // 각각의 멤버 변수를 value 로 관리할 수 있도록 static class 를 선언한다.
    // Membership 클래스에 직접 접근은 불가능하지만 MembershipId 라는 static 클래스를 통해서 membershipId 에 값을 설정할 수 있다.
    // 안전하게 사용하고 값을 보호할 수 있다고 함...
    @Value
    public static class RegisteredBankAccountId {
        public RegisteredBankAccountId(String value) {
            this.registeredBankAccountId = value;
        }
        String registeredBankAccountId;
    }

    @Value
    public static class MembershipId {
        public MembershipId(String value) {
            this.membershipId = value;
        }
        String membershipId;
    }
    @Value
    public static class BankName {
        public BankName(String value) {
            this.bankName = value;
        }
        String bankName;
    }
    @Value
    public static class BankAccountNumber {
        public BankAccountNumber(String value) {
            this.bankAccountNumber = value;
        }
        String bankAccountNumber;
    }
    @Value
    public static class LinkedStatusIsValid {
        public LinkedStatusIsValid(boolean value) {
            this.linkedStatusIsValid = value;
        }
        boolean linkedStatusIsValid;
    }
}
