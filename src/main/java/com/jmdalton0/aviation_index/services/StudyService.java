package com.jmdalton0.aviation_index.services;

import java.util.List;
import org.springframework.stereotype.Service;

import com.jmdalton0.aviation_index.models.Question;
import com.jmdalton0.aviation_index.models.User;
import com.jmdalton0.aviation_index.models.UserQuestion;
import com.jmdalton0.aviation_index.models.UserQuestion.Status;
import com.jmdalton0.aviation_index.services.exceptions.ResourceNotFoundException;

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

    public void initUserStudy(Long userId) {
        List<Question> allQuestions = questionService.findAll();
        for (Question question : allQuestions) {
            UserQuestion userQuestion = new UserQuestion();
            userQuestion.setUserId(userId);
            userQuestion.setQuestionId(question.getId());
            userQuestion.setActive(true);
            userQuestion.setStudyStatus(Status.NEW);
            userQuestionService.save(userQuestion);
        }

        User user = userService.findById(userId);
        user.setStudyQuestionId(allQuestions.get(0).getId());
        userService.save(user);
    }

    public Question getStudyQuestion(Long userId) {
        User user = userService.findById(userId);
        Long studyQuestionId = user.getStudyQuestionId();

        if (studyQuestionId == null) {
            throw new ResourceNotFoundException("No active UserQuestion was found");
        }

        return questionService.findById(studyQuestionId);
    }

    public UserQuestion getStudyQuestionStatus(Long userId, Long questionId) {
        return userQuestionService.findByUserIdAndQuestionId(userId, questionId);
    }

    private void updateStudyQuestion(Long userId, boolean next) {
        User user = userService.findById(userId);
        Long curQuestionId = user.getStudyQuestionId();
        try {
            Question curQuestion = questionService.findById(curQuestionId);
            UserQuestion updatedUserQuestion = null;
            if (next) {
                updatedUserQuestion = userQuestionService.findNextByUserId(userId, curQuestion.getPosition());
            } else {
                updatedUserQuestion = userQuestionService.findPrevByUserId(userId, curQuestion.getPosition());
            }
            user.setStudyQuestionId(updatedUserQuestion.getQuestionId());
        } catch (ResourceNotFoundException e) {
            user.setStudyQuestionId(null);
        }
        userService.save(user);
    }

    public void prevStudyQuestion(Long userId) {
        updateStudyQuestion(userId, false);
    }

    public void nextStudyQuestion(Long userId) {
        updateStudyQuestion(userId, true);
    }
    
}
