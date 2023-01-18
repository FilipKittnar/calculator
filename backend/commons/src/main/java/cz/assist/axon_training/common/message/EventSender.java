package cz.assist.axon_training.common.message;

import lombok.NonNull;

public interface EventSender {

    void sendEvent(@NonNull Event<? extends BaseId> event);

}
