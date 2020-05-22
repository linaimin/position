package com.program.position.model.enums;

/**
 * @Author: huanglin
 * @Date: 5/20/20 7:59 PM
 * @Version 1.0
 */
public enum ActionTypeEnum {
    /**
     * Insert
     */
    INSERT("INSERT"),
    /**
     * Update
     */
    UPDATE("UPDATE"),
    /**
     * Cancel
     */
    CANCEL("CANCEL");

    private String key;

    ActionTypeEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
