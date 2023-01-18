package cz.assist.axon_training.common.message;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@SuperBuilder
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseEvent<T extends BaseId> implements Event<T> {

    @NonNull
    private T id;

    @Override
    public @NonNull T getId() {
        return id;
    }

}
