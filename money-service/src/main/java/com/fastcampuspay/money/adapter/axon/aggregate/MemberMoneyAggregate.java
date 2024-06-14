package com.fastcampuspay.money.adapter.axon.aggregate;

import com.fastcampuspay.money.adapter.axon.command.IncreaseMemberMoneyCommand;
import com.fastcampuspay.money.adapter.axon.command.MemberMoneyCreatedCommand;
import com.fastcampuspay.money.adapter.axon.event.IncreaseMemberMoneyEvent;
import com.fastcampuspay.money.adapter.axon.event.MemberMoneyCreatedEvent;
import lombok.Data;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

// aggregate 변경은 이 클래스를 통해서만 가능하도록 한다. - DDD 를 강제로 구현해둔 프레임워크.
@Aggregate()
@Data
public class MemberMoneyAggregate {
    @AggregateIdentifier
    private String id;

    private Long membershipId;

    private int balance;

    @CommandHandler
    public MemberMoneyAggregate(MemberMoneyCreatedCommand command) {
        // 1. command 를 받고,
        System.out.println("MemberMoneyCreatedCommand Handler");

        // 2. event 를 발생시킨다.
        apply(new MemberMoneyCreatedEvent(command.getMembershipId()));
    }

    @CommandHandler
    public String handle(IncreaseMemberMoneyCommand command) {
        System.out.println("2. IncreaseMemberMoneyCommand Handler");
        id = command.getAggregateIdentifier();

        apply(new IncreaseMemberMoneyEvent(id, command.getMembershipId(), command.getAmount()));
        return id;
    }

    @EventSourcingHandler
    public void on(MemberMoneyCreatedEvent event) {
        // 3. event 를 받아, aggregate 를 새로 생서한다.
        System.out.println("MemberMoneyCreatedEvent Sourcing Handler");
        id = UUID.randomUUID().toString();
        membershipId = Long.parseLong(event.getMembershipId());
        balance = 0;
    }

    @EventSourcingHandler
    public void on(IncreaseMemberMoneyEvent event) {
        // 3. event 를 받아, aggregate 를 새로 생서한다.
        System.out.println("3. IncreaseMemberMoneyEvent Sourcing Handler");
        id = event.getAggregateIdentifier();
        membershipId = Long.parseLong(event.getTargetMembershipId());
        balance = event.getAmount();
    }

    public MemberMoneyAggregate() {
    }
}
