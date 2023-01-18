package cz.assist.axon_training.command.aggregate.model;

import cz.assist.axon_training.CalculatorId;
import cz.assist.axon_training.OperationType;
import cz.assist.axon_training.command.response.CalculationErrorType;
import cz.assist.axon_training.command.response.CalculationResponse;
import cz.assist.axon_training.common.message.EventSender;
import cz.assist.axon_training.common.util.NumberUtils;
import cz.assist.axon_training.event.AdditionOperationSelected;
import cz.assist.axon_training.event.AdditionPerformed;
import cz.assist.axon_training.event.DecimalSeparatorAddedToEntry;
import cz.assist.axon_training.event.DivisionOperationSelected;
import cz.assist.axon_training.event.DivisionPerformed;
import cz.assist.axon_training.event.EntryCleared;
import cz.assist.axon_training.event.HistoryCleared;
import cz.assist.axon_training.event.LastEntryCharacterRemoved;
import cz.assist.axon_training.event.MemoryCleared;
import cz.assist.axon_training.event.MultiplicationOperationSelected;
import cz.assist.axon_training.event.MultiplicationPerformed;
import cz.assist.axon_training.event.Number0AddedToEntry;
import cz.assist.axon_training.event.Number1AddedToEntry;
import cz.assist.axon_training.event.Number2AddedToEntry;
import cz.assist.axon_training.event.Number3AddedToEntry;
import cz.assist.axon_training.event.Number4AddedToEntry;
import cz.assist.axon_training.event.Number5AddedToEntry;
import cz.assist.axon_training.event.Number6AddedToEntry;
import cz.assist.axon_training.event.Number7AddedToEntry;
import cz.assist.axon_training.event.Number8AddedToEntry;
import cz.assist.axon_training.event.Number9AddedToEntry;
import cz.assist.axon_training.event.RowRemovedFromHistory;
import cz.assist.axon_training.event.SubtractionOperationSelected;
import cz.assist.axon_training.event.SubtractionPerformed;
import cz.assist.axon_training.event.ValueUpdated;
import lombok.With;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;

import static cz.assist.axon_training.command.constants.CalculatorConstants.ENTRY_CAPACITY_FULL_LOG;
import static cz.assist.axon_training.command.constants.CalculatorConstants.ZERO;

