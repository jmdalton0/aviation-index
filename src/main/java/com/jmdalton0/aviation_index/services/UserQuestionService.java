package com.jmdalton0.aviation_index.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jmdalton0.aviation_index.data.UserQuestionRepository;
import com.jmdalton0.aviation_index.models.UserQuestion;
import com.jmdalton0.aviation_index.models.UserQuestion.Status;
import com.jmdalton0.aviation_index.services.exceptions.ResourceNotFoundException;

@Service
public class UserQuestionService {

    private final UserQuestionRepository userQuestionRepository;

    public UserQuestionService(
        UserQuestionRepository userQuestionRepository
    ) {
        this.userQuestionRepository = userQuestionRepository;
    }

    public List<UserQuestion> findAll() {
        return userQuestionRepository.findAll();
    }

    public List<UserQuestion> findByUserId(Long userId) {
        return userQuestionRepository.findByUserId(userId);
    }

    public UserQuestion findById(Long id) {
        return userQuestionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("UserQuestion with id " + id + " not found"));
    }

    public UserQuestion findByUserIdAndQuestionId(Long userId, Long questionId) {
        return userQuestionRepository.findByUserIdAndQuestionId(userId, questionId)
            .orElseThrow(() -> new ResourceNotFoundException("UserQuestion with userId " + userId + " not found"));
    }

    public UserQuestion findPrevByUserId(Long userId, int curPosition) {
        return userQuestionRepository.findPrevByUserId(userId, curPosition)
            .or(() -> userQuestionRepository.findLastByUserId(userId))
            .orElseThrow(() -> new ResourceNotFoundException("No active UserQuestion was found"));
    }

    public UserQuestion findNextByUserId(Long userId, int curPosition) {
        return userQuestionRepository.findNextByUserId(userId, curPosition)
            .or(() -> userQuestionRepository.findFirstByUserId(userId))
            .orElseThrow(() -> new ResourceNotFoundException("No active UserQuestion was found"));
    }

    public void reset(Long userId) {
        userQuestionRepository.reset(userId);
    }

    public void save(UserQuestion userQuestion) {
        userQuestionRepository.save(userQuestion);
    }

    public void deleteQuestion(Long questionId) {
        userQuestionRepository.deleteByQuestionId(questionId);
    }
 
    public String calcStudyStatusPercentage(Long userId, Status status) {
        double total = userQuestionRepository.countByUserId(userId);
        double numStatus = userQuestionRepository.countByUserIdAndStudyStatus(userId, status);
        double perc = numStatus / total * 100;
        return "%.2f".formatted(perc);
    }

}
