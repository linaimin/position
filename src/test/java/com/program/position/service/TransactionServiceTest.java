package com.program.position.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.program.position.PositionApplication;
import com.program.position.exception.ApplicationException;
import com.program.position.model.dto.TransactionCreateDTO;
import com.program.position.model.entity.Transaction;
import com.program.position.model.enums.ActionTypeEnum;
import com.program.position.model.enums.DealTypeEnum;
import com.program.position.util.ErrorCodeUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @Author: huanglin
 * @Date: 5/21/20 10:57 AM
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PositionApplication.class)
@AutoConfigureMockMvc
public class TransactionServiceTest {
    @Autowired
    private TransactionService transactionService;

    @Test
    public void testSummaryTransactionFail() throws Exception {
        TransactionCreateDTO transactionCreateDTO = new TransactionCreateDTO();
        transactionCreateDTO.setActionType(ActionTypeEnum.INSERT);
        try {
            transactionCreateDTO.setTradeId(1L);
           transactionService.createByActionOfTransactionCreateDTO(transactionCreateDTO);
           fail();
        } catch (ApplicationException e) {
            assertEquals(ErrorCodeUtil.CURRENT_TRADE_IS_EXIST, e.getErrorCode());
        }

        try {
            transactionCreateDTO.setActionType(ActionTypeEnum.UPDATE);
            transactionCreateDTO.setTradeId(null);
            transactionService.createByActionOfTransactionCreateDTO(transactionCreateDTO);
            fail();
        } catch (ApplicationException e) {
            assertEquals(ErrorCodeUtil.ELEMENT_IS_NULL, e.getErrorCode());
        }

        try {
            transactionCreateDTO.setActionType(ActionTypeEnum.UPDATE);
            transactionCreateDTO.setTradeId(9999999L);
            transactionService.createByActionOfTransactionCreateDTO(transactionCreateDTO);
            fail();
        } catch (ApplicationException e) {
            assertEquals(ErrorCodeUtil.SYSTEM_NOT_EXIST, e.getErrorCode());
        }

        Transaction transaction = transactionService.createByActionOfTransactionCreateDTO(
                new TransactionCreateDTO("UUU", 90, DealTypeEnum.SELL, ActionTypeEnum.INSERT, null));
        transactionService.createByActionOfTransactionCreateDTO(
                new TransactionCreateDTO("UUU", 50, DealTypeEnum.BUY, ActionTypeEnum.CANCEL,
                        transaction.getTradeId()));
        try {
            transactionCreateDTO.setActionType(ActionTypeEnum.UPDATE);
            transactionCreateDTO.setTradeId(transaction.getTradeId());
            transactionService.createByActionOfTransactionCreateDTO(transactionCreateDTO);
            fail();
        } catch (ApplicationException e) {
            assertEquals(ErrorCodeUtil.CURRENT_TRADE_IS_CANCEL, e.getErrorCode());
        }

        transactionService.getAllTransactionsBySecurityCodeAndLastVersionOfTrade("111");
    }
}
