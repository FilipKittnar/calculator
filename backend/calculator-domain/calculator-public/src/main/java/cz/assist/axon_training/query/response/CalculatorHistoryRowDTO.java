package cz.assist.axon_training.query.response;

import cz.assist.axon_training.OperationType;
import lombok.With;

import java.math.BigDecimal;
import java.util.UUID;

@With
public record CalculatorHistoryRowDTO(UUID rowId,
                                      BigDecimal value,
                                      OperationType operation,
                                      BigDecimal incomingValue,
                                      BigDecimal result) {
}
