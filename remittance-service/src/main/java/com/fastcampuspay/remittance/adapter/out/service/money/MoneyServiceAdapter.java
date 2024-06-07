package com.fastcampuspay.remittance.adapter.out.service.money;

import com.fastcampuspay.common.CommonHttpClient;
import com.fastcampuspay.common.ExternalSystemAdapter;
import com.fastcampuspay.remittance.application.port.out.banking.BankingInfo;
import com.fastcampuspay.remittance.application.port.out.banking.BankingPort;
import com.fastcampuspay.remittance.application.port.out.money.MoneyInfo;
import com.fastcampuspay.remittance.application.port.out.money.MoneyPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@ExternalSystemAdapter
@RequiredArgsConstructor
public class MoneyServiceAdapter implements MoneyPort {

    private final CommonHttpClient httpClient;

    @Value("${service.money.url}")
    private String moneyServiceEndpoint;

    @Override
    public MoneyInfo getMoneyInfo(String membershipId) {

        return null;
    }

    @Override
    public boolean requestMoneyRecharging(String membershipId, int amount) {

        return false;
    }

    @Override
    public boolean requestMoneyIncrease(String membershipId, int amount) {

        return false;
    }

    @Override
    public boolean requestMoneyDecrease(String membershipId, int amount) {

        return false;
    }

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

}
