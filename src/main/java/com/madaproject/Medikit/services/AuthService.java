package com.madaproject.Medikit.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.madaproject.Medikit.dto.LoginRequest;
import com.madaproject.Medikit.dto.RegisterRequest;
import com.madaproject.Medikit.models.User;
import com.madaproject.Medikit.repository.UserRepository;
import com.madaproject.Medikit.security.JwtUtil;

import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists!");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // hash du mot de passe
        user.setRole(request.getRole());

        return userRepository.save(user);
    }

    public String login(LoginRequest request) {
        Optional<User> userOpt = userRepository.findByUsername(request.getUsername());
        if (userOpt.isEmpty()) {
            return null; // utilisateur inexistant
        }
        User user = userOpt.get();

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return null; // mot de passe incorrect
        }

        return JwtUtil.generateToken(user.getUsername(), user.getRole());
    }
}
