package com.bunary.vocab.post.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bunary.vocab.post.model.Media;
import com.bunary.vocab.post.model.enums.MediaTargetType;

public interface MediaRepo extends JpaRepository<Media, Long> {

    Page<Media> findByTargetTypeAndTargetIdIn(MediaTargetType targetType, List<Long> targetId, Pageable pageable);
}