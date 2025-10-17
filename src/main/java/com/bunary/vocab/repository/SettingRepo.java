package com.bunary.vocab.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bunary.vocab.model.Setting;

public interface SettingRepo extends JpaRepository<Setting, Long> {

    @Query(value = """
                SELECT * FROM settings s
                WHERE s.user_id = :userId
                AND s.type = :type
                AND JSON_UNQUOTE(JSON_EXTRACT(s.settings, '$.mode')) = :mode
            """, nativeQuery = true)
    Optional<Setting> findByUserIdAndTypeAndMode(
            @Param("userId") UUID userId,
            @Param("type") String type,
            @Param("mode") String mode);

    Setting findByUserIdAndType(UUID userId, String type);
}
