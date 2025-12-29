package com.bunary.vocab.user.model;

import com.bunary.vocab.common.model.base.BaseSoftDeleteEntity;
import com.bunary.vocab.model.User;

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
@Table(name = "user_stat_daily")
public class UserStatDaily extends BaseSoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Learning stats
    private int learnedWordSetsCount = 0;

    // Points & rewards
    private int point = 0;
    private int spark = 0;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
