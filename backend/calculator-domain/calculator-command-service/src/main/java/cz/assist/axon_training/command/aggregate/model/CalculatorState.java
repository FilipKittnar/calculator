package cz.assist.axon_training.command.aggregate.model;

import cz.assist.axon_training.OperationType;
import lombok.With;

import java.math.BigDecimal;
import java.util.Optional;

@With
public record CalculatorState(Optional<BigDecimal> value,
                              Optional<BigDecimal> previousValue,
                              Optional<String> entry,
                              Optional<OperationType> operationType) {

    public CalculatorState() {
        this(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
    }
}
