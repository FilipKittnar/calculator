package cz.assist.axon_training.bff.ws.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WsRequest<T> {

    private String trackingId;

    private T payload;

}
