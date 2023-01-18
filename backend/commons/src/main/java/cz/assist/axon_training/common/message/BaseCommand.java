package cz.assist.axon_training.common.message;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Setter
@SuperBuilder
@EqualsAndHashCode
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseCommand<T extends BaseId> implements Command<T> {

    @NonNull
    private T id;

    @Override
    public @NonNull T getId() {
        return id;
    }

    @TargetAggregateIdentifier
    public String getStringId() {
        return id.asStringId();
    }

}
