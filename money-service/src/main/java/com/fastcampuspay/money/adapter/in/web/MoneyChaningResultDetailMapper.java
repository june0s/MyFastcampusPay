package com.fastcampuspay.money.adapter.in.web;

import com.fastcampuspay.money.domain.MoneyChangingRequest;
import com.fastcampuspay.money.domain.MoneyChangingRequest.ChangingType;
import org.springframework.stereotype.Component;

@Component
public class MoneyChaningResultDetailMapper {
    public MoneyChangingResultDetail mapToMoneyChangingResultDetail(MoneyChangingRequest request) {

//        switch (request.getMoneyChangingType()) {
//            case MoneyChangingRequest.ChangingType.INCREASE:
//                return new MoneyChangingResultDetail(
//                        request.getMoneyChangingRequestId(),
//                        request.getMoneyChangingType(),
//                        request.getMoneyChangingResultStatus(),
//                        request.getAmount()
//                );
//            case DECREASE:
//                return new MoneyChangingResultDetail(
//                        request.getMoneyChangingRequestId(),
//                        request.getMoneyChangingType(),
//                        request.getMoneyChangingResultStatus(),
//                        request.getAmount()
//                );
//            default:
//                throw new IllegalArgumentException("잘못된 요청입니다.");
//        }

//        return new MoneyChangingResultDetail(
//                request.getMoneyChangingRequestId(),
//                request.getMoneyChangingType(),
//                request.getMoneyChangingResultStatus(),
//                request.getAmount()
//        );
        // skip
        return null;
    }
}
