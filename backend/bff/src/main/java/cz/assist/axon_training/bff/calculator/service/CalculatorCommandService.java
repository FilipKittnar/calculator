package cz.assist.axon_training.bff.calculator.service;

import cz.assist.axon_training.CalculatorId;
import cz.assist.axon_training.OperationType;
import cz.assist.axon_training.bff.calculator.dto.CalculationErrorResponse;
import cz.assist.axon_training.bff.ws.model.MessageProperties;
import cz.assist.axon_training.bff.ws.model.ResponseType;
import cz.assist.axon_training.bff.ws.service.NotificationService;
import cz.assist.axon_training.command.AddDecimalSeparatorToEntry;
import cz.assist.axon_training.command.AddNumber0ToEntry;
import cz.assist.axon_training.command.AddNumber1ToEntry;
import cz.assist.axon_training.command.AddNumber2ToEntry;
import cz.assist.axon_training.command.AddNumber3ToEntry;
import cz.assist.axon_training.command.AddNumber4ToEntry;
import cz.assist.axon_training.command.AddNumber5ToEntry;
import cz.assist.axon_training.command.AddNumber6ToEntry;
import cz.assist.axon_training.command.AddNumber7ToEntry;
import cz.assist.axon_training.command.AddNumber8ToEntry;
import cz.assist.axon_training.command.AddNumber9ToEntry;
import cz.assist.axon_training.command.ClearEntry;
import cz.assist.axon_training.command.ClearHistory;
import cz.assist.axon_training.command.ClearMemory;
import cz.assist.axon_training.command.PerformCalculation;
import cz.assist.axon_training.command.RemoveLastEntryCharacter;
import cz.assist.axon_training.command.RemoveRowFromHistory;
import cz.assist.axon_training.command.SelectAdditionOperation;
import cz.assist.axon_training.command.SelectDivisionOperation;
import cz.assist.axon_training.command.SelectMultiplicationOperation;
import cz.assist.axon_training.command.SelectSubtractionOperation;
import cz.assist.axon_training.command.response.CalculationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalculatorCommandService {

    private final CommandGateway commandGateway;

    private final NotificationService notificationService;

    public void performCalculate(CalculatorId id, MessageProperties msgProps) {
        var cmd = PerformCalculation.builder().id(id).build();

        CalculationResponse response = commandGateway.sendAndWait(cmd);
        response.getErrorType().ifPresent(error -> {
            var errorResponse = new CalculationErrorResponse(error);
            notificationService.pushResponse(ResponseType.GET_CALCULATE_ERROR_RESPONSE, errorResponse, msgProps);
        });
    }

    public void clearMemory(CalculatorId id) {
        var cmd = ClearMemory.builder().id(id).build();
        commandGateway.sendAndWait(cmd);
    }

    public void clearHistory(CalculatorId id) {
        var cmd = ClearHistory.builder().id(id).build();
        commandGateway.sendAndWait(cmd);
    }

    public void clearEntry(CalculatorId id) {
        var cmd = ClearEntry.builder().id(id).build();
        commandGateway.sendAndWait(cmd);
    }

    public void removeHistoryRow(CalculatorId id, UUID rowId) {
        var cmd = RemoveRowFromHistory.builder()
            .id(id)
            .rowId(rowId)
            .build();
        commandGateway.sendAndWait(cmd);
    }

    public void addNumberToEntry(CalculatorId id, Integer number) {
        if (number < 0 || number > 9) {
            log.warn("Invalid number provided, no command will be sent");
            return;
        }

        var cmd = switch (number) {
            case 0 -> AddNumber0ToEntry.builder().id(id).build();
            case 1 -> AddNumber1ToEntry.builder().id(id).build();
            case 2 -> AddNumber2ToEntry.builder().id(id).build();
            case 3 -> AddNumber3ToEntry.builder().id(id).build();
            case 4 -> AddNumber4ToEntry.builder().id(id).build();
            case 5 -> AddNumber5ToEntry.builder().id(id).build();
            case 6 -> AddNumber6ToEntry.builder().id(id).build();
            case 7 -> AddNumber7ToEntry.builder().id(id).build();
            case 8 -> AddNumber8ToEntry.builder().id(id).build();
            case 9 -> AddNumber9ToEntry.builder().id(id).build();
            default -> null;
        };
        commandGateway.sendAndWait(cmd);
    }

    public void addDecimalSeparatorToEntry(CalculatorId id) {
        var cmd = AddDecimalSeparatorToEntry.builder()
            .id(id)
            .build();
        commandGateway.sendAndWait(cmd);
    }

    public void removeLastCharacterFromEntry(CalculatorId id) {
        var cmd = RemoveLastEntryCharacter.builder()
            .id(id)
            .build();
        commandGateway.sendAndWait(cmd);
    }

    public void selectOperator(CalculatorId id, OperationType operationType, MessageProperties msgProps) {
        var cmd = switch (operationType) {
            case ADDITION -> SelectAdditionOperation.builder().id(id).build();
            case SUBTRACTION -> SelectSubtractionOperation.builder().id(id).build();
            case MULTIPLICATION -> SelectMultiplicationOperation.builder().id(id).build();
            case DIVISION -> SelectDivisionOperation.builder().id(id).build();
        };
        commandGateway.sendAndWait(cmd);
    }
}
