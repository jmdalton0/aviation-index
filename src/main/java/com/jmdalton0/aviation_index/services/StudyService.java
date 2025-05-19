package com.jmdalton0.aviation_index.services;

import org.springframework.stereotype.Service;

import com.jmdalton0.aviation_index.models.Question;
import com.jmdalton0.aviation_index.models.User;

@Service
public class StudyService {

    private UserService userService;
    private QuestionService questionService;

    public StudyService(
        UserService userService,
        QuestionService questionService
    ) {
        this.userService = userService;
        this.questionService = questionService;
    }

    public Question getNextQuestion(Long userId) {
        User user = userService.findById(userId);
        Long studyQuestionId = user.getStudyQuestionId();

        if (studyQuestionId == null) {
            //FIXME
            studyQuestionId = 1L;
        }

        return questionService.findById(studyQuestionId);
    }

    public void advanceNextQuestion(Long userId) {

    }
    
}
