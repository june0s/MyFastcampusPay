package com.fastcampuspay.banking;

import com.fastcampuspay.banking.adapter.in.web.RegisterBankAccountRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RegisterBankAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void registerBankAccount() throws Exception {
        RegisterBankAccountRequest request = new RegisterBankAccountRequest("002030", "woori", "1002-341", true);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/banking/account/register")
                               .contentType("application/json")
                               .content(objectMapper.writeValueAsString(request))
                ).andExpect(MockMvcResultMatchers.status().isOk());
//                .andExpect(MockMvcResultMatchers.jsonPath("$.bankName").value("woori"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.bankAccountNumber").value("1002-341"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.isValid").value(true));
    }
}
