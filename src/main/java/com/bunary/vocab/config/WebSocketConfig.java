package com.bunary.vocab.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.bunary.vocab.Queue.MessageQueue.IChatMessageQueue;
import com.bunary.vocab.mapper.ChatMessageMapper;
import com.bunary.vocab.security.JwtUtil;
import com.bunary.vocab.service.ChatMessage.IChatMessageService;
import com.bunary.vocab.service.user.IUserService;
import com.bunary.vocab.websocket.ChatHandshakeInterceptor;
import com.bunary.vocab.websocket.ChatWebSocketHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private final JwtUtil jwtUtil;
    private ObjectMapper objectMapper;
    private final IUserService userService;
    private IChatMessageService chatMessageService;
    private IChatMessageQueue chatMessageQueue;
    private ChatMessageMapper chatMessageMapper;
    // Nó có nghĩa là mọi request kết nối đến /chat sẽ được xử lý như một WebSocket,
    // không phải request HTTP thông thường nữa.

    // Giao thức của websocket là: ws:// thay vì: https://
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                .addHandler(chatWebSocketHandler(), "/ws")
                .addInterceptors(new ChatHandshakeInterceptor(jwtUtil))
                .setAllowedOrigins("*");
    }

    @Bean
    public WebSocketHandler chatWebSocketHandler() {
        return new ChatWebSocketHandler(objectMapper, userService, chatMessageService, chatMessageQueue,
                chatMessageMapper);
    }

}
