package com.jmdalton0.aviation_index.security;

import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Utility class for accessing Spring Security Session information
 */
public class SecurityUtil {

    /**
     * Get the current authenticated user for this context. 
     * @return the authenticated user.
     */
    public static Long getAuthenticatedUserId() {
        SecurityUserDetails userDetails = (SecurityUserDetails) SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getPrincipal();
        return userDetails.getId();
    }
    
}
