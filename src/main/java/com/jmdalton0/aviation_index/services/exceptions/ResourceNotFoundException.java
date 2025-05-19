package com.jmdalton0.aviation_index.services.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceNotFoundException(Long id, String type) {
        super(type + ": " + id + " not found");
    }
    
}
