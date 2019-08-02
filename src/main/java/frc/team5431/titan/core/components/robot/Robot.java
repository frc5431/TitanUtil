package frc.team5431.titan.core.components.robot;

import java.util.List;

import edu.wpi.first.wpilibj.TimedRobot;

public abstract class Robot<T extends Robot<T>> extends TimedRobot {
    public abstract List<Component<T>> getComponents();
}