package com.jmdalton0.aviation_index.security.exceptions;

public class UsernameAlreadyExistsException extends RuntimeException {

    public UsernameAlreadyExistsException(String username) {
        super("Username: " + username + " already exists");
    }
    
}
