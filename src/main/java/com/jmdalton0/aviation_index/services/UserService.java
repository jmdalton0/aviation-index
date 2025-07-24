package com.jmdalton0.aviation_index.services;

import org.springframework.stereotype.Service;

import com.jmdalton0.aviation_index.data.UserRepository;
import com.jmdalton0.aviation_index.models.User;
import com.jmdalton0.aviation_index.services.exceptions.ResourceNotFoundException;

/**
 * A service class that manages user data.
 */
@Service
public class UserService {

    /**
     * A UserRepository is used to enact database transactions.
     */
    private final UserRepository userRepository;

    /**
     * A parameterized constructor.
     * @param userRepository
     */
    public UserService(
        UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    /**
     * Find a user by its ID.
     * @param id the user ID.
     * @return the user.
     */
    public User findById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
    }

    /**
     * Add a new or update an existing user.
     * @param user the user to add or update.
     */
    public void save(User user) {
        userRepository.save(user);
    }

}
