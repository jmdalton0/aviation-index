package com.jmdalton0.aviation_index.services;

import org.springframework.stereotype.Service;

import com.jmdalton0.aviation_index.data.UserRepository;
import com.jmdalton0.aviation_index.models.User;
import com.jmdalton0.aviation_index.services.exceptions.ResourceNotFoundException;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(
        UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    public User findById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
    }

    public void save(User user) {
        userRepository.save(user);
    }

}
