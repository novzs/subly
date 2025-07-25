package com.example.subly.service;

import com.example.subly.config.JwtTokenProvider;
import com.example.subly.entity.User;
import com.example.subly.repository.UserRepository;
import com.example.subly.util.GoogleTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final GoogleTokenVerifier googleTokenVerifier;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public String handleGoogleLogin(String idTokenString) {
        GoogleIdToken.Payload payload = googleTokenVerifier.verify(idTokenString);
        if (payload == null) {
            throw new RuntimeException("Invalid Google ID Token");
        }

        String email = payload.getEmail();
        String name = (String) payload.get("name");
        String picture = (String) payload.get("picture");

        // 기존 유저 확인 또는 신규 생성
        User user = userRepository.findByEmail(email).orElseGet(() ->
                User.builder()
                        .email(email)
                        .nickname(name)
                        .profileImageUrl(picture)
                        .provider("google")
                        .createdAt(LocalDateTime.now())
                        .lastLoginAt(LocalDateTime.now())
                        .isActive(true)
                        .build()
        );

        // 로그인 처리
        user.setLastLoginAt(LocalDateTime.now());
        User savedUser = userRepository.save(user);

        // JWT 토큰 발급
        return jwtTokenProvider.generateToken(savedUser.getEmail(), savedUser.getId());
    }
}