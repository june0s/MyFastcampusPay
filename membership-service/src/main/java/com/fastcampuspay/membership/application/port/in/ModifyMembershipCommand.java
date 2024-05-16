package com.fastcampuspay.membership.application.port.in;

import common.SelfValidating;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class ModifyMembershipCommand extends SelfValidating<ModifyMembershipCommand> {

    @NotNull
    private final String membershipId;

    @NotNull
    private final String name;

    @NotNull
    private final String address;

    @NotNull
    private final String email;

    @AssertTrue
    private final boolean isValid;

    private final boolean isCorp;

    public ModifyMembershipCommand(String membershipId, String name, String address, String email, boolean isValid, boolean isCorp) {
        this.membershipId = membershipId;
        this.name = name;
        this.address = address;
        this.email = email;
        this.isValid = isValid;
        this.isCorp = isCorp;

        this.validateSelf(); // 이 메소드가 name 의 @NotNull 검사를 해준다. 통과 안 되면, exception 발생시키고 그 이유를 알려준다.
    }
}
