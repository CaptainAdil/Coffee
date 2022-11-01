package com.coffee.coffee.exception;

import com.coffee.coffee.exception.error.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;
import java.util.Map;

@Slf4j
public class CoffeeException extends RuntimeException {
    private final ErrorResponse error;
    private final Map<String, Object> messageArguments;


    public CoffeeException(ErrorResponse error, Map<String, Object> messageArguments) {
        this.error = error;
        this.messageArguments = messageArguments;
    }


    public CoffeeException(ErrorResponse error){
        this.error = error;
        this.messageArguments = Map.of();
    }


    public ErrorResponse getError() {
        return error;
    }

    @Override
    public String getMessage() {
        return messageArguments.isEmpty() ? error.getMessage() :
                StringSubstitutor.replace(error.getMessage(), messageArguments, "{", "}");
    }

    public String getLocalizedMessage(Locale locale, MessageSource messageSource) {
        try {
            String localizedMessage = messageSource.getMessage(error.getKey(), new Object[]{}, locale);
            return messageArguments.isEmpty()?localizedMessage:
                    StringSubstitutor.replace(localizedMessage, messageArguments, "{","}");
        }catch (NoSuchMessageException exception){
            log.warn("Please consider adding localized message for key {} and locale {}",
                    error.getKey(), locale);
        }
        return getMessage();
    }
}
