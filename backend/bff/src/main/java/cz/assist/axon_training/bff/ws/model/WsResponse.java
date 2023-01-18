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
public class WsResponse<T> {

    private ResponseType type;

    private String trackingId;

    private T payload;

    public static <T> WsResponse<T> success(T payload) {
        return success(null, payload, null);
    }

    public static <T> WsResponse<T> success(T payload, String trackingId) {
        return success(null, payload, trackingId);
    }

    public static <T> WsResponse<T> success(ResponseType type, T payload) {
        return success(type, payload, null);
    }

    public static <T> WsResponse<T> success(ResponseType type, T payload, String trackingId) {
        return new WsResponse<>(
            type,
            trackingId,
            payload
        );
    }

}
