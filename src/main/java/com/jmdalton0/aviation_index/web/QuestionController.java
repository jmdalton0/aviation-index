package com.jmdalton0.aviation_index.web;

import com.jmdalton0.aviation_index.models.Question;
import com.jmdalton0.aviation_index.services.QuestionService;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    private QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public String index(Model model) {
        List<Question> questions = questionService.findAll();
        model.addAttribute("title", "Questions");
        model.addAttribute("questions", questions);
        return "questions/index";
    }
   
}
