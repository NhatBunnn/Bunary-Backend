package com.bunary.vocab.post.model;

import java.util.List;

import com.bunary.vocab.common.model.base.BaseSoftDeleteEntity;
import com.bunary.vocab.model.User;
import com.bunary.vocab.post.model.enums.PostVisibility;

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
@Table(name = "posts")
@Entity
public class Post extends BaseSoftDeleteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Content
    private String content;
    private String background;

    // Reaction summary
    private long reactionCount;
    private long likeCount;
    private long loveCount;
    private long hahaCount;
    private long wowCount;
    private long sadCount;
    private long angryCount;

    // Visibility
    @Enumerated(EnumType.STRING)
    private PostVisibility visibility;

    // Relationship
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
