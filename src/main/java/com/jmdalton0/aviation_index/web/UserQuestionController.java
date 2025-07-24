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

/**
 * A controller for managing user questions.
 */
@Controller
@RequestMapping("/user-questions")
public class UserQuestionController {

    /**
     * A UserQuestionService is used to manage user questions.
     */
    private final UserQuestionService userQuestionService;
    
    /**
     * A paramaterized constructor.
     * @param userQuestionService
     */
    public UserQuestionController(UserQuestionService userQuestionService) {
        this.userQuestionService = userQuestionService;
    }

    /**
     * An endpoint to reset all user questions belonging to a user.
     * @return redirect to the reports view.
     */
    @PostMapping("/reset")
    public String resetUserQuestions() {
        Long userId = SecurityUtil.getAuthenticatedUserId();
        userQuestionService.reset(userId);
        return "redirect:/reports";
    }

    /**
     * An endpoint to edit a user question.
     * @param id the user question ID.
     * @param status the new status data.
     * @return redirect to the study view.
     */
    @PostMapping("/{id}")
    public String editUserQuestion(@PathVariable Long id, @RequestParam String status) {
        UserQuestion userQuestion = userQuestionService.findById(id);
        userQuestion.setStudyStatus(Status.valueOf(status));
        userQuestionService.save(userQuestion);
        return "redirect:/study";
    }
    
}
