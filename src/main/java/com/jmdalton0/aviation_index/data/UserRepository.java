package com.jmdalton0.aviation_index.data;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jmdalton0.aviation_index.models.User;

/**
 * A JPA Repository used to interact with the 'user' database table.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find a user by username.
     * @param username the username.
     * @return the user.
     */
    Optional<User> findByUsername(String username);
    
}
