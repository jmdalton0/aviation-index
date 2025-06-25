package com.jmdalton0.aviation_index.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jmdalton0.aviation_index.models.UserQuestion;
import com.jmdalton0.aviation_index.models.UserQuestion.Status;
import com.jmdalton0.aviation_index.security.SecurityUtil;
import com.jmdalton0.aviation_index.services.UserQuestionService;

@Controller
@RequestMapping("/user-questions")
public class UserQuestionController {

    private final UserQuestionService userQuestionService;
    
    public UserQuestionController(UserQuestionService userQuestionService) {
        this.userQuestionService = userQuestionService;
    }

    @PostMapping("/reset")
    public String resetUserQuestions() {
        Long userId = SecurityUtil.getAuthenticatedUserId();
        userQuestionService.reset(userId);
        return "redirect:/reports";
    }

    @PostMapping("/{id}")
    public String editUserQuestion(@PathVariable Long id, @RequestParam String status) {
        UserQuestion userQuestion = userQuestionService.findById(id);
        userQuestion.setStudyStatus(Status.valueOf(status));
        userQuestionService.save(userQuestion);
        return "redirect:/study";
    }
    
}
