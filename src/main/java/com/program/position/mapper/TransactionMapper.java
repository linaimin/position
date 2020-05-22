package com.program.position.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.program.position.model.dto.TransactionCreateDTO;
import com.program.position.model.entity.Transaction;

/**
 * @Author: huanglin
 * @Date: 5/20/20 9:12 PM
 * @Version 1.0
 */
@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TransactionMapper {
    void mapping(TransactionCreateDTO transactionCreateDTO, @MappingTarget Transaction transaction);
}
