package com.usermodule.utility.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends  RuntimeException{

    private final long serialVersionUId = 1L;

    private final String message;

    private final HttpStatus httpStatus;

    public CustomException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
