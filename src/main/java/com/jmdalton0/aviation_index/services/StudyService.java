package com.jmdalton0.aviation_index.services;

import java.util.List;
import org.springframework.stereotype.Service;

import com.jmdalton0.aviation_index.models.Question;
import com.jmdalton0.aviation_index.models.Topic;
import com.jmdalton0.aviation_index.models.User;
import com.jmdalton0.aviation_index.models.UserQuestion;
import com.jmdalton0.aviation_index.models.UserQuestion.Status;
import com.jmdalton0.aviation_index.services.exceptions.ResourceNotFoundException;

/**
 * A service class that manages study sessions for users.
 * The study service should be thought of as an abstraction layer
 * of all other services used to manage study sessions.
 */
@Service
public class StudyService {

    /**
     * An instance of all other main services is used to control study session operations.
     */
    private UserService userService;
    private TopicService topicService;
    private QuestionService questionService;
    private UserQuestionService userQuestionService;

    /**
     * A parameterized constructor.
     * @param userService
     * @param topicService
     * @param questionService
     * @param userQuestionService
     */
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

    /**
     * Initialize a study session for new users.
     * This includes creating new user questions assigned to the new user for each question.
     * This also includes setting the new user's current study question.
     * @param userId the authenticated user ID.
     */
    public void initUserStudy(Long userId) {
        // for all questions
        List<Question> allQuestions = questionService.findAll();
        for (Question question : allQuestions) {

            // create a new user question for this user
            UserQuestion userQuestion = new UserQuestion();
            userQuestion.setUserId(userId);
            userQuestion.setQuestionId(question.getId());
            userQuestion.setActive(true);
            userQuestion.setStudyStatus(Status.NEW); // default status is NEW
            userQuestionService.save(userQuestion);
        }

        User user = userService.findById(userId);
        user.setStudyQuestionId(allQuestions.get(0).getId()); // default study question is lowest order question
        userService.save(user);
    }

    /**
     * Get the current study question for the authenticated user.
     * @param userId the authenticated user ID.
     * @return the current study question set by this user.
     */
    public Question getStudyQuestion(Long userId) {
        User user = userService.findById(userId);
        Long studyQuestionId = user.getStudyQuestionId();

        if (studyQuestionId == null) {
            return null;
        }

        return questionService.findById(studyQuestionId);
    }

    /**
     * Get the study status of the current study question for the authenticated user.
     * @param userId the authenticated user ID.
     * @param questionId the authenticated user's current study question ID.
     * @return the current study question's associated user question.
     */
    public UserQuestion getStudyQuestionStatus(Long userId, Long questionId) {
        return userQuestionService.findByUserIdAndQuestionId(userId, questionId);
    }

    /**
     * Advance the authenticated user's current study question.
     * This method will set the user's study question to either the next or previous question
     * based on global question order depending on the 'next' parameter.
     * This method also respects the user's saved study filters, such as topic (this is handled in the repository layer).
     * @param userId the authenticated user ID.
     * @param next a flag that controls if the advancement should move forward or backward with respect to question order.
     */
    private void updateStudyQuestion(Long userId, boolean next) {
        User user = userService.findById(userId);
        Long curQuestionId = user.getStudyQuestionId();

        // if no current study question exists, use the first active question in the global question order
        if (curQuestionId == null) {
            curQuestionId = questionService.findFirst().getId();
        }

        try {
            Question curQuestion = questionService.findById(curQuestionId);
            UserQuestion updatedUserQuestion = null;

            // next flag is true, find the 'next' question
            if (next) {
                updatedUserQuestion = userQuestionService.findNextByUserId(userId, curQuestion.getPosition());

            // next flag is false, find the 'previous' question
            } else {
                updatedUserQuestion = userQuestionService.findPrevByUserId(userId, curQuestion.getPosition());
            }
            user.setStudyQuestionId(updatedUserQuestion.getQuestionId());

        } catch (ResourceNotFoundException e) {

            // if no active user question is found, set the current study question to null
            user.setStudyQuestionId(null);
        }
        userService.save(user);
    }

    /**
     * Advance the authenticated user's current study question to the previous question.
     * @param userId the authenticated user ID.
     */
    public void prevStudyQuestion(Long userId) {
        updateStudyQuestion(userId, false);
    }

    /**
     * Advance the authenticated user's current study question to the previous question.
     * @param userId the authenticated user ID.
     */
    public void nextStudyQuestion(Long userId) {
        updateStudyQuestion(userId, true);
    }

    /**
     * Get the authenticated user's current study topic filter.
     * @param userId the authenticated user ID.
     * @return the authenticated user's current study topic.
     */
    public Topic getStudyTopic(Long userId) {
        User user = userService.findById(userId);
        Long studyTopicId = user.getStudyTopicId();
        if (studyTopicId == null) {
            return null;
        }
        return topicService.findById(studyTopicId);
    }

    /**
     * Update the authenticated user's current study topic filter.
     * @param userId the authenticated user ID.
     * @param topicId the new study topic ID.
     */
    public void setStudyTopic(Long userId, Long topicId) {
        User user = userService.findById(userId);
        user.setStudyTopicId(topicId);
        userService.save(user);

        // update user's study session with new filters
        updateStudySession(userId);
    }

    /**
     * Get the authenticated user's current study status filter.
     * @param userId the authenticated user ID.
     * @return the authenticated user's current study status.
     */
    public Status getStudyStatus(Long userId) {
        User user = userService.findById(userId);
        return user.getStudyStatus();
    }

    /**
     * Update the authenticated user's current study status filter.
     * @param userId the authenticated user ID.
     * @param status the new study status.
     */
    public void setStudyStatus(Long userId, Status status) {
        User user = userService.findById(userId);
        user.setStudyStatus(status);
        userService.save(user);

        // update user's study session with new filters
        updateStudySession(userId);
    }

    /**
     * A helper method to perform study session changes when a user updates their study session.
     * This method activates and deactivates user questions according to the user's updated study filters.
     * @param userId the athenticated user ID.
     */
    private void updateStudySession(Long userId) {
        User user = userService.findById(userId);
        Long studyTopicId = user.getStudyTopicId();
        Status studyStatus = user.getStudyStatus();

        // for all user questions that belong to this user
        List<UserQuestion> userQuestions = userQuestionService.findByUserId(userId);
        for (UserQuestion userQuestion : userQuestions) {
            Question question = questionService.findById(userQuestion.getQuestionId());

            // initially deactivate
            userQuestion.setActive(false);

            // reactivate if the question matches the user's current study session filters
            if (
                // set the user question as active if all of the following conditions are met:

                // 1)
                // if the study topic filter is not set, indicating questions must not be filtered by topic,
                // or if the question is a child of the current study topic filter
                (studyTopicId == null || topicService.isChild(question.getTopicId(), studyTopicId)) &&

                // 2)
                // if the study status filter is not set, indicating questions must not be filtered by study status,
                // or if the user question's study status match the current study status filter
                (studyStatus == null || userQuestion.getStudyStatus() == studyStatus)
            ) {
                userQuestion.setActive(true);
            }

            userQuestionService.save(userQuestion);
        }

        // call the nextStudyQuestion() method to ensure that a question is set as the current study question,
        // based on the new filters
        nextStudyQuestion(userId);
    }

}
