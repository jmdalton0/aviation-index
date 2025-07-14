package com.jmdalton0.aviation_index.web;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.jmdalton0.aviation_index.models.User;
import com.jmdalton0.aviation_index.security.SecurityConfig;
import com.jmdalton0.aviation_index.security.SecurityUserDetails;
import com.jmdalton0.aviation_index.services.UserService;

@WebMvcTest(UserController.class)
@Import(SecurityConfig.class)
public class UserControllerTest {

    @Autowired MockMvc mvc;

    @MockitoBean UserService userService;

    @Test
    @DisplayName("T.W.4: Profile page loads at GET /users")
    void profilePageLoads() throws Exception {

        SecurityUserDetails userDetails = new SecurityUserDetails(
            1L, "john", "pw",
            List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );

        User user = new User(1L, "john", "pw", "USER");
        when(userService.findById(user.getId())).thenReturn(user);

        mvc.perform(get("/users")
                .with(user(userDetails)))
            .andExpect(status().isOk())
            .andExpect(view().name("users/view"))
            .andExpect(content().string(containsString("Profile")))
            .andExpect(model().attributeExists("user"));
    }
    
}
