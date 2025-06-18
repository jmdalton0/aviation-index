package com.jmdalton0.aviation_index.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jmdalton0.aviation_index.models.Question;
import com.jmdalton0.aviation_index.models.Topic;
import com.jmdalton0.aviation_index.services.QuestionService;
import com.jmdalton0.aviation_index.services.TopicService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
@RequestMapping("/topics")
public class TopicController {

    private TopicService topicService;
    private QuestionService questionService;

    public TopicController(
        TopicService topicService,
        QuestionService questionService
    ) {
        this.topicService = topicService;
        this.questionService = questionService;
    }

    @GetMapping
    public String index(Model model) {
        List<Topic> subTopics = topicService.findByParentId(null);
        model.addAttribute("title", "Topics");
        model.addAttribute("subTopics", subTopics);
        return "topics/index";
    }

    @GetMapping("/{id}")
    public String viewTopic(@PathVariable Long id, Model model) {
        Topic topic = topicService.findById(id);
        List<Topic> parents = topicService.findParentsById(id);
        List<Topic> subTopics = topicService.findByParentId(id);
        List<Question> questions = questionService.findByTopicId(id);
        model.addAttribute("title", "Topics");
        model.addAttribute("topic", topic);
        model.addAttribute("parentTopics", parents);
        model.addAttribute("subTopics", subTopics);
        model.addAttribute("questions", questions);
        return "topics/view";
    }

    @GetMapping("/{id}/edit")
    public String viewEditTopic(@PathVariable Long id, Model model) {
        Topic topic = topicService.findById(id);
        model.addAttribute("title", "Edit Topic");
        model.addAttribute("topic", topic);
        return "topics/edit";
    }
    
}
