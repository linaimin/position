package com.program.position.service;

import java.util.List;

import com.program.position.model.dto.PositionDTO;

/**
 * @Author: huanglin
 * @Date: 5/20/20 8:12 PM
 * @Version 1.0
 */
public interface PositionService {
    List<PositionDTO> summaryTransaction(String securityCode) throws Exception;
}
