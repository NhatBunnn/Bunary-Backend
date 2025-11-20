package com.bunary.vocab.repository.nosql;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.bunary.vocab.model.nosql.ChatMessage;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {

    List<ChatMessage> findBySenderId(Long senderId);

    List<ChatMessage> findByReceiverId(Long receiverId);

    @Query("{ $or: [ { 'senderId': ?0, 'receiverId': ?1 }, { 'senderId': ?1, 'receiverId': ?0 } ] }")
    Page<ChatMessage> findChatBetween(String senderId, String receiverId, Pageable pageable);

}
