package cz.assist.axon_training.projection.domain.history.service;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import cz.assist.axon_training.CalculatorId;
import cz.assist.axon_training.OperationType;
import cz.assist.axon_training.projection.domain.history.entity.CalculatorEntity;
import cz.assist.axon_training.projection.domain.history.entity.HistoryRow;
import cz.assist.axon_training.projection.mongo.service.MongoService;
import cz.assist.axon_training.projection.mongo.util.FieldNameBuilder;
import lombok.extern.slf4j.Slf4j;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class CalculatorMongoService extends MongoService<CalculatorEntity> {

    private static final String DECIMAL_SEPARATOR = ".";

    public CalculatorMongoService(MongoDatabase database) {
        super(database.getCollection(CalculatorEntity.COLLECTION_NAME, CalculatorEntity.class));
    }

    public Optional<CalculatorEntity> findById(CalculatorId id) {
        log.debug("Going to find vehicle search calculator by id {}", id.getValue());

        var entity = Optional.ofNullable(findFirst(Filters.eq(CalculatorEntity.Fields.userId, id.getValue())));

        entity.ifPresentOrElse(calculator -> log.debug("Found calculator with id {}", id),
            () -> log.debug("Calculator with id {} was not found", id));

        return entity;
    }

    public void create(CalculatorEntity entity) {
        log.info("Going to create calculator for user with id {}", entity.getUserId());
        insertOne(entity);
    }

    public void removeRow(CalculatorId id, UUID rowId) {
        var fieldName = FieldNameBuilder.builder(CalculatorEntity.Fields.history)
            .add(rowId.toString())
            .build();

        update(id, Updates.unset(fieldName));
    }

    public void clearHistory(CalculatorId id) {
        var fieldName = CalculatorEntity.Fields.history;
        update(id, Updates.unset(fieldName));
    }

    public void clearEntry(CalculatorId id) {
        var updates = new ArrayList<Bson>();
        updates.add(Updates.unset(CalculatorEntity.Fields.entry));
        updates.add(Updates.unset(CalculatorEntity.Fields.latestResult));
        update(id, updates);
    }

    public void clearMemory(CalculatorId id) {
        var memoryFields = List.of(CalculatorEntity.Fields.value,
            CalculatorEntity.Fields.currentOperation,
            CalculatorEntity.Fields.entry,
            CalculatorEntity.Fields.latestResult);
        var updates = memoryFields.stream().map(Updates::unset).toList();
        update(id, updates);
    }

    public void updateOperationType(CalculatorId id, OperationType operationType) {
        update(id, Updates.set(CalculatorEntity.Fields.currentOperation, operationType));
    }

    public void addCharacterToEntry(CalculatorId id, String valueToAdd) {
        findById(id).ifPresent(e -> {
            var fieldName = CalculatorEntity.Fields.entry;
            var newValue = StringUtils.hasText(e.getEntry()) ? e.getEntry() + valueToAdd : valueToAdd;
            update(id, Updates.set(fieldName, newValue));
        });
    }

    public void addDecimalSeparatorToEntry(CalculatorId id) {
        addCharacterToEntry(id, DECIMAL_SEPARATOR);
    }

    public void removeLastEntryCharacter(CalculatorId id) {
        findById(id).ifPresent(e -> {
            var fieldName = CalculatorEntity.Fields.entry;
            update(id, Updates.set(fieldName, e.getEntry().substring(0, e.getEntry().length() - 1)));
        });

    }

    public void additionPerformed(CalculatorId id, UUID resultId, BigDecimal value, BigDecimal incomingValue, BigDecimal result) {
        var updates = new ArrayList<Bson>();
        var historyRow = createHistoryRow(resultId, value, incomingValue, result, OperationType.ADDITION);
        var historyField = FieldNameBuilder.builder(CalculatorEntity.Fields.history)
            .add(resultId.toString())
            .build();

        updates.add(Updates.set(historyField, historyRow));
        updates.add(Updates.set(CalculatorEntity.Fields.latestResult, result));
        updates.add(Updates.unset(CalculatorEntity.Fields.entry));
        updates.add(Updates.unset(CalculatorEntity.Fields.currentOperation));
        updates.add(Updates.unset(CalculatorEntity.Fields.value));
        update(id, updates);

    }

    public void subtractionPerformed(CalculatorId id, UUID resultId, BigDecimal value, BigDecimal incomingValue, BigDecimal result) {
        var updates = new ArrayList<Bson>();
        var historyRow = createHistoryRow(resultId, value, incomingValue, result, OperationType.SUBTRACTION);
        var historyField = FieldNameBuilder.builder(CalculatorEntity.Fields.history)
            .add(resultId.toString())
            .build();

        updates.add(Updates.set(historyField, historyRow));
        updates.add(Updates.set(CalculatorEntity.Fields.latestResult, result));
        updates.add(Updates.unset(CalculatorEntity.Fields.entry));
        updates.add(Updates.unset(CalculatorEntity.Fields.currentOperation));
        updates.add(Updates.unset(CalculatorEntity.Fields.value));
        update(id, updates);
    }

    public void multiplicationPerformed(CalculatorId id, UUID resultId, BigDecimal value, BigDecimal multiplier, BigDecimal result) {
        var updates = new ArrayList<Bson>();
        var historyRow = createHistoryRow(resultId, value, multiplier, result, OperationType.MULTIPLICATION);
        var historyField = FieldNameBuilder.builder(CalculatorEntity.Fields.history)
            .add(resultId.toString())
            .build();

        updates.add(Updates.set(historyField, historyRow));
        updates.add(Updates.set(CalculatorEntity.Fields.latestResult, result));
        updates.add(Updates.unset(CalculatorEntity.Fields.entry));
        updates.add(Updates.unset(CalculatorEntity.Fields.currentOperation));
        updates.add(Updates.unset(CalculatorEntity.Fields.value));
        update(id, updates);
    }

    public void divisionPerformed(CalculatorId id, UUID resultId, BigDecimal value, BigDecimal dividend, BigDecimal result) {
        var updates = new ArrayList<Bson>();
        var historyRow = createHistoryRow(resultId, value, dividend, result, OperationType.DIVISION);
        var historyField = FieldNameBuilder.builder(CalculatorEntity.Fields.history)
            .add(resultId.toString())
            .build();

        updates.add(Updates.set(historyField, historyRow));
        updates.add(Updates.set(CalculatorEntity.Fields.latestResult, result));
        updates.add(Updates.unset(CalculatorEntity.Fields.entry));
        updates.add(Updates.unset(CalculatorEntity.Fields.currentOperation));
        updates.add(Updates.unset(CalculatorEntity.Fields.value));
        update(id, updates);
    }

    public void valueUpdated(CalculatorId id, BigDecimal value) {
        update(id, Updates.set(CalculatorEntity.Fields.value, value));
    }

    private HistoryRow createHistoryRow(UUID rowId, BigDecimal value, BigDecimal incomingValue, BigDecimal result, OperationType operation) {
        return HistoryRow.builder()
            .operation(operation)
            .value(value)
            .incomingValue(incomingValue)
            .result(result)
            .rowId(rowId)
            .addedToProjectionAt(Instant.now())
            .build();
    }


    private void update(CalculatorId id, Bson update) {
        updateOne(CalculatorEntity.Fields.userId, id.getValue(), Collections.singletonList(update));
    }

    private void update(CalculatorId id, List<Bson> updates) {
        updateOne(CalculatorEntity.Fields.userId, id.getValue(), updates);
    }

}
