package com.jmdalton0.aviation_index.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jmdalton0.aviation_index.models.UserQuestion;
import com.jmdalton0.aviation_index.models.UserQuestion.Status;
import com.jmdalton0.aviation_index.services.UserQuestionService;

@Controller
@RequestMapping("/user-questions")
public class UserQuestionController {

    private UserQuestionService userQuestionService;
    
    public UserQuestionController(UserQuestionService userQuestionService) {
        this.userQuestionService = userQuestionService;
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @RequestParam("status") String status) {
        UserQuestion userQuestion = userQuestionService.findById(id);
        userQuestion.setStudyStatus(Status.valueOf(status));
        userQuestionService.save(userQuestion);
        return "redirect:/study";
    }
    
}
