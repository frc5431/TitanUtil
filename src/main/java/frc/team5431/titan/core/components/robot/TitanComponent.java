package frc.team5431.titan.core.components.robot;

public abstract class TitanComponent<T extends TitanRobot<T>>{
    public abstract void init(final T robot);

    public abstract void periodic(final T robot);

    public abstract void disabled(final T robot);

    public void tick(final T robot){
        //do nothing
    }
}