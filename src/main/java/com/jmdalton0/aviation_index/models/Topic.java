package com.jmdalton0.aviation_index.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * An aviation information topic.
 * Topics are application-universal and can be viewed by all users.
 * Topics can have a set of questions that are related.
 * Topics can have subtopics and parent topics, forming a hierarchy.
 */
@Entity
public class Topic {

    /**
     * The unique identifier.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The id of the parent topic that this topic belongs to.
     * Topics can belong to one or 0 parents.
     */
    private Long parentId;

    /**
     * The value or title of the topic.
     */
    private String topic;

    /**
     * The default constructor.
     */
    public Topic() {}

    /**
     * Get the topic ID.
     * @return the unique identifier.
     */
    public Long getId() {
        return id;
    }

    /**
     * Get the parent ID.
     * @return the parent topic ID.
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * Get the topic.
     * @return the topic value.
     */
    public String getTopic() {
        return topic;
    }

    /**
     * Set the topic ID.
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Set the parent topic ID.
     * @param parentId
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * Set the topic value.
     * @param topic
     */
    public void setTopic(String topic) {
        this.topic = topic;
    }

}
