package com.program.position.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.program.position.model.enums.ActionTypeEnum;
import com.program.position.model.enums.DealTypeEnum;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author: huanglin
 * @Date: 5/20/20 7:51 PM
 * @Version 1.0
 */
@Entity
@Getter
@Setter
@Table(name = "transaction", uniqueConstraints = {
        @UniqueConstraint(name = "uk_transaction_trade_id_version", columnNames = { "trade_id", "version" }) })
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Transaction {
    @Id
    @Column(name = "transaction_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_transaction_id")
    @SequenceGenerator(name = "seq_transaction_id", sequenceName = "seq_transaction_id", allocationSize = 1)
    private Long transactionId;

    @Column(name = "trade_id", nullable = false)
    private Long tradeId;

    @Column(name = "version", nullable = false)
    private Integer version;

    @Column(name = "security_code", columnDefinition = "char(3) not null")
    private String securityCode;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "action_type", length = 6, nullable = false)
    @Enumerated(EnumType.STRING)
    private ActionTypeEnum actionType;

    @Column(name = "deal_type", length = 4, nullable = false)
    @Enumerated(EnumType.STRING)
    private DealTypeEnum dealType;
}
