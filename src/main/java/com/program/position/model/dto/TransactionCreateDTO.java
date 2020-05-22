package com.program.position.model.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.program.position.model.enums.ActionTypeEnum;
import com.program.position.model.enums.DealTypeEnum;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author: huanglin
 * @Date: 5/20/20 8:31 PM
 * @Version 1.0
 */
@Getter
@Setter
@ApiModel("Transaction create dto model")
@NoArgsConstructor
public class TransactionCreateDTO {
    @ApiModelProperty("Transaction trade id")
    private Long tradeId;

    @ApiModelProperty("Transaction securityCode")
    @NotNull
    @Length(min = 3, max = 3)
    private String securityCode;

    @ApiModelProperty("Transaction quantity")
    @NotNull
    private Integer quantity;

    @ApiModelProperty("Transaction action type")
    @NotNull
    private ActionTypeEnum actionType;

    @ApiModelProperty("Transaction deal type")
    @NotNull
    private DealTypeEnum dealType;

    public TransactionCreateDTO(String securityCode, Integer quantity, DealTypeEnum dealType,
            ActionTypeEnum actionType, Long tradeId) {
        this.securityCode = securityCode;
        this.quantity = quantity;
        this.dealType = dealType;
        this.actionType = actionType;
        this.tradeId = tradeId;
    }
}
