package com.fastcampuspay.banking.adapter.in.web;

import com.fastcampuspay.banking.application.port.in.RegisterBankAccountCommand;
import com.fastcampuspay.banking.application.port.in.RegisterBankAccountUseCase;
import com.fastcampuspay.banking.domain.RegisteredBankAccount;
import com.fastcampuspay.common.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RegisterBankAccountController {

    private final RegisterBankAccountUseCase registerBankAccountUseCase;

    @PostMapping(path = "/banking/account/register")
    RegisteredBankAccount registerBankAccount(@RequestBody RegisterBankAccountRequest request) {
        System.out.println("POST /banking/account/register :)");
        // request 처리 ...

        // 1. 중간 계층 추상화 request -> Command, request 변경에 port 가 영향 없게 하려고.
        RegisterBankAccountCommand command = RegisterBankAccountCommand.builder()
                .membershipId(request.getMembershipId())
                .bankAccountNumber(request.getBankName())
                .bankName(request.getBankAccountNumber())
                .linkedStatusIsValid(request.isValid())
                .build();

        // 2. Usecase 를 통해서 요청을 처리할 거다. (command 를 인자로 받을 거다)
        // - membership 을 등록하는 사용 예이고, 이것을 interface 화 해볼 것이다.
        RegisteredBankAccount registeredBankAccount = registerBankAccountUseCase.registerBankAccount(command);
        if (registeredBankAccount == null) {
            // Todo: error handling
            System.out.println("등록 실패");
            return null;
        }
        return registeredBankAccount;
    }
}
