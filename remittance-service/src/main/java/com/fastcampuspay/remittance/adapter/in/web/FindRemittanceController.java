package com.fastcampuspay.remittance.adapter.in.web;

import com.fastcampuspay.common.WebAdapter;
import com.fastcampuspay.remittance.application.port.in.FindRemittanceCommand;
import com.fastcampuspay.remittance.application.port.in.FindRemittanceUseCase;
import com.fastcampuspay.remittance.application.port.in.RequestRemittanceCommand;
import com.fastcampuspay.remittance.application.port.in.RequestRemittanceUseCase;
import com.fastcampuspay.remittance.domain.RemittanceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class FindRemittanceController {

    private final FindRemittanceUseCase findRemittanceUseCase;

    @GetMapping(path = "/remittance/{membershipId}")
    List<RemittanceRequest> findRemittanceHistory(String membershipId) {
        FindRemittanceCommand command = FindRemittanceCommand.builder()
                .membershipId(membershipId)
                .build();

        return findRemittanceUseCase.findRemittanceHistory(command);
    }
}
