package com.example.subly.repository;

import com.example.subly.entity.OAuthAccount;
import com.example.subly.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface OAuthAccountRepository extends JpaRepository<OAuthAccount, Long> {
    Optional<OAuthAccount> findByUserAndProvider(User user, String provider);
    Optional<OAuthAccount> findByProviderAndProviderId(String provider, String providerId);
}
