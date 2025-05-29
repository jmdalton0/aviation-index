package com.jmdalton0.aviation_index.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jmdalton0.aviation_index.models.Question;
import com.jmdalton0.aviation_index.models.User;
import com.jmdalton0.aviation_index.models.UserQuestion;
import com.jmdalton0.aviation_index.models.UserQuestion.Status;

@Service
public class StudyService {

    private UserService userService;
    private QuestionService questionService;
    private UserQuestionService userQuestionService;

    public StudyService(
        UserService userService,
        QuestionService questionService,
        UserQuestionService userQuestionService
    ) {
        this.userService = userService;
        this.questionService = questionService;
        this.userQuestionService = userQuestionService;
    }

    public Question findNextQuestion(Long userId) {
        User user = userService.findById(userId);
        Long studyQuestionId = user.getStudyQuestionId();

        if (studyQuestionId == null) {
            //FIXME
            studyQuestionId = 1L;
        }

        return questionService.findById(studyQuestionId);
    }

    public void advanceNextQuestion(Long userId) {
        User user = userService.findById(userId);
        Long currentStudyQuestionId = user.getStudyQuestionId();
    }
    
    public void createUserQuestions(Long userId) {
        List<Question> allQuestions = questionService.findAll();
        for (Question question : allQuestions) {
            UserQuestion userQuestion = new UserQuestion();
            userQuestion.setUserId(userId);
            userQuestion.setQuestionId(question.getId());
            userQuestion.setActive(true);
            userQuestion.setStudyStatus(Status.NEW);
            
            userQuestionService.save(userQuestion);
        }
    }
}
