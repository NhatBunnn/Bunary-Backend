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

    // Learning stats
    private int learnedWordSetsCount = 0;

    // Points & rewards
    private int point = 0;
    private int spark = 0;

    // Streak
    private int streak;
    private int max_streak;

    // Relationship
    private UUID userId;

}
