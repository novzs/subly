package com.example.subly.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString

public class JwtToken {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}