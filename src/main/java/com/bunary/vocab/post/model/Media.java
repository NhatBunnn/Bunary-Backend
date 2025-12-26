package com.bunary.vocab.post.model;

import com.bunary.vocab.common.model.base.BaseEntity;
import com.bunary.vocab.post.model.enums.MediaTargetType;
import com.bunary.vocab.post.model.enums.MediaType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "medias")
@Entity
public class Media extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long targetId;

    private MediaTargetType targetType;
    private MediaType mediaType;

    private String url;

}
