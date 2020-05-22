package com.program.position.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author: huanglin
 * @Date: 5/20/20 8:45 PM
 * @Version 1.0
 */
@Entity
@Getter
@Setter
@Table(name = "trade_id_sequence")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class TradeIdSequence {
    @Id
    @Column(name = "sequence_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_trade_id_sequence_id")
    @SequenceGenerator(name = "seq_trade_id_sequence_id", sequenceName = "seq_trade_id_sequence_id", allocationSize = 1)
    private Long id;
}
