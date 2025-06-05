package com.jmdalton0.aviation_index.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jmdalton0.aviation_index.models.Question;
import com.jmdalton0.aviation_index.models.UserQuestion;
import com.jmdalton0.aviation_index.security.SecurityUtil;
import com.jmdalton0.aviation_index.services.StudyService;

@Controller
@RequestMapping("/study")
public class StudyController {

    private StudyService studyService;

    public StudyController(StudyService service) {
        this.studyService = service;
    }

    @GetMapping
    public String viewStudyQuestion(Model model) {
        Long userId = SecurityUtil.getAuthenticatedUserId();
        Question question = studyService.getStudyQuestion(userId);
        UserQuestion userQuestion = studyService.getStudyQuestionStatus(userId, question.getId());
        model.addAttribute("title", "Study");
        model.addAttribute("question", question);
        model.addAttribute("userQuestion", userQuestion);
        return "study";
    }

    @PostMapping("/prev")
    public String previousStudyQuestion(Model model) {
        Long userId = SecurityUtil.getAuthenticatedUserId();
        studyService.prevStudyQuestion(userId);
        return "redirect:/study";
    }

    @PostMapping("/next")
    public String nextStudyQuestion(Model model) {
        Long userId = SecurityUtil.getAuthenticatedUserId();
        studyService.nextStudyQuestion(userId);
        return "redirect:/study";
    }

}
