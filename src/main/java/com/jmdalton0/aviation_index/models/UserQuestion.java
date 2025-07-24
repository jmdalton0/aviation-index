package com.jmdalton0.aviation_index.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * A relationship between a specific user and a specific question.
 * Because question data is used by all users and must remain constant,
 * this class is used to model any question data that is specific to a user.
 * The active flag is used to indicate if the user question is part of the user's current study session.
 * The studyStatus field is used to indicate what status the user has assigned to the associated question.
 */
@Entity
public class UserQuestion {

    /**
     * The unique identifier.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The id of the user that this user question belongs to.
     * User questions must belong to exactly one user.
     */
    private Long userId;

    /*
     * The id of the question that this user question is associated with.
     * User questions must be associated with exaclty one question.
     */
    private Long questionId;

    /**
     * The flag that indicates whether this user question is included in the user's current study session
     */
    private Boolean active;

    /**
     * The status that the user has assigned to this question.
     */
    @Enumerated(EnumType.STRING)
    private Status studyStatus;

    /**
     * A set of statuses that are avialable to be assigned to user questions.
     */
    public enum Status {
        NEW, FOCUSED, LEARNING, MASTERED
    }

    /**
     * The default constructor.
     */
    public UserQuestion() {}

    /**
     * Get the user question ID.
     * @return the unique identifier.
     */
    public Long getId() {
        return id;
    }

    /**
     * Get the user ID.
     * @return the associated user ID.
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Get the question ID.
     * @return the associated question ID.
     */
    public Long getQuestionId() {
        return questionId;
    }

    /**
     * Get the active flag.
     * @return true if the associated question is included in the user's current study session, else false.
     */
    public Boolean isActive() {
        return active;
    }

    /**
     * Get the study status.
     * @return the assigned status.
     */
    public Status getStudyStatus() {
        return studyStatus;
    }

    /**
     * Set the ID.
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Set the user ID.
     * @param userId
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Set the question ID.
     * @param questionId
     */
    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    /**
     * Set the active flag.
     * @param active
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     * Set the study status.
     * @param studyStatus
     */
    public void setStudyStatus(Status studyStatus) {
        this.studyStatus = studyStatus;
    }

}
