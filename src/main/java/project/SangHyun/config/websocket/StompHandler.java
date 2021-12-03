package project.SangHyun.config.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import project.SangHyun.advice.exception.InvalidWebSocketConnection;
import project.SangHyun.config.security.jwt.JwtTokenProvider;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String token = Objects.requireNonNull(accessor.getFirstNativeHeader("X-AUTH-TOKEN"));
            if (!jwtTokenProvider.validateTokenExpiration(token))
                throw new InvalidWebSocketConnection();
        }

        return message;
    }
}
