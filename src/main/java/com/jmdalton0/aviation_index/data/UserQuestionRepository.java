package com.jmdalton0.aviation_index.data;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jmdalton0.aviation_index.models.UserQuestion;
import com.jmdalton0.aviation_index.models.UserQuestion.Status;

/**
 * A JPA Repository used to interact with the 'user-question' database table.
 */
@Repository
public interface UserQuestionRepository extends JpaRepository<UserQuestion, Long> {

    /**
     * Find all user questions that belong to a user.
     * @param userId the parent user ID.
     * @return a list of user questions that belong to the specified user.
     */
    public List<UserQuestion> findByUserId(Long userId);

    /**
     * Find the user question that models the relationship between a specified user and a specified question
     * @param userId the parent user ID.
     * @param questionId the associated question ID.
     * @return the user question.
     */
    public Optional<UserQuestion> findByUserIdAndQuestionId(Long userId, Long questionId);

    /**
     * Count the number of user questions that belong to a user.
     * @param userId the parent user ID.
     * @return the number of user questions that belong to a specified user.
     */
    public Long countByUserId(Long userId);

    /**
     * Count the number of user questions that belong to a user and have a specified study status assignment.
     * @param userId the parent user ID.
     * @param studyStatus the specified stady status.
     * @return the number of user questions that belong to a user and have a specified study status assignment.
     */
    public Long countByUserIdAndStudyStatus(Long userId, Status studyStatus);

    /**
     * Delete all user questions that are associated with a specified question ID.
     * @param questionId the question ID.
     */
    @Transactional
    public void deleteByQuestionId(Long questionId);

    /**
     * Reset all user questions that belong to a specific user.
     * User questions that are reset are assigned a study status of 'NEW'.
     * @param userId the user ID.
     */
    @Transactional
    @Modifying
    @Query(value = """
        UPDATE user_question
        SET study_status = 'NEW'
        WHERE user_id = :userId
    """, nativeQuery = true)
    public void reset(
        @Param("userId") Long userId
    );

    /**
     * Find the first active user question belonging to a specified user,
     * according to the global question order.
     * @param userId the user ID.
     * @return the user question associated with the lowest order question that is also active in the specified user's study session.
     */
    @Query(value = """
        SELECT uq.*
        FROM user_question uq
        JOIN question q ON uq.question_id = q.id
        WHERE uq.user_id = :userId
            AND uq.active = true
        ORDER BY q.position ASC
        LIMIT 1
    """, nativeQuery = true)
    public Optional<UserQuestion> findFirstByUserId(
        @Param("userId") Long userId
    );

    /**
     * Find the last active user question belonging to a specified user,
     * according to the global question order.
     * @param userId the user ID.
     * @return the user question associated with the highest order question that is also active in the specified user's study session.
     */
    @Query(value = """
        SELECT uq.*
        FROM user_question uq
        JOIN question q ON uq.question_id = q.id
        WHERE uq.user_id = :userId
            AND uq.active = true
        ORDER BY q.position DESC
        LIMIT 1
    """, nativeQuery = true)
    public Optional<UserQuestion> findLastByUserId(
        @Param("userId") Long userId
    );

    /**
     * Find the active user question previous to the current study question belonging to a specified user,
     * according to the global question order.
     * @param userId the user ID.
     * @param curPosition the position of the current study question belonging to the user.
     * @return the user question associated with the next lowest order question that is also active in the specified user's study session.
     */
    @Query(value = """
        SELECT uq.*
        FROM user_question uq
        JOIN question q ON uq.question_id = q.id
        WHERE uq.user_id = :userId
            AND uq.active = true
            AND q.position < :curPosition
        ORDER BY q.position DESC
        LIMIT 1
    """, nativeQuery = true)
    public Optional<UserQuestion> findPrevByUserId(
        @Param("userId") Long userId,
        @Param("curPosition") int curPosition
    );

    /**
     * Find the active user question after the current study question belonging to a specified user,
     * according to the global question order.
     * @param userId the user ID.
     * @param curPosition the position of the current study question belonging to the user.
     * @return the user question associated with the next highest order question that is also active in the specified user's study session.
     */
    @Query(value = """
        SELECT uq.*
        FROM user_question uq
        JOIN question q ON uq.question_id = q.id
        WHERE uq.user_id = :userId
            AND uq.active = true
            AND q.position > :curPosition
        ORDER BY q.position ASC
        LIMIT 1
    """, nativeQuery = true)
    public Optional<UserQuestion> findNextByUserId(
        @Param("userId") Long userId,
        @Param("curPosition") int curPosition
    );
   
}
