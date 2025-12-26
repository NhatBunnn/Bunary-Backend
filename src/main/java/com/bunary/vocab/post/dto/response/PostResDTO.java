package com.bunary.vocab.post.dto.response;

import java.time.Instant;
import java.util.List;

import com.bunary.vocab.dto.reponse.UserResponseDTO;
import com.bunary.vocab.post.model.enums.PostVisibility;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResDTO {
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
    private PostVisibility visibility;

    // Relationship
    private UserResponseDTO user;

    private List<MediaResDTO> medias;

    // Timestamp
    private Instant createdAt;
    private Instant updatedAt;
}
