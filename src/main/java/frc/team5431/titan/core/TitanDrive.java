package frc.team5431.titan.core;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import frc.team5431.titan.core.components.Logger;

import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;

public class TitanDrive {
    private final Map<String, Pair<PIDConfig, Source>> pidSource = new HashMap<>();
    private final Map<String, Drive> pidOutput = new HashMap<>();
    private final TitanPIDSource source = new TitanPIDSource();
    private final TitanPIDOutput output = new TitanPIDOutput();
    private final PIDController controller = new PIDController(0, 0, 0, 0, source, output);
    private String currentSource = "NONE";
    private String currentControl = "NONE";
    private double speed = 0.0, heading = 0.0;

    public TitanDrive() {
        addSource("NONE",
                new PIDConfig(0, 0, 0, 0, 0, 0, 0), () -> heading);
        addControl("NONE", output -> {
        });

        //Some default configuration options
        controller.setContinuous(true);
    }

    public boolean isSourceNone() {
        return currentSource.startsWith("NONE");
    }

    public boolean isControlNone() {
        return currentControl.startsWith("NONE");
    }

    public void addSource(final String name, final PIDConfig config, final Source source) {
        pidSource.put(name, Pair.of(config, source));
    }

    public void updatePID(final String name, final PIDConfig config) {
        if (pidSource.containsKey(name)) {
            final Pair<PIDConfig, Source> cConfig = pidSource.get(name);
            cConfig.getLeft().update(config);
            pidSource.put(name, cConfig);
        } else Logger.e("The PID source %s doesn't exist!", name);
    }

    public void setSource(final String name) {
        if (pidSource.containsKey(name)) {
            final PIDConfig config = pidSource.get(name).getLeft();
            controller.setPID(config.P, config.I, config.D);
            controller.setInputRange(config.minIn, config.maxIn);
            controller.setOutputRange(config.minOut, config.maxOut);
            controller.setSetpoint(heading);

            if (name.startsWith("NONE") && !isSourceNone()) {
                disable();
            } else if (!name.startsWith("NONE") && isSourceNone()) {
                reset();
                enable();
            }

            currentSource = name;
        } else Logger.e("The PID source %s doesn't exist!", name);
    }

    public void addControl(final String name, final Drive method) {
        pidOutput.put(name, method);
    }

    public void setControl(final String name) {
        if (pidOutput.containsKey(name)) {
            currentControl = name;
        } else Logger.e("The PID output %s doesn't exist!", name);
    }

    public void addMode(final String name, final PIDConfig config, final Source source, final Drive drive) {
        addSource(name, config, source);
        addControl(name, drive);
    }

    public void setMode(final String name) {
        setSource(name);
        setControl(name);
    }

    public double getHeading() {
        return heading;
    }

    public void setHeading(final double h) {
        heading = h;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(final double s) {
        speed = s;
    }

    public void enable() {
        controller.enable();
    }

    public void disable() {
        controller.disable();
    }

    public void reset() {
        controller.reset();
    }

    public interface Source {
        double getAngle();
    }

    public interface Drive {
        void control(final double output);
    }

    public static class PIDConfig {
        public double P, I, D, minIn, minOut, maxIn, maxOut;

        public PIDConfig(final double p, final double i, final double d, final double mIn,
                         final double maIn, final double mOut, final double maOut) {
            P = p;
            I = i;
            D = d;
            minIn = mIn;
            minOut = mOut;
            maxIn = maIn;
            maxOut = maOut;
        }

        public void update(PIDConfig u) {
            P = u.P;
            I = u.I;
            D = u.D;
            minIn = u.minIn;
            minOut = u.minOut;
            maxIn = u.maxIn;
            maxOut = u.maxOut;
        }
    }

    public class TitanPIDSource implements PIDSource {
        PIDSourceType filler = PIDSourceType.kDisplacement;

        @Override
        public PIDSourceType getPIDSourceType() {
            return filler;
        }

        @Override
        public void setPIDSourceType(PIDSourceType pidSource) {
            filler = pidSource;
        }

        @Override
        public double pidGet() {
            return pidSource.get(currentSource).getRight().getAngle(); //Get the current sensor angle
        }
    }

    public class TitanPIDOutput implements PIDOutput {
        @Override
        public void pidWrite(double output) {
            pidOutput.get(currentControl).control(output); //Set the current PID output
        }
    }
}
