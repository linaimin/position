package com.program.position.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: huanglin
 * @Date: 5/20/20 9:21 PM
 * @Version 1.0
 */
@Getter
@Setter
public class ApplicationError {
    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime timestamp;
    private String errorCode;
    private String message;
    private String debugMessage;

    private ApplicationError() {
        timestamp = LocalDateTime.now();
    }

    ApplicationError(HttpStatus status, String errorCode, Throwable ex) {
        this();
        this.message = "Unexpected error";
        this.status = status;
        this.errorCode = errorCode;
        this.debugMessage = ex.getLocalizedMessage();
    }

    ApplicationError(HttpStatus status, String errorCode, String message, Throwable ex) {
        this();
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }
}
