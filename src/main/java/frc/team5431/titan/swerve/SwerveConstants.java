package frc.team5431.titan.swerve;

public record SwerveConstants(double nominalVoltage, double driveCurrentLimit, double steerCurrentLimit,
        GearRatio ratio, double steerKP, double steerKI, double steerKD,
        double steerMMkV, double steerMMkA, double steerMMkS) {

    public SwerveConstants(GearRatio ratio, double steerKP, double steerKI, double steerKD) {
        this(12.0, 80.0, 20.0, ratio, steerKP, steerKI, steerKD, Double.NaN, Double.NaN, Double.NaN);
    }

    public SwerveConstants(GearRatio ratio, double steerKP, double steerKI, double steerKD,
            double steerMMkV, double steerMMkA, double steerMMkS) {
        this(12.0, 80.0, 20.0, ratio, steerKP, steerKI, steerKD, steerMMkV, steerMMkA, steerMMkS);
    }
}
