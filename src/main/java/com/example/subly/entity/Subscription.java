package com.example.subly.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "subscriptions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // 구독 서비스명

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    private double price;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(name = "payment_day")
    private int paymentDay; // 1~31

    @Column(name = "payment_method")
    private String paymentMethod;

    @JsonProperty("is_annual")
    @Column(name = "is_annual")
    private Boolean isAnnual = false;

    @JsonProperty("is_freetrial")
    @Column(name = "is_freetrial")
    private Boolean isFreeTrial = false;

    @Column(name = "freetrial_period")
    private Integer freeTrialPeriod; // days

    private String color;

    @JsonProperty("is_cancelled")
    @Column(name = "is_cancelled")
    private Boolean isCancelled = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "payment_date")
    private LocalDateTime paymentDate;

    public enum Category {
        music, video, ai, design, news, gaming, fitness,
        education, cloud, ecommerce, vpn, utilities, other
    }

    public enum Currency {
        dollar, euro
    }
}
