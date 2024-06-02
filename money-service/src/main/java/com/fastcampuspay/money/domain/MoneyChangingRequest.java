package com.fastcampuspay.money.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.util.Date;
import java.util.UUID;

// 접근 제어자를 PRIVATE 으로 설정. 클래스 내의 해당 멤버를 수정할 수 있다 - 아키텍처 적으로 좀 더 clean 하다고 함.
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MoneyChangingRequest {

    @Getter private final String moneyChangingRequestId;
    // 어떤 고객의 증액/감액 요청 했는 지에 대한 멤버 정보.
    @Getter private final String targetMembershipId;
    // 그 요청이 증액 요청인지 / 감액 요청인지
    @Getter private final int moneyChangingType; // todo: enum 0: 증액, 1: 감액.
    public enum ChangingType {
        INCREASING, // 증액
        DECREASING, // 감액.
    }
    // 증액 또는 감액 금액.
    @Getter private final int changingMoneyAmount;
    // 머니 변경 요청에 때한 상태.
    @Getter private final int moneyChangingStatus; // enum

    enum ChangingMoneyStatus {
        REQUESTED, // 요청됨.
        SUCCESS, // 성공.
        FAILED, // 실패.
        CANCELLED, // 취소됨
    }
    @Getter private final String uuid;
    @Getter private final Date createdAt;


    // Membership 클래스는 오염이 되면 안되는 클래스. 고객 정보. 핵심 도메인. 안전하게 관리해야 한다.
    // -> 멤버 변수 를 private final 로 설정하고, 클래스 접근 제어자를 PRIVATE 으로 설정. 새롭게 클래스 생성하는 것이 어려워진다.

    // membership 클래스는 generateMember 를 사용해, static 클래스의 membershipId 를 통해 membership 을 생성할 수도 있다.
    public static MoneyChangingRequest generateMoneyChangingRequest(
            MoneyChangingRequestId moneyChangingRequestId
            , TargetMembershipId targetMembershipId
            , MoneyChangingType moneyChangingType
            , ChangingMoneyAmount changingMoneyAmount
            , MoneyChangingStatus moneyChangingStatus
            , Uuid uuid
    ) {
        return new MoneyChangingRequest(
                moneyChangingRequestId.moneyChangingRequestId
                , targetMembershipId.targetMembershipId
                , moneyChangingType.getChangingType()
                , changingMoneyAmount.changingMoneyAmount
                , moneyChangingStatus.changingMoneyStatus
                , uuid.uuid
                , new Date()
        );
    }

    // 각각의 멤버 변수를 value 로 관리할 수 있도록 static class 를 선언한다.
    // Membership 클래스에 직접 접근은 불가능하지만 MembershipId 라는 static 클래스를 통해서 membershipId 에 값을 설정할 수 있다.
    // 안전하게 사용하고 값을 보호할 수 있다고 함...
    @Value
    public static class MoneyChangingRequestId {
        public MoneyChangingRequestId(String value) {
            this.moneyChangingRequestId = value;
        }
        String moneyChangingRequestId;
    }
    @Value
    public static class TargetMembershipId {
        public TargetMembershipId(String value) {
            this.targetMembershipId = value;
        }
        String targetMembershipId;
    }
    @Value
    public static class MoneyChangingType {
        public MoneyChangingType(int value) {
            this.changingType = value;
        }
        int changingType;
    }
    @Value
    public static class ChangingMoneyAmount {
        public ChangingMoneyAmount(int value) {
            this.changingMoneyAmount = value;
        }
        int changingMoneyAmount;
    }
    @Value
    public static class MoneyChangingStatus {
        public MoneyChangingStatus(int value) {
            this.changingMoneyStatus = value;
        }
        int changingMoneyStatus;
    }
    @Value
    public static class Uuid {
        public Uuid(UUID value) {
            this.uuid = value.toString();
        }
        String uuid;
    }
}
