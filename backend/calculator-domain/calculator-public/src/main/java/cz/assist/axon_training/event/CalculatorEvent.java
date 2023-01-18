package cz.assist.axon_training.event;

import cz.assist.axon_training.CalculatorId;
import cz.assist.axon_training.common.message.BaseEvent;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
abstract class CalculatorEvent extends BaseEvent<CalculatorId> {
}
