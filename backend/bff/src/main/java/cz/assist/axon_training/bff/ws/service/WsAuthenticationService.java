package cz.assist.axon_training.bff.ws.service;

import org.springframework.messaging.simp.stomp.StompHeaderAccessor;

public interface WsAuthenticationService {

    void authenticateMessage(StompHeaderAccessor accessor);

}
