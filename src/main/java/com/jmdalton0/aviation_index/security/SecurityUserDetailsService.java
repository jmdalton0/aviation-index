package com.jmdalton0.aviation_index.security;

import java.util.ArrayList;
import java.util.List;

import com.jmdalton0.aviation_index.data.UserRepository;
import com.jmdalton0.aviation_index.models.User;
import com.jmdalton0.aviation_index.security.exceptions.UsernameAlreadyExistsException;
import com.jmdalton0.aviation_index.services.StudyService;
import com.jmdalton0.aviation_index.services.exceptions.ResourceNotFoundException;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * A service that manages user security sessions.
 */
@Service
public class SecurityUserDetailsService implements UserDetailsService {

    /**
     * A BCrypt PasswordEncoder is used to encode user passwords before storage.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * A UserRepository is used to enact database transactions.
     */
    private final UserRepository userRepository;

    /**
     * A StudyService is used to make necessary updates to user study sessions.
     */
    private final StudyService studyService;

    /**
     * A parameterized constructor.
     * @param userRepository
     * @param passwordEncoder
     * @param studyService
     */
    SecurityUserDetailsService(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        StudyService studyService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.studyService = studyService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(username));
        
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));

        return new SecurityUserDetails(
            user.getId(),
            user.getUsername(),
            user.getPassword(),
            authorities
        );
    }

    /**
     * Create a new user.
     * @param user the new user credentials to be verified an then stored.
     * @return the new user.
     * @throws UsernameAlreadyExistsException if the given username already belongs to another existant user.
     */
    public User register(User user) throws UsernameAlreadyExistsException {
        userRepository.findByUsername(user.getUsername())
            .ifPresent((u) -> { throw new UsernameAlreadyExistsException(u.getUsername()); });

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");

        user = userRepository.save(user);

        studyService.initUserStudy(user.getId());

        return user;
    }

    /**
     * Verify that the given password matches a user's stored password.
     * @param password the password to verify.
     * @return true if the passwords match according to the password encoder, else false.
     */
    public boolean verifyPassword(String password) {
        Long userId = SecurityUtil.getAuthenticatedUserId();
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User with id: " + userId + " not found"));
        if (passwordEncoder.matches(password, user.getPassword())) {
            return true;
        }
        return false;
    }

    /**
     * Update a user's password.
     * @param password the new password.
     */
    public void updatePassword(String password) {
        Long userId = SecurityUtil.getAuthenticatedUserId();
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User with id: " + userId + " not found"));

        String hashedPassword = passwordEncoder.encode(password);
        user.setPassword(hashedPassword);

        userRepository.save(user);
    }

}
