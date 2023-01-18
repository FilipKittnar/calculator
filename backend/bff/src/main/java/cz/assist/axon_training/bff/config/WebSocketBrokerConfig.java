package cz.assist.axon_training.bff.config;

import cz.assist.axon_training.bff.ws.constants.WebsocketConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

import java.util.List;


@RequiredArgsConstructor
@Slf4j
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketBrokerConfig implements WebSocketMessageBrokerConfigurer {

    private static final long WS_HEART_BEAT_SERVER_TIMEOUT = 10000;
    private static final long WS_HEART_BEAT_CLIENT_TIMEOUT = 10000;

    // 2 MB
    private static final Integer MESSAGE_SIZE_LIMIT = 2 * 1024 * 1024;
    private static final Integer SEND_TIME_LIMIT = 20 * 10000;

    // 512 MB
    private static final Integer SEND_BUFFER_SIZE_LIMIT = 512 * 1024 * 1024;

    private final AuthChannelInterceptor interceptor;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Defines broker URL endpoint for client connection
        registry.addEndpoint("/ws")
            .setAllowedOriginPatterns(List.of("*").toArray(new String[0]));

        registry.addEndpoint("/ws")
            .setAllowedOriginPatterns(List.of("*").toArray(new String[0]))
            .withSockJS();
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        registration.setMessageSizeLimit(MESSAGE_SIZE_LIMIT);
        registration.setSendTimeLimit(SEND_TIME_LIMIT);
        registration.setSendBufferSizeLimit(SEND_BUFFER_SIZE_LIMIT);
    }

    @Override
    public void configureClientInboundChannel(final ChannelRegistration registration) {
        registration.interceptors(interceptor);
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        var heartBeatScheduler = new ThreadPoolTaskScheduler();
        heartBeatScheduler.setPoolSize(1);
        heartBeatScheduler.setThreadNamePrefix("wss-heartbeat-thread-");
        heartBeatScheduler.initialize();

        registry.enableSimpleBroker("/topic/", "/queue/")
            .setHeartbeatValue(new long[]{WS_HEART_BEAT_SERVER_TIMEOUT, WS_HEART_BEAT_CLIENT_TIMEOUT})
            .setTaskScheduler(heartBeatScheduler);

        registry.setApplicationDestinationPrefixes(WebsocketConstants.WS_DESTINATION_PREFIX);
        // messages within same client session are published to the clientOutboundChannel one at a time
        registry.setPreservePublishOrder(true);
    }

}
