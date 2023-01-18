package cz.assist.axon_training.bff.calculator;


import cz.assist.axon_training.CalculatorId;
import cz.assist.axon_training.bff.calculator.constants.CalculatorConstants;
import cz.assist.axon_training.bff.calculator.dto.AddNumberToEntryRequest;
import cz.assist.axon_training.bff.calculator.dto.RemoveHistoryRowRequest;
import cz.assist.axon_training.bff.calculator.dto.SelectOperationTypeRequest;
import cz.assist.axon_training.bff.calculator.service.CalculatorCommandService;
import cz.assist.axon_training.bff.calculator.service.CalculatorSubscribeService;
import cz.assist.axon_training.bff.ws.model.MessageProperties;
import cz.assist.axon_training.bff.ws.model.WsRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;

import java.util.Optional;

import static org.springframework.messaging.simp.SimpMessageHeaderAccessor.SESSION_ID_HEADER;
import static org.springframework.messaging.simp.SimpMessageHeaderAccessor.USER_HEADER;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CalculatorController {

    private final CalculatorCommandService commandService;

    private final CalculatorSubscribeService subscribeService;

    @MessageMapping(CalculatorConstants.FETCH_CALCULATOR_URL)
    public void fetchCalculator(@Payload WsRequest<?> rq,
                                @Header(USER_HEADER) UsernamePasswordAuthenticationToken user,
                                @Header(SESSION_ID_HEADER) String sessionId) {
        var msgProps = getMessageProperties(user.getName(), rq.getTrackingId(), sessionId);
        subscribeService.subscribeToCalculator(CalculatorId.from(user.getName()), msgProps);
    }

    @MessageMapping(CalculatorConstants.PERFORM_CALCULATE_URL)
    public void performCalculate(@Payload WsRequest<?> rq,
                                 @Header(USER_HEADER) UsernamePasswordAuthenticationToken user,
                                 @Header(SESSION_ID_HEADER) String sessionId) {
        var id = CalculatorId.from(user.getName());
        var msgProps = getMessageProperties(user.getName(), rq.getTrackingId(), sessionId);
        commandService.performCalculate(id, msgProps);
    }

    @MessageMapping(CalculatorConstants.CLEAR_MEMORY_URL)
    public void clearMemory(@Header(USER_HEADER) UsernamePasswordAuthenticationToken user) {
        var id = CalculatorId.from(user.getName());
        commandService.clearMemory(id);
    }

    @MessageMapping(CalculatorConstants.CLEAR_HISTORY_URL)
    public void clearHistory(@Header(USER_HEADER) UsernamePasswordAuthenticationToken user) {
        var id = CalculatorId.from(user.getName());
        commandService.clearHistory(id);
    }

    @MessageMapping(CalculatorConstants.REMOVE_HISTORY_ROW_URL)
    public void removeHistoryRow(@Payload WsRequest<RemoveHistoryRowRequest> rq,
                                 @Header(USER_HEADER) UsernamePasswordAuthenticationToken user) {
        var payload = rq.getPayload();
        var id = CalculatorId.from(user.getName());
        commandService.removeHistoryRow(id, payload.rowId());
    }

    @MessageMapping(CalculatorConstants.CLEAR_ENTRY_URL)
    public void clearEntry(@Header(USER_HEADER) UsernamePasswordAuthenticationToken user) {
        var id = CalculatorId.from(user.getName());
        commandService.clearEntry(id);
    }

    @MessageMapping(CalculatorConstants.ADD_NUMBER_TO_ENTRY_URL)
    public void addNumberToEntry(@Payload WsRequest<AddNumberToEntryRequest> rq,
                                 @Header(USER_HEADER) UsernamePasswordAuthenticationToken user) {
        var payload = rq.getPayload();
        var id = CalculatorId.from(user.getName());
        commandService.addNumberToEntry(id, payload.number());
    }

    @MessageMapping(CalculatorConstants.ADD_DECIMAL_SEPARATOR_TO_ENTRY_URL)
    public void addDecimalSeparatorToEntry(@Header(USER_HEADER) UsernamePasswordAuthenticationToken user) {
        var id = CalculatorId.from(user.getName());
        commandService.addDecimalSeparatorToEntry(id);
    }

    @MessageMapping(CalculatorConstants.REMOVE_LAST_CHARACTER_FROM_ENTRY_URL)
    public void removeLastCharacterFromEntry(@Header(USER_HEADER) UsernamePasswordAuthenticationToken user) {
        var id = CalculatorId.from(user.getName());
        commandService.removeLastCharacterFromEntry(id);
    }

    @MessageMapping(CalculatorConstants.SELECT_OPERATOR_URL)
    public void selectOperator(@Payload WsRequest<SelectOperationTypeRequest> rq,
                               @Header(USER_HEADER) UsernamePasswordAuthenticationToken user,
                               @Header(SESSION_ID_HEADER) String sessionId) {
        var payload = rq.getPayload();
        var id = CalculatorId.from(user.getName());
        var msgProps = getMessageProperties(user.getName(), rq.getTrackingId(), sessionId);
        commandService.selectOperator(id, payload.operationType(), msgProps);
    }

    private MessageProperties getMessageProperties(String userId, String trackingId, String sessionId) {
        return MessageProperties.builder()
            .userId(userId)
            .trackingId(trackingId)
            .sessionId(Optional.ofNullable(sessionId))
            .build();
    }

}
