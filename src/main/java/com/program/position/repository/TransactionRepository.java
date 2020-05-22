package com.program.position.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.program.position.model.data.TradeVersion;
import com.program.position.model.entity.Transaction;

/**
 * @Author: huanglin
 * @Date: 5/20/20 8:09 PM
 * @Version 1.0
 */
public interface TransactionRepository extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {
    Optional<Transaction> findFirstByTradeIdOrderByVersionDesc(Long tradeId);

    @Query(value = "select new com.program.position.model.data.TradeVersion(trans.tradeId, "
            + "trans.version, trans.securityCode) from Transaction trans")
    List<TradeVersion> getAllTradeVersion();

    @Query(value = "select new com.program.position.model.data.TradeVersion(trans.tradeId, "
            + "trans.version, trans.securityCode) from Transaction trans where trans.tradeId in ?1")
    List<TradeVersion> getAllTradeVersionByTradeIds(Set<Long> tradeIds);

    @Query(value = "select distinct(trans.tradeId) from Transaction trans where trans.securityCode = ?1")
    Set<Long> findAllDistinctTradeIdsBySecurityCode(String securityCode);

    @Query(value = "select trans from Transaction trans where "
            + "concat(trans.tradeId, ',', trans.version) in ?1")
    List<Transaction> findByTradeVersionUk(List<String> tradeVersionUk);

    @Query(value = "select distinct(trans.securityCode) from Transaction trans")
    Set<String> findAllDistinctSecurityCodes();
}