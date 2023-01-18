package cz.assist.axon_training.projection.domain.history.entity;

import cz.assist.axon_training.OperationType;
import cz.assist.axon_training.projection.mongo.entity.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@FieldNameConstants
public class CalculatorEntity extends MongoEntity {

    public static final String COLLECTION_NAME = "calculator";

    private String userId;

    private BigDecimal value;

    private BigDecimal latestResult;

    private String entry;

    private OperationType currentOperation;

    private Map<String, HistoryRow> history;

}
