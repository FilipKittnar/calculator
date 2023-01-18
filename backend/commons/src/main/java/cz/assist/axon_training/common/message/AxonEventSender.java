package cz.assist.axon_training.common.message;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.modelling.command.AggregateLifecycle;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AxonEventSender implements EventSender {

    private static final AxonEventSender INSTANCE = new AxonEventSender();

    @Override
    public void sendEvent(@NonNull Event<? extends BaseId> event) {
        log.debug("Going to apply event {}.", event);
        AggregateLifecycle.apply(event);
    }

    public static AxonEventSender getInstance() {
        return INSTANCE;
    }

}
