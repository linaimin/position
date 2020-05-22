package com.program.position.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: huanglin
 * @Date: 5/20/20 9:18 PM
 * @Version 1.0
 */
@Getter
@AllArgsConstructor
public class ApplicationException extends RuntimeException {
    private static final long serialVersionUID = 2112403852259986396L;
    private final String errorCode;
    private final String errorMessage;
}
