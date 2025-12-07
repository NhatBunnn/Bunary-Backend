package com.bunary.vocab.user.model;

import java.util.UUID;

import com.bunary.vocab.common.model.base.BaseSoftDeleteEntity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_stats")
public class UserStats extends BaseSoftDeleteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Number of word sets created by the user
    private int learnedWordSetsCount = 0;
    private int learnedWordsCount = 0;

    // Stats
    private int point;
    private int spark;

    // Streak
    private int streak;
    private int max_streak;

    // Relationship
    private UUID userId;

}
