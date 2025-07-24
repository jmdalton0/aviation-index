package com.jmdalton0.aviation_index.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * An aviation information question / answer pair.
 * Questions are application-universal and can be viewed by all users.
 * Questions are also ordered using the position field. This order is used when users cycle through the question bank.
 */
@Entity
public class Question {

    /**
     * The unique identifier.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The id of the topic that this question belongs to.
     * Questions must belong to exactly one topic.
     */
    private Long topicId;

    /**
     * The position of this question in the global order of all questions.
     */
    private Integer position;

    /**
     * The question that is posed to the user.
     */
    private String question;

    /**
     * The correct answer to the question.
     */
    private String answer;

    /**
     * The default constructor.
     */
    public Question() {}

    /**
     * Get the question ID.
     * @return the unique identifier.
     */
    public Long getId() {
        return id;
    }

    /**
     * Get the topic ID.
     * @return the parent topic ID.
     */
    public Long getTopicId() {
        return topicId;
    }

    /**
     * Get the position.
     * @return the global position.
     */
    public Integer getPosition() {
        return position;
    }

    /**
     * Get the question.
     * @return the question value.
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Get the answer.
     * @return the answer to the question.
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * Set the ID.
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Set the topic ID.
     * @param topicId
     */
    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    /**
     * Set the position.
     * @param position
     */
    public void setPosition(Integer position) {
        this.position = position;
    }

    /**
     * Set the question.
     * @param question
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * Set the answer.
     * @param answer
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

}
