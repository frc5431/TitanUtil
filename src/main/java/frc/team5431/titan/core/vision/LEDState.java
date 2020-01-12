package frc.team5431.titan.core.vision;

public enum LEDState {
    // Values Gathered from limelight documentation
    DEFAULT(0), OFF(1), BLINK(2), ON(3);

    private final int id;

    private LEDState(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}