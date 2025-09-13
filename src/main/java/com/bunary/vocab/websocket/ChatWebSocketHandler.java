package com.bunary.vocab.websocket;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.bunary.vocab.Queue.MessageQueue.IChatMessageQueue;
import com.bunary.vocab.dto.reponse.ChatMessageResponseDTO;
import com.bunary.vocab.exception.GlobalErrorCode;
import com.bunary.vocab.mapper.ChatMessageMapper;
import com.bunary.vocab.service.ChatMessage.IChatMessageService;
import com.bunary.vocab.service.user.IUserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ChatWebSocketHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper;
    private final IUserService userService;
    private final IChatMessageService chatMessageService;
    private final IChatMessageQueue chatMessageQueue;
    private final ChatMessageMapper chatMessageMapper;

    private static final Map<String, WebSocketSession> userSessions = new ConcurrentHashMap<>();
    private static final Map<String, Boolean> chatWindowStatus = new ConcurrentHashMap<>();
    private static final Map<String, Boolean> notificationSent = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Object userId = session.getAttributes().get("userId");
        if (userId != null) {
            userSessions.put(userId.toString(), session);
            chatWindowStatus.put(userId.toString(), false);
            notificationSent.put(userId.toString(), false);

            List<String> onlineUserIds = new ArrayList<>(userSessions.keySet());
            String jsonMessage = objectMapper.writeValueAsString(
                    Map.of("type", "onlineUsers", "users", onlineUserIds));

            for (WebSocketSession s : userSessions.values()) {
                s.sendMessage(new TextMessage(jsonMessage));
            }

            System.out.println("[=====] Kết nối WebSocket thành công với người dùng: " + userId.toString());
        }
    }

    @Override
    // "session" đại diện cho kết nối WebSocket giữa client và server.
    // "message" đại diện cho từng tin nhắn mà client gửi.
    // TextMessage: Đang nhận chuỗi String (và có thể nhận Json)
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        System.out.println("Received JSON: " + payload);

        JsonNode jsonNode = objectMapper.readTree(payload);

        String type = jsonNode.get("type").asText();

        switch (type) {
            case "chatMessage": {

                try {
                    String senderId = session.getAttributes().get("userId").toString();
                    String receiverId = jsonNode.get("receiverId").asText();
                    String messageContent = jsonNode.get("messageContent").asText();

                    if (senderId == receiverId)
                        return;

                    ChatMessageResponseDTO chatMessageDTO = new ChatMessageResponseDTO();

                    chatMessageDTO.setReceiverId(UUID.fromString(receiverId));
                    chatMessageDTO.setSenderId(UUID.fromString(senderId));
                    chatMessageDTO.setType("chatMessage");
                    chatMessageDTO.setMessageContent(messageContent);
                    chatMessageDTO.setTimestamp(Instant.now());

                    String jsonMessage = objectMapper.writeValueAsString(chatMessageDTO);

                    WebSocketSession receiverSession = null;
                    try {
                        receiverSession = userSessions.get(receiverId);
                        receiverSession.sendMessage(new TextMessage(jsonMessage));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    this.chatMessageQueue.addChatMessage(this.chatMessageMapper.convertToChatMessage(chatMessageDTO));

                    Boolean isOpen = chatWindowStatus.get(receiverId);
                    Boolean isNotified = notificationSent.get(receiverId);

                    if (!isOpen && !isNotified) {
                        String notification = "{ \"type\": \"notification\", \"message\": \"Bạn có tin nhắn mới\" }";
                        receiverSession.sendMessage(new TextMessage(notification));
                        notificationSent.put(receiverId, true);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            }
            case "chatWindowStatus": {
                String isOpen = jsonNode.get("isOpen").asText();
                String userId = session.getAttributes().get("userId").toString();

                if (Boolean.parseBoolean(isOpen)) {
                    notificationSent.put(userId, true);
                }

                chatWindowStatus.put(session.getAttributes().get("userId").toString(), Boolean.parseBoolean(isOpen));
                break;
            }
            default:
                break;
        }

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Object userId = session.getAttributes().get("userId");
        if (userId != null) {
            userSessions.remove(userId.toString());
            chatWindowStatus.remove(userId.toString());
            notificationSent.remove(userId.toString());

            this.chatMessageQueue.flush();

            System.out.println("[=====] Người dùng " + userId.toString() + " đã ngắt kết nối WebSocket.");

            List<String> onlineUserIds = new ArrayList<>(userSessions.keySet());
            String jsonMessage = objectMapper.writeValueAsString(
                    Map.of("type", "onlineUsers", "users", onlineUserIds));

            for (WebSocketSession s : userSessions.values()) {
                s.sendMessage(new TextMessage(jsonMessage));
            }

        }
    }

}
