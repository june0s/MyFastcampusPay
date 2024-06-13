package com.fastcampuspay.money.adapter.axon.command;

import com.fastcampuspay.common.SelfValidating;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.constraints.NotNull;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class IncreaseMemberMoneyCommand extends SelfValidating<IncreaseMemberMoneyCommand> {

    @NotNull
    @TargetAggregateIdentifier
    private String aggregateIdentifier;

    @NotNull
    private String membershipId;

    @NotNull
    private final int amount;


//    public IncreaseMemberMoneyCommand(String membershipId) {
//        this.membershipId = membershipId;
//
//        this.validateSelf(); // 이 메소드가 name 의 @NotNull 검사를 해준다. 통과 안 되면, exception 발생시키고 그 이유를 알려준다.
//    }
//
//    public IncreaseMemberMoneyCommand() {
//    }
}

