package com.example.subly.dto;

import com.example.subly.entity.User;
import com.example.subly.entity.OAuthAccount;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString

public class SignUp {
    private String username;
    private String password;
    private String email;
    private String nickname;

    public User toUserEntity() {
        return User.builder()
                .email(email)
                .nickname(nickname)
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public OAuthAccount toOAuthAccountEntity(User user, String encodedPassword) {
        return OAuthAccount.builder()
                .provider("local")
                .password(encodedPassword)
                .user(user)
                .build();
    }
}
