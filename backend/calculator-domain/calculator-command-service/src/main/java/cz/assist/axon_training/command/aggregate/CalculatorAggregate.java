package cz.assist.axon_training.command.aggregate;

import cz.assist.axon_training.CalculatorId;
import cz.assist.axon_training.OperationType;
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
import cz.assist.axon_training.command.aggregate.model.Calculator;
import cz.assist.axon_training.command.aggregate.model.CalculatorState;
import cz.assist.axon_training.command.response.CalculationResponse;
import cz.assist.axon_training.common.message.AxonEventSender;
import cz.assist.axon_training.common.message.EventSender;
import cz.assist.axon_training.common.util.NumberUtils;
import cz.assist.axon_training.event.AdditionOperationSelected;
import cz.assist.axon_training.event.AdditionPerformed;
import cz.assist.axon_training.event.CalculatorInitialized;
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
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateCreationPolicy;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.CreationPolicy;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.HashSet;
import java.util.Optional;

@Aggregate
@NoArgsConstructor
public class CalculatorAggregate {

    @AggregateIdentifier
    private String id;

    private Calculator calculator;

    private static final EventSender EVENT_SENDER = AxonEventSender.getInstance();

    /**
     * -----------------------
     * Command handlers
     * -----------------------
     */
    @CommandHandler
    @CreationPolicy(AggregateCreationPolicy.CREATE_IF_MISSING)
    public boolean handle(AddNumber0ToEntry command) {
        initCalculatorIfNull(command.getId());
        return calculator.addNumber0ToEntry();
    }

    @CommandHandler
    @CreationPolicy(AggregateCreationPolicy.CREATE_IF_MISSING)
    public boolean handle(AddNumber1ToEntry command) {
        initCalculatorIfNull(command.getId());
        return calculator.addNumber1ToEntry();
    }

    @CommandHandler
    @CreationPolicy(AggregateCreationPolicy.CREATE_IF_MISSING)
    public boolean handle(AddNumber2ToEntry command) {
        initCalculatorIfNull(command.getId());
        return calculator.addNumber2ToEntry();
    }

    @CommandHandler
    @CreationPolicy(AggregateCreationPolicy.CREATE_IF_MISSING)
    public boolean handle(AddNumber3ToEntry command) {
        initCalculatorIfNull(command.getId());
        return calculator.addNumber3ToEntry();
    }

    @CommandHandler
    @CreationPolicy(AggregateCreationPolicy.CREATE_IF_MISSING)
    public boolean handle(AddNumber4ToEntry command) {
        initCalculatorIfNull(command.getId());
        return calculator.addNumber4ToEntry();
    }

    @CommandHandler
    @CreationPolicy(AggregateCreationPolicy.CREATE_IF_MISSING)
    public boolean handle(AddNumber5ToEntry command) {
        initCalculatorIfNull(command.getId());
        return calculator.addNumber5ToEntry();
    }

    @CommandHandler
    @CreationPolicy(AggregateCreationPolicy.CREATE_IF_MISSING)
    public boolean handle(AddNumber6ToEntry command) {
        initCalculatorIfNull(command.getId());
        return calculator.addNumber6ToEntry();
    }

    @CommandHandler
    @CreationPolicy(AggregateCreationPolicy.CREATE_IF_MISSING)
    public boolean handle(AddNumber7ToEntry command) {
        initCalculatorIfNull(command.getId());
        return calculator.addNumber7ToEntry();
    }

    @CommandHandler
    @CreationPolicy(AggregateCreationPolicy.CREATE_IF_MISSING)
    public boolean handle(AddNumber8ToEntry command) {
        initCalculatorIfNull(command.getId());
        return calculator.addNumber8ToEntry();
    }

    @CommandHandler
    @CreationPolicy(AggregateCreationPolicy.CREATE_IF_MISSING)
    public boolean handle(AddNumber9ToEntry command) {
        initCalculatorIfNull(command.getId());
        return calculator.addNumber9ToEntry();
    }

    @CommandHandler
    @CreationPolicy(AggregateCreationPolicy.CREATE_IF_MISSING)
    public boolean handle(SelectAdditionOperation command) {
        initCalculatorIfNull(command.getId());
        return calculator.selectAdditionOperation();
    }

    @CommandHandler
    @CreationPolicy(AggregateCreationPolicy.CREATE_IF_MISSING)
    public boolean handle(SelectSubtractionOperation command) {
        initCalculatorIfNull(command.getId());
        return calculator.selectSubtractionOperation();
    }

    @CommandHandler
    @CreationPolicy(AggregateCreationPolicy.CREATE_IF_MISSING)
    public boolean handle(SelectMultiplicationOperation command) {
        initCalculatorIfNull(command.getId());
        return calculator.selectMultiplicationOperation();
    }


