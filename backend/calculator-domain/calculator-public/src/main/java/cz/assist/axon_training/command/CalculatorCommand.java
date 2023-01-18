package cz.assist.axon_training.command;

import cz.assist.axon_training.CalculatorId;
import cz.assist.axon_training.common.message.BaseCommand;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
abstract class CalculatorCommand extends BaseCommand<CalculatorId> {
}
