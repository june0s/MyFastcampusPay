package com.fastcampuspay.membership.application.port.in;

import com.fastcampuspay.common.SelfValidating;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class RegisterMembershipCommand extends SelfValidating<RegisterMembershipCommand> {

    @NotNull
    private final String name;

    @NotNull
    private final String email;

    @NotNull
    @NotBlank // not 빈 문자열
    private final String address;

    @AssertTrue // 항상 valid 하다는 가정.
    private final boolean isValid;

    private final boolean isCorp;

    public RegisterMembershipCommand(String name, String email, String address, boolean isValid, boolean isCorp) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.isValid = isValid;
        this.isCorp = isCorp;

        this.validateSelf(); // 이 메소드가 name 의 @NotNull 검사를 해준다. 통과 안 되면, exception 발생시키고 그 이유를 알려준다.
    }
}

