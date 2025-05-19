package com.jmdalton0.aviation_index.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long topicId;

    private Integer position;

    private String question;

    private String answer;

    public Question() {}

    public Long getId() {
        return id;
    }

    public Long getTopicId() {
        return topicId;
    }

    public Integer getPosition() {
        return position;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

}
