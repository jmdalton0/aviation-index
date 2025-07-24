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

/**
 * A controller to manage a user's study session.
 */
@Controller
@RequestMapping("/study")
public class StudyController {

    /**
     * A StudyService is used to manage the user's study session.
     */
    private final StudyService studyService;

    /**
     * A parameterized constructor.
     * @param service
     */
    public StudyController(StudyService service) {
        this.studyService = service;
    }

    /**
     * An endoint to view the study page with the user's current study question.
     * @param model view model.
     * @return study view.
     */
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

    /**
     * An endopint to view the filters page.
     * @param model view model.
     * @return filters view.
     */
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

    /**
     * An endpoint the update a user's study filters.
     * @param studyTopicId the new study topic data.
     * @param studyStatus the new study status data.
     * @param model view model.
     * @return redirect to the study view.
     */
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

    /**
     * An endpoint to update the user's study topic filter.
     * @param topicId the new study topic data.
     * @return redirect to the study view.
     */
    @PostMapping("/topic")
    public String setStudyTopic(@RequestParam(required = false) Long topicId) {
        Long userId = SecurityUtil.getAuthenticatedUserId();
        studyService.setStudyTopic(userId, topicId);
        return "redirect:/study";
    }

    /**
     * An endpoint to advance the user's study question to the next question.
     * @param model view model.
     * @return redirect to the study view.
     */
    @PostMapping("/prev")
    public String previousStudyQuestion(Model model) {
        Long userId = SecurityUtil.getAuthenticatedUserId();
        studyService.prevStudyQuestion(userId);
        return "redirect:/study";
    }

    /**
     * An endpoint to advance the user's study question to the next question.
     * @param model view model.
     * @return redirect to the study view.
     */
    @PostMapping("/next")
    public String nextStudyQuestion(Model model) {
        Long userId = SecurityUtil.getAuthenticatedUserId();
        studyService.nextStudyQuestion(userId);
        return "redirect:/study";
    }

}
