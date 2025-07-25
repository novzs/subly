package com.example.subly.controller;

import com.example.subly.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/google-login")
    public ResponseEntity<?> googleLogin(@RequestBody Map<String, String> request) {
        try {
            String idToken = request.get("idToken");
            if (idToken == null || idToken.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "ID token is required"));
            }

            String jwt = authService.handleGoogleLogin(idToken);
            return ResponseEntity.ok(Map.of(
                    "token", jwt,
                    "message", "Login successful"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }
}