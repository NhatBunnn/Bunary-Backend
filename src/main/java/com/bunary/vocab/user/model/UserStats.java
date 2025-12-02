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

    // Experience points
    private int xp = 0;

    // Number of word sets created by the user
    private int learnedWordSet = 0;
    private int learnedWord = 0;

    // Relationship
    private UUID userId;

}
