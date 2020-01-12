package frc.team5431.titan.core.vision;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight {

    private NetworkTable table;
    private LEDState ledState = LEDState.OFF;
    private CameraMode cameraMode = CameraMode.VISION;

    public Limelight(final String dev) {
        table = NetworkTableInstance.getDefault().getTable(dev);
    }

    private Number get(String val) {
        return table.getEntry(val).getNumber(0.0);
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

    public final int getValid() {
        return get("tv").intValue();
    }

    public final NetworkTable getTable() {
        return table;
    }

    public final LEDState getLEDState() {
        // Break if led mode was changed outside of class
        assert (get("ledMode").intValue() == ledState.getId());
        return ledState;
    }

    public final CameraMode getCameraMode() {
        // Break if camera mode was changed outside of class
        assert (get("camMode").intValue() == cameraMode.getId());
        return cameraMode;
    }

    public final void setCameraMode(CameraMode state) {
        table.getEntry("camMode").setNumber(state.getId());

    }

    public final void setLEDState(LEDState state) {
        table.getEntry("ledMode").setNumber(state.getId());
    }
}