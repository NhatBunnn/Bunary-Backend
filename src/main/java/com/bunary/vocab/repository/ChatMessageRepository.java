package com.bunary.vocab.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bunary.vocab.model.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    @Query("SELECT c FROM ChatMessage c WHERE c.sender.id = :senderId AND c.receiver.id = :receiverId OR c.sender.id = :receiverId AND c.receiver.id = :senderId")
    List<ChatMessage> findBySenderIdAndReceiverId(@Param("senderId") UUID senderId,
            @Param("receiverId") UUID receiverId);

}
