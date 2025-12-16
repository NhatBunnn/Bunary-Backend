package com.bunary.vocab.user.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bunary.vocab.user.model.Follow;

public interface FollowRepo extends JpaRepository<Follow, Long> {
        Optional<Follow> findByFollowerIdAndFolloweeId(UUID followerId, UUID followeeId);

        @EntityGraph(attributePaths = "follower")
        @Query("""
                            SELECT f
                            FROM Follow f
                            WHERE f.followee.id = :followeeId
                              AND (:keyword IS NULL
                                   OR LOWER(f.follower.fullName)
                                      LIKE LOWER(CONCAT('%', :keyword, '%')))
                        """)
        Page<Follow> findAllFollowerByKeywordAndFolloweeId(
                        UUID followeeId,
                        String keyword,
                        Pageable pageable);

}
