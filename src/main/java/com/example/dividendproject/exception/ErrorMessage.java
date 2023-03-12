package com.example.dividendproject.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class ErrorMessage {

    private final int code;

    private final String errorName;

    private final String msg;

    private final LocalDateTime timestamp;

    public ErrorMessage(Exception exception, HttpStatus status) {
        this.code = status.value();
        this.errorName = exception.getLocalizedMessage();
        this.msg = exception.getMessage();
        this.timestamp = LocalDateTime.now();
    }

    public static ErrorMessage of(Exception exception, HttpStatus status) {
        return new ErrorMessage(exception, status);
    }
}
