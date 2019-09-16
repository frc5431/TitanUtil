package frc.team5431.titan.core.hololens;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import frc.team5431.titan.core.robot.Component;
import frc.team5431.titan.core.robot.Robot;

public abstract class Hololens<T extends Robot<T>> extends Component<T> {

    private NetworkTable hololens_table;

    @Override
    public void init(T robot) {
        hololens_table = NetworkTableInstance.getDefault().getTable("hololens");
    }

    @Override
    public void periodic(T robot) {
    }

    @Override
    public void disabled(T robot) {
    }

    @Override
    public void tick(T robot) {
        super.tick(robot);

        DriverStation driverStation = DriverStation.getInstance();
        double time  = driverStation.getMatchTime();
        int matchNumber = driverStation.getMatchNumber();

        hololens_table.getEntry("time").setDouble(time);
        hololens_table.getEntry("match").setNumber(matchNumber);
    }
}