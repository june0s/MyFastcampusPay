package com.fastcampuspay.remittance.adapter.out.service.banking;

import com.fastcampuspay.common.CommonHttpClient;
import com.fastcampuspay.common.ExternalSystemAdapter;
import com.fastcampuspay.remittance.adapter.out.service.membership.Membership;
import com.fastcampuspay.remittance.application.port.out.banking.BankingInfo;
import com.fastcampuspay.remittance.application.port.out.banking.BankingPort;
import com.fastcampuspay.remittance.application.port.out.membership.MembershipPort;
import com.fastcampuspay.remittance.application.port.out.membership.MembershipStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@ExternalSystemAdapter
@RequiredArgsConstructor
public class BankingServiceAdapter implements BankingPort {

    private final CommonHttpClient httpClient;

    @Value("${service.banking.url}")
    private String bankingServiceEndpoint;

//    @Override
//    public MembershipStatus getMembershipStatus(String membershipId) {
//
//        String buildUrl = String.join("/", this.membershipServiceEndpoint, "membership", membershipId);
//        try {
//            String jsonResponse = membershipServiceHttpClient.sendGetRequest(buildUrl).body();
//            ObjectMapper mapper = new ObjectMapper();
//
//            Membership mem = mapper.readValue(jsonResponse, Membership.class);
//            if (mem.isValid()){
//                return new MembershipStatus(mem.getMembershipId(), "true");
//            } else{
//                return new MembershipStatus(mem.getMembershipId(), "false");
//            }
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Override
    public BankingInfo getBankingInfo(String bankName, String bankAccountNumber) {

        return null;
    }

    @Override
    public boolean requestFirmbanking(String bankName, String bankAccountNumber, int amount) {

        return false;
    }
}
