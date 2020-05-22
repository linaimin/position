package com.program.position.controller;

import java.util.List;

import javax.validation.Valid;

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.program.position.model.dto.PositionDTO;
import com.program.position.service.PositionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Author: huanglin
 * @Date: 5/20/20 8:13 PM
 * @Version 1.0
 */
@RestController
@Api(tags = { "Position controller" })
@RequestMapping(value = "/api/position", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PositionController {
    @Autowired
    private PositionService positionService;

    @GetMapping(value = "/summary")
    @ApiOperation("Summary transaction by security code")
    public ResponseEntity<List<PositionDTO>> summaryTransaction(
            @Valid @Length(min = 3, max = 3) @RequestParam(value = "securityCode", required = false) String securityCode)
            throws Exception {
        return ResponseEntity.ok(positionService.summaryTransaction(securityCode));
    }
}
