package com.fastcampuspay.banking.application.port.in;

import com.fastcampuspay.banking.domain.FirmbankingRequest;

// 외부에서 들어오는(in) 요청.
//@UseCase
public interface RequestFirmbankingUseCase {

    FirmbankingRequest requestFirmbanking(FirmbankingRequestCommand command);
}
