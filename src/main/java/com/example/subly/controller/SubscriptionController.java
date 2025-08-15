package com.example.subly.controller;

import com.example.subly.dto.SubscriptionResponse;
import com.example.subly.entity.Subscription;
import com.example.subly.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @GetMapping("/{userId}")
    public List<SubscriptionResponse> getSubscriptions(@PathVariable Long userId) {
        return subscriptionService.getUserSubscriptions(userId);
    }

    @PostMapping("/{userId}")
    public SubscriptionResponse createSubscription(
            @PathVariable Long userId,
            @RequestBody Subscription subscription) {
        return subscriptionService.createSubscription(userId, subscription);
    }

    @PutMapping("/{id}")
    public SubscriptionResponse updateSubscription(
            @PathVariable Long id,
            @RequestBody Subscription subscription) {
        return subscriptionService.updateSubscription(id, subscription);
    }

    @DeleteMapping("/{id}")
    public void deleteSubscription(@PathVariable Long id) {
        subscriptionService.deleteSubscription(id);
    }
}
