package com.program.position.model.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: huanglin
 * @Date: 5/20/20 9:46 PM
 * @Version 1.0
 */
@Getter
@Setter
@AllArgsConstructor
public class TradeVersion {
    private Long tradeId;
    private Integer version;
    private String securityCode;
}
