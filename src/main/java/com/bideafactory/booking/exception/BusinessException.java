package com.bideafactory.booking.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {

    private final int statusCode;
    private final HttpStatus error;

    public BusinessException(int statusCode, HttpStatus error, String message) {
        super(message);
        this.statusCode = statusCode;
        this.error = error;
    }
}
