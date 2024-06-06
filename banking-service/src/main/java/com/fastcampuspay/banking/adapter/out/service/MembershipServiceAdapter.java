package com.fastcampuspay.banking.adapter.out.service;

import com.fastcampuspay.banking.application.port.out.GetMembershipPort;
import com.fastcampuspay.banking.application.port.out.MembershipStatus;
import com.fastcampuspay.common.CommonHttpClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.http.HttpResponse;

@Component
public class MembershipServiceAdapter implements GetMembershipPort {

    private final CommonHttpClient commonHttpClient;
    private final String membershipServiceUrl;

    public MembershipServiceAdapter(CommonHttpClient commonHttpClient,
                                    @Value("${service.membership.url}") String membershipServiceUrl) {
        this.commonHttpClient = commonHttpClient;
        this.membershipServiceUrl = membershipServiceUrl;
    }

    @Override
    public MembershipStatus getMembership(String membershipId) {

        // 실제로 http call
        final String url = String.join("/", membershipServiceUrl, "membership", membershipId);
        try {
            final HttpResponse<String> response = commonHttpClient.sendGetRequest(url);
            final String json = response.body();

            final ObjectMapper mapper = new ObjectMapper();
            final Membership membership = mapper.readValue(json, Membership.class);

            if (membership.isValid()) {
                return new MembershipStatus(membershipId, true);
            } else {
                return new MembershipStatus(membershipId, false);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
