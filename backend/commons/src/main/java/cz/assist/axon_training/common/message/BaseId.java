package cz.assist.axon_training.common.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public abstract class BaseId implements Id {

    protected static final String SEPARATOR = "_";

    @JsonIgnore
    @NonNull
    private final String prefix;

    @JsonValue
    private String value;

    @Override
    public String toString() {
        return asStringId();
    }

    @Override
    public final String asStringId() {
        return prefix + SEPARATOR + value;
    }

    public @NonNull String getValue() {
        return value;
    }

    public void setValue(@NonNull String value) {
        this.value = value;
    }

}
