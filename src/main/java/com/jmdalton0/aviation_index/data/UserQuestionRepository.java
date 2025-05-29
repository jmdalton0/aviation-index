package com.jmdalton0.aviation_index.data;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jmdalton0.aviation_index.models.UserQuestion;

@Repository
public interface UserQuestionRepository extends JpaRepository<UserQuestion, Long> {

    public Optional<UserQuestion> findByUserIdAndQuestionId(Long userId, Long questionId);
   
}
