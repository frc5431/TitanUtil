package frc.team5431.titan.core.components.robot;

import java.util.List;

import edu.wpi.first.wpilibj.TimedRobot;

public abstract class TitanRobot<T extends TitanRobot<T>> extends TimedRobot {
    public abstract List<TitanComponent<T>> getComponents();
}