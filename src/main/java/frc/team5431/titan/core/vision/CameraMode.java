package frc.team5431.titan.core.vision;

public enum CameraMode {
    VISION(0), DRIVER(1);

    private int id;

    private CameraMode(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}