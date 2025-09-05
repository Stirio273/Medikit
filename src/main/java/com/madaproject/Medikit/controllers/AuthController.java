package com.madaproject.Medikit.controllers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.madaproject.Medikit.dto.LoginRequest;
import com.madaproject.Medikit.dto.RegisterRequest;
import com.madaproject.Medikit.models.User;
import com.madaproject.Medikit.security.JwtUtil;
import com.madaproject.Medikit.services.AuthService;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok("Utilisateur créé avec succès !");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        String token = authService.login(request);
        if (token == null) {
            return ResponseEntity.status(401).body("Identifiants invalides");
        }
        return ResponseEntity.ok(token); // renvoie directement le JWT
    }
}
