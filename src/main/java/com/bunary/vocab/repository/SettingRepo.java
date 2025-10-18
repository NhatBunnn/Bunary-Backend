package com.bunary.vocab.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bunary.vocab.model.Setting;

public interface SettingRepo extends JpaRepository<Setting, Long> {

    Setting findByUserIdAndType(UUID userId, String type);

}
