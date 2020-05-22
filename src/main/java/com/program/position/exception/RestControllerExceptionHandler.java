package com.program.position.exception;

import java.lang.reflect.UndeclaredThrowableException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.program.position.util.ErrorCodeUtil;

import lombok.extern.log4j.Log4j2;

/**
 * @Author: huanglin
 * @Date: 5/20/20 9:23 PM
 * @Version 1.0
 */
@RestControllerAdvice
@Log4j2
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({ ApplicationException.class })
    public ResponseEntity<Object> handleException(ApplicationException e) {
        return new ResponseEntity<>(new ApplicationError(HttpStatus.BAD_REQUEST, e.getErrorCode(), e.getErrorMessage(), e),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ UndeclaredThrowableException.class })
    public ResponseEntity<Object> handleException(UndeclaredThrowableException e) {
        log.error("System error.", e);
        if (e.getUndeclaredThrowable().getCause() instanceof ApplicationException) {
            handleException((ApplicationException) e.getUndeclaredThrowable().getCause());
        }
        return new ResponseEntity<>(
                new ApplicationError(HttpStatus.BAD_REQUEST, ErrorCodeUtil.SYSTEM_ERROR, "System error.", e),
                HttpStatus.BAD_REQUEST);
    }
}
