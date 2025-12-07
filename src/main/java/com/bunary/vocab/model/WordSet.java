package com.bunary.vocab.model;

import java.time.Instant;
import java.util.List;

import com.bunary.vocab.learning.model.UserWordSetDaily;
import com.bunary.vocab.learning.model.UserWordSetRecent;
import com.bunary.vocab.model.enums.VisibilityEnum;
import com.bunary.vocab.model.enums.WordSetLevelEnum;
import com.bunary.vocab.model.relation.WordSetTag;
import com.bunary.vocab.user.model.UserWordSetProgress;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "WordSets")
@Entity
public class WordSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private VisibilityEnum visibility = VisibilityEnum.PRIVATE;

    @Column(length = 1000)
    private String thumbnail;

    @Enumerated(EnumType.STRING)
    private WordSetLevelEnum level;

    private Instant createdAt;

    private Instant updatedAt;

    private Instant deletedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "wordSet", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Word> Words;

    @OneToMany(mappedBy = "wordSet", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<WordSetTag> wordSetTags;

    @ManyToMany(mappedBy = "wordSets", fetch = FetchType.LAZY)
    private List<Collection> collections;

    @OneToMany(mappedBy = "wordSet", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<WordSetRating> wordSetRatings;

    @OneToOne(mappedBy = "wordSet", fetch = FetchType.LAZY)
    @JsonIgnore
    private WordSetStat wordSetStat;

    @OneToMany(mappedBy = "wordSet", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserWordSetProgress> userWordSetProgresses;

    @OneToMany(mappedBy = "wordSet", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserWordSetRecent> userWordSetRecents;

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }
}
