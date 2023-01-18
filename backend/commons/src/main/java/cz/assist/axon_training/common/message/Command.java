package cz.assist.axon_training.common.message;

import lombok.NonNull;

public interface Command<T extends Id> {

    @NonNull T getId();

}
