package cz.assist.axon_training.projection.domain.history;

import cz.assist.axon_training.CalculatorId;
import cz.assist.axon_training.OperationType;
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
import cz.assist.axon_training.projection.domain.history.entity.CalculatorEntity;
import cz.assist.axon_training.projection.domain.history.mapper.CalculatorQueryMapper;
import cz.assist.axon_training.projection.domain.history.service.CalculatorMongoService;
import cz.assist.axon_training.query.GetUserCalculator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.springframework.stereotype.Service;

@Service
@ProcessingGroup("calculator")
@RequiredArgsConstructor
@Slf4j
public class CalculatorEventHandler {

    private final QueryUpdateEmitter updateEmitter;

    private final CalculatorMongoService mongoService;

    @EventHandler
    public void on(CalculatorInitialized event) {
        var entity = CalculatorEntity.builder()
            .userId(event.getId().getValue())
            .build();
        mongoService.create(entity);
    }

    @EventHandler
    public void on(RowRemovedFromHistory event) {
        mongoService.removeRow(event.getId(), event.getRowId());
        loadAndEmitUpdate(event.getId());
    }

    @EventHandler
    public void on(HistoryCleared event) {
        mongoService.clearHistory(event.getId());
        loadAndEmitUpdate(event.getId());
    }

    @EventHandler
    public void on(EntryCleared event) {
        mongoService.clearEntry(event.getId());
        loadAndEmitUpdate(event.getId());
    }

    @EventHandler
    public void on(MemoryCleared event) {
        mongoService.clearMemory(event.getId());
        loadAndEmitUpdate(event.getId());
    }

    @EventHandler
    public void on(AdditionOperationSelected event) {
        mongoService.updateOperationType(event.getId(), OperationType.ADDITION);
        loadAndEmitUpdate(event.getId());
    }

    @EventHandler
    public void on(SubtractionOperationSelected event) {
        mongoService.updateOperationType(event.getId(), OperationType.SUBTRACTION);
        loadAndEmitUpdate(event.getId());
    }

    @EventHandler
    public void on(MultiplicationOperationSelected event) {
        mongoService.updateOperationType(event.getId(), OperationType.MULTIPLICATION);
        loadAndEmitUpdate(event.getId());
    }

    @EventHandler
    public void on(DivisionOperationSelected event) {
        mongoService.updateOperationType(event.getId(), OperationType.DIVISION);
        loadAndEmitUpdate(event.getId());
    }

    @EventHandler
    public void on(Number0AddedToEntry event) {
        mongoService.addCharacterToEntry(event.getId(), "0");
        loadAndEmitUpdate(event.getId());
    }

    @EventHandler
    public void on(Number1AddedToEntry event) {
        mongoService.addCharacterToEntry(event.getId(), "1");
        loadAndEmitUpdate(event.getId());
    }

    @EventHandler
    public void on(Number2AddedToEntry event) {
        mongoService.addCharacterToEntry(event.getId(), "2");
        loadAndEmitUpdate(event.getId());
    }

    @EventHandler
    public void on(Number3AddedToEntry event) {
        mongoService.addCharacterToEntry(event.getId(), "3");
        loadAndEmitUpdate(event.getId());
    }

    @EventHandler
    public void on(Number4AddedToEntry event) {
        mongoService.addCharacterToEntry(event.getId(), "4");
        loadAndEmitUpdate(event.getId());
    }

    @EventHandler
    public void on(Number5AddedToEntry event) {
        mongoService.addCharacterToEntry(event.getId(), "5");
        loadAndEmitUpdate(event.getId());
    }

    @EventHandler
    public void on(Number6AddedToEntry event) {
        mongoService.addCharacterToEntry(event.getId(), "6");
        loadAndEmitUpdate(event.getId());
    }

    @EventHandler
    public void on(Number7AddedToEntry event) {
        mongoService.addCharacterToEntry(event.getId(), "7");
        loadAndEmitUpdate(event.getId());
    }

    @EventHandler
    public void on(Number8AddedToEntry event) {
        mongoService.addCharacterToEntry(event.getId(), "8");
        loadAndEmitUpdate(event.getId());
    }

    @EventHandler
    public void on(Number9AddedToEntry event) {
        mongoService.addCharacterToEntry(event.getId(), "9");
        loadAndEmitUpdate(event.getId());
    }

    @EventHandler
    public void on(DecimalSeparatorAddedToEntry event) {
        mongoService.addDecimalSeparatorToEntry(event.getId());
        loadAndEmitUpdate(event.getId());
    }

    @EventHandler
    public void on(LastEntryCharacterRemoved event) {
        mongoService.removeLastEntryCharacter(event.getId());
        loadAndEmitUpdate(event.getId());
    }

    @EventHandler
    public void on(AdditionPerformed event) {
        var id = event.getId();
        var resultId = event.getResultId();
        var value = event.getValue();
        var incomingValue = event.getIncomingValue();
        var result = event.getResult();
        mongoService.additionPerformed(id, resultId, value, incomingValue, result);
        loadAndEmitUpdate(id);
    }

    @EventHandler
    public void on(SubtractionPerformed event) {
        var id = event.getId();
        var resultId = event.getResultId();
        var value = event.getValue();
        var incomingValue = event.getIncomingValue();
        var result = event.getResult();
        mongoService.subtractionPerformed(id, resultId, value, incomingValue, result);
        loadAndEmitUpdate(id);
    }

    @EventHandler
    public void on(MultiplicationPerformed event) {
        var id = event.getId();
        var resultId = event.getResultId();
        var value = event.getValue();
        var multiplier = event.getMultiplier();
        var result = event.getResult();
        mongoService.multiplicationPerformed(id, resultId, value, multiplier, result);
        loadAndEmitUpdate(id);
    }

    @EventHandler
    public void on(ValueUpdated event) {
        mongoService.valueUpdated(event.getId(), event.getValue());
        loadAndEmitUpdate(event.getId());
    }


    @EventHandler
    public void on(DivisionPerformed event) {
        var id = event.getId();
        var resultId = event.getResultId();
        var value = event.getValue();
        var dividend = event.getDividend();
        var result = event.getResult();
        mongoService.divisionPerformed(id, resultId, value, dividend, result);
        loadAndEmitUpdate(id);
    }

    private void loadAndEmitUpdate(CalculatorId id) {
        var entity = mongoService.findById(id);
        entity.ifPresent(e -> updateEmitter.emit(
            GetUserCalculator.class,
            q -> q.id().getValue().equals(id.getValue()),
            CalculatorQueryMapper.INSTANCE.map(e)
        ));

    }
}
