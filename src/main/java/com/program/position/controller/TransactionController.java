package com.program.position.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.program.position.model.dto.TransactionCreateDTO;
import com.program.position.service.TransactionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Author: huanglin
 * @Date: 5/20/20 8:28 PM
 * @Version 1.0
 */
@RestController
@Api(tags = { "Transaction controller" })
@RequestMapping(value = "/api/transaction", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping(value = "/action")
    @ApiOperation("Create transaction by action datas")
    public void createByActionOfTransactionCreateDTO(
            @Valid @RequestBody TransactionCreateDTO transactionCreateDTO) throws Exception {
        transactionService.createByActionOfTransactionCreateDTO(transactionCreateDTO);
    }
}
