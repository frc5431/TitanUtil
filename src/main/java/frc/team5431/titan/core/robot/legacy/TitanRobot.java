package frc.team5431.titan.core.robot.legacy;

import java.util.List;

import edu.wpi.first.wpilibj.TimedRobot;

/**
 * @deprecated
 */
public abstract class TitanRobot<T extends TitanRobot<T>> extends TimedRobot {
    public abstract List<Component<T>> getComponents();
}