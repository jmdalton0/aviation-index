package com.jmdalton0.aviation_index.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jmdalton0.aviation_index.data.UserQuestionRepository;
import com.jmdalton0.aviation_index.models.UserQuestion;
import com.jmdalton0.aviation_index.models.UserQuestion.Status;
import com.jmdalton0.aviation_index.services.exceptions.ResourceNotFoundException;

/**
 * A service class that manages user question data.
 */
@Service
public class UserQuestionService {

    /**
     * A UserQuestionRepository is used to enact database transactions.
     */
    private final UserQuestionRepository userQuestionRepository;

    /**
     * A parameterized constructor.
     * @param userQuestionRepository
     */
    public UserQuestionService(
        UserQuestionRepository userQuestionRepository
    ) {
        this.userQuestionRepository = userQuestionRepository;
    }

    /**
     * Find all user questions.
     * @return a list of all user questions.
     */
    public List<UserQuestion> findAll() {
        return userQuestionRepository.findAll();
    }

    /**
     * Find all user questions that belong to a parent user.
     * @param userId the parent user ID.
     * @return a list of user questions that belong to the specified user.
     */
    public List<UserQuestion> findByUserId(Long userId) {
        return userQuestionRepository.findByUserId(userId);
    }

    /**
     * Find a user question by its ID.
     * @param id the user question ID.
     * @return the user question.
     */
    public UserQuestion findById(Long id) {
        return userQuestionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("UserQuestion with id " + id + " not found"));
    }

    /**
     * Find a user question by parent user ID and associated question ID.
     * @param userId the parent user ID.
     * @param questionId the associated question ID.
     * @return the user question.
     */
    public UserQuestion findByUserIdAndQuestionId(Long userId, Long questionId) {
        return userQuestionRepository.findByUserIdAndQuestionId(userId, questionId)
            .orElseThrow(() -> new ResourceNotFoundException("UserQuestion with userId " + userId + " not found"));
    }

    /**
     * Find the active user question previous to the current study question belonging to a specified user,
     * according to the global question order.
     * @param userId the user ID.
     * @param curPosition the position of the current study question belonging to the user.
     * @return the user question associated with the next lowest order question that is also active in the specified user's study session.
     */
    public UserQuestion findPrevByUserId(Long userId, int curPosition) {
        return userQuestionRepository.findPrevByUserId(userId, curPosition)
            .or(() -> userQuestionRepository.findLastByUserId(userId))
            .orElseThrow(() -> new ResourceNotFoundException("No active UserQuestion was found"));
    }

    /**
     * Find the active user question after the current study question belonging to a specified user,
     * according to the global question order.
     * @param userId the user ID.
     * @param curPosition the position of the current study question belonging to the user.
     * @return the user question associated with the next highest order question that is also active in the specified user's study session.
     */
    public UserQuestion findNextByUserId(Long userId, int curPosition) {
        return userQuestionRepository.findNextByUserId(userId, curPosition)
            .or(() -> userQuestionRepository.findFirstByUserId(userId))
            .orElseThrow(() -> new ResourceNotFoundException("No active UserQuestion was found"));
    }

    /**
     * Reset all user questions that belong to a specific user.
     * User questions that are reset are assigned a study status of 'NEW'.
     * @param userId the user ID.
     */
    public void reset(Long userId) {
        userQuestionRepository.reset(userId);
    }

    /**
     * Add a new or update an existing user question.
     * @param userQuestion the user question to add or update.
     */
    public void save(UserQuestion userQuestion) {
        userQuestionRepository.save(userQuestion);
    }

    /**
     * Delete all user questions associated with a question.
     * @param questionId the question ID.
     */
    public void deleteQuestion(Long questionId) {
        userQuestionRepository.deleteByQuestionId(questionId);
    }
 
    /**
     * Calculate the percentage of user questions belonging to a user that have been assigned a specified status.
     * @param userId the parent user ID.
     * @param status the specified study status.
     * @return the percentage of a user's user questions that have been assigned a specified status.
     */
    public String calcStudyStatusPercentage(Long userId, Status status) {

        // total user questions that belong to the user
        double total = userQuestionRepository.countByUserId(userId);

        // total user questions that belong to the user with the given status
        double numStatus = userQuestionRepository.countByUserIdAndStudyStatus(userId, status);

        // calculated percentage
        double perc = numStatus / total * 100;

        return "%.2f".formatted(perc);
    }

}
