package com.fastcampuspay.money.adapter.in.web;

import com.fastcampuspay.common.WebAdapter;
import com.fastcampuspay.money.application.port.in.IncreaseMoneyRequestCommand;
import com.fastcampuspay.money.application.port.in.IncreaseMoneyRequestUseCase;
import com.fastcampuspay.money.domain.MoneyChangingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RequestMoneyChangingController {

    private final IncreaseMoneyRequestUseCase increaseMoneyRequestUseCase;
//    private final DecreaseMoneyRequestUseCase decreaseMoneyRequestUseCase;

    @PostMapping(path = "/money/increase")
    MoneyChangingResultDetail increaseMoneyChangingRequest(@RequestBody IncreaseMoneyChangingRequest request) {
        System.out.println("POST /money/increase :)");
        // request 처리 ...

        // 2. Usecase 를 통해서 요청을 처리할 거다. (command 를 인자로 받을 거다)
        // - membership 을 등록하는 사용 예이고, 이것을 interface 화 해볼 것이다.
//        MoneyChangingRequest moneyChangingRequest = increaseMoneyRequestUseCase.registerBankAccount(command);
//        if (moneyChangingRequest == null) {
//            // Todo: error handling
//            System.out.println("등록 실패");
//            return null;
//        }
        IncreaseMoneyRequestCommand command = IncreaseMoneyRequestCommand.builder()
                .targetMembershipId(request.getTargetMembershipId())
                .amount(request.getAmount())
                .build();

        final MoneyChangingRequest moneyChangingRequest = increaseMoneyRequestUseCase.increaseMoneyRequest(command);
        final MoneyChangingResultDetail resultDetail = new MoneyChangingResultDetail(
                moneyChangingRequest.getMoneyChangingRequestId(),
                0, // INCREASE
                0, // SUCCESS
                moneyChangingRequest.getChangingMoneyAmount()
        );

//        return new MoneyChaningResultDetailMapper().mapToMoneyChangingResultDetail(moneyChangingRequest);
        return resultDetail;
    }

    @PostMapping(path = "/money/decrease")
    MoneyChangingResultDetail decreaseMoneyChangingRequest(@RequestBody DecreaseMoneyChangingRequest request) {
        System.out.println("POST /money/decrease :)");
        // request 처리 ...

        // 2. Usecase 를 통해서 요청을 처리할 거다. (command 를 인자로 받을 거다)
        // - membership 을 등록하는 사용 예이고, 이것을 interface 화 해볼 것이다.
//        MoneyChangingRequest moneyChangingRequest = increaseMoneyRequestUseCase.registerBankAccount(command);
//        if (moneyChangingRequest == null) {
//            // Todo: error handling
//            System.out.println("등록 실패");
//            return null;
//        }
//        final MoneyChangingRequest moneyChangingRequest1 = increaseMoneyRequestUseCase.increaseMoneyRequest(command);
        return null;
    }
}
