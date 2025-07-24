package com.jmdalton0.aviation_index.data;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jmdalton0.aviation_index.models.Question;

/**
 * A JPA Repository used to interact with the 'question' database table.
 */
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    /**
     * Find all questions that belong to a topic.
     * @param id the parent topic ID.
     * @return a list of questions that belong to the specified topic.
     */
    public List<Question> findByTopicId(Long id);

    /**
     * Search for questions using a search term.
     * The search term is applied to question as well as answer column.
     * The search is case insensitive and includes non-complete matches.
     * @param questionSearch the search term.
     * @param answerSearch the search term duplicated to match JPA structure.
     * @return a list of questions that match the search criteria.
     */
    public List<Question> findByQuestionContainingIgnoreCaseOrAnswerContainingIgnoreCase(String questionSearch, String answerSearch);

    /**
     * Find the first question according to the global order.
     * @return the first question.
     */
    public Optional<Question> findFirstByOrderByPosition();

}
