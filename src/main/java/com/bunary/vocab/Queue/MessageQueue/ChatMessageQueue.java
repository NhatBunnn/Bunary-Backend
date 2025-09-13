package com.bunary.vocab.Queue.MessageQueue;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bunary.vocab.model.ChatMessage;
import com.bunary.vocab.service.ChatMessage.IChatMessageService;

import jakarta.annotation.PreDestroy;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ChatMessageQueue implements IChatMessageQueue {
    private final List<ChatMessage> buffer = new ArrayList<>();
    private final int BATCH_SIZE = 10;
    private final IChatMessageService chatMessageService;

    @Override
    public synchronized void addChatMessage(ChatMessage message) {
        buffer.add(message);

        if (buffer.size() >= BATCH_SIZE) {
            flush();
        }
    }

    @PreDestroy
    public void onShutdown() {
        flush();
    }

    @Override
    public synchronized void flush() {
        try {
            this.chatMessageService.saveAllAsync(new ArrayList<>(buffer));
        } catch (Exception e) {
            e.printStackTrace();
        }
        buffer.clear();
    }
}
