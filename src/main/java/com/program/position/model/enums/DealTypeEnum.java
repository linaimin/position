package com.program.position.model.enums;

/**
 * @Author: huanglin
 * @Date: 5/20/20 8:06 PM
 * @Version 1.0
 */
public enum DealTypeEnum {
    /**
     * Buy
     */
    BUY("Buy"),
    /**
     * Sell
     */
    SELL("Sell");

    private String key;

    DealTypeEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
