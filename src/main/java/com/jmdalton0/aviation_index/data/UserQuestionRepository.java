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

@Repository
public interface UserQuestionRepository extends JpaRepository<UserQuestion, Long> {

    public List<UserQuestion> findByUserId(Long userId);

    public Optional<UserQuestion> findByUserIdAndQuestionId(Long userId, Long questionId);

    public Long countByUserId(Long userId);

    public Long countByUserIdAndStudyStatus(Long userId, Status studyStatus);

    @Transactional
    public void deleteByQuestionId(Long questionId);

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
