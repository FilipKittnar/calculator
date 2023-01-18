package cz.assist.axon_training.bff.ws.service;


import cz.assist.axon_training.bff.ws.constants.WebsocketConstants;
import cz.assist.axon_training.bff.ws.model.MessageProperties;
import cz.assist.axon_training.bff.ws.model.ResponseType;
import cz.assist.axon_training.bff.ws.model.WsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public <T> void pushResponse(ResponseType messageType, T payload, MessageProperties msgProps) {
        var wsMessage = WsResponse.<T>builder()
            .type(messageType)
            .trackingId(msgProps.getTrackingId())
            .payload(payload)
            .build();
        pushMessage(wsMessage, msgProps);
    }

    private <T> void pushMessage(WsResponse<T> wsMessage, MessageProperties msgProps) {
        var sessionId = msgProps.getSessionId();
        var user = sessionId.orElseGet(msgProps::getUserId);
        var headers = sessionId.map(id -> {
            var headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
            headerAccessor.setSessionId(user);
            headerAccessor.setLeaveMutable(true);
            return headerAccessor.getMessageHeaders();
        }).orElse(null);

        simpMessagingTemplate.convertAndSendToUser(user, WebsocketConstants.DESTINATION_QUEUE_MAIN, wsMessage, headers);
    }
}
