package com.example.subly.service;

import com.example.subly.dto.SubscriptionResponse;
import com.example.subly.entity.Subscription;
import com.example.subly.entity.User;
import com.example.subly.repository.SubscriptionRepository;
import com.example.subly.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    public List<SubscriptionResponse> getUserSubscriptions(Long userId) {
        return subscriptionRepository.findByUserId(userId)
                .stream()
                .map(SubscriptionResponse::fromEntity)
                .toList();
    }

    public SubscriptionResponse createSubscription(Long userId, Subscription sub) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        sub.setUser(user);
        Subscription saved = subscriptionRepository.save(sub);
        return SubscriptionResponse.fromEntity(saved);
    }

    public SubscriptionResponse updateSubscription(Long id, Subscription sub) {
        Subscription existing = subscriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));

        existing.setName(sub.getName());
        existing.setCategory(sub.getCategory());
        existing.setPrice(sub.getPrice());
        existing.setCurrency(sub.getCurrency());
        existing.setPaymentDay(sub.getPaymentDay());
        existing.setPaymentMethod(sub.getPaymentMethod());
        existing.setIsAnnual(sub.getIsAnnual());
        existing.setIsFreeTrial(sub.getIsFreeTrial());
        existing.setFreeTrialPeriod(sub.getFreeTrialPeriod());
        existing.setColor(sub.getColor());
        existing.setIsCancelled(sub.getIsCancelled());

        return SubscriptionResponse.fromEntity(subscriptionRepository.save(existing));
    }

    public void deleteSubscription(Long id) {
        subscriptionRepository.deleteById(id);
    }
}
