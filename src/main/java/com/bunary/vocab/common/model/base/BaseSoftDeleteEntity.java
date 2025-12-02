package com.bunary.vocab.common.model.base;

import java.time.Instant;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseSoftDeleteEntity extends BaseEntity {
    private Instant deletedAt;
}
