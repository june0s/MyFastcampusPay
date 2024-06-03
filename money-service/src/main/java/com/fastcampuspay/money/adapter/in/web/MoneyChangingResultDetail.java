package com.fastcampuspay.money.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoneyChangingResultDetail {

    // 증액, 감액 요청
    private String moneyChangingRequestId;

    private int moneyChangingType;
    private int moneyChangingResultStatus;

    private int amount;
}

enum MoneyChangingType {
    INCREASING, // 증액
    DECREASING, // 감액.
}

enum MoneyChangingResultStatus {
    SUCCESS, // 성공.
    FAILED, // 실패.
    FAILED_NOT_ENOUGH_MONEY, // 실패 - 잔액 부족
    FAILED_NOT_EXIST_MEMBERSHIP, // 실패 - 멤버십 없음
    FAILED_NOT_EXIST_MONEY_CHANGING_REQUEST, // 실패 - 머니 증액, 감액 요청 없음
    FAILED_NOT_EXIST_BANK_ACCOUNT, // 실패 - 은행 계좌 없음
}
