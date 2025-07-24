package com.jmdalton0.aviation_index.web;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jmdalton0.aviation_index.models.Question;
import com.jmdalton0.aviation_index.models.Topic;
import com.jmdalton0.aviation_index.services.QuestionService;
import com.jmdalton0.aviation_index.services.TopicService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * A controller for managing topics.
 */
@Controller
@RequestMapping("/topics")
public class TopicController {

    /**
     * A TopicService and QuestionService are used to manage topics and child questions.
     */
    private final TopicService topicService;
    private final QuestionService questionService;

    /**
     * A parameterized constructor.
     * @param topicService
     * @param questionService
     */
    public TopicController(
        TopicService topicService,
        QuestionService questionService
    ) {
        this.topicService = topicService;
        this.questionService = questionService;
    }

    /**
     * An endpoint to view the top level topics page.
     * @param model view model.
     * @return top level topics view.
     */
    @GetMapping
    public String index(Model model) {
        List<Topic> subTopics = topicService.findByParentId(null);
        model.addAttribute("title", "Topics");
        model.addAttribute("subTopics", subTopics);
        return "topics/index";
    }

    /**
     * An endpoint to view a sub topic page.
     * @param id the topic ID.
     * @param model view model.
     * @return sub topic view.
     */
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

    /**
     * An endpoint to view a form for creating a new top level topic.
     * @param model view model.
     * @return new top level topic form view.
     */
    @GetMapping("/new")
    public String viewNewTopic(Model model) {
        model.addAttribute("title", "New Topic");
        return "topics/new";
    }

    /**
     * An endpoint to view a form for creating a new sub topic.
     * @param parentId the parent topic ID.
     * @param model view model.
     * @return new sub topic form view.
     */
    @GetMapping("/new/parent/{parentId}")
    public String viewNewTopic(@PathVariable Long parentId, Model model) {
        model.addAttribute("title", "New Topic");
        model.addAttribute("parentId", parentId);
        return "topics/new";
    }

    /**
     * An endpoint to view a form for editing a topic.
     * @param id the topic ID.
     * @param model view model.
     * @return edit topic form view.
     */
    @GetMapping("/edit/{id}")
    public String viewEditTopic(@PathVariable Long id, Model model) {
        Topic topic = topicService.findById(id);
        model.addAttribute("title", "Edit Topic");
        model.addAttribute("topic", topic);
        return "topics/edit";
    }

    /**
     * An endpoint to create a new topic.
     * @param parentId the parent topic ID.
     * @param topic the topic data.
     * @return redirect to the parent topic view.
     */
    @PostMapping("/new")
    public String newTopic(
        @RequestParam(required = false) Long parentId,
        @RequestParam String topic
    ) {
        Topic t = new Topic();
        t.setParentId(parentId);
        t.setTopic(topic);
        topicService.save(t);
        if (parentId == null) {
            return "redirect:/topics";
        } else {
            return "redirect:/topics/" + parentId;
        }
    }

    /**
     * An endpoint to edit a topic.
     * @param id the topic ID.
     * @param topic the topic data.
     * @return redirect to the topic view.
     */
    @PostMapping("/edit/{id}")
    public String editTopic(@PathVariable Long id, @RequestParam String topic) {
        Topic t = topicService.findById(id);
        t.setTopic(topic);
        topicService.save(t);
        return "redirect:/topics/" + id;
    }

    /**
     * An endpoint to delete a topic.
     * @param id the topic ID.
     * @param model view model.
     * @return redirect to the parent topic view if successful, else the given topic view.
     */
    @PostMapping("/delete/{id}")
    public String deleteTopic(@PathVariable Long id, Model model) {
        Topic topic = topicService.findById(id);
        try {
            topicService.delete(id);
        } catch (DataIntegrityViolationException e) {
            List<Topic> parents = topicService.findParentsById(id);
            List<Topic> subTopics = topicService.findByParentId(id);
            List<Question> questions = questionService.findByTopicId(id);
            model.addAttribute("title", "Topics");
            model.addAttribute("topic", topic);
            model.addAttribute("parentTopics", parents);
            model.addAttribute("subTopics", subTopics);
            model.addAttribute("questions", questions);
            model.addAttribute("error", "Unable to delete. Subtopics or child questions are present.");
            return "topics/view";
        }
        if (topic.getParentId() == null) {
            return "redirect:/topics";
        }
        return "redirect:/topics/" + topic.getParentId();
    }

}
