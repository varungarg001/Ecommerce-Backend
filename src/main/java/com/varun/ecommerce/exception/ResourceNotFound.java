package com.varun.ecommerce.exception;

public class ResourceNotFound extends RuntimeException {

    public ResourceNotFound(String message){
        super(message);
    }
}
