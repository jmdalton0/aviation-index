package com.jmdalton0.aviation_index.web;

import com.jmdalton0.aviation_index.data.QuestionRepository;
import com.jmdalton0.aviation_index.models.Question;
import com.jmdalton0.aviation_index.services.QuestionService;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService, QuestionRepository questionRepository) {
        this.questionService = questionService;
    }

    @GetMapping
    public String index(Model model) {
        List<Question> questions = questionService.findAll();
        model.addAttribute("title", "Questions");
        model.addAttribute("questions", questions);
        return "questions/index";
    }

    @GetMapping("/search")
    public String search(@RequestParam String search, Model model) {
        List<Question> questions = questionService.search(search);
        model.addAttribute("title", "Questions");
        model.addAttribute("questions", questions);
        return "questions/index";
    }

    @GetMapping("/edit/{id}")
    public String viewEditQuestion(@PathVariable Long id, Model model) {
        Question question = questionService.findById(id);
        model.addAttribute("title", "Edit Question");
        model.addAttribute("question", question);
        return "questions/edit";
    }

    @PostMapping("/edit/{id}")
    public String editQuestion(
        @PathVariable Long id,
        @RequestParam String question,
        @RequestParam String answer 
    ) {
        Question q = questionService.findById(id);
        q.setQuestion(question);
        q.setAnswer(answer);
        questionService.save(q);
        return "redirect:/topics/" + q.getTopicId();
    }

    @PostMapping("/delete/{id}")
    public String deleteQuestion(@PathVariable Long id, Model model) {
        Question question = questionService.findById(id);
        questionService.delete(id);
        return "redirect:/topics/" + question.getTopicId();
    }

}
