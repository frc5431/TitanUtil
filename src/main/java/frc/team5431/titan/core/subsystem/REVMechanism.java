
package frc.team5431.titan.core.subsystem;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix6.controls.MotionMagicTorqueCurrentFOC;
import com.ctre.phoenix6.controls.MotionMagicVelocityTorqueCurrentFOC;
import com.ctre.phoenix6.controls.MotionMagicVelocityVoltage;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.MAXMotionConfig.MAXMotionPositionMode;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Subsystem;

public abstract class REVMechanism implements Subsystem {
    protected boolean attached = false;
    protected SparkFlex motor;
    protected SparkClosedLoopController mController;
    public Config config;

    public enum SensorType {
        Absolute,
        Relative
    }

    /**
     * 
     * Multi-Purpose subsystem class based on 3847 Spectrum's Mechanism class
     * utlizing PID Closed-Loop and Max Motion positional or velocity control,
     * and WPILib Units
     * 
     * Only use one control method per mechanism, PLEASE!
     * 
     * @param attached for if the motor is in use
     */
    public REVMechanism(boolean attached) {
        this.attached = attached;
        this.config = setConfig();
        mController = motor.getClosedLoopController();

    }

    protected abstract Config setConfig();

    protected void setConfig(Config config) {
        this.config = config;
    };

    /** Sets the mechanism position of the motor to 0 */
    public void resetPosition() {
        if (attached) {
            setMotorPosition(Units.Degrees.of(0));
        }
    }

    /**
     * Sets the mechanism position of the motor based on constant value
     *
     * @param position Measure of Angle; Converted to Rotations
     */
    public void setMotorPosition(Angle position) {
        if (attached) {
            motor.getClosedLoopController().setReference(position.in(Units.Rotations), ControlType.kPosition,
                    ClosedLoopSlot.kSlot0);
        }
    }

    /**
     * Sets the mechanism position of the motor based on constant value with ArbFF
     *
     * @param position Measure of Angle; Converted to Rotations
     * @param arbff    A value from which is represented in voltage applied to the
     *                 motor after the result of the specified control mode. The
     *                 units for the parameter is Volts.
     */
    public void setMotorPosition(Angle position, double arbff) {
        if (attached) {
            motor.getClosedLoopController().setReference(position.in(Units.Rotations), ControlType.kPosition,
                    ClosedLoopSlot.kSlot0, arbff);
        }
    }

    /**
     * Closed-loop Velocity Max Motion
     *
     * @param velocity Mesure of Velocity in Terms of Angle; Converted to Rotations
     *                 Per Minute
     */
    public void setMMVelocity(AngularVelocity velocity) {
        if (attached) {
            motor.getClosedLoopController().setReference(velocity.in(Units.RPM), ControlType.kMAXMotionVelocityControl,
                    ClosedLoopSlot.kSlot0);
        }
    }

    /**
     * Closed-loop Velocity
     *
     * @param velocity Mesure of Velocity in Terms of Angle; Converted to Rotations
     *                 Per Minute
     */
    public void setVelocity(AngularVelocity velocity) {
        if (attached) {
            motor.getClosedLoopController().setReference(velocity.in(Units.RPM), ControlType.kVelocity,
                    ClosedLoopSlot.kSlot0);
        }
    }

    /**
     * Closed-loop Position Max Motion with arbFF
     *
     * @param position Measure of Aggregate Rotation; Converted to Rotations
     * @param arbff    A value from which is represented in voltage applied to the
     *                 motor after the result of the specified control mode. The
     *                 units for the parameter is Volts.
     *
     */
    public void setMMPosition(Angle position, double arbFF) {
        if (attached) {
            motor.getClosedLoopController().setReference(position.in(Units.Rotations),
                    ControlType.kMAXMotionPositionControl, ClosedLoopSlot.kSlot0, arbFF);
        }
    }

