package com.example.subly.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "oauth_accounts",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"provider", "providerId"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OAuthAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String provider;

    private String providerId;

    // 일반 로그인 사용자만 사용
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
