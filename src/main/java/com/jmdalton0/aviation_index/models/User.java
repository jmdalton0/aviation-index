package com.jmdalton0.aviation_index.models;

import com.jmdalton0.aviation_index.models.UserQuestion.Status;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * A user account.
 * Users must enter their unique username and a password to register a new user account.
 * Study session settings and meta-data are also stored in this class.
 */
@Entity
public class User {

    /**
     * The unique identifier.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The unique username used to identify and authenticate users.
     */
    private String username;

    /**
     * The password used for authentication.
     */
    private String password;

    /**
     * The security role assigned to the user.
     */
    private String role;

    /**
     * The study status filter that the user has applied to their study session.
     */
    @Enumerated(EnumType.STRING)
    private Status studyStatus;

    /**
     * The id of the study topic filter that the user has applied to their study session.
     */
    private Long studyTopicId;

    /**
     * The id of the users's current study session question.
     */
    private Long studyQuestionId;

    /**
     * The default constructor.
     */
    public User() {}

    /**
     * A parameterized constructor.
     * @param id
     * @param username
     * @param password
     * @param role
     */
    public User(Long id, String username, String password, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    /**
     * Get the user ID.
     * @return the unique identifier.
     */
    public Long getId() {
        return id;
    }

    /**
     * Get the username.
     * @return the username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get the password.
     * @return the password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Get the role.
     * @return
     */
    public String getRole() {
        return role;
    }

    /**
     * Get the study status filter.
     * @return
     */
    public Status getStudyStatus() {
        return studyStatus;
    }

    /**
     * Get the study topic filter.
     * @return
     */
    public Long getStudyTopicId() {
        return studyTopicId;
    }

    /**
     * Get the current study question.
     * @return
     */
    public Long getStudyQuestionId() {
        return studyQuestionId;
    }

    /**
     * Set the ID.
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Set the username.
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Set the password.
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Set the security role.
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Set the study status filter.
     * @param studyStatus
     */
    public void setStudyStatus(Status studyStatus) {
        this.studyStatus = studyStatus;
    }

    /**
     * Set the study topic filter.
     * @param studyTopicId
     */
    public void setStudyTopicId(Long studyTopicId) {
        this.studyTopicId = studyTopicId;
    }

    /**
     * Set the current study question.
     * @param studyQuestionId
     */
    public void setStudyQuestionId(Long studyQuestionId) {
        this.studyQuestionId = studyQuestionId;
    }

}
