package com.fastcampuspay.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RechargingMoneyTask { // increase money 머니 충전을 위한 task
    private String taskId;
    private String taskName;
    private String membershipId;
    private List<SubTask> subTaskList;
    private String toBankName; // 법인 계좌
    private String toBankAccountNumber; // 법인 계좌 번호.
    private int moneyAmount; // won
}
