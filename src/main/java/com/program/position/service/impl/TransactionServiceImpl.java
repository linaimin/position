package com.program.position.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.program.position.exception.ApplicationException;
import com.program.position.model.data.TradeVersion;
import com.program.position.model.dto.TransactionCreateDTO;
import com.program.position.model.entity.Transaction;
import com.program.position.model.enums.ActionTypeEnum;
import com.program.position.repository.TradeIdSequenceRepository;
import com.program.position.repository.TransactionRepository;
import com.program.position.service.TransactionService;
import com.program.position.util.ErrorCodeUtil;
import com.program.position.util.MapperUtil;

import lombok.extern.log4j.Log4j2;

/**
 * @Author: huanglin
 * @Date: 5/20/20 8:29 PM
 * @Version 1.0
 */
@Service
@Log4j2
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TradeIdSequenceRepository tradeIdSequenceRepository;

    /**
     * create transaction by dto
     * @param transactionCreateDTO create dto
     */
    @Override
    @Transactional
    public Transaction createByActionOfTransactionCreateDTO(TransactionCreateDTO transactionCreateDTO) throws Exception {
        Transaction transaction = new Transaction();
        // check the action type, when it is INSERT, tradeId of transactionCreateDTO should be null,
        // INSERT is a new trade record and the version increases from 1
        if (transactionCreateDTO.getActionType() == ActionTypeEnum.INSERT) {
            if (transactionCreateDTO.getTradeId() != null) {
                throw new ApplicationException(ErrorCodeUtil.CURRENT_TRADE_IS_EXIST,
                        String.format("INSERT action does not need trade id %s.", transactionCreateDTO.getTradeId()));
            }
            // through the database sequence to generate tradeId of new transaction INSERT
            transactionCreateDTO.setTradeId(tradeIdSequenceRepository.getTradeIdSequence());
            transaction.setVersion(1);
        } else {
            // when the action type is UPDATE or CANCEL, tradeId of transactionCreateDTO should not be null
            if (transactionCreateDTO.getTradeId() == null) {
                throw new ApplicationException(ErrorCodeUtil.ELEMENT_IS_NULL, "Trade id is required.");
            }
            // for the action UPDATE or CANCEL, tradeId of transactionCreateDTO should exist in system
            // get the last version transaction of the trade from system, use to add the version
            Optional<Transaction> existTransactionOptional = transactionRepository.findFirstByTradeIdOrderByVersionDesc(transactionCreateDTO.getTradeId());
            if(!existTransactionOptional.isPresent()) {
                throw new ApplicationException(ErrorCodeUtil.SYSTEM_NOT_EXIST,
                        String.format("Transaction is not exist by trade id %s.", transactionCreateDTO.getTradeId()));
            }
            Transaction existTransaction = existTransactionOptional.get();
            // for the action UPDATE or CANCEL, action of transaction by tradeId exist in system should not be CANCEL
            if (existTransaction.getActionType() == ActionTypeEnum.CANCEL) {
                throw new ApplicationException(ErrorCodeUtil.CURRENT_TRADE_IS_CANCEL,
                        String.format("Current trade id %s of transaction has the final action CANCEL.",
                                transactionCreateDTO.getTradeId()));
            }
            // for the action UPDATE or CANCEL, increase the version by the last version transaction of the trade from system
            transaction.setVersion(existTransaction.getVersion() + 1);
        }
        MapperUtil.TRANSACTION_MAPPER.mapping(transactionCreateDTO, transaction);
        return transactionRepository.save(transaction);
    }

    /**
     * get all the transactions by securityCode which can be null,
     * the list only contains the last version of trade transaction
     * @param securityCode parameter which can be null
     * @return all the last version of trade transaction
     * @throws Exception throw exception
     */
    @Override
    public List<Transaction> getAllTransactionsBySecurityCodeAndLastVersionOfTrade(String securityCode) throws Exception {
        List<Transaction> transactions = null;
        List<TradeVersion> tradeVersions;
        if (securityCode == null) {
            // when the securityCode is null, get all the TradeVersion from transactions
            tradeVersions = transactionRepository.getAllTradeVersion();
        } else {
            // when the securityCode is not null, find all the distinct tradeIds by securityCode
            // then, get all the tradeVersions by tradeIds when the result is not empty
            Set<Long> tradeIds = findAllDistinctTradeIdsBySecurityCode(securityCode);
            if (tradeIds.isEmpty()) {
                tradeVersions = new ArrayList<>();
            } else {
                tradeVersions = transactionRepository.getAllTradeVersionByTradeIds(tradeIds);
            }
        }
        Map<Long, TradeVersion> tradeVersionMap = new HashMap<>();
        if (tradeVersions != null && !tradeVersions.isEmpty()) {
            for (TradeVersion tradeVersion : tradeVersions) {
                // check the tradeVersion whether exist in tradeVersionMap,
                // when it is not exist or the version is larger than the version from tradeVersionMap,
                // should update the tradeVersionMap by current tradeVersion
                if (!tradeVersionMap.containsKey(tradeVersion.getTradeId())
                        || tradeVersion.getVersion() > tradeVersionMap.get(tradeVersion.getTradeId()).getVersion()) {
                    // when the securityCode is null or the securityCode is equals to securityCode of tradeVersion,
                    // should update the tradeVersionMap by tradeId and tradeVersion
                    if (securityCode == null || securityCode.equals(tradeVersion.getSecurityCode())) {
                        tradeVersionMap.put(tradeVersion.getTradeId(), tradeVersion);
                    }
                    // when the tradeVersionMap contains the tradeId of tradeVersion and
                    // the version of tradeVersion is larger than the version from tradeVersionMap
                    // and the securityCode is not equals to securityCode of tradeVersion,
                    // remove the tradeId from tradeVersionMap
                    if (tradeVersionMap.containsKey(tradeVersion.getTradeId())
                            && tradeVersion.getVersion() > tradeVersionMap.get(tradeVersion.getTradeId()).getVersion()
                            && !tradeVersion.getSecurityCode().equals(securityCode)) {
                        tradeVersionMap.remove(tradeVersion.getTradeId());
                    }
                }
            }
            // get the UK of all the tradeVersion from tradeVersionMap
            // and use the UK to get all the transactions
            List<String> tradeVersionUk = new ArrayList<>();
            tradeVersionMap.forEach((k, v) -> tradeVersionUk.add(k + "," + v.getVersion()));
            transactions = tradeVersionUk.isEmpty() ?
                    new ArrayList<>() :
                    transactionRepository.findByTradeVersionUk(tradeVersionUk);
        }
        return transactions;
    }

    /**
     * get all the distinct securityCodes from the system
     * @return all the distinct securityCodes
     * @throws Exception throw exception
     */
    @Override
    public Set<String> findAllDistinctSecurityCodes() throws Exception {
        return transactionRepository.findAllDistinctSecurityCodes();
    }

    /**
     * get all the distinct tradeIds by securityCode
     * @param securityCode parameter
     * @return all the distinct tradeIds
     * @throws Exception throw exception
     */
    @Override
    public Set<Long> findAllDistinctTradeIdsBySecurityCode(String securityCode) throws Exception {
        return transactionRepository.findAllDistinctTradeIdsBySecurityCode(securityCode);
    }
}
