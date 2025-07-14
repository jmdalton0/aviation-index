package com.jmdalton0.aviation_index.web;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

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
import com.jmdalton0.aviation_index.services.TopicService;

@WebMvcTest(TopicController.class)
@Import(SecurityConfig.class)
public class TopicControllerTest {

    @Autowired MockMvc mvc;

    @MockitoBean TopicService topicService;
    @MockitoBean QuestionService questionService;

    @Test
    @DisplayName("T.W.7: Topics index page loads at GET /topics")
    @WithMockUser(roles = "USER")
    void topicsPageLoads() throws Exception {
        mvc.perform(get("/topics"))
            .andExpect(status().isOk())
            .andExpect(view().name("topics/index"))
            .andExpect(content().string(containsString("Topics")))
            .andExpect(model().attributeExists("subTopics"));
    }
    
}

