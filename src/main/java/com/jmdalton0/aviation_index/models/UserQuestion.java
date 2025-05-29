package com.jmdalton0.aviation_index.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UserQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long questionId;

    private Boolean active;

    private Status studyStatus;

    public enum Status {
        NEW, FOCUSED, LEARNING, MASTERED
    }

    public UserQuestion() {}

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public Boolean isActive() {
        return active;
    }

    public Status getStudyStatus() {
        return studyStatus;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setStudyStatus(Status studyStatus) {
        this.studyStatus = studyStatus;
    }

}
