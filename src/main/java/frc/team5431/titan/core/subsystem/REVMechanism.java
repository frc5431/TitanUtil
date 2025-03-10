
package frc.team5431.titan.core.subsystem;

import java.util.function.DoubleSupplier;

import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.MAXMotionConfig.MAXMotionPositionMode;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.team5431.titan.core.misc.Calc;

public abstract class REVMechanism implements Subsystem {
    protected boolean attached = false;
    protected SparkMax motor;
    public Config config;

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
    public REVMechanism(SparkMax motor, boolean attached) {
        this.attached = attached;
        this.motor = motor;
        this.config = setConfig();
    }

    protected abstract Config setConfig();

    protected void setConfig(Config config) {
        this.config = config;
    };

    /** Sets the mechanism position of the motor to 0 */
    public void resetPosition() {
        if (attached) {
            motor.getEncoder().setPosition(0);
        }
    }

    /**
     * Checks if the motor is reaching the velocity setpoint
     * 
     * @param target the target RPM to reach
     * @param error  allowed error in RPM
     * @return true if the motor's RPM is within the error of the target RPM
     */
    public boolean getVelocitySetpointGoal(AngularVelocity target, AngularVelocity error) {
        if (attached) {
            if (Calc.approxEquals(motor.getEncoder().getVelocity(), target.in(Units.RPM), error.in(Units.RPM))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the motor is reaching the rotational setpoint
     * 
     * @param target the target rotation angle
     * @param error  allowed error in rotations (keep SMALL)
     * @return true if the motor's angle position is within the error of the target
     *         angle position
     */
    public boolean getPositionSetpointGoal(Angle target, Angle error) {
        if (attached) {
            if (Calc.approxEquals(motor.getEncoder().getPosition(), target.in(Units.Rotations),
                    error.in(Units.Rotations))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the motor is reaching the rotational setpoint
     * Uses absolute encoder
     * 
     * @param target the target rotation angle
     * @param error  allowed error in rotation (keep SMALL)
     * @return true if the motor's angle position is within the error of the target
     *         angle position
     */
    public boolean getAngleSetpointGoal(Angle target, Angle error) {
        if (attached) {
            if (Calc.approxEquals(motor.getAbsoluteEncoder().getPosition(), target.in(Units.Rotation),
                    error.in(Units.Rotation))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the motor is reaching the output setpoint
     * 
     * @param target the target output to reach
     * @param error  allowed error
     * @return true if the motor's output is within the error of the target output
     */
    public boolean getOutputSetpointGoal(double target, double error) {
        if (attached) {
            if (Calc.approxEquals(motor.getAppliedOutput(), target, error)) {
                return true;
            }
        }
        return false;
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
     * Closed-loop Position Max Motion with arbff
     *
     * @param position Measure of Aggregate Rotation; Converted to Rotations
     * @param arbff    A value from which is represented in voltage applied to the
     *                 motor after the result of the specified control mode. The
     *                 units for the parameter is Volts.
     *
     */
    public void setMMPosition(Angle position, double arbff) {
        if (attached) {
            motor.getClosedLoopController().setReference(position.in(Units.Rotations),
                    ControlType.kMAXMotionPositionControl, ClosedLoopSlot.kSlot0, arbff);
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
    public void setMMPosition(DoubleSupplier position, double arbff) {
        if (attached) {
            motor.getClosedLoopController().setReference(position.getAsDouble(), ControlType.kMAXMotionPositionControl,
                    ClosedLoopSlot.kSlot0, arbff);
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
            config.configIdleMode(idleMode);
            config.applySparkConfig(motor);
        }
    }

    /**
     * @param enabled
     * @param threshold motor.configAccessor.softLimit.getReverseSoftLimit();
     */
    public void toggleReverseSoftLimit(boolean enabled, double threshold) {
        if (attached) {
            config.configReverseSoftLimit(Units.Rotation.of(threshold), enabled);
            config.applySparkConfig(motor);
        }
    }

    public void toggleTorqueCurrentLimit(Current enabledLimit, boolean enabled) {
        if (attached) {
            if (enabled) {
                config.configSmartStallCurrentLimit(enabledLimit);
                config.applySparkConfig(motor);
            }
        }
    }

    public double getMotorVelocity() {
        if (attached) {
            return motor.getEncoder().getVelocity();
        }
        return 0;
    }

    public double getMotorVoltage() {
        if (attached) {
            return motor.getBusVoltage();
        }

        return 0;
    }

    public double getMotorCurrent() {
        if (attached) {
            return motor.getOutputCurrent();
        }

        return 0;
    }

    public double getMotorOutput() {
        if (attached) {
            return motor.getAppliedOutput();
        }

        return 0;
    }

    public static class Config {
        public String title;
        public int id;
        public SparkMaxConfig sparkConfig;
        public double voltageCompSaturation; // 12V by default

        public Config(String title, int id) {
            this.title = title;
            this.voltageCompSaturation = 12.0;
            this.id = id;
            sparkConfig = new SparkMaxConfig();

            /* Put default config settings for all mechanisms here */
            sparkConfig.limitSwitch.forwardLimitSwitchEnabled(false);
            sparkConfig.limitSwitch.reverseLimitSwitchEnabled(false);
        }

        public void applySparkConfig(SparkMax spark) {
            spark.configure(sparkConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        }

        public void applySparkConfig(SparkMax spark, ResetMode resetMode) {
            spark.configure(sparkConfig, resetMode, PersistMode.kPersistParameters);
        }

        public void configVoltageCompensation(Voltage voltageCompSaturation) {
            this.voltageCompSaturation = voltageCompSaturation.in(Units.Volts);
        }

        public void setVoltageCompensation() {
            sparkConfig.voltageCompensation(this.voltageCompSaturation);
        }

        public void configMaxMotionPositionMode(MAXMotionPositionMode mode) {
            sparkConfig.closedLoop.maxMotion.positionMode(mode);
        }

        public void configMaxIAccum(double max) {
            sparkConfig.closedLoop.iMaxAccum(max);
        }

        public void configPositionWrapping(boolean enabled) {
            sparkConfig.closedLoop.positionWrappingEnabled(enabled);
        }

        public void configPositionWrapping(boolean enabled, Angle max, Angle min) {
            sparkConfig.closedLoop.positionWrappingEnabled(enabled);
            sparkConfig.closedLoop.positionWrappingInputRange(min.in(Units.Rotation), max.in(Units.Rotation));
        }

        public void configMinPositionWrapping(boolean enabled, Angle min) {
            sparkConfig.closedLoop.positionWrappingEnabled(enabled);
            sparkConfig.closedLoop.positionWrappingMinInput(min.in(Units.Rotation));
        }

        public void configSoftLimit(boolean enabled, Angle fowardLimit, Angle reverseLimit) {
            sparkConfig.softLimit.forwardSoftLimitEnabled(enabled);
            sparkConfig.softLimit.reverseSoftLimitEnabled(enabled);
            sparkConfig.softLimit.forwardSoftLimit(fowardLimit.in(Units.Rotation));
            sparkConfig.softLimit.reverseSoftLimit(reverseLimit.in(Units.Rotation));
        }

        public void configForwardSoftLimit(boolean enabled, Angle fowardLimit) {
            sparkConfig.softLimit.forwardSoftLimitEnabled(enabled);
            sparkConfig.softLimit.forwardSoftLimit(fowardLimit.in(Units.Rotation));
        }

        public void configReverseSoftLimit(boolean enabled, Angle reverseLimit) {
            sparkConfig.softLimit.reverseSoftLimitEnabled(enabled);
            sparkConfig.softLimit.reverseSoftLimit(reverseLimit.in(Units.Rotation));
        }

        public void configMaxPositionWrapping(boolean enabled, Angle max) {
            sparkConfig.closedLoop.positionWrappingEnabled(enabled);
            sparkConfig.closedLoop.positionWrappingMaxInput(max.in(Units.Rotation));
        }

        public void configInverted(boolean inverted) {
            sparkConfig.inverted(inverted);
        }

        public void configRelativeEncoderInverted(boolean inverted) {
            sparkConfig.encoder.inverted(inverted);
        }

        public void configAbsoluteEncoderInverted(boolean inverted) {
            sparkConfig.absoluteEncoder.inverted(inverted);
        }

        public void configSmartCurrentLimit(Current stallLimit, Current supplyLimit) {
            sparkConfig.smartCurrentLimit((int) stallLimit.in(Units.Amps), (int) supplyLimit.in(Units.Amps));
        }

        public void configSmartStallCurrentLimit(Current stallLimit) {
            sparkConfig.smartCurrentLimit((int) stallLimit.in(Units.Amps));
        }

        public void configStatorCurrentLimit(Current stallLimit, int chop) {
            sparkConfig.secondaryCurrentLimit((int) stallLimit.in(Units.Amps), chop);
        }

        /**
         * 
         * TODO: learn how these set actually
         * 
         * @param forward The maxiumum forward output [0-1]
         * @param reverse The maximum reverse output [-1-0]
         */
        public void configPeakOutput(double forward, double reverse) {
            sparkConfig.closedLoop.outputRange(reverse, forward);
        }

        /**
         * @param threshold foward max angle or cumulative rotation
         * @param enabled
         */
        public void configForwardSoftLimit(Angle threshold, boolean enabled) {
            sparkConfig.softLimit.forwardSoftLimit(threshold.in(Units.Rotations));
            sparkConfig.softLimit.forwardSoftLimitEnabled(enabled);
        }

        /**
         * @param threshold reverse max angle or cumulative rotation
         * @param enabled
         */
        public void configReverseSoftLimit(Angle threshold, boolean enabled) {
            sparkConfig.softLimit.reverseSoftLimit(threshold.in(Units.Rotations));
            sparkConfig.softLimit.reverseSoftLimitEnabled(enabled);
        }

        /**
         * @param acceleration desired ROC of motor (Rotations Per Minute)
         * @param velocity     desired crusing velocity of motor (setpoint) (Rotations
         *                     Per Minute)
         */
        public void configMaxMotionVelocity(AngularVelocity acceleration, AngularVelocity velocity) {
            sparkConfig.closedLoop.maxMotion.maxAcceleration(acceleration.in(Units.RPM));
            sparkConfig.closedLoop.maxMotion.maxVelocity(velocity.in(Units.RPM));
        }

        /**
         * @param velocity desired crusing velocity of motor (setpoint) (Rotations Per
         *                 Minute)
         */
        public void configMaxMotionVelocity(AngularVelocity velocity) {
            sparkConfig.closedLoop.maxMotion.maxVelocity(velocity.in(Units.RPM));
        }

        /**
         * @param acceleration desired ROC of motor (Rotations Per Minute)
         * @param velocity     desired crusing velocity of motor (Rotations
         *                     Per Minute)
         * @param error        allowed error in RPM
         */
        public void configMaxMotion(AngularVelocity velocity, AngularVelocity acceleration, AngularVelocity error) {
            sparkConfig.closedLoop.maxMotion.maxAcceleration(acceleration.in(Units.RPM));
            sparkConfig.closedLoop.maxMotion.maxVelocity(velocity.in(Units.RPM));
            sparkConfig.closedLoop.maxMotion.allowedClosedLoopError(error.in(Units.RPM));
        }

        /**
         * If a remote sensor is used this a ratio of sensor rotations to the
         * mechanism's output.
         * 
         * @param gearRatio Ratio of Relvative encoder rotations relative to mechanism's
         *                  rotary output
         */
        public void configEncoderPosRatio(double gearRatio) {
            sparkConfig.encoder.positionConversionFactor(gearRatio);
            sparkConfig.encoder.velocityConversionFactor(gearRatio);
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
        public void configIdleMode(IdleMode mode) {
            sparkConfig.idleMode(mode);
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
            sparkConfigFeedbackPID(slot, kP, kI, kD);
        }

        public void configVelocityPIDGains(int slot, double kP, double kI, double kD) {
            sparkConfigVelocityPID(slot, kP, kI, kD);
        }

        /**
         * Defaults to slot 0
         *
         * @param FF
         * @param kP
         * @param kI
         * @param kD
         */
        public void configFeedForwardGains(double FF, double kP, double kI, double kD) {
            configFeedForwardGains(0, FF, kP, kI, kD);
        }

        public void configFeedForwardGains(int slot, double FF, double kP, double kI, double kD) {
            sparkConfigFeedForward(slot, FF, kP, kI, kD);
        }

        public void configFeedbackSensorSource(FeedbackSensor source) {
            configFeedbackSensorSource(source, Units.Rotation.of(0));
        }

        public void configFeedbackSensorSource(FeedbackSensor source, Angle offset) {
            sparkConfig.closedLoop.feedbackSensor(source);
            sparkConfig.absoluteEncoder.zeroOffset(offset.in(Units.Rotation));
        }

        private void sparkConfigFeedForward(int slot, double FF, double kP, double kI, double kD) {
            if (slot == 0) {
                sparkConfig.closedLoop.pidf(kP, kI, kD, FF, ClosedLoopSlot.kSlot0);
            } else if (slot == 1) {
                sparkConfig.closedLoop.pidf(kP, kI, kD, FF, ClosedLoopSlot.kSlot1);
            } else if (slot == 2) {
                sparkConfig.closedLoop.pidf(kP, kI, kD, FF, ClosedLoopSlot.kSlot2);
            } else {
                DriverStation.reportWarning("RevMechConfig: Invalid FeedForward slot", false);
            }
        }

        private void sparkConfigVelocityPID(int slot, double kP, double kI, double kD) {
            if (slot == 0) {
                sparkConfig.closedLoop.pid(kP, kI, kD, ClosedLoopSlot.kSlot0);
            } else if (slot == 1) {
                sparkConfig.closedLoop.pid(kP, kI, kD, ClosedLoopSlot.kSlot1);
            } else if (slot == 2) {
                sparkConfig.closedLoop.pid(kP, kI, kD, ClosedLoopSlot.kSlot2);
            } else {
                DriverStation.reportWarning("RevMechConfig: Invalid Feedback slot", false);
            }
        }

        private void sparkConfigFeedbackPID(int slot, double kP, double kI, double kD) {
            if (slot == 0) {
                sparkConfig.closedLoop.pid(kP, kI, kD, ClosedLoopSlot.kSlot0);
            } else if (slot == 1) {
                sparkConfig.closedLoop.pid(kP, kI, kD, ClosedLoopSlot.kSlot1);
            } else if (slot == 2) {
                sparkConfig.closedLoop.pid(kP, kI, kD, ClosedLoopSlot.kSlot2);

            } else {
                DriverStation.reportWarning("RevMechConfig: Invalid Feedback slot", false);
            }
        }
    }
}