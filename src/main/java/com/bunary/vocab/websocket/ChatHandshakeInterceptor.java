package com.bunary.vocab.websocket;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.lang.Nullable;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.bunary.vocab.security.JwtUtil;
import com.bunary.vocab.security.SecurityUtil;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ChatHandshakeInterceptor implements HandshakeInterceptor {
    private final JwtUtil jwtUtil;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
            WebSocketHandler wsHandler, Map<String, Object> attributes) {

        try {
            if (request instanceof ServletServerHttpRequest servletRequest) {
                var httpRequest = servletRequest.getServletRequest();
                String accessToken = httpRequest.getParameter("accessToken");

                if (accessToken != null) {
                    Jwt decodedJwt = this.jwtUtil.decodeToken(accessToken);
                    String userId = decodedJwt.getSubject();
                    UUID uuid = UUID.fromString(userId);

                    attributes.put("userId", uuid);
                    System.out.println("Access token hợp lệ, userId: " + userId);
                } else {
                    System.out.println("Không có access token trong query param");
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            System.out.println("lỗi handshake " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
            WebSocketHandler wsHandler, @Nullable Exception exception) {
        // để trống nếu không cần
    }

}
