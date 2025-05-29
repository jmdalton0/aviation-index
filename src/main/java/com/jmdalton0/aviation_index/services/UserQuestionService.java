package com.jmdalton0.aviation_index.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jmdalton0.aviation_index.data.UserQuestionRepository;
import com.jmdalton0.aviation_index.models.UserQuestion;
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

    public UserQuestion findById(Long id) {
        return userQuestionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("UserQuestion with id " + id + " not found"));
    }

    public UserQuestion findByUserIdAndQuestionId(Long userId, Long questionId) {
        return userQuestionRepository.findByUserIdAndQuestionId(userId, questionId)
            .orElseThrow(() -> new ResourceNotFoundException("UserQuestion with userId " + userId + " not found"));
    }

    public void save(UserQuestion userQuestion) {
        userQuestionRepository.save(userQuestion);
    }

}
