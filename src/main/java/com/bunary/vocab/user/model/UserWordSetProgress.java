package com.bunary.vocab.user.model;

import java.time.Instant;

import com.bunary.vocab.common.model.base.BaseSoftDeleteEntity;
import com.bunary.vocab.model.User;
import com.bunary.vocab.model.WordSet;
import com.bunary.vocab.user.model.enums.StudyModeEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "UserWordsetProgress", uniqueConstraints = @UniqueConstraint(columnNames = { "user_id", "wordset_id",
        "studyMode" }))
public class UserWordSetProgress extends BaseSoftDeleteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private StudyModeEnum studyMode;

    private int learnCount = 0;

    private Instant lastStudiedAt;

    // Relationship
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wordset_id")
    private WordSet wordSet;
}
