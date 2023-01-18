package cz.assist.axon_training.bff.ws.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseType {

    GET_CALCULATE_ERROR_RESPONSE("AXON_TRAINING_APP/CALCULATOR/GET_CALCULATE_ERROR_RESPONSE"),

    GET_CALCULATOR_RESPONSE("AXON_TRAINING_APP/CALCULATOR/GET_CALCULATOR_RESPONSE");

    private final String value;

    @JsonValue
    @Override
    public String toString() {
        return value;
    }

}
