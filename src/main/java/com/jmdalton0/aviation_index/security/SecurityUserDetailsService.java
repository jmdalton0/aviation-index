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

@Service
public class SecurityUserDetailsService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final StudyService studyService;

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

    public User register(User user) throws UsernameAlreadyExistsException {
        userRepository.findByUsername(user.getUsername())
            .ifPresent((u) -> { throw new UsernameAlreadyExistsException(u.getUsername()); });

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");

        user = userRepository.save(user);

        studyService.initUserStudy(user.getId());

        return user;
    }

    public boolean verifyPassword(String password) {
        Long userId = SecurityUtil.getAuthenticatedUserId();
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User with id: " + userId + " not found"));
        if (passwordEncoder.matches(password, user.getPassword())) {
            return true;
        }
        return false;
    }

    public void updatePassword(String password) {
        Long userId = SecurityUtil.getAuthenticatedUserId();
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User with id: " + userId + " not found"));

        String hashedPassword = passwordEncoder.encode(password);
        user.setPassword(hashedPassword);

        userRepository.save(user);
    }

}
