package com.bunary.vocab.model;

import java.time.Instant;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "VerifyCodes")
@Entity
public class VerifyCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private Instant expiresAt;
    private Instant createdAt = Instant.now();
    private int failedVerifyAttempts = 0;

    private boolean isUsed = false;

    private Instant retryAvailableAt = Instant.now();

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
