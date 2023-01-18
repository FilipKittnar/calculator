package cz.assist.axon_training.bff.calculator.service;

import cz.assist.axon_training.CalculatorId;
import cz.assist.axon_training.bff.calculator.mapper.CalculatorMapper;
import cz.assist.axon_training.bff.service.QueryService;
import cz.assist.axon_training.bff.ws.model.MessageProperties;
import cz.assist.axon_training.bff.ws.model.ResponseType;
import cz.assist.axon_training.bff.ws.service.NotificationService;
import cz.assist.axon_training.query.GetUserCalculator;
import cz.assist.axon_training.query.response.CalculatorDTO;
import cz.assist.axon_training.query.response.GetUserCalculatorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryResponseMessage;
import org.axonframework.queryhandling.SubscriptionQueryUpdateMessage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalculatorSubscribeService {

    private final QueryService queryService;

    private final NotificationService notificationService;

    public void subscribeToCalculator(CalculatorId id, MessageProperties msgProps) {
        var query = new GetUserCalculator(id);

        var sub = queryService.subscriptionQuery(query, GetUserCalculatorResponse.class, CalculatorDTO.class);
        sub.handle(initial -> handleInitial(initial, msgProps), update -> handleUpdate(update, msgProps));
    }

    private void handleInitial(QueryResponseMessage<GetUserCalculatorResponse> initial, MessageProperties msgProps) {
        pushCalculatorDTO(initial.getPayload().calculator(), msgProps);
    }

    private void handleUpdate(SubscriptionQueryUpdateMessage<CalculatorDTO> update, MessageProperties msgProps) {
        pushCalculatorDTO(update.getPayload(), msgProps);
    }


    private void pushCalculatorDTO(CalculatorDTO calculatorDTO, MessageProperties msgProps) {
        var response = CalculatorMapper.map(calculatorDTO);
        notificationService.pushResponse(ResponseType.GET_CALCULATOR_RESPONSE, response, msgProps);
    }

}
