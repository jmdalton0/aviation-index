package com.jmdalton0.aviation_index.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jmdalton0.aviation_index.models.Question;
import com.jmdalton0.aviation_index.security.SecurityUtil;
import com.jmdalton0.aviation_index.services.StudyService;

@Controller
@RequestMapping("/study")
public class StudyController {

    private StudyService service;

    public StudyController(StudyService service) {
        this.service = service;
    }

    @GetMapping
    public String viewNextQuestion(Model model) {
        Long userId = SecurityUtil.getAuthenticatedUserId();
        Question question = service.findStudyQuestion(userId);
        model.addAttribute("title", "Study");
        model.addAttribute("question", question);
        return "study";
    }

    @PostMapping("/prev")
    public String previousStudyQuestion(Model model) {
        Long userId = SecurityUtil.getAuthenticatedUserId();
        service.prevStudyQuestion(userId);
        return "redirect:/study";
    }

    @PostMapping("/next")
    public String nextStudyQuestion(Model model) {
        Long userId = SecurityUtil.getAuthenticatedUserId();
        service.nextStudyQuestion(userId);
        return "redirect:/study";
    }

}
