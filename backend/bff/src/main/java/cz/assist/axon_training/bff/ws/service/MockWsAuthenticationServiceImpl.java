package cz.assist.axon_training.bff.ws.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Slf4j
public class MockWsAuthenticationServiceImpl implements WsAuthenticationService {

    @Override
    public void authenticateMessage(StompHeaderAccessor accessor) {
        var user = User.builder()
            .username("mockUser")
            .authorities(Collections.emptyList())
            .password("mockPassword")
            .build();
        final var authentication = new UsernamePasswordAuthenticationToken(user, "mockPassword", user.getAuthorities());
        accessor.setUser(authentication);
    }
}
