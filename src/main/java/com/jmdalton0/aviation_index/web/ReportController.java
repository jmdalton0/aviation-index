package com.jmdalton0.aviation_index.web;
import com.jmdalton0.aviation_index.services.UserQuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jmdalton0.aviation_index.models.UserQuestion.Status;
import com.jmdalton0.aviation_index.security.SecurityUtil;

@Controller
@RequestMapping("/reports")
public class ReportController {

    private final UserQuestionService userQuestionService;

    ReportController(UserQuestionService userQuestionService) {
        this.userQuestionService = userQuestionService;
    }

    @GetMapping
    public String index(Model model) {
        Long userId = SecurityUtil.getAuthenticatedUserId();

        String percNew = userQuestionService.calcStudyStatusPercentage(userId, Status.NEW);
        String percFocused = userQuestionService.calcStudyStatusPercentage(userId, Status.FOCUSED);
        String percLearning = userQuestionService.calcStudyStatusPercentage(userId, Status.LEARNING);
        String percMastered = userQuestionService.calcStudyStatusPercentage(userId, Status.MASTERED);

        model.addAttribute("title", "Reports");
        model.addAttribute("percNew", percNew);
        model.addAttribute("percFocused", percFocused);
        model.addAttribute("percLearning", percLearning);
        model.addAttribute("percMastered", percMastered);
        return "reports";
    }
    
}
