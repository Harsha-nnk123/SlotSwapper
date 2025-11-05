package com.project.SlotSwapper.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.SlotSwapper.app.dtos.LoginRequest;
import com.project.SlotSwapper.app.dtos.LoginResponse;
import com.project.SlotSwapper.app.dtos.RegisterRequest;
import com.project.SlotSwapper.app.model.User;
import com.project.SlotSwapper.app.repo.UserRepo;

import java.time.Instant;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepo users;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    public void register(RegisterRequest req){
        users.findByEmail(req.email()).ifPresent(u -> { throw new RuntimeException("Email already exists"); });
        User u = User.builder()
        .name(req.name())
        .email(req.email())
        .passwordHash(encoder.encode(req.password()))
        .createdAt(Instant.now())
        .build();
        users.save(u);
    }

    public LoginResponse login(LoginRequest req){
        User u = users.findByEmail(req.email()).orElseThrow(() -> new RuntimeException("Invalid credentials"));
        if(!encoder.matches(req.password(), u.getPasswordHash())) throw new RuntimeException("Invalid credentials");
        String token = jwtService.generateToken(u.getId(), u.getEmail());
        return new LoginResponse(u.getId(), u.getName(), u.getEmail(), token);
    }
}