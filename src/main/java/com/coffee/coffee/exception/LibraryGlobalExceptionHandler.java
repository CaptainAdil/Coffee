package com.coffee.coffee.exception;


import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class LibraryGlobalExceptionHandler extends DefaultErrorAttributes {

    private final MessageSource messageSource;

    public LibraryGlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }



    @ExceptionHandler(CoffeeException.class)
    public ResponseEntity<Map<String, Object>> handle(CoffeeException ex,
                                                      WebRequest request) {
        return ofType(request, ex.getError().getHttpStatus(), ex);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<Map<String, Object>> handle(
            MethodArgumentNotValidException ex,
            WebRequest request) {
        List<ConstraintsViolationError> validationErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new ConstraintsViolationError(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
        return ofType(request, HttpStatus.BAD_REQUEST, ex.getMessage(), null, validationErrors);
    }

    @ExceptionHandler({
            javax.validation.ConstraintViolationException.class,
            org.hibernate.exception.ConstraintViolationException.class,
            DataIntegrityViolationException.class})
    public final ResponseEntity<Map<String, Object>> handle(Exception ex, WebRequest request){
        String message = NestedExceptionUtils.getMostSpecificCause(ex).getMessage();
        Map<String, Object> attributes = getErrorAttributes(request, ErrorAttributeOptions.defaults());
        attributes.put(HttpResponseConstants.STATUS, HttpStatus.INTERNAL_SERVER_ERROR);
        attributes.put(HttpResponseConstants.MESSAGE, message);
        attributes.put(HttpResponseConstants.TIMESTAMP, new Date());
        attributes.put(HttpResponseConstants.PATH, ((ServletWebRequest) request).getRequest().getRequestURL());
        return new ResponseEntity<>(attributes, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ RuntimeException.class})
    public final ResponseEntity<Map<String, Object>> handle(RuntimeException ex, WebRequest request) {
        Map<String, Object> attributes = getErrorAttributes(request, ErrorAttributeOptions.defaults());
        attributes.put(HttpResponseConstants.STATUS, HttpStatus.INTERNAL_SERVER_ERROR);
        attributes.put(HttpResponseConstants.MESSAGE, ex.getLocalizedMessage());
        attributes.put(HttpResponseConstants.TIMESTAMP, new Date());
        attributes.put(HttpResponseConstants.PATH, ((ServletWebRequest) request).getRequest().getRequestURL());
        return new ResponseEntity<>(attributes, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    protected ResponseEntity<Map<String, Object>> ofType(WebRequest request,
                                                         HttpStatus status, CoffeeException ex) {
        return ofType(request, status, ex.getLocalizedMessage(LocaleContextHolder.getLocale(), messageSource),
                ex.getError().getKey(), Collections.EMPTY_LIST);
    }

    private ResponseEntity<Map<String, Object>> ofType(WebRequest request, HttpStatus status, String message,
                                                       String key, List validationErrors) {
        Map<String, Object> attributes = getErrorAttributes(request, ErrorAttributeOptions.defaults());
        attributes.put(HttpResponseConstants.STATUS, status.value());
        attributes.put(HttpResponseConstants.ERROR, status);
        attributes.put(HttpResponseConstants.MESSAGE, message);
        attributes.put(HttpResponseConstants.ERRORS, validationErrors);
        attributes.put(HttpResponseConstants.ERROR_KEY, key);
        attributes.put(HttpResponseConstants.TIMESTAMP, new Date());
        attributes.put(HttpResponseConstants.PATH, ((ServletWebRequest) request).getRequest().getRequestURI());
        return new ResponseEntity<>(attributes, status);
    }

}
