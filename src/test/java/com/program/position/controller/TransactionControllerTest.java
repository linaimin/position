package com.program.position.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.program.position.PositionApplication;
import com.program.position.model.dto.TransactionCreateDTO;
import com.program.position.model.enums.ActionTypeEnum;
import com.program.position.model.enums.DealTypeEnum;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @Author: huanglin
 * @Date: 5/20/20 8:28 PM
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PositionApplication.class)
@AutoConfigureMockMvc
public class TransactionControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private ObjectMapper jsonMapper = new ObjectMapper();
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).dispatchOptions(true).build();
    }

    @Test
    public void testSummaryTransaction() throws Exception {
        TransactionCreateDTO transactionCreateDTO = new TransactionCreateDTO();
        transactionCreateDTO.setActionType(ActionTypeEnum.INSERT);
        transactionCreateDTO.setDealType(DealTypeEnum.BUY);
        transactionCreateDTO.setQuantity(50);
        transactionCreateDTO.setSecurityCode("TTT");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/transaction/action")
                .content(jsonMapper.writeValueAsString(transactionCreateDTO)).accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
}
