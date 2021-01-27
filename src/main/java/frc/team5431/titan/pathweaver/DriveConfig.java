public class DriveConfig {
    public DriveConfig(double sVolts.
                        double vVoltSPM,
                        double aVoltSSPM,
                        double trackwidth,
                        double maxSpeedMPS,
                        double maxAccelMPSS,
                        double ramseteb,
                        double ramsetezeta,
                        double pDriveVel) {
        this.ksVolts = sVolts;
        this.kvVoltSecondsPerMeter = vVoltSPM;
        this.kaVoltSecondsSquaredPerMeter = aVoltSSPM;
        this.kTrackwidthMeters = trackwidth;
        this.kMaxSpeedMetersPerSecond = maxSpeedMPS;
        this.kMaxAccelerationMetersPerSecondSquared = maxAccelMPSS;
        this.kRamseteB = ramseteb;
        this.kRamseteZeta = ramsetezeta;
        this.kPDriveVel = pDriveVel;
    }
    public final double ksVolts;
    public final double kvVoltSecondsPerMeter;
    public final double kaVoltSecondsSquaredPerMeter;
    public final double kTrackwidthMeters;
    public final double kMaxSpeedMetersPerSecond;
    public final double kMaxAccelerationMetersPerSecondSquared;
    public final double kRamseteB;
    public final double kRamseteZeta;
    public final double kPDriveVel;
}
