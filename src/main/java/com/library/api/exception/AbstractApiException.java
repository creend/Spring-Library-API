package com.library.api.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public abstract class AbstractApiException extends RuntimeException {

    private final HttpStatus code;
    private final String description;
    private final String message;
    private final Throwable cause;

    public AbstractApiException(String message, String description, HttpStatus code) {
        super(message, null);
        this.code = code;
        this.message = message;
        this.description = description;
        this.cause = null;
    }

    public AbstractApiException(String message, String description, HttpStatus code, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
        this.description = description;
        this.cause = cause;
    }
}