package com.bunary.vocab.learning.model;

import java.time.Instant;

import com.bunary.vocab.common.model.base.BaseSoftDeleteEntity;
import com.bunary.vocab.model.User;
import com.bunary.vocab.model.WordSet;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// @Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_wordset_recents")
public class UserWordSetRecent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Instant lastLearnedAt = Instant.now();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "wordset_id")
    private WordSet wordSet;

}
