package cz.assist.axon_training.query;

import cz.assist.axon_training.CalculatorId;
import cz.assist.axon_training.common.message.Query;

public record GetUserCalculator(CalculatorId id) implements Query {
}
