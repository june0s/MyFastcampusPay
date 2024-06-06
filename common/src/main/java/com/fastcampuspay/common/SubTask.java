package com.fastcampuspay.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubTask {
    private String membershipId;
    private String taskName;
    private String taskType; // "banking", "membership"
    private String status; // "success", "fail", "ready"
}
