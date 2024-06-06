package com.fastcampuspay.remittance.application.port.out.banking;

public interface BankingPort {
    BankingInfo getBankingInfo(String bankName, String bankAccountNumber);
    boolean requestFirmbanking(String bankName, String bankAccountNumber, int amount);
}
