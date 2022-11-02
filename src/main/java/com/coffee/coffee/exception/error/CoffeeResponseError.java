package com.coffee.coffee.exception.error;

import org.springframework.http.HttpStatus;

public enum CoffeeResponseError implements ErrorResponse{


    USER_NOT_FOUND_ID("USER_NOT_FOUND",HttpStatus.NOT_FOUND,"User with id {id} not found");

    final String key;
    final HttpStatus httpStatus;
    final String message;


     CoffeeResponseError(String key, HttpStatus httpStatus, String message) {

        this.key = key;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public String getKey() {
        return key;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}

