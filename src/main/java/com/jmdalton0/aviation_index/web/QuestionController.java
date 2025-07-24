package com.jmdalton0.aviation_index.web;

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

/**
 * A controller for managing questions.
 */
@Controller
@RequestMapping("/questions")
public class QuestionController {

    /**
     * A QuestionService is used to manage questions.
     */
    private final QuestionService questionService;

    /**
     * A parameterized constructor.
     * @param questionService
     */
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    /**
     * An endpoint to view the questions page.
     * @param model view model.
     * @return questions page view.
     */
    @GetMapping
    public String index(Model model) {
        List<Question> questions = questionService.findAll();
        model.addAttribute("title", "Questions");
        model.addAttribute("questions", questions);
        return "questions/index";
    }

    /**
     * An endpoint to view the questions page, filtered by a search term.
     * @param search the search term filter.
     * @param model view model.
     * @return questions page view.
     */
    @GetMapping("/search")
    public String search(@RequestParam String search, Model model) {
        List<Question> questions = questionService.search(search);
        model.addAttribute("title", "Questions");
        model.addAttribute("questions", questions);
        return "questions/index";
    }

    /**
     * An endpoint to view a form for creating a new question.
     * @param topicId the parent topic ID.
     * @param model view model.
     * @return new question form view.
     */
    @GetMapping("/new/topic/{topicId}")
    public String viewNewQuestion(@PathVariable Long topicId, Model model) {
        model.addAttribute("title", "New Question");
        model.addAttribute("topicId", topicId);
        return "questions/new";
    }

    /**
     * An endpoint to view a form for editing a question.
     * @param id the question ID.
     * @param model view model.
     * @return edit question form view.
     */
    @GetMapping("/edit/{id}")
    public String viewEditQuestion(@PathVariable Long id, Model model) {
        Question question = questionService.findById(id);
        model.addAttribute("title", "Edit Question");
        model.addAttribute("question", question);
        return "questions/edit";
    }

    /**
     * An endpoint to create a new question.
     * @param topicId the parent topic ID.
     * @param question the new question data.
     * @param answer the new answer data.
     * @return redirect to the parent topic view.
     */
    @PostMapping("/new")
    public String newQuestion(
        @RequestParam Long topicId,
        @RequestParam String question,
        @RequestParam String answer 
    ) {
        Question q = new Question();
        q.setTopicId(topicId);
        q.setQuestion(question);
        q.setAnswer(answer);
        questionService.save(q);
        return "redirect:/topics/" + topicId;
    }

    /**
     * An endpoint to edit a question.
     * @param id the question ID.
     * @param question the new question data.
     * @param answer the new answer data.
     * @return redirect to the parent topic view.
     */
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

    /**
     * An endpoint to delete a question.
     * @param id the question ID.
     * @return redirect to the parent topic view.
     */
    @PostMapping("/delete/{id}")
    public String deleteQuestion(@PathVariable Long id) {
        Question question = questionService.findById(id);
        questionService.delete(id);
        return "redirect:/topics/" + question.getTopicId();
    }

}
