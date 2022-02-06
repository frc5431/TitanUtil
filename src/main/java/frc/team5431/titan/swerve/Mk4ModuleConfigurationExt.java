package frc.team5431.titan.swerve;

import java.util.Objects;

import com.swervedrivespecialties.swervelib.Mk4ModuleConfiguration;

public class Mk4ModuleConfigurationExt extends Mk4ModuleConfiguration {
    private double steerKP = Double.NaN;
    private double steerKI = Double.NaN;
    private double steerKD = Double.NaN;

    private double steerMMkV = Double.NaN;
    private double steerMMkA = Double.NaN;
    private double steerMMkS = Double.NaN;

    public double getSteerKP() {
        return steerKP;
    }

    public double getSteerKI() {
        return steerKI;
    }

    public double getSteerKD() {
        return steerKD;
    }

    public void setSteerPID(double steerKP, double steerKI, double steerKD) {
        this.steerKP = steerKP;
        this.steerKI = steerKI;
        this.steerKD = steerKD;
    }

    public double getSteerMMkV() {
        return steerMMkV;
    }

    public double getSteerMMkA() {
        return steerMMkA;
    }

    public double getSteerMMkS() {
        return steerMMkS;
    }

    public void setSteerMM(double steerMMkV, double steerMMkA, double steerMMkS) {
        this.steerMMkV = steerMMkV;
        this.steerMMkA = steerMMkA;
        this.steerMMkS = steerMMkS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Mk4ModuleConfigurationExt that = (Mk4ModuleConfigurationExt) o;
        return Double.compare(that.getNominalVoltage(), getNominalVoltage()) == 0
                && Double.compare(that.getDriveCurrentLimit(), getDriveCurrentLimit()) == 0
                && Double.compare(that.getSteerCurrentLimit(), getSteerCurrentLimit()) == 0
                && Double.compare(that.getSteerKP(), getSteerKP()) == 0
                && Double.compare(that.getSteerKI(), getSteerKI()) == 0
                && Double.compare(that.getSteerKD(), getSteerKD()) == 0
                && Double.compare(that.getSteerMMkV(), getSteerMMkV()) == 0
                && Double.compare(that.getSteerMMkA(), getSteerMMkA()) == 0
                && Double.compare(that.getSteerMMkS(), getSteerMMkS()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getNominalVoltage(),
                getDriveCurrentLimit(),
                getSteerCurrentLimit(),
                getSteerKP(),
                getSteerKI(),
                getSteerKD(),
                getSteerMMkV(),
                getSteerMMkA(),
                getSteerMMkS());
    }

    @Override
    public String toString() {
        return "Mk4ModuleConfiguration{" +
                "nominalVoltage=" + getNominalVoltage() +
                ", driveCurrentLimit=" + getDriveCurrentLimit() +
                ", steerCurrentLimit=" + getSteerCurrentLimit() +
                ", steerKP=" + getSteerKP() +
                ", steerKI=" + getSteerKI() +
                ", steerKD=" + getSteerKD() +
                ", steerMMkV=" + getSteerMMkV() +
                ", steerMMkA=" + getSteerMMkA() +
                ", steerMMkS=" + getSteerMMkS() +
                '}';
    }

    public static Mk4ModuleConfigurationExt getDefaultFalcon500() {
        Mk4ModuleConfigurationExt config = new Mk4ModuleConfigurationExt();
        config.setSteerPID(0.2, 0.0, 0.1);
        return config;
    }

    public static Mk4ModuleConfigurationExt getDefaultNEO() {
        Mk4ModuleConfigurationExt config = new Mk4ModuleConfigurationExt();
        config.setSteerPID(1.0, 0.0, 0.1);
        return config;
    }
}
