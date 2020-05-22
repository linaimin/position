package com.program.position.util;

import org.mapstruct.factory.Mappers;

import com.program.position.mapper.TransactionMapper;

/**
 * @Author: huanglin
 * @Date: 5/20/20 9:16 PM
 * @Version 1.0
 */
public class MapperUtil {
    public static final TransactionMapper TRANSACTION_MAPPER = Mappers.getMapper(TransactionMapper.class);
}
