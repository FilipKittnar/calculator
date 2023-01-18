package cz.assist.axon_training.command.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalculationResponse {

    private Optional<CalculationErrorType> errorType;

}
