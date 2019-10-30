package frc.team5431.titan.core.hololens;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import frc.team5431.titan.core.robot.Component;
import frc.team5431.titan.core.robot.Robot;

public class Hololens {

    private NetworkTable hololens_table;
    private String custommsg = "";

    @Override
    public Hololens() {
        hololens_table = NetworkTableInstance.getDefault().getTable("hololens");
    }

    public void customMsg(final String msg) {
        custommsg = msg;
    }

    @Override
    public void tick() {

        DriverStation driverStation = DriverStation.getInstance();
        double time  = driverStation.getMatchTime();
        int matchNumber = driverStation.getMatchNumber();

        hololens_table.getEntry("time").setDouble(time);
        hololens_table.getEntry("custom").setString(custommsg);
    }
}