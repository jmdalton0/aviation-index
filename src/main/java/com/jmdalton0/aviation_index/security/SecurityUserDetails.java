package com.jmdalton0.aviation_index.security;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class SecurityUserDetails extends User {

    private Long id;

    SecurityUserDetails(
        Long id,
        String username,
        String password,
        List<GrantedAuthority> authorites
    ) {
        super(username, password, authorites);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
}
