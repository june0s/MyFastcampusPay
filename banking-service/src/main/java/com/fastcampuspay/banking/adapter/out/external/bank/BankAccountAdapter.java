package com.fastcampuspay.banking.adapter.out.external.bank;

import com.fastcampuspay.banking.application.port.out.RequestBankAccountInfoPort;
import com.fastcampuspay.common.ExternalSystemAdapter;
import lombok.RequiredArgsConstructor;

@ExternalSystemAdapter
@RequiredArgsConstructor
public class BankAccountAdapter implements RequestBankAccountInfoPort {
    @Override
    public BankAccount getBankAccountInfo(GetBankAccountRequest request) {
        // 외부 은행에 요청을 보내고 응답을 받는다.
        // - 실제로 외부 은행에 http 를 보내고, 은행 계좌 정보를 가져온 다음,
        // - 실제 은행 계좌 -> BankAccount 로 변환해서 리턴해야 한다.

        // - 실제 은행과 통신할 수 없어, 임시로 BankAccount 만들어서 리턴한다.

        return new BankAccount(request.getBankName(), request.getBankAccountNumber(), true);
    }
}
