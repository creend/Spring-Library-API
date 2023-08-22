package com.library.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotFoundException extends AbstractApiException {

    public NotFoundException(String description){
        super("Resource not found",description,HttpStatus.NOT_FOUND);
    }
    public NotFoundException(){
        super("Resource not found",null,HttpStatus.NOT_FOUND);
    }


}
