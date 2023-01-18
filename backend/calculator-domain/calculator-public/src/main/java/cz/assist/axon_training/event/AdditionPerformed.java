package cz.assist.axon_training.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@NoArgsConstructor
public class AdditionPerformed extends CalculatorEvent {

    private UUID resultId;

    private BigDecimal value;

    private BigDecimal incomingValue;

    private BigDecimal result;
}
