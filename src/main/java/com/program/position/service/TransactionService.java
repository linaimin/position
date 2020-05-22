package com.program.position.service;

import java.util.List;
import java.util.Set;

import com.program.position.model.dto.TransactionCreateDTO;
import com.program.position.model.entity.Transaction;

/**
 * @Author: huanglin
 * @Date: 5/20/20 8:29 PM
 * @Version 1.0
 */
public interface TransactionService {
    Transaction createByActionOfTransactionCreateDTO(TransactionCreateDTO transactionCreateDTO) throws Exception;

    List<Transaction> getAllTransactionsBySecurityCodeAndLastVersionOfTrade(String securityCode) throws Exception;

    Set<String> findAllDistinctSecurityCodes() throws Exception;

    Set<Long> findAllDistinctTradeIdsBySecurityCode(String securityCode) throws Exception;
}
