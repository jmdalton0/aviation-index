package com.jmdalton0.aviation_index.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jmdalton0.aviation_index.models.Topic;

/**
 * A JPA Repository used to interact with the 'topic' database table.
 */
@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    /**
     * Find all topics that belong to a parent topic.
     * @param id the parent topic ID.
     * @return a list of topics that belong to the specified topic.
     */
    public List<Topic> findByParentId(Long id);
    
}
