package com.bunary.vocab.learning.model;

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

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_wordset_daily")
public class UserWordSetDaily extends BaseSoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int point_earned;
    private int spark_earned;

    private int learned_count;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
