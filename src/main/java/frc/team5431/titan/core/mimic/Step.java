package frc.team5431.titan.core.mimic;

import java.util.EnumMap;

import frc.team5431.titan.core.misc.Logger;

/**
 */
public class Step<PV extends Enum<PV> & PropertyValue<?>> {
    public EnumMap<PV, Object> values;

    public Step(final EnumMap<PV, Object> values) {
        this.values = values;
    }

    public Step(final String toParse, final Class<PV> clazz) {
        try {
            values = new EnumMap<>(clazz);
            final String parts[] = toParse.split(",");
            for (final PV key : clazz.getEnumConstants()) {
                values.put(key, key.getType().convert(parts[key.ordinal()]));
            }
        } catch (Exception e) {
            Logger.ee("MimicParse", e);
        }
    }

    public EnumMap<PV, Object> getValues() {
        return values;
    }

    public Object get(final PV value) {
        return values.get(value);
    }

    public double getDouble(final PV value) {
        return (double) get(value);
    }

    public boolean getBoolean(final PV value) {
        return (boolean) get(value);
    }

    public int getInteger(final PV value) {
        return (Integer) get(value);
    }

    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for (final Object obj : values.values()) {
            builder.append(obj.toString()).append(",");
        }
        builder.append(System.lineSeparator());
        return builder.toString();
    }
}
