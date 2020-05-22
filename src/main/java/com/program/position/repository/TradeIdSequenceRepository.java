package com.program.position.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.program.position.model.entity.TradeIdSequence;

/**
 * @Author: huanglin
 * @Date: 5/20/20 9:02 PM
 * @Version 1.0
 */
public interface TradeIdSequenceRepository extends JpaRepository<TradeIdSequence, Long>,
        JpaSpecificationExecutor<TradeIdSequence> {
    @Query(value = "select nextval('seq_trade_id_sequence_id')", nativeQuery = true)
    Long getTradeIdSequence();
}
