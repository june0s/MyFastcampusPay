package com.fastcampuspay.banking.adapter.in.web;

import com.fastcampuspay.banking.application.port.in.FirmbankingRequestCommand;
import com.fastcampuspay.banking.application.port.in.RequestFirmbankingCommand;
import com.fastcampuspay.banking.application.port.in.RequestFirmbankingUseCase;
import com.fastcampuspay.banking.domain.FirmbankingRequest;
import com.fastcampuspay.banking.domain.RegisteredBankAccount;
import com.fastcampuspay.common.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RequestFirmbankingController {

    private final RequestFirmbankingUseCase requestFirmbankingUseCase;

    @PostMapping(path = "/banking/firmbanking/request")
    FirmbankingRequest registerBankAccount(@RequestBody RequestFirmbankingRequest request) {
        System.out.println("POST /banking/firmbanking/request :)");
        // request 처리 ...

        // 1. 중간 계층 추상화 request -> Command, request 변경에 port 가 영향 없게 하려고.
        FirmbankingRequestCommand command = FirmbankingRequestCommand.builder()
                .fromBankName(request.getFromBankName())
                .fromBankAccountNumber(request.getFromBankAccountNumber())
                .toBankName(request.getToBankName())
                .toBankAccountNumber(request.getToBankAccountNumber())
                .moneyAmount(request.getMoneyAmountt())
                .build();

        // 2. Usecase 를 통해서 요청을 처리할 거다. (command 를 인자로 받을 거다)
        return requestFirmbankingUseCase.requestFirmbanking(command);
    }
}
