package com.coffee.coffee.exception.error;

import org.springframework.http.HttpStatus;

public enum CoffeeResponseError implements ErrorResponse{


    COMPUTER_NOT_FOUND_ID( "DOCUMENT_NOT_FOUND",HttpStatus.NOT_FOUND , "Computer with id {id} not found!!!");

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

