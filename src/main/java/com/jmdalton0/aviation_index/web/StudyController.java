package com.jmdalton0.aviation_index.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jmdalton0.aviation_index.models.Question;
import com.jmdalton0.aviation_index.models.Topic;
import com.jmdalton0.aviation_index.models.UserQuestion;
import com.jmdalton0.aviation_index.models.UserQuestion.Status;
import com.jmdalton0.aviation_index.security.SecurityUtil;
import com.jmdalton0.aviation_index.services.StudyService;

@Controller
@RequestMapping("/study")
public class StudyController {

    private final StudyService studyService;

    public StudyController(StudyService service) {
        this.studyService = service;
    }

    @GetMapping
    public String viewStudyQuestion(Model model) {
        Long userId = SecurityUtil.getAuthenticatedUserId();
        Question question = studyService.getStudyQuestion(userId);

        UserQuestion userQuestion = null;
        if (question != null) {
            userQuestion = studyService.getStudyQuestionStatus(userId, question.getId());
        }

        model.addAttribute("title", "Study");
        model.addAttribute("question", question);
        model.addAttribute("userQuestion", userQuestion);
        return "study";
    }

    @GetMapping("/filters")
    public String viewFilters(Model model) {
        Long userId = SecurityUtil.getAuthenticatedUserId();
        Topic studyTopic = studyService.getStudyTopic(userId);
        Status studyStatus = studyService.getStudyStatus(userId);

        model.addAttribute("title", "Filters");
        model.addAttribute("topic", studyTopic);
        model.addAttribute("status", studyStatus);
        return "filters";
    }

    @PostMapping("/filters")
    public String editFilters(
        @RequestParam(required = false) Long studyTopicId,
        @RequestParam(required = false) Status studyStatus,
        Model model
    ) {
        Long userId = SecurityUtil.getAuthenticatedUserId();
        studyService.setStudyTopic(userId, studyTopicId);
        studyService.setStudyStatus(userId, studyStatus);
        return "redirect:/study";
    }

    @PostMapping("/topic")
    public String setStudyTopic(@RequestParam(required = false) Long topicId) {
        Long userId = SecurityUtil.getAuthenticatedUserId();
        studyService.setStudyTopic(userId, topicId);
        return "redirect:/study";
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
