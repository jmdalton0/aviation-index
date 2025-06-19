package com.jmdalton0.aviation_index.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jmdalton0.aviation_index.data.TopicRepository;
import com.jmdalton0.aviation_index.models.Topic;
import com.jmdalton0.aviation_index.services.exceptions.ResourceNotFoundException;

@Service
public class TopicService {

    private final TopicRepository topicRepository;

    public TopicService(
        TopicRepository topicRepository
    ) {
        this.topicRepository = topicRepository;
    }

    public List<Topic> findAll() {
        return topicRepository.findAll();
    }

    public List<Topic> findByParentId(Long parentId) {
        return topicRepository.findByParentId(parentId);
    }

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

    public Topic findById(Long id) {
        return topicRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Topic with id " + id + " not found"));
    }

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

    public void save(Topic topic) {
        topicRepository.save(topic);
    }

    public void delete(Long id) {
        topicRepository.deleteById(id);
    }

}
