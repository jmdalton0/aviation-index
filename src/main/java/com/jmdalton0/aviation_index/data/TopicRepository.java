package com.jmdalton0.aviation_index.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jmdalton0.aviation_index.models.Topic;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    public List<Topic> findByParentId(Long id);
    
}
