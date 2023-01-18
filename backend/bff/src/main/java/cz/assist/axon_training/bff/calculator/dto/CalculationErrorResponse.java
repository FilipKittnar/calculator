package cz.assist.axon_training.bff.calculator.dto;

import cz.assist.axon_training.command.response.CalculationErrorType;

public record CalculationErrorResponse(CalculationErrorType errorType) {
}
