package cz.assist.axon_training.common.message;

import lombok.NonNull;

public interface Event<T extends Id> {

    @NonNull T getId();

}