    @CommandHandler
    @CreationPolicy(AggregateCreationPolicy.CREATE_IF_MISSING)
    public boolean handle(SelectDivisionOperation command) {
        initCalculatorIfNull(command.getId());
        return calculator.selectDivisionOperation();
    }

    @CommandHandler
    @CreationPolicy(AggregateCreationPolicy.CREATE_IF_MISSING)
    public CalculationResponse handle(PerformCalculation command) {
        initCalculatorIfNull(command.getId());
        return calculator.performCalculation();
    }

    @CommandHandler
    @CreationPolicy(AggregateCreationPolicy.CREATE_IF_MISSING)
    public boolean handle(AddDecimalSeparatorToEntry command) {
        initCalculatorIfNull(command.getId());
        return calculator.addDecimalSeparatorToEntry();
    }

    @CommandHandler
    @CreationPolicy(AggregateCreationPolicy.CREATE_IF_MISSING)
    public boolean handle(RemoveLastEntryCharacter command) {
        initCalculatorIfNull(command.getId());
        return calculator.removeLastCharacter();
    }

    @CommandHandler
    @CreationPolicy(AggregateCreationPolicy.CREATE_IF_MISSING)
    public boolean handle(ClearEntry command) {
        initCalculatorIfNull(command.getId());
        return calculator.clearEntry();
    }

    @CommandHandler
    @CreationPolicy(AggregateCreationPolicy.CREATE_IF_MISSING)
    public boolean handle(ClearMemory command) {
        initCalculatorIfNull(command.getId());
        return calculator.clearMemory();
    }

    @CommandHandler
    @CreationPolicy(AggregateCreationPolicy.CREATE_IF_MISSING)
    public boolean handle(ClearHistory command) {
        initCalculatorIfNull(command.getId());
        return calculator.clearHistory();
    }

    @CommandHandler
    @CreationPolicy(AggregateCreationPolicy.CREATE_IF_MISSING)
    public boolean handle(RemoveRowFromHistory command) {
        initCalculatorIfNull(command.getId());
        return calculator.removeRowFromHistory(command.getRowId());
    }


    /**
     * -----------------------
     * Event sourcing handlers
     * -----------------------
     */

    @EventSourcingHandler
    public void on(CalculatorInitialized event) {
        this.id = event.getId().asStringId();
        this.calculator = new Calculator(event.getId(), EVENT_SENDER);
    }

    @EventSourcingHandler
    public void on(RowRemovedFromHistory event) {
        calculator.historyRows().remove(event.getRowId());
    }

    @EventSourcingHandler
    public void on(HistoryCleared event) {
        this.calculator = this.calculator.withHistoryRows(new HashSet<>());
    }

    @EventSourcingHandler
    public void on(MemoryCleared event) {
        this.calculator = this.calculator.withState(new CalculatorState());
    }

    @EventSourcingHandler
    public void on(EntryCleared event) {
        var newState = this.calculator.state().withEntry(Optional.empty());
        this.calculator = this.calculator.withState(newState);
    }

    @EventSourcingHandler
    public void on(Number0AddedToEntry event) {
        var currentState = this.calculator.state();
        var newState = currentState.withEntry(currentState.entry().map(e -> e + "0").or(() -> Optional.of("0")));
        this.calculator = this.calculator.withState(newState);
    }

    @EventSourcingHandler
    public void on(Number1AddedToEntry event) {
        var currentState = this.calculator.state();
        var newState = currentState.withEntry(currentState.entry().map(e -> e + "1").or(() -> Optional.of("1")));
        this.calculator = this.calculator.withState(newState);
    }

    @EventSourcingHandler
    public void on(Number2AddedToEntry event) {
        var currentState = this.calculator.state();
        var newState = currentState.withEntry(currentState.entry().map(e -> e + "2").or(() -> Optional.of("2")));
        this.calculator = this.calculator.withState(newState);
    }

    @EventSourcingHandler
    public void on(Number3AddedToEntry event) {
        var currentState = this.calculator.state();
        var newState = currentState.withEntry(currentState.entry().map(e -> e + "3").or(() -> Optional.of("3")));
        this.calculator = this.calculator.withState(newState);
    }

    @EventSourcingHandler
    public void on(Number4AddedToEntry event) {
        var currentState = this.calculator.state();
        var newState = currentState.withEntry(currentState.entry().map(e -> e + "4").or(() -> Optional.of("4")));
        this.calculator = this.calculator.withState(newState);
    }

