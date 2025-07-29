package com.example.subly.controller;

import com.example.subly.dto.SignIn;
import com.example.subly.dto.SignUp;
import com.example.subly.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody SignIn requestDto) {
        String token = authService.handleLogin(requestDto.getEmail(), requestDto.getPassword());
        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUp requestDto) {
        authService.handleSignup(requestDto.getEmail(), requestDto.getPassword(), requestDto.getNickname());
        return ResponseEntity.ok(Map.of("message", "Signup successful"));
    }

}
