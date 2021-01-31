package frc.team5431.titan.pathfinder;

/**
 * @author Ryan Hirasaki
 */
public class DriveConfig {
    private DriveConfig(Builder builder) {
        this.ksVolts = builder.ksVolts;
        this.kvVoltSecondsPerMeter = builder.kvVoltSecondsPerMeter;
        this.kaVoltSecondsSquaredPerMeter = builder.kaVoltSecondsSquaredPerMeter;
        this.kTrackwidthMeters = builder.kTrackwidthMeters;
        this.kRamseteB = builder.kRamseteB;
        this.kRamseteZeta = builder.kRamseteZeta;
        this.kPDriveVel = builder.kPDriveVel;
    }

    public final double ksVolts;
    public final double kvVoltSecondsPerMeter;
    public final double kaVoltSecondsSquaredPerMeter;
    public final double kTrackwidthMeters;
    public final double kRamseteB;
    public final double kRamseteZeta;
    public final double kPDriveVel;

    public static class Builder {
        private double ksVolts;
        private double kvVoltSecondsPerMeter;
        private double kaVoltSecondsSquaredPerMeter;
        private double kTrackwidthMeters;
        private double kRamseteB;
        private double kRamseteZeta;
        private double kPDriveVel;

        public Builder() {
        }

        public DriveConfig build() {
            return new DriveConfig(this);
        }

        public Builder setVolts(double volts) {
            this.ksVolts = volts;
            return this;
        }

        public Builder setVoltsSpeed(double voltSecondsPerMeter) {
            this.kvVoltSecondsPerMeter = voltSecondsPerMeter;
            return this;
        }

        public Builder setVoltsAccel(double voltSecondsSquaredPerMeter) {
            this.kaVoltSecondsSquaredPerMeter = voltSecondsSquaredPerMeter;
            return this;
        }

        public Builder setTrackwidth(double meters) {
            this.kTrackwidthMeters = meters;
            return this;
        }

        public Builder setRamseteB(double b) {
            this.kRamseteB = b;
            return this;
        }

        public Builder setRamseteZeta(double zeta) {
            this.kRamseteZeta = zeta;
            return this;
        }

        public Builder setPDriveVel(double p) {
            this.kPDriveVel = p;
            return this;
        }

    }
}