    /**
     * Closed-loop Position Max Motion
     *
     * @param position Measure of Aggregate Rotation; Converted to Rotations
     */
    public void setMMPosition(Angle position) {
        if (attached) {
            motor.getClosedLoopController().setReference(position.in(Units.Rotations),
                    ControlType.kMAXMotionPositionControl);
        }
    }

    /**
     * Closed-loop Position Max Motion
     *
     * @param position Measure of Aggregate Rotation; Converted to Rotations
     */
    public void setMMPosition(DoubleSupplier position) {
        if (attached) {
            motor.getClosedLoopController().setReference(position.getAsDouble(), ControlType.kMAXMotionPositionControl);
        }
    }

    /**
     * Closed-loop Position Max Motion
     *
     * @param position Measure of Aggregate Rotation; Converted to Rotations
     * @param arbff    A value from which is represented in voltage applied to the
     *                 motor after the result of the specified control mode. The
     *                 units for the parameter is Volts.
     */
    public void setMMPosition(DoubleSupplier position, double arbFF) {
        if (attached) {
            motor.getClosedLoopController().setReference(position.getAsDouble(), ControlType.kMAXMotionPositionControl,
                    ClosedLoopSlot.kSlot0, arbFF);
        }
    }

    /**
     * Closed-loop Position Max Motion using a slot other than 0
     *
     * @param position rotations
     * @param slot     gains slot
     */
    public void setMMPosition(Angle position, ClosedLoopSlot slot) {
        if (attached) {
            motor.getClosedLoopController().setReference(position.in(Units.Rotations),
                    ControlType.kMAXMotionPositionControl, slot);
        }
    }

    /**
     * Open-loop Percent output control with voltage compensation
     *
     * @param percent fractional units between -1 and +1
     */
    public void setPercentOutput(double percent) {
        if (attached) {
            motor.set(percent);

        }
    }

    public void setBrakeMode(IdleMode idleMode) {
        if (attached) {
            config.configNeutralBrakeMode(idleMode);
            config.applyflexConfig(motor);
        }
    }

    public void toggleReverseSoftLimit(boolean enabled) {
        if (attached) {
            double threshold = motor.configAccessor.softLimit.getReverseSoftLimit();
            config.configReverseSoftLimit(Units.Rotation.of(threshold), enabled);
            config.applyflexConfig(motor);
        }
    }

    public void toggleTorqueCurrentLimit(Current enabledLimit, boolean enabled) {
        if (attached) {
            if (enabled) {
                config.configSmartStallCurrentLimit(enabledLimit);
                config.applyflexConfig(motor);
            } else {
                config.configSmartStallCurrentLimit(enabledLimit);
                config.applyflexConfig(motor);
            }
        }
    }

    public static class Config {
        public String title;
        public int id;
        public SparkFlexConfig flexConfig;
        public double voltageCompSaturation; // 12V by default

        public MotionMagicVelocityTorqueCurrentFOC mmVelocityFOC = new MotionMagicVelocityTorqueCurrentFOC(0);
        public MotionMagicTorqueCurrentFOC mmPositionFOC = new MotionMagicTorqueCurrentFOC(0);
        public MotionMagicVelocityVoltage mmVelocityVoltage = new MotionMagicVelocityVoltage(0);
        public MotionMagicVoltage mmPositionVoltage = new MotionMagicVoltage(0);
        public MotionMagicVoltage mmPositionVoltageSlot = new MotionMagicVoltage(0).withSlot(1);

        public Config(String title, int id, String canbus) {
            this.title = title;
            this.voltageCompSaturation = 12.0;
            this.id = id;
            flexConfig = new SparkFlexConfig();

            /* Put default config settings for all mechanisms here */
            flexConfig.limitSwitch.forwardLimitSwitchEnabled(false);
            flexConfig.limitSwitch.reverseLimitSwitchEnabled(false);
        }

