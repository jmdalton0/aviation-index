package com.jmdalton0.aviation_index.services;

import java.util.List;

import org.springframework.stereotype.Service;
import com.jmdalton0.aviation_index.data.QuestionRepository;
import com.jmdalton0.aviation_index.models.Question;
import com.jmdalton0.aviation_index.services.exceptions.ResourceNotFoundException;

/**
 * A service class that manages question data.
 */
@Service
public class QuestionService {

    /**
     * A QuestionRepository is used to enact database transactions
     */
    private final QuestionRepository questionRepository;

    /**
     * A UserQuestionSerivce is used for operations involving study sessions advancement.
     */
    private final UserQuestionService userQuestionService;

    /**
     * A parameterized constructor.
     * @param questionRepository
     * @param userQuestionService
     */
    public QuestionService(
        QuestionRepository questionRepository,
        UserQuestionService userQuestionService
    ) {
        this.questionRepository = questionRepository;
        this.userQuestionService = userQuestionService;
    }

    /**
     * Find all questions.
     * @return a list of all questions.
     */
    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    /**
     * Find all questions that belong to a parent topic.
     * @param topicId the parent topic ID.
     * @return a list of questions that belong to a parent topic.
     */
    public List<Question> findByTopicId(Long topicId) {
        return questionRepository.findByTopicId(topicId);
    }

    /**
     * Search for questions using a search term.
     * The search term is applied to question value as well as the answer.
     * The search is case insensitive and includes non-complete matches.
     * @param search the search term.
     * @return a list of questions that match the search criteria.
     */
    public List<Question> search(String search) {
        return questionRepository.findByQuestionContainingIgnoreCaseOrAnswerContainingIgnoreCase(search, search);
    }

    /**
     * Find a question by its ID.
     * @param id the question ID.
     * @return the question.
     */
    public Question findById(Long id) {
        return questionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Question with id " + id + " not found"));
    }

    /**
     * Find the first question according to the global order.
     * @return the first question.
     */
    public Question findFirst() {
        return questionRepository.findFirstByOrderByPosition()
            .orElseThrow(() -> new ResourceNotFoundException("Questions table empty"));
    }

    /**
     * Count the number of questions.
     * @return the number of questions.
     */
    public Long count() {
        return questionRepository.count();
    }

    /**
     * Add a new or update an existing question.
     * @param question the question to add or update.
     */
    public void save(Question question) {
        question.setPosition((count().intValue()) + 1);
        questionRepository.save(question);
    }

    /**
     * Delete an existing question
     * @param id the question ID.
     */
    public void delete(Long id) {
        userQuestionService.deleteQuestion(id);
        questionRepository.deleteById(id);
    }
   
}