    @EventSourcingHandler
    public void on(Number5AddedToEntry event) {
        var currentState = this.calculator.state();
        var newState = currentState.withEntry(currentState.entry().map(e -> e + "5").or(() -> Optional.of("5")));
        this.calculator = this.calculator.withState(newState);
    }

    @EventSourcingHandler
    public void on(Number6AddedToEntry event) {
        var currentState = this.calculator.state();
        var newState = currentState.withEntry(currentState.entry().map(e -> e + "6").or(() -> Optional.of("6")));
        this.calculator = this.calculator.withState(newState);
    }

    @EventSourcingHandler
    public void on(Number7AddedToEntry event) {
        var currentState = this.calculator.state();
        var newState = currentState.withEntry(currentState.entry().map(e -> e + "7").or(() -> Optional.of("7")));
        this.calculator = this.calculator.withState(newState);
    }

    @EventSourcingHandler
    public void on(Number8AddedToEntry event) {
        var currentState = this.calculator.state();
        var newState = currentState.withEntry(currentState.entry().map(e -> e + "8").or(() -> Optional.of("8")));
        this.calculator = this.calculator.withState(newState);
    }

    @EventSourcingHandler
    public void on(Number9AddedToEntry event) {
        var currentState = this.calculator.state();
        var newState = currentState.withEntry(currentState.entry().map(e -> e + "9").or(() -> Optional.of("9")));
        this.calculator = this.calculator.withState(newState);
    }

    @EventSourcingHandler
    public void on(AdditionOperationSelected event) {
        var newState = this.calculator.state().withOperationType(Optional.of(OperationType.ADDITION));
        this.calculator = this.calculator.withState(newState);
    }

    @EventSourcingHandler
    public void on(DivisionOperationSelected event) {
        var newState = this.calculator.state().withOperationType(Optional.of(OperationType.DIVISION));
        this.calculator = this.calculator.withState(newState);
    }

    @EventSourcingHandler
    public void on(MultiplicationOperationSelected event) {
        var newState = this.calculator.state().withOperationType(Optional.of(OperationType.MULTIPLICATION));
        this.calculator = this.calculator.withState(newState);
    }

    @EventSourcingHandler
    public void on(SubtractionOperationSelected event) {
        var newState = this.calculator.state().withOperationType(Optional.of(OperationType.SUBTRACTION));
        this.calculator = this.calculator.withState(newState);
    }

    @EventSourcingHandler
    public void on(AdditionPerformed event) {
        this.calculator.historyRows().add(event.getResultId());
        this.calculator = this.calculator.withState(new CalculatorState()
            .withEntry(Optional.of(""))
            .withPreviousValue(Optional.of(event.getResult())));
    }

    @EventSourcingHandler
    public void on(SubtractionPerformed event) {
        this.calculator.historyRows().add(event.getResultId());
        this.calculator = this.calculator.withState(new CalculatorState()
            .withEntry(Optional.of(""))
            .withPreviousValue(Optional.of(event.getResult())));
    }

    @EventSourcingHandler
    public void on(MultiplicationPerformed event) {
        this.calculator.historyRows().add(event.getResultId());
        this.calculator = this.calculator.withState(new CalculatorState()
            .withEntry(Optional.of(""))
            .withPreviousValue(Optional.of(event.getResult())));
    }

    @EventSourcingHandler
    public void on(DivisionPerformed event) {
        this.calculator.historyRows().add(event.getResultId());
        this.calculator = this.calculator.withState(new CalculatorState()
            .withEntry(Optional.of(""))
            .withPreviousValue(Optional.of(event.getResult())));
    }

    @EventSourcingHandler
    public void on(DecimalSeparatorAddedToEntry event) {
        var currentState = this.calculator.state();
        var newState = currentState.withEntry(currentState.entry().map(e -> e + NumberUtils.DECIMAL_SEPARATOR));
        this.calculator = this.calculator.withState(newState);
    }

    @EventSourcingHandler
    public void on(LastEntryCharacterRemoved event) {
        var currentState = this.calculator.state();
        var newState = currentState.withEntry(currentState.entry().map(e -> e.substring(0, e.length() - 1)));
        this.calculator = this.calculator.withState(newState);
    }

    @EventSourcingHandler
    public void on(ValueUpdated event) {
        var newState = this.calculator.state().withValue(Optional.of(event.getValue()));
        this.calculator = this.calculator.withState(newState);
    }

    private void initCalculatorIfNull(CalculatorId id) {
        if (calculator == null) {
            var event = CalculatorInitialized.builder()
                .id(id)
                .build();
            EVENT_SENDER.sendEvent(event);
            this.calculator = new Calculator(event.getId(), EVENT_SENDER);
        }
    }
}
