package com.bunary.vocab.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.bunary.vocab.profile.model.Profile;
import java.util.Optional;
import java.util.UUID;

@Component
public interface ProfileRepo extends JpaRepository<Profile, Long> {
    Optional<Profile> findByUserId(UUID userId);
}
