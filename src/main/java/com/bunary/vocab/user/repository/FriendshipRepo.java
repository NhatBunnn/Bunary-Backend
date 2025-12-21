package com.bunary.vocab.user.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bunary.vocab.user.model.Friendship;

public interface FriendshipRepo extends JpaRepository<Friendship, Long> {
        Optional<Friendship> findByRequesterIdAndAddresseeId(UUID requesterId, UUID addresseeId);

        @EntityGraph(attributePaths = "requester")
        @Query("""
                        SELECT f
                        FROM Friendship f
                        WHERE (f.addressee.id = :addresseeId OR f.requester.id = :requesterId)
                        AND (:keyword IS NULL
                             OR LOWER(f.requester.fullName)
                                LIKE LOWER(CONCAT('%', :keyword, '%')))
                        AND f.friendshipStatus = 'ACCEPTED'
                        """)
        Page<Friendship> findMyFriends(UUID addresseeId, String keyword, Pageable pageable);

        @Query("""
                        SELECT f
                        FROM Friendship f
                        WHERE
                        (f.addressee.id = :addresseeId AND f.requester.id = :requesterId)
                        OR
                        (f.addressee.id = :requesterId AND f.requester.id = :addresseeId)
                        """)
        Optional<Friendship> findBetween(UUID addresseeId, UUID requesterId);
}
