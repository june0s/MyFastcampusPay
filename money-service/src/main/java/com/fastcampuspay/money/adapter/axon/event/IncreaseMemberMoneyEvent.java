package com.fastcampuspay.money.adapter.axon.event;

import com.fastcampuspay.common.SelfValidating;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class IncreaseMemberMoneyEvent extends SelfValidating<IncreaseMemberMoneyEvent> {

    private String aggregateIdentifier;
    private String targetMembershipId;
    private int amount;

    public IncreaseMemberMoneyEvent(String aggregateIdentifier, String targetMembershipId, int amount) {
        this.aggregateIdentifier = aggregateIdentifier;
        this.targetMembershipId = targetMembershipId;
        this.amount = amount;

        this.validateSelf(); // 이 메소드가 name 의 @NotNull 검사를 해준다. 통과 안 되면, exception 발생시키고 그 이유를 알려준다.
    }

    public IncreaseMemberMoneyEvent() {
    }
}