@Slf4j
@With
public record Calculator(CalculatorId id,
                         EventSender eventSender,
                         CalculatorState state,
                         Set<UUID> historyRows) {

    public Calculator(CalculatorId id, EventSender eventSender) {
        this(id, eventSender, new CalculatorState(), new HashSet<>());
    }


    public boolean clearEntry() {
        log.info("Clear entry for calculator {} initialized", id);

        if (state().entry().isEmpty()) {
            log.warn("Entry is empty, no need to clear entry");
            return false;
        }

        eventSender.sendEvent(EntryCleared.builder().id(id).build());
        log.info("Entry cleared.");
        return true;
    }

    public boolean clearMemory() {
        log.info("Clear memory for calculator {} initialized", id);
        if (state().entry().isEmpty() && state.operationType().isEmpty() && state.entry().isEmpty()) {
            log.warn("Memory is empty, no need to clear memory");
            return false;
        }

        eventSender.sendEvent(MemoryCleared.builder().id(id).build());

        log.info("Memory cleared.");
        return true;
    }

    public boolean removeLastCharacter() {
        log.info("Attempting to remove last entry character for calculator {}", id);

        if (state.entry().map(e -> !StringUtils.hasText(e)).orElse(true)) {
            log.warn("Nothing to remove from entry");
            return false;
        }

        eventSender.sendEvent(LastEntryCharacterRemoved.builder().id(id).build());
        log.info("Last character removed");
        return true;
    }

    public boolean addDecimalSeparatorToEntry() {
        log.info("Attempting to add comma to entry for calculator {}", id);

        if (state.entry().map(e -> e.contains(NumberUtils.DECIMAL_SEPARATOR)).orElse(false)) {
            log.warn("Entry already contains separator");
            return false;
        }

        if (state.entry().map(StringUtils::hasText).orElse(false)) {
            log.warn("Missing number before decimal separator, going to add zero.");
            eventSender.sendEvent(Number0AddedToEntry.builder().id(id).build());
        }

        eventSender.sendEvent(DecimalSeparatorAddedToEntry.builder().id(id).build());
        log.info("Comma added to entry");
        return true;
    }

    public boolean addNumber0ToEntry() {
        log.info("Attempting to add number 0 to entry for calculator {}", id);
        if (ZERO.equals(state.entry().orElse(null))) {
            log.warn("Zero is already present, aborting update.");
            return false;
        }

        if (canAddAnotherNumber()) {
            eventSender.sendEvent(Number0AddedToEntry.builder().id(id).build());
            log.info("Number 0 added");
            return true;
        }

        log.warn(ENTRY_CAPACITY_FULL_LOG);
        return false;
    }

    public boolean addNumber1ToEntry() {
        log.info("Attempting to add number 1 to entry for calculator {}", id);
        if (canAddAnotherNumber()) {
            eventSender.sendEvent(Number1AddedToEntry.builder().id(id).build());
            log.info("Number 1 added");
            return true;
        }

        log.warn(ENTRY_CAPACITY_FULL_LOG);
        return false;
    }

    public boolean addNumber2ToEntry() {
        log.info("Attempting to add number 2 to entry for calculator {}", id);
        if (canAddAnotherNumber()) {
            eventSender.sendEvent(Number2AddedToEntry.builder().id(id).build());
            log.info("Number 2 added");
            return true;
        }

        log.warn(ENTRY_CAPACITY_FULL_LOG);
        return false;
    }

    public boolean addNumber3ToEntry() {
        log.info("Attempting to add number 3 to entry for calculator {}", id);
        if (canAddAnotherNumber()) {
            eventSender.sendEvent(Number3AddedToEntry.builder().id(id).build());
            log.info("Number 3 added");
            return true;
        }

        log.warn(ENTRY_CAPACITY_FULL_LOG);
        return false;
    }

    public boolean addNumber4ToEntry() {
        log.info("Attempting to add number 4 to entry for calculator {}", id);
        if (canAddAnotherNumber()) {
            eventSender.sendEvent(Number4AddedToEntry.builder().id(id).build());
            log.info("Number 4 added");
            return true;
        }

        log.warn(ENTRY_CAPACITY_FULL_LOG);
        return false;
    }

    public boolean addNumber5ToEntry() {
        log.info("Attempting to add number 5 to entry for calculator {}", id);
        if (canAddAnotherNumber()) {
            eventSender.sendEvent(Number5AddedToEntry.builder().id(id).build());
            log.info("Number 5 added");
            return true;
        }

        log.warn(ENTRY_CAPACITY_FULL_LOG);
        return false;
    }

    public boolean addNumber6ToEntry() {
        log.info("Attempting to add number 6 to entry for calculator {}", id);
        if (canAddAnotherNumber()) {
            eventSender.sendEvent(Number6AddedToEntry.builder().id(id).build());
            log.info("Number 6 added");
            return true;
        }

        log.warn(ENTRY_CAPACITY_FULL_LOG);
        return false;
    }

    public boolean addNumber7ToEntry() {
        log.info("Attempting to add number 7 to entry for calculator {}", id);
        if (canAddAnotherNumber()) {
            eventSender.sendEvent(Number7AddedToEntry.builder().id(id).build());
            log.info("Number 7 added");
            return true;
        }

        log.warn(ENTRY_CAPACITY_FULL_LOG);
        return false;
    }

    public boolean addNumber8ToEntry() {
        log.info("Attempting to add number 8 to entry for calculator {}", id);
        if (canAddAnotherNumber()) {
            eventSender.sendEvent(Number8AddedToEntry.builder().id(id).build());
            log.info("Number 8 added");
            return true;
        }

        log.warn(ENTRY_CAPACITY_FULL_LOG);
        return false;
    }

    public boolean addNumber9ToEntry() {
        log.info("Attempting to add number 9 to entry for calculator {}", id);
        if (canAddAnotherNumber()) {
            eventSender.sendEvent(Number9AddedToEntry.builder().id(id).build());
            log.info("Number 9 added");
            return true;
        }

        log.warn(ENTRY_CAPACITY_FULL_LOG);
        return false;
    }

    public boolean selectAdditionOperation() {
        log.info("Select addition operation initialized for calculator {}", id);

        if (OperationType.ADDITION.equals(this.state.operationType().orElse(null))) {
            handleValueAndEntryAfterOperationSelection();
            log.warn("Addition is already selected, nothing to be updated");
            return false;
        }

        eventSender.sendEvent(AdditionOperationSelected.builder().id(id).build());
        handleValueAndEntryAfterOperationSelection();
        log.info("Selected addition operation");

        return true;
    }


    public boolean selectSubtractionOperation() {
        log.info("Select subtraction operation initialized for calculator {}", id);

        if (OperationType.SUBTRACTION.equals(this.state.operationType().orElse(null))) {
            handleValueAndEntryAfterOperationSelection();
            log.warn("Subtraction is already selected, nothing to be updated");
            return false;
        }

        eventSender.sendEvent(SubtractionOperationSelected.builder().id(id).build());
        handleValueAndEntryAfterOperationSelection();
        log.info("Selected subtraction operation");

        return true;
    }

    public boolean selectMultiplicationOperation() {
        log.info("Select multiplication operation initialized for calculator {}", id);

        if (OperationType.MULTIPLICATION.equals(this.state.operationType().orElse(null))) {
            handleValueAndEntryAfterOperationSelection();
            log.warn("Multiplication is already selected, nothing to be updated");
            return false;
        }

        eventSender.sendEvent(MultiplicationOperationSelected.builder().id(id).build());
        handleValueAndEntryAfterOperationSelection();
        log.info("Selected multiplication operation");

        return true;
    }

    public boolean selectDivisionOperation() {
        log.info("Select division operation initialized for calculator {}", id);
        if (OperationType.DIVISION.equals(this.state.operationType().orElse(null))) {
            handleValueAndEntryAfterOperationSelection();
            log.warn("Division is already selected, nothing to be updated");
            return false;
        }

        eventSender.sendEvent(DivisionOperationSelected.builder().id(id).build());
        handleValueAndEntryAfterOperationSelection();
        log.info("Selected division operation");

        return true;
    }

    public CalculationResponse performCalculation() {
        log.info("Perform calculation initialized for calculator {}", id);
        var calculationErrorOptional = state.operationType().map(operation -> switch (operation) {
            case ADDITION -> performAddition();
            case SUBTRACTION -> performSubtraction();
            case MULTIPLICATION -> performMultiplication();
            case DIVISION -> performDivision();
        }).orElseGet(() -> {
            log.warn("Operation is no selected, aborting calculation");
            return Optional.of(CalculationErrorType.MISSING_OPERATION);
        });
        return CalculationResponse.builder().errorType(calculationErrorOptional).build();
    }


    public boolean clearHistory() {
        log.info("Clear history for calculator {} initialized", id);

        if (CollectionUtils.isEmpty(this.historyRows)) {
            log.warn("History is already empty, no need to clear entry");
            return false;
        }

        eventSender.sendEvent(HistoryCleared.builder().id(id).build());

        log.info("History cleared.");
        return true;
    }

    public boolean removeRowFromHistory(UUID rowId) {
        log.info("Remove row {} from history for calculator {} initialized", rowId, id);

        if (CollectionUtils.isEmpty(this.historyRows) || !this.historyRows.contains(rowId)) {
            log.warn("Row is not present in history.");
            return false;
        }

        eventSender.sendEvent(RowRemovedFromHistory.builder()
            .id(id)
            .rowId(rowId)
            .build());

        log.info("Row removed.");
        return true;
    }

    private Optional<CalculationErrorType> performAddition() {
        return performCalculation(valueToAdd -> {
            var value = getValue();
            var newTotal = value.add(valueToAdd);
            eventSender.sendEvent(AdditionPerformed.builder()
                .id(id)
                .value(value)
                .incomingValue(valueToAdd)
                .result(newTotal)
                .resultId(UUID.randomUUID())
                .build());

            log.info("Performed {} + {} = {}", value.toPlainString(), valueToAdd.toPlainString(), newTotal.toPlainString());
            return Optional.empty();
        });
    }

    private Optional<CalculationErrorType> performSubtraction() {
        return performCalculation(valueToSubtract -> {
            var value = getValue();
            var newTotal = value.subtract(valueToSubtract);
            eventSender.sendEvent(SubtractionPerformed.builder()
                .id(id)
                .value(value)
                .incomingValue(valueToSubtract)
                .result(newTotal)
                .resultId(UUID.randomUUID())
                .build());
            log.info("Performed {} - {} = {}", value.toPlainString(), valueToSubtract.toPlainString(), newTotal.toPlainString());
            return Optional.empty();
        });
    }

    private Optional<CalculationErrorType> performMultiplication() {
        return performCalculation(valueToMultiplyWith -> {
            var value = getValue();
            var newTotal = value.multiply(valueToMultiplyWith);
            eventSender.sendEvent(MultiplicationPerformed.builder()
                .id(id)
                .value(value)
                .multiplier(valueToMultiplyWith)
                .result(newTotal)
                .resultId(UUID.randomUUID())
                .build());
            log.info("Performed {} * {} = {}", value.toPlainString(), valueToMultiplyWith.toPlainString(), newTotal.toPlainString());
            return Optional.empty();
        });
    }

    private Optional<CalculationErrorType> performDivision() {
        return performCalculation(valueToDivideWith -> {
            if (BigDecimal.ZERO.compareTo(valueToDivideWith) == 0) {
                log.warn("Cannot attempt with zero, aborting operation");
                return Optional.of(CalculationErrorType.ZERO_DIVISION_ATTEMPTED);
            }

            var value = getValue();
            var newTotal = value.divide(valueToDivideWith, RoundingMode.HALF_UP);
            eventSender.sendEvent(DivisionPerformed.builder()
                .id(id)
                .value(value)
                .dividend(valueToDivideWith)
                .result(newTotal)
                .resultId(UUID.randomUUID())
                .build());
            log.info("Performed {} / {} = {}", value.toPlainString(), valueToDivideWith.toPlainString(), newTotal.toPlainString());
            return Optional.empty();
        });
    }

    private Optional<CalculationErrorType> performCalculation(Function<BigDecimal, Optional<CalculationErrorType>> mapper) {
        var normalizedEntry = NumberUtils.getNormalizedNumber(this.state.entry());
        return normalizedEntry.map(mapper).orElseGet(() -> {
            log.warn("No entry is present, nothing to calculate");
            return Optional.of(CalculationErrorType.MISSING_ENTRY);
        });
    }

    private boolean canAddAnotherNumber() {
        return NumberUtils.getNormalizedNumber(state.entry()).map(number -> number.toPlainString().length() < 10).orElse(true);
    }

    private BigDecimal getValue() {
        return state.value().orElse(BigDecimal.ZERO);
    }

    private void handleValueAndEntryAfterOperationSelection() {
        setValueFromPreviousIfMissing();
        eventSender.sendEvent(EntryCleared.builder().id(id).build());
    }

    private void setValueFromPreviousIfMissing() {
        eventSender.sendEvent(ValueUpdated.builder()
            .id(id)
            .value(NumberUtils.getNormalizedNumber(state.entry()).or(state::previousValue).orElse(BigDecimal.ZERO))
            .build());
    }

}
