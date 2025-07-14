package com.jmdalton0.aviation_index.models;

import com.jmdalton0.aviation_index.models.UserQuestion.Status;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String role;

    @Enumerated(EnumType.STRING)
    private Status studyStatus;

    private Long studyTopicId;

    private Long studyQuestionId;

    public User() {}

    public User(Long id, String username, String password, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public Status getStudyStatus() {
        return studyStatus;
    }

    public Long getStudyTopicId() {
        return studyTopicId;
    }

    public Long getStudyQuestionId() {
        return studyQuestionId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setStudyStatus(Status studyStatus) {
        this.studyStatus = studyStatus;
    }

    public void setStudyTopicId(Long studyTopicId) {
        this.studyTopicId = studyTopicId;
    }

    public void setStudyQuestionId(Long studyQuestionId) {
        this.studyQuestionId = studyQuestionId;
    }

}