        public void applyflexConfig(SparkFlex flex) {
            flex.configure(flexConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        }

        public void configVoltageCompensation(Voltage voltageCompSaturation) {
            this.voltageCompSaturation = voltageCompSaturation.in(Units.Volts);
        }

        public void configMaxMotionPositionMode(MAXMotionPositionMode mode) {
            flexConfig.closedLoop.maxMotion.positionMode(mode);
        }

        /**
         * i'm not actually sure if this is clockwise or counter clockwise
         */
        public void configCounterClockwise_Positive() {
            flexConfig.inverted(false);
        }

        /**
         * i'm not actually sure if this is clockwise or counter clockwise
         */
        public void configClockwise_Positive() {
            flexConfig.inverted(true);
        }

        public void configSmartCurrentLimit(Current stallLimit, Current supplyLimit) {
            flexConfig.smartCurrentLimit((int) stallLimit.in(Units.Amps), (int) supplyLimit.in(Units.Amps));
        }

        public void configSmartStallCurrentLimit(Current stallLimit) {
            flexConfig.smartCurrentLimit((int) stallLimit.in(Units.Amps));
        }

        public void configStatorCurrentLimit(Current stallLimit, int chop) {
            flexConfig.secondaryCurrentLimit((int) stallLimit.in(Units.Amps), chop);
        }

        /**
         * 
         * TODO: learn how these set actually
         * 
         * @param forward The maxiumum forward output [0-1]
         * @param reverse The maximum reverse output [-1-0]
         */
        public void configPeakOutput(double forward, double reverse) {
            flexConfig.closedLoop.outputRange(reverse, forward);
        }

        /**
         * @param threshold foward max angle or cumulative rotation
         * @param enabled
         */
        public void configForwardSoftLimit(Angle threshold, boolean enabled) {
            flexConfig.softLimit.forwardSoftLimit(threshold.in(Units.Rotations));
            flexConfig.softLimit.forwardSoftLimitEnabled(enabled);
        }

        /**
         * @param threshold reverse max angle or cumulative rotation
         * @param enabled
         */
        public void configReverseSoftLimit(Angle threshold, boolean enabled) {
            flexConfig.softLimit.reverseSoftLimit(threshold.in(Units.Rotations));
            flexConfig.softLimit.reverseSoftLimitEnabled(enabled);
        }

        /**
         * @param acceleration desired ROC of motor (Rotations Per Minute)
         * @param velocity     desired crusing velocity of motor (setpoint) (Rotations
         *                     Per Minute)
         */
        public void configMaxMotionVelocity(AngularVelocity acceleration, AngularVelocity velocity) {
            flexConfig.closedLoop.maxMotion.maxAcceleration(acceleration.in(Units.RPM));
            flexConfig.closedLoop.maxMotion.maxVelocity(velocity.in(Units.RPM));
        }

        /**
         * @param velocity desired crusing velocity of motor (setpoint) (Rotations Per
         *                 Minute)
         */
        public void configMaxMotionVelocity(AngularVelocity velocity) {
            flexConfig.closedLoop.maxMotion.maxVelocity(velocity.in(Units.RPM));
        }

        /**
         * @param acceleration desired ROC of motor (Rotations Per Minute)
         * @param velocity     desired crusing velocity of motor (Rotations
         *                     Per Minute)
         * @param error        allowed error in RPM
         */
        public void configMaxMotion(AngularVelocity velocity, AngularVelocity acceleration, AngularVelocity error) {
            flexConfig.closedLoop.maxMotion.maxAcceleration(acceleration.in(Units.RPM));
            flexConfig.closedLoop.maxMotion.maxVelocity(velocity.in(Units.RPM));
            flexConfig.closedLoop.maxMotion.allowedClosedLoopError(error.in(Units.RPM));
        }

        /**
         * If a remote sensor is used this a ratio of sensor rotations to the
         * mechanism's output.
         * 
         * @param gearRatio Ratio of Relvative encoder rotations relative to mechanism's
         *                  rotary output
         */
        public void configGearRatio(Angle gearRatio) {
            flexConfig.encoder.positionConversionFactor(gearRatio.in(Units.Rotations));
        }

        /**
         * @param motor motor
         * @return returns given motor's relative encoder position conversion factor
         */
        public double getGearRatio(SparkFlex motor) {
            return motor.configAccessor.encoder.getPositionConversionFactor();
        }

        /**
         * @param mode Desired IdleMode
         */
        public void configNeutralBrakeMode(IdleMode mode) {
            flexConfig.idleMode(mode);
        }

        /**
         * Defaults to slot 0
         *
         * @param kP
         * @param kI
         * @param kD
         */
        public void configPIDGains(double kP, double kI, double kD) {
            configPIDGains(0, kP, kI, kD);
        }

        public void configPIDGains(int slot, double kP, double kI, double kD) {
            flexConfigFeedbackPID(slot, kP, kI, kD);
        }

        public void configVelocityPIDGains(int slot, double kS, double kP, double kI, double kD) {
            flexConfigVelocityPID(slot, kS, kP, kI, kD);
        }

        /**
         * Defaults to slot 0
         *
         * @param kS
         * @param kV
         * @param kA
         * @param kG
         */
        public void configFeedForwardGains(double FF, double kP, double kI, double kD) {
            configFeedForwardGains(0, FF, kP, kI, kD);
        }

        public void configFeedForwardGains(int slot, double FF, double kP, double kI, double kD) {
            flexConfigFeedForward(slot, FF, kP, kI, kD);
        }

        public void configFeedbackSensorSource(SensorType source) {
            configFeedbackSensorSource(source, Units.Rotation.of(0));
        }

        public void configFeedbackSensorSource(SensorType source, Angle offset) {
            if (SensorType.Absolute == source) {
                flexConfig.absoluteEncoder.zeroOffset(offset.in(Units.Rotation));
            } else if (SensorType.Relative == source) {
                // why would you do this bro?
            } else {
                DriverStation.reportWarning("RevMechConfig: Unkwn SensorType provided", false);
            }
        }

        // Configure the TalonFXConfiguration feed forward gains
        private void flexConfigFeedForward(int slot, double FF, double kP, double kI, double kD) {
            if (slot == 0) {
                flexConfig.closedLoop.pidf(kP, kI, kD, FF, ClosedLoopSlot.kSlot0);
            } else if (slot == 1) {
                flexConfig.closedLoop.pidf(kP, kI, kD, FF, ClosedLoopSlot.kSlot1);
            } else if (slot == 2) {
                flexConfig.closedLoop.pidf(kP, kI, kD, FF, ClosedLoopSlot.kSlot2);
            } else {
                DriverStation.reportWarning("RevMechConfig: Invalid FeedForward slot", false);
            }
        }

        private void flexConfigVelocityPID(int slot, double kS, double kP, double kI, double kD) {
            if (slot == 0) {
                flexConfig.closedLoop.pid(kP, kI, kD, ClosedLoopSlot.kSlot0);
            } else if (slot == 1) {
                flexConfig.closedLoop.pid(kP, kI, kD, ClosedLoopSlot.kSlot1);
            } else if (slot == 2) {
                flexConfig.closedLoop.pid(kP, kI, kD, ClosedLoopSlot.kSlot2);
            } else {
                DriverStation.reportWarning("RevMechConfig: Invalid Feedback slot", false);
            }
        }

        private void flexConfigFeedbackPID(int slot, double kP, double kI, double kD) {
            if (slot == 0) {
                flexConfig.closedLoop.pid(kP, kI, kD, ClosedLoopSlot.kSlot0);
            } else if (slot == 1) {
                flexConfig.closedLoop.pid(kP, kI, kD, ClosedLoopSlot.kSlot1);
            } else if (slot == 2) {
                flexConfig.closedLoop.pid(kP, kI, kD, ClosedLoopSlot.kSlot2);

            } else {
                DriverStation.reportWarning("RevMechConfig: Invalid Feedback slot", false);
            }
        }
    }
}