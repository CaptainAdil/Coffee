package com.coffee.coffee.exception.error;

import org.springframework.http.HttpStatus;

public interface ErrorResponse {
    public String getKey();
    public HttpStatus getHttpStatus();
    public String getMessage();
}
