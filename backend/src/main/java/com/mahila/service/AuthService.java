package com.mahila.service;

import com.mahila.dto.AuthRequest;
import com.mahila.dto.AuthResponse;
import com.mahila.model.HealthProfile;
import com.mahila.model.User;
import com.mahila.repository.HealthProfileRepository;
import com.mahila.repository.UserRepository;
import com.mahila.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HealthProfileRepository healthProfileRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Transactional
    public AuthResponse register(AuthRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered!");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .build();

        user = userRepository.save(user);

        HealthProfile profile = HealthProfile.builder()
                .user(user)
                .age(request.getAge() != null ? request.getAge() : 25)
                .averageCycleLength(28)
                .averagePeriodLength(5)
                .preferredLanguage("en")
                .build();

        healthProfileRepository.save(profile);

        String token = jwtUtils.generateJwtToken(user.getEmail(), user.getId());

        return AuthResponse.builder()
                .token(token)
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .age(profile.getAge())
                .preferredLanguage(profile.getPreferredLanguage())
                .build();
    }

    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found with email: " + request.getEmail()));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid password!");
        }

        HealthProfile profile = healthProfileRepository.findByUserId(user.getId())
                .orElse(null);

        String token = jwtUtils.generateJwtToken(user.getEmail(), user.getId());

        return AuthResponse.builder()
                .token(token)
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .age(profile != null ? profile.getAge() : 0)
                .preferredLanguage(profile != null ? profile.getPreferredLanguage() : "en")
                .build();
    }
}
