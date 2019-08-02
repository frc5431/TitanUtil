package frc.team5431.titan.core.mimic;

import java.util.function.Function;

public enum PropertyType {
    DOUBLE(Double::parseDouble), INTEGER(Integer::parseInt), BOOLEAN(Boolean::parseBoolean);

    final Function<String, Object> converter;

    private PropertyType(final Function<String, Object> converter) {
        this.converter = converter;
    }

    public Object convert(final String in) {
        return converter.apply(in);
    }
}