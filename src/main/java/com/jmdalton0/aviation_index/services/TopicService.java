package com.jmdalton0.aviation_index.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jmdalton0.aviation_index.data.TopicRepository;
import com.jmdalton0.aviation_index.models.Topic;
import com.jmdalton0.aviation_index.services.exceptions.ResourceNotFoundException;

/**
 * A service class that manages topic data.
 */
@Service
public class TopicService {

    /**
     * A TopicRepository is used to enact database transactions.
     */
    private final TopicRepository topicRepository;

    /**
     * A parameterized constructor.
     * @param topicRepository
     */
    public TopicService(
        TopicRepository topicRepository
    ) {
        this.topicRepository = topicRepository;
    }

    /**
     * Find all topics.
     * @return a list of all topics.
     */
    public List<Topic> findAll() {
        return topicRepository.findAll();
    }

    /**
     * Find all topics that belong to a parent topic.
     * @param parentId the parent topic ID.
     * @return a list of topics that belong to the specified topic.
     */
    public List<Topic> findByParentId(Long parentId) {
        return topicRepository.findByParentId(parentId);
    }

    /**
     * Find all topics that are considered a parent of a topic.
     * A topic is considered a parent of its direct children, as well as any of those childrens' children.
     * @param id the child topic.
     * @return a list of all topics that are a parent of the specified topic.
     */
    public List<Topic> findParentsById(Long id) {
        Topic topic = topicRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Topic with id " + id + " not found"));

        Long parentId = topic.getParentId();
        List<Topic> parents = new ArrayList<>();
        while (parentId != null) {
            Topic parent = topicRepository.findById(parentId)
                .orElseThrow(() -> new ResourceNotFoundException("Topic with id " + id + " not found"));
            
            parents.add(0, parent);
            parentId = parent.getParentId();
        }
        
        return parents;
    }

    /**
     * Find a topic by its ID.
     * @param id the topic ID.
     * @return the topic.
     */
    public Topic findById(Long id) {
        return topicRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Topic with id " + id + " not found"));
    }

    /**
     * A recursive function that verifies whether a topic is to be considered a child of another topic.
     * A topic is considered a child of its direct parent, as well as any of that parent topic's parents.
     * @param childId the potential child topic ID.
     * @param parentId the potential parent topic ID.
     * @return true if the topic is a child of the specified parent topic, else false.
     */
    public boolean isChild(Long childId, Long parentId) {
        if (childId == null || parentId == null) {
            return false;
        } else if (childId == parentId) {
            return true;
        } else {
            Topic child = findById(childId);
            return isChild(child.getParentId(), parentId);
        }
    }

    /**
     * Add a new or update an existing topic.
     * @param topic the topic to add or update.
     */
    public void save(Topic topic) {
        topicRepository.save(topic);
    }

    /**
     * Delete an existing topic.
     * @param id
     */
    public void delete(Long id) {
        topicRepository.deleteById(id);
    }

}
