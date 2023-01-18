package cz.assist.axon_training.projection.mongo.util;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FieldNameBuilder {
    private static final String DOT = ".";

    private String value;

    public static FieldNameBuilder builder(String value) {
        return new FieldNameBuilder(value);
    }

    public FieldNameBuilder add(String value) {
        this.value = this.value + DOT + value;
        return this;
    }

    public String build() {
        return value;
    }

}
