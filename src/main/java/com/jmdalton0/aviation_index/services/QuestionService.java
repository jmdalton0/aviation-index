package com.jmdalton0.aviation_index.services;

import java.util.List;

import org.springframework.stereotype.Service;
import com.jmdalton0.aviation_index.data.QuestionRepository;
import com.jmdalton0.aviation_index.models.Question;
import com.jmdalton0.aviation_index.services.exceptions.ResourceNotFoundException;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final UserQuestionService userQuestionService;

    public QuestionService(
        QuestionRepository questionRepository,
        UserQuestionService userQuestionService
    ) {
        this.questionRepository = questionRepository;
        this.userQuestionService = userQuestionService;
    }

    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    public List<Question> findByTopicId(Long topicId) {
        return questionRepository.findByTopicId(topicId);
    }

    public List<Question> search(String search) {
        return questionRepository.findByQuestionContainingIgnoreCaseOrAnswerContainingIgnoreCase(search, search);
    }

    public Question findById(Long id) {
        return questionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Question with id " + id + " not found"));
    }

    public Question findFirst() {
        return questionRepository.findFirstByOrderByPosition()
            .orElseThrow(() -> new ResourceNotFoundException("Questions table empty"));
    }

    public Long count() {
        return questionRepository.count();
    }

    public void save(Question question) {
        question.setPosition((count().intValue()) + 1);
        questionRepository.save(question);
    }

    public void delete(Long id) {
        userQuestionService.deleteQuestion(id);
        questionRepository.deleteById(id);
    }
   
}
