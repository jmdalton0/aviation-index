package com.jmdalton0.aviation_index.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security configuration class.
 */
@Configuration
public class SecurityConfig {

    /**
     * The filter chain that specifies the security rules for the application.
     * The home, login, and register pages are public.
     * Users can access the majority of pages.
     * Admins only can access pages that allow making changes to the aviation knowledge content.
     * @param http Injected by Spring
     * @return the configured httpSecurity object
     * @throws Exception
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                
                // public pages
                .requestMatchers("/", "/login", "/register", "/css/**", "/img/**").permitAll()

                // topics and questions pages available with ROLE_USER 
                .requestMatchers(
                    HttpMethod.GET,
                    "/topics",
                    "/topics/{id:[0-9]+}",
                    "/questions",
                    "/questions/search"
                ).authenticated()

                // topics and questions pages available with ROLE_ADMIN
                .requestMatchers("/topics/**", "/questions/**").hasRole("ADMIN")

                // all other pages require authentication
                .anyRequest().authenticated()
            )
            .formLogin(login -> login
                .loginPage("/login") // see login view
                .defaultSuccessUrl("/", true) // navigate to home page after login
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/") // navigate to home page after logout
                .invalidateHttpSession(true)
                .permitAll()
            );
        return http.build();
    }

    /**
     * Configures hierarchical relationships between security roles.
     * An Admin is also considered a regular User.
     * @return the configured RoleHierarchy implementation.
     */
    @Bean
    RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.withDefaultRolePrefix()
            .role("ADMIN").implies("USER")
            .build();
    }

    /**
     * Configures the password encoder used to encrypt passwords.
     * @return a BCrypt password encoder instance.
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
}
