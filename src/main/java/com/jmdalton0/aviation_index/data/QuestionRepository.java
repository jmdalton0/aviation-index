package com.jmdalton0.aviation_index.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jmdalton0.aviation_index.models.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    
}
