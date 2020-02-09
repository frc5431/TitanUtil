package frc.team5431.titan.core.vision;

/**
 * @author Ryan Hirasaki
 */
public enum CameraMode {
    VISION(0), DRIVER(1);

    private int id;

    private CameraMode(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static CameraMode getModeFromInt(int value) {
        for (CameraMode x : CameraMode.values()) {
            if (x.getId() == value)
                return x;
        }
        return null;
    }
}