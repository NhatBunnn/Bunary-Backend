package com.bunary.vocab.user.model;

import com.bunary.vocab.common.model.base.BaseEntity;
import com.bunary.vocab.model.User;
import com.bunary.vocab.user.model.enums.FriendshipStatusEnum;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "friendships", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "requester_id", "addressee_id" }),
        @UniqueConstraint(columnNames = { "addressee_id", "requester_id" })
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Friendship extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id", nullable = false)
    private User requester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "addressee_id", nullable = false)
    private User addressee;

    @Enumerated(EnumType.STRING)
    private FriendshipStatusEnum friendshipStatus;

}
