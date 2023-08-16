package com.library.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotFoundException extends ResponseStatusException {
    public NotFoundException(String message){
        super(HttpStatus.NOT_FOUND, message);
    }
    public NotFoundException(){
        super(HttpStatus.NOT_FOUND, "Resource not found");
    }


}
