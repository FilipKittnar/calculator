package cz.assist.axon_training;

import cz.assist.axon_training.common.message.BaseId;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CalculatorId extends BaseId {

    private static final String PREFIX = "calculator";

    public CalculatorId() {
        super(PREFIX);
    }

    public CalculatorId(@NonNull String value) {
        super(PREFIX, value);
    }

    public static CalculatorId from(String userId) {
        return new CalculatorId(userId);
    }
}
