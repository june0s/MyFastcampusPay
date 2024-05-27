package com.fastcampuspay.banking.application.service;

import com.fastcampuspay.banking.adapter.out.external.bank.BankAccount;
import com.fastcampuspay.banking.adapter.out.external.bank.GetBankAccountRequest;
import com.fastcampuspay.banking.adapter.out.persistence.RegisterBankAccountJpaEntity;
import com.fastcampuspay.banking.adapter.out.persistence.RegisterBankAccountMapper;
import com.fastcampuspay.banking.application.port.in.FirmbankingRequestCommand;
import com.fastcampuspay.banking.application.port.in.RegisterBankAccountUseCase;
import com.fastcampuspay.banking.application.port.in.RequestFirmbankingCommand;
import com.fastcampuspay.banking.application.port.in.RequestFirmbankingUseCase;
import com.fastcampuspay.banking.application.port.out.RegisterBankAccountPort;
import com.fastcampuspay.banking.application.port.out.RequestBankAccountInfoPort;
import com.fastcampuspay.banking.domain.FirmbankingRequest;
import com.fastcampuspay.banking.domain.RegisteredBankAccount;
import com.fastcampuspay.common.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
public class RequestFirmbankingService implements RequestFirmbankingUseCase {

    private final RegisterBankAccountPort registerBankAccountPort;
    private final RegisterBankAccountMapper mapper;
    private final RequestBankAccountInfoPort requestBankAccountInfoPort;

//    @Override
//    public RegisteredBankAccount registerBankAccount(RequestFirmbankingCommand command) {
//
//        // 은행 계좌를 등록해야하는 서비스(비즈니스 로직)
//
//        // {멤버 서비스 확인?} 지금은 skip
////        command.getMembershipId();
//
//        // 1. 외부 실제 은행에 등록 가능한 계좌인지(정상인지) 확인
//        // 외부의 은행에 이 계좌가 정상인지 확인 해야한다.
//        // Biz logic -> External System
//        // Port -> Adapter -> External System
//        BankAccount bankAccount = requestBankAccountInfoPort.getBankAccountInfo(new GetBankAccountRequest(
//                command.getBankName()
//                , command.getBankAccountNumber()
//        ));
//        boolean valid = bankAccount.isValid();
//
//        // 2. 등록가능한 계좌라면, 등록한다. 성공하면, 등록에 성공한 등록 정보를 리턴.
//        // 2-1 등록가능하지 않은 계좌라면, 에러를 리턴
//        if (valid) {
//            // 등록 정보 저장
//            RegisterBankAccountJpaEntity savedAccountInfo = registerBankAccountPort.createRegisteredBankAccount(
//                    new RegisteredBankAccount.MembershipId(command.getMembershipId())
//                    , new RegisteredBankAccount.BankName(command.getBankName())
//                    , new RegisteredBankAccount.BankAccountNumber(command.getBankAccountNumber())
//                    , new RegisteredBankAccount.LinkedStatusIsValid(command.isLinkedStatusIsValid())
//            );
//
//            return mapper.mapToDomainEntity(savedAccountInfo);
//        } else {
//            return null;
//        }
//    }

    @Override
    public FirmbankingRequest requestFirmbanking(FirmbankingRequestCommand command) {


        return null;
    }
}
