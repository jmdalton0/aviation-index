package com.jmdalton0.aviation_index.security;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * A wrapper class that extends Spring Security's userdetails class.
 * This class adds a field for the user's database ID,
 * simplifying other processes that utilize database transactions.
 */
public class SecurityUserDetails extends User {

    /**
     * The unique identifier used for this user in the database.
     */
    private Long id;

    /**
     * A parameterized constructor.
     * @param id
     * @param username
     * @param password
     * @param authorites
     */
    public SecurityUserDetails(
        Long id,
        String username,
        String password,
        List<GrantedAuthority> authorites
    ) {
        super(username, password, authorites);
        this.id = id;
    }

    /**
     * Get the user ID.
     * @return the unique identifier.
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the user ID.
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }
    
}
