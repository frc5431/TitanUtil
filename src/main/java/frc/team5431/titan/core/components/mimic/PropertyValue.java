package frc.team5431.titan.core.components.mimic;

public interface PropertyValue<R> {
    public PropertyType getType();

    public Object get(final R robot);
}
