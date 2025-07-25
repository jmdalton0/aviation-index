package com.jmdalton0.aviation_index.web;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.jmdalton0.aviation_index.security.SecurityConfig;
import com.jmdalton0.aviation_index.services.QuestionService;

@WebMvcTest(QuestionController.class)
@Import(SecurityConfig.class)
public class QuestionControllerTest {

    @Autowired MockMvc mvc;

    @MockitoBean QuestionService questionService;

    @Test
    @DisplayName("T.W.8: Questions page loads at GET /questions")
    @WithMockUser(roles = "USER")
    void questionsPageLoads() throws Exception {
        mvc.perform(get("/questions"))
            .andExpect(status().isOk())
            .andExpect(view().name("questions/index"))
            .andExpect(content().string(containsString("Questions")))
            .andExpect(model().attributeExists("questions"));
    }
    
}
