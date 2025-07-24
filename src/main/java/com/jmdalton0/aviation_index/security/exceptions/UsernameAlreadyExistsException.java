package com.jmdalton0.aviation_index.security.exceptions;

/**
 * An Exception to be thrown when a username has already been used by another user.
 */
public class UsernameAlreadyExistsException extends RuntimeException {

    public UsernameAlreadyExistsException(String username) {
        super("Username: " + username + " already exists");
    }
    
}
