package com.bunary.vocab.Queue.MessageQueue;

import com.bunary.vocab.model.ChatMessage;

public interface IChatMessageQueue {
    void addChatMessage(ChatMessage message);

    void flush();
}
