package frc.team5431.titan.core.vision;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * @author Ryan Hirasaki
 */
public class Limelight {
    private final NetworkTable table;

    public Limelight(final String dev) {
        table = NetworkTableInstance.getDefault().getTable(dev);
    }

    public final NetworkTable getTable() {
        return table;
    }

    private final NetworkTableEntry getEntry(String key) {
        return getTable().getEntry(key);
    }

    private final Number get(String val) {
        return getEntry(val).getNumber(0.0);
    }

    public final double getX() {
        return get("tx").doubleValue();
    }

    public final double getY() {
        return get("ty").doubleValue();
    }

    public final double getArea() {
        return get("ta").doubleValue();
    }

    public final boolean getValid() {
        return get("tv").intValue() > 0;
    }

    public final LEDState getLEDState() {
        return LEDState.values()[get("ledMode").intValue()];
    }

    public final CameraMode getCameraMode() {
        return CameraMode.values()[get("camMode").intValue()];
    }

    public final void setCameraMode(CameraMode state) {
        table.getEntry("camMode").setNumber(state.ordinal());
    }

    public final void setLEDState(LEDState state) {
        table.getEntry("ledMode").setNumber(state.ordinal());
    }

    public final void setPipeline(int pipeline) {
        table.getEntry("pipeline").setNumber(pipeline);
    }
}