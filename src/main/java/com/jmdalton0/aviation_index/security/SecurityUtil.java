package com.jmdalton0.aviation_index.security;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    public static Long getAuthenticatedUserId() {
        SecurityUserDetails userDetails = (SecurityUserDetails) SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getPrincipal();
        return userDetails.getId();
    }
    
}
