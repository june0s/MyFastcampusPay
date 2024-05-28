package com.fastcampuspay.banking.application.port.out;

import com.fastcampuspay.banking.adapter.out.external.bank.ExternalFirmbankingRequest;
import com.fastcampuspay.banking.adapter.out.external.bank.FirmBankingResult;

public interface RequestExternalFirmbankingPort {
    FirmBankingResult requestFirmbanking(ExternalFirmbankingRequest externalFirmbankingRequest);
}
