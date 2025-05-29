package com.jmdalton0.aviation_index.data;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jmdalton0.aviation_index.models.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    
    public List<Question> findByTopicId(Long id);

    public List<Question> findByQuestionContainingIgnoreCaseOrAnswerContainingIgnoreCase(String questionSearch, String answerSearch);

    @Query(
        value = """
            SELECT * FROM question
            LIMIT 1
        """,
        nativeQuery = true
    )
    public Optional<Question> findNextQuestion(
        @Param("userId") Long userId,
        @Param("position") int position
    );
 
}
