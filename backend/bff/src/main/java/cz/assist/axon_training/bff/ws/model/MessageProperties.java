package cz.assist.axon_training.bff.ws.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Optional;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class MessageProperties {

    private String userId;

    private String trackingId;

    @Builder.Default
    private Optional<String> sessionId = Optional.empty();

}
