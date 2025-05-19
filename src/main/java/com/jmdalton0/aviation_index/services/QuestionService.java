package com.jmdalton0.aviation_index.services;

import java.util.List;

import org.springframework.stereotype.Service;
import com.jmdalton0.aviation_index.data.QuestionRepository;
import com.jmdalton0.aviation_index.models.Question;
import com.jmdalton0.aviation_index.services.exceptions.ResourceNotFoundException;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(
        QuestionRepository questionRepository
    ) {
        this.questionRepository = questionRepository;
    }

    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    public Question findById(Long id) {
        return questionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(id, "Question"));
    }
   
}
