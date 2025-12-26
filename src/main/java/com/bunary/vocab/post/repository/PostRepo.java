package com.bunary.vocab.post.repository;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bunary.vocab.post.model.Post;

public interface PostRepo extends JpaRepository<Post, Long> {

    @Query("""
            SELECT p
            FROM Post p
            WHERE p.user.id = :userId
            AND p.visibility = 'PUBLIC'
            """)
    Page<Post> findByUserIdAndVisibilityIsPublic(UUID userId, Pageable pageable);

    @Query("""
            SELECT p
            FROM Post p
            WHERE p.user.id = :currentUserId
            """)
    Page<Post> findAllByCurrentUserId(UUID currentUserId, Pageable pageable);

}
