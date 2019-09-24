package frc.team5431.titan.core.vision;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight {

    private NetworkTable table;

    public Limelight(final String dev) {
        table = NetworkTableInstance.getDefault().getTable(dev);
        //aaaa
    }

    private final Number get(String val) {
        return table.getEntry(val).getNumber(0.0);
    }

    public class basic {
        public final double getX() {
            return get("tx").doubleValue();
        }

        public final double getY() {
            return get("ty").doubleValue();
        }

        public final double getArea() {
            return get("ta").doubleValue();
        }

        public final int getValid() {
            return get("tv").intValue();
        }
    }
}