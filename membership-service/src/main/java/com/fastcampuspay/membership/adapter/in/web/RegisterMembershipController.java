package com.fastcampuspay.membership.adapter.in.web;

import com.fastcampuspay.common.WebAdapter;
import com.fastcampuspay.membership.application.port.in.RegisterMembershipCommand;
import com.fastcampuspay.membership.application.port.in.RegisterMembershipUseCase;
import com.fastcampuspay.membership.domain.Membership;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RegisterMembershipController {

    private final RegisterMembershipUseCase registerMembershipUseCase;

    @PostMapping(path = "/membership/register")
    Membership registerMembership(@RequestBody RegisterMembershipRequest request) {
        System.out.println("Hello test :)");
        // request 처리 ...

        // 1. 중간 계층 추상화 request -> Command, request 변경에 port 가 영향 없게 하려고.
        RegisterMembershipCommand command = RegisterMembershipCommand.builder()
                .name(request.getName())
                .address(request.getAddress())
                .email(request.getEmail())
                .isValid(true)
                .isCorp(request.isCorp())
                .build();

        // 2. Usecase 를 통해서 요청을 처리할 거다. (command 를 인자로 받을 거다)
        // - membership 을 등록하는 사용 예이고, 이것을 interface 화 해볼 것이다.
        return registerMembershipUseCase.registerMembership(command);
    }
}
