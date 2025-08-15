package com.example.subly.dto;

import com.example.subly.entity.Subscription;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionResponse {
    private Long id;
    private String name;
    private Subscription.Category category;
    private double price;
    private Subscription.Currency currency;
    private int payment_day;
    private String payment_method;
    private Integer freetrial_period;
    private String color;

    @JsonProperty("is_annual")
    private boolean annual;

    @JsonProperty("is_freetrial")
    private boolean freetrial;

    @JsonProperty("is_cancelled")
    private boolean cancelled;

    public static SubscriptionResponse fromEntity(Subscription sub) {
        return SubscriptionResponse.builder()
                .id(sub.getId())
                .name(sub.getName())
                .category(sub.getCategory())
                .price(sub.getPrice())
                .currency(sub.getCurrency())
                .payment_day(sub.getPaymentDay())
                .payment_method(sub.getPaymentMethod())
                .freetrial_period(sub.getFreeTrialPeriod())
                .color(sub.getColor())
                .annual(Boolean.TRUE.equals(sub.getIsAnnual()))
                .freetrial(Boolean.TRUE.equals(sub.getIsFreeTrial()))
                .cancelled(Boolean.TRUE.equals(sub.getIsCancelled()))
                .build();
    }
}
