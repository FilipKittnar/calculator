package cz.assist.axon_training.query.response;

import cz.assist.axon_training.common.message.QueryResponse;

public record GetUserCalculatorResponse(CalculatorDTO calculator) implements QueryResponse {
}
