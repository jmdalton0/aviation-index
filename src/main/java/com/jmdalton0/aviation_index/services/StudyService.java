package com.jmdalton0.aviation_index.services;

import java.util.List;
import org.springframework.stereotype.Service;

import com.jmdalton0.aviation_index.models.Question;
import com.jmdalton0.aviation_index.models.Topic;
import com.jmdalton0.aviation_index.models.User;
import com.jmdalton0.aviation_index.models.UserQuestion;
import com.jmdalton0.aviation_index.models.UserQuestion.Status;
import com.jmdalton0.aviation_index.services.exceptions.ResourceNotFoundException;

@Service
public class StudyService {

    private UserService userService;
    private TopicService topicService;
    private QuestionService questionService;
    private UserQuestionService userQuestionService;

    public StudyService(
        UserService userService,
        TopicService topicService,
        QuestionService questionService,
        UserQuestionService userQuestionService
    ) {
        this.userService = userService;
        this.topicService = topicService;
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
            return null;
        }

        return questionService.findById(studyQuestionId);
    }

    public UserQuestion getStudyQuestionStatus(Long userId, Long questionId) {
        return userQuestionService.findByUserIdAndQuestionId(userId, questionId);
    }

    private void updateStudyQuestion(Long userId, boolean next) {
        User user = userService.findById(userId);
        Long curQuestionId = user.getStudyQuestionId();

        if (curQuestionId == null) {
            curQuestionId = questionService.findFirst().getId();
        }

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

    public Topic getStudyTopic(Long userId) {
        User user = userService.findById(userId);
        Long studyTopicId = user.getStudyTopicId();
        if (studyTopicId == null) {
            return null;
        }
        return topicService.findById(studyTopicId);
    }

    public void setStudyTopic(Long userId, Long topicId) {
        User user = userService.findById(userId);
        user.setStudyTopicId(topicId);
        userService.save(user);
        updateStudySession(userId);
    }

    public Status getStudyStatus(Long userId) {
        User user = userService.findById(userId);
        return user.getStudyStatus();
    }

    public void setStudyStatus(Long userId, Status status) {
        User user = userService.findById(userId);
        user.setStudyStatus(status);
        userService.save(user);
        updateStudySession(userId);
    }

    private void updateStudySession(Long userId) {
        User user = userService.findById(userId);
        Long studyTopicId = user.getStudyTopicId();
        Status studyStatus = user.getStudyStatus();

        List<UserQuestion> userQuestions = userQuestionService.findByUserId(userId);
        for (UserQuestion userQuestion : userQuestions) {
            Question question = questionService.findById(userQuestion.getQuestionId());

            userQuestion.setActive(false);

            if (
                (studyTopicId == null || topicService.isChild(question.getTopicId(), studyTopicId)) &&
                (studyStatus == null || userQuestion.getStudyStatus() == studyStatus)
            ) {
                userQuestion.setActive(true);
            }

            userQuestionService.save(userQuestion);
        }

        nextStudyQuestion(userId);
    }

}
