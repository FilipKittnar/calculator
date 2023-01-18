package cz.assist.axon_training.bff.config;

import cz.assist.axon_training.bff.ws.service.WsAuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthChannelInterceptor implements ChannelInterceptor {

    private final WsAuthenticationService wsAuthenticationService;

    @Override
    public Message<?> preSend(@Nullable final Message<?> message,
                              @Nullable final MessageChannel channel) throws AuthenticationException {
        if (message == null) {
            throw new RuntimeException("message is null");
        }
        final StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (accessor == null) {
            throw new RuntimeException("StompHeaderAccessor is null");
        }
        if (accessor.getCommand() == StompCommand.CONNECT) {
            wsAuthenticationService.authenticateMessage(accessor);
        }

        return message;
    }

}
