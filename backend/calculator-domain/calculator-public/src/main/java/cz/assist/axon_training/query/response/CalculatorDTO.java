package cz.assist.axon_training.query.response;

import cz.assist.axon_training.OperationType;

import java.math.BigDecimal;
import java.util.List;

public record CalculatorDTO(BigDecimal value,
                            String entry,
                            BigDecimal latestResult,
                            OperationType currentOperation,
                            List<CalculatorHistoryRowDTO> results) {
}
