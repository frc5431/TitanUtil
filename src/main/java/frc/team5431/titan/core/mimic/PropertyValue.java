package frc.team5431.titan.core.mimic;

public interface PropertyValue<R> {
    public PropertyType getType();

    public Object get(final R robot);
}
