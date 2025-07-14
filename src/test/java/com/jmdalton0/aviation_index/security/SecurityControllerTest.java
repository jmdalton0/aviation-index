package com.jmdalton0.aviation_index.security;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(SecurityController.class)
@Import(SecurityConfig.class)
public class SecurityControllerTest {

    @Autowired MockMvc mvc;

    @MockitoBean SecurityUserDetailsService securityUserDetailsService;

    @Test
    @DisplayName("T.W.2: Register page loads at GET /register")
    void registerPageLoads() throws Exception {
        mvc.perform(get("/register"))
            .andExpect(status().isOk())
            .andExpect(view().name("security/register"))
            .andExpect(content().string(containsString("Register")));
    }
 
    @Test
    @DisplayName("T.W.3: Login page loads at GET /login")
    void loginPageLoads() throws Exception {
        mvc.perform(get("/login"))
            .andExpect(status().isOk())
            .andExpect(view().name("security/login"))
            .andExpect(content().string(containsString("Login")));
    }
    
}
