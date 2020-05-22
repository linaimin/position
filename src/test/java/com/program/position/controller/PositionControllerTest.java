package com.program.position.controller;

import java.util.List;

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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.program.position.PositionApplication;
import com.program.position.model.dto.PositionDTO;
import com.program.position.model.dto.TransactionCreateDTO;
import com.program.position.model.entity.Transaction;
import com.program.position.model.enums.ActionTypeEnum;
import com.program.position.model.enums.DealTypeEnum;
import com.program.position.service.TransactionService;

import lombok.extern.log4j.Log4j2;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @Author: huanglin
 * @Date: 5/20/20 8:13 PM
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PositionApplication.class)
@AutoConfigureMockMvc
@Log4j2
public class PositionControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private ObjectMapper jsonMapper = new ObjectMapper();
    private MockMvc mockMvc;

    @Autowired
    private TransactionService transactionService;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).dispatchOptions(true).build();
    }

    @Test
    public void testSummaryTransaction() throws Exception {
        TransactionCreateDTO transactionCreateDTO = new TransactionCreateDTO("TTT", 90, DealTypeEnum.SELL,
                ActionTypeEnum.INSERT, null);
        log.info(String.format("Insert data: %s", jsonMapper.writeValueAsString(transactionCreateDTO)));
        Transaction transaction = transactionService.createByActionOfTransactionCreateDTO(transactionCreateDTO);

        transactionCreateDTO = new TransactionCreateDTO("TTT", 50, DealTypeEnum.BUY, ActionTypeEnum.UPDATE,
                transaction.getTradeId());
        log.info(String.format("Insert data: %s", jsonMapper.writeValueAsString(transactionCreateDTO)));
        transactionService.createByActionOfTransactionCreateDTO(transactionCreateDTO);

        transactionCreateDTO = new TransactionCreateDTO("TTT", 60, DealTypeEnum.SELL, ActionTypeEnum.UPDATE,
                transaction.getTradeId());
        log.info(String.format("Insert data: %s", jsonMapper.writeValueAsString(transactionCreateDTO)));
        transactionService.createByActionOfTransactionCreateDTO(transactionCreateDTO);

        transactionCreateDTO = new TransactionCreateDTO("FFF", 70, DealTypeEnum.SELL, ActionTypeEnum.INSERT, null);
        log.info(String.format("Insert data: %s", jsonMapper.writeValueAsString(transactionCreateDTO)));
        transaction = transactionService.createByActionOfTransactionCreateDTO(transactionCreateDTO);

        transactionCreateDTO = new TransactionCreateDTO("GGG", 70, DealTypeEnum.BUY, ActionTypeEnum.CANCEL,
                transaction.getTradeId());
        log.info(String.format("Insert data: %s", jsonMapper.writeValueAsString(transactionCreateDTO)));
        transactionService.createByActionOfTransactionCreateDTO(transactionCreateDTO);

        transactionCreateDTO = new TransactionCreateDTO("TTT", 40, DealTypeEnum.SELL, ActionTypeEnum.INSERT, null);
        log.info(String.format("Insert data: %s", jsonMapper.writeValueAsString(transactionCreateDTO)));
        transactionService.createByActionOfTransactionCreateDTO(transactionCreateDTO);

        String result = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/position/summary").accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString();
        log.info(String.format("Summary all result: %s", result));
        List<PositionDTO> positions = jsonMapper.readValue(result, List.class);
        assertEquals(6, positions.size());

        result = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/position/summary").param("securityCode", "TTT").accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString();
        log.info(String.format("Summary securityCode TTT result: %s", result));
        positions = jsonMapper.readValue(result, List.class);
        positions = jsonMapper.convertValue(positions, new TypeReference<List<PositionDTO>>() { });
        assertEquals(1, positions.size());

        result = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/position/summary").param("securityCode", "FFF").accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString();
        log.info(String.format("Summary securityCode FFF result: %s", result));
        positions = jsonMapper.readValue(result, List.class);
        assertEquals(1, positions.size());

        result = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/position/summary").param("securityCode", "XXX").accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString();
        log.info(String.format("Summary securityCode XXX result: %s", result));
        positions = jsonMapper.readValue(result, List.class);
        assertEquals(0, positions.size());
    }
}
