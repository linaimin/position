package com.program.position.model.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: huanglin
 * @Date: 5/20/20 9:41 PM
 * @Version 1.0
 */
@Getter
@Setter
@ApiModel("Position dto model")
public class PositionDTO {
    private String securityCode;
    private Long summaryResult;
}
