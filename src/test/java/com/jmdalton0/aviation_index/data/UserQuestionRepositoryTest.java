package com.jmdalton0.aviation_index.data;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Random;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.jmdalton0.aviation_index.models.Question;
import com.jmdalton0.aviation_index.models.User;
import com.jmdalton0.aviation_index.models.UserQuestion;
import com.jmdalton0.aviation_index.models.UserQuestion.Status;

import jakarta.persistence.EntityManager;

@DataJpaTest
@ActiveProfiles("test")
public class UserQuestionRepositoryTest {

    private Random rand;

    @Autowired EntityManager em;
    @Autowired UserQuestionRepository repo;

    public UserQuestionRepositoryTest() {
        rand = new Random(12);
    }

    private User user(String username) {
        User user = new User();
        user.setId(null);
        user.setUsername(username);
        user.setPassword("pw");
        user.setRole("USER");
        em.persist(user);
        return user;
    }

    private Question question(int pos, String term) {
        Question question = new Question();
        question.setId(null);
        question.setTopicId(rand.nextLong());
        question.setPosition(pos);
        question.setQuestion("what is " + term);
        question.setAnswer(term + " is " + term);
        em.persist(question);
        return question;
    }

    private UserQuestion userQuestion(Long userId, Long questionId, boolean active, Status status) {
        UserQuestion userQuestion = new UserQuestion();
        userQuestion.setId(null);
        userQuestion.setUserId(userId);
        userQuestion.setQuestionId(questionId);
        userQuestion.setActive(active);
        userQuestion.setStudyStatus(status);
        em.persist(userQuestion);
        return userQuestion;
    }

    @Test
    @DisplayName("T.D.1")
    void resetSetsAllStatusesToNewForSingleUser() {
        User u1 = user("john");
        User u2 = user("paul");

        Question q1 = question(1, "wing");
        Question q2 = question(2, "aileron");
        Question q3 = question(3, "elevator");

        UserQuestion u1q1 = userQuestion(u1.getId(), q1.getId(), true, Status.NEW);
        UserQuestion u1q2 = userQuestion(u1.getId(), q2.getId(), true, Status.FOCUSED);
        UserQuestion u1q3 = userQuestion(u1.getId(), q3.getId(), true, Status.MASTERED);

        UserQuestion u2q1 = userQuestion(u2.getId(), q1.getId(), true, Status.NEW);
        UserQuestion u2q2 = userQuestion(u2.getId(), q2.getId(), true, Status.FOCUSED);
        UserQuestion u2q3 = userQuestion(u2.getId(), q3.getId(), true, Status.MASTERED);

        repo.reset(u1.getId());
        em.flush();
        em.clear();

        u1q1 = em.find(UserQuestion.class, u1q1.getId());
        u1q2 = em.find(UserQuestion.class, u1q1.getId());
        u1q3 = em.find(UserQuestion.class, u1q1.getId());

        u2q1 = em.find(UserQuestion.class, u2q1.getId());
        u2q2 = em.find(UserQuestion.class, u2q2.getId());
        u2q3 = em.find(UserQuestion.class, u2q3.getId());

        assertThat(u1q1.getStudyStatus()).isEqualTo(Status.NEW);
        assertThat(u1q2.getStudyStatus()).isEqualTo(Status.NEW);
        assertThat(u1q3.getStudyStatus()).isEqualTo(Status.NEW);

        assertThat(u2q1.getStudyStatus()).isEqualTo(Status.NEW);
        assertThat(u2q2.getStudyStatus()).isEqualTo(Status.FOCUSED);
        assertThat(u2q3.getStudyStatus()).isEqualTo(Status.MASTERED);
    }
    
}
