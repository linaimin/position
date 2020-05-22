package com.program.position.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.program.position.model.dto.PositionDTO;
import com.program.position.model.entity.Transaction;
import com.program.position.model.enums.ActionTypeEnum;
import com.program.position.model.enums.DealTypeEnum;
import com.program.position.service.PositionService;
import com.program.position.service.TransactionService;

import lombok.extern.log4j.Log4j2;

/**
 * @Author: huanglin
 * @Date: 5/20/20 8:13 PM
 * @Version 1.0
 */
@Service
@Log4j2
public class PositionServiceImpl implements PositionService {
    @Autowired
    private TransactionService transactionService;

    /**
     * summary all the positions of securityCode, can be filter by securityCode or find all
     * @param securityCode parameter to filter the result
     * @return all the position results
     * @throws Exception throw exception
     */
    @Override
    public List<PositionDTO> summaryTransaction(String securityCode) throws Exception {
        // check whether the securityCode is exist in system,
        // if the securityCode is not exist, return a empty list.
        if (securityCode != null) {
            // use the securityCode to find all the trade id which related to it.
            Set<Long> tradeIds = transactionService.findAllDistinctTradeIdsBySecurityCode(securityCode);
            if (tradeIds.isEmpty()) {
                return new ArrayList<>();
            }
        }
        Map<String, PositionDTO> positionMap = new HashMap<>();
        // get all the transactions by securityCode which can be null,
        // the list only contains the last version of trade transaction
        List<Transaction> transactions = transactionService
                .getAllTransactionsBySecurityCodeAndLastVersionOfTrade(securityCode);
        // get all the distinct securityCodes from the system, check whether the securityCode is null
        Set<String> securityCodes =
                securityCode == null ? transactionService.findAllDistinctSecurityCodes() : new HashSet<String>() {{
                    add(securityCode);
                }};
        if (transactions != null && !transactions.isEmpty()) {
            // summary the position by all the transaction
            for (Transaction transaction : transactions) {
                PositionDTO positionDTO;
                String securityCodeTmp = transaction.getSecurityCode();
                if (!positionMap.containsKey(securityCodeTmp)) {
                    positionDTO = getNewPositionToMap(securityCodeTmp);
                    positionMap.put(securityCodeTmp, positionDTO);
                } else {
                    positionDTO = positionMap.get(securityCodeTmp);
                }
                updatePositionByTransaction(positionDTO, transaction);
            }
        }
        for (String securityCodeTmp : securityCodes) {
            // update the result of position,
            // check all the securityCode in system can have a result to return
            // when the securityCode not exist in positionMap, should init it by zero
            if (!positionMap.containsKey(securityCodeTmp)) {
                positionMap.put(securityCodeTmp, getNewPositionToMap(securityCodeTmp));
            }
        }
        // sorted the position result by asc, if no need, sort can be removed.
        return positionMap.values().stream().sorted(Comparator.comparing(PositionDTO::getSecurityCode))
                .collect(Collectors.toList());
    }

    /**
     * init a position by securityCode
     * @param securityCode parameter
     * @return init position
     */
    private PositionDTO getNewPositionToMap(String securityCode) {
        PositionDTO positionDTO = new PositionDTO();
        positionDTO.setSecurityCode(securityCode);
        positionDTO.setSummaryResult(0L);
        return positionDTO;
    }

    /**
     * update the position by transaction
     * @param positionDTO which to be updated
     * @param transaction input
     */
    private void updatePositionByTransaction(PositionDTO positionDTO, Transaction transaction) {
        Integer quantity = 0;
        // when the action type is INSERT or UPDATE, need to summary the quantity to exist position
        // the quantity default 0
        if (transaction.getActionType() == ActionTypeEnum.INSERT
                || transaction.getActionType() == ActionTypeEnum.UPDATE) {
            // when the deal type is BUY, should add the quantity of transaction from the position, use the origin value
            // others, when the deal type is SELL, should sub the quantity of transaction from the position, reverse the origin value
            if (transaction.getDealType() == DealTypeEnum.BUY) {
                quantity = transaction.getQuantity();
            } else if (transaction.getDealType() == DealTypeEnum.SELL) {
                quantity = -transaction.getQuantity();
            }
        }
        positionDTO.setSummaryResult(positionDTO.getSummaryResult() + quantity);
    }
}
