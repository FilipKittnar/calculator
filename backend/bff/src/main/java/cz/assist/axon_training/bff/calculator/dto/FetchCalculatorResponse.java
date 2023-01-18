package cz.assist.axon_training.bff.calculator.dto;

import cz.assist.axon_training.OperationType;
import cz.assist.axon_training.query.response.CalculatorHistoryRowDTO;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public record FetchCalculatorResponse(BigDecimal value,
                                      String entry,
                                      BigDecimal latestResult,
                                      OperationType currentOperation,
                                      List<CalculatorHistoryRowDTO> results) {

    public FetchCalculatorResponse() {
        this(null, null, null, null, Collections.emptyList());
    }
}
