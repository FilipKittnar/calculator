package cz.assist.axon_training.projection.domain.history.entity;

import cz.assist.axon_training.OperationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
@FieldNameConstants
public class HistoryRow {

    private BigDecimal value;

    private OperationType operation;

    private BigDecimal incomingValue;

    private BigDecimal result;

    private UUID rowId;

    private Instant addedToProjectionAt;
}
