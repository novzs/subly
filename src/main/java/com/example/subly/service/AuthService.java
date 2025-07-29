package com.example.subly.service;

import com.example.subly.config.JwtTokenProvider;
import com.example.subly.entity.OAuthAccount;
import com.example.subly.entity.User;
import com.example.subly.repository.OAuthAccountRepository;
import com.example.subly.repository.UserRepository;
import com.example.subly.util.GoogleTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final OAuthAccountRepository oauthAccountRepository;
    private final PasswordEncoder passwordEncoder;
    private final GoogleTokenVerifier googleTokenVerifier;
    private final JwtTokenProvider jwtTokenProvider;

    // ✅ 로컬 회원가입
    public String handleSignup(String email, String password, String nickname) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        User user = User.builder()
                .email(email)
                .nickname(nickname)
                .createdAt(LocalDateTime.now())
                .isActive(true)
                .build();

        user = userRepository.save(user);

        OAuthAccount account = OAuthAccount.builder()
                .provider("local")
                .providerId(null)
                .password(passwordEncoder.encode(password))
                .user(user)
                .build();

        oauthAccountRepository.save(account);

        return jwtTokenProvider.generateToken(user.getEmail(), user.getId());
    }

    // ✅ 로컬 로그인
    public String handleLogin(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다."));

        OAuthAccount account = oauthAccountRepository.findByUserAndProvider(user, "local")
                .orElseThrow(() -> new IllegalArgumentException("일반 로그인 계정을 찾을 수 없습니다."));

        if (!passwordEncoder.matches(password, account.getPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다.");
        }

        return jwtTokenProvider.generateToken(user.getEmail(), user.getId());
    }

    // ✅ 구글 로그인
    public String handleGoogleLogin(String idTokenString) {
        GoogleIdToken.Payload payload = googleTokenVerifier.verify(idTokenString);
        if (payload == null) {
            throw new RuntimeException("유효하지 않은 구글 ID 토큰입니다.");
        }

        String providerId = payload.getSubject(); // 구글의 고유 사용자 ID
        String email = payload.getEmail();
        String name = (String) payload.get("name");
        String picture = (String) payload.get("picture");

        Optional<OAuthAccount> existingAccount = oauthAccountRepository.findByProviderAndProviderId("google", providerId);

        User user;
        if (existingAccount.isPresent()) {
            user = existingAccount.get().getUser();
        } else {
            user = User.builder()
                    .email(email)
                    .nickname(name)
                    .profileImageUrl(picture)
                    .createdAt(LocalDateTime.now())
                    .isActive(true)
                    .build();

            user = userRepository.save(user);

            OAuthAccount account = OAuthAccount.builder()
                    .provider("google")
                    .providerId(providerId)
                    .user(user)
                    .build();

            oauthAccountRepository.save(account);
        }

        return jwtTokenProvider.generateToken(user.getEmail(), user.getId());
    }
}
