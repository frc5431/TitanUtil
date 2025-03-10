
package frc.team5431.titan.core.subsystem;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.MotionMagicTorqueCurrentFOC;
import com.ctre.phoenix6.controls.MotionMagicVelocityTorqueCurrentFOC;
import com.ctre.phoenix6.controls.MotionMagicVelocityVoltage;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.PositionTorqueCurrentFOC;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.controls.VelocityTorqueCurrentFOC;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Subsystem;

public abstract class CTREMechanism implements Subsystem {
    protected boolean attached = false;
    protected TalonFX motor;
    public Config config;

    /**
     * 
     * Multi-Purpose subsystem class based on 3847 Spectrum's Mechanism class
     * utlizing PID Closed-Loop and Motion Magic positional or velocity control,
     * WPILib Units, and TorqueFOC
     * 
     * Only use one control method per mechanism, PLEASE!
     * 
     * @param attached for if the motor is in use
     */
    public CTREMechanism(TalonFX motor, boolean attached) {
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
            resetMotorPosition(Units.Degrees.of(0));
        }
    }

    /**
     * Sets the mechanism position of the motor based on constant value
     *
     * @param position Measure of Angle; Converted to Rotations
     */
    public void resetMotorPosition(Angle position) {
        if (attached) {
            motor.setPosition(position.in(Units.Rotations));
        }
    }

    /**
     * Closed-loop Velocity Motion Magic with torque control (requires Pro)
     *
     * @param velocity Mesure of Velocity in Terms of Angle; Converted to
     *                 Rotations/Revolutions Per Second
     */
    public void setMMVelocityFOC(AngularVelocity velocity) {
        if (attached) {
            MotionMagicVelocityTorqueCurrentFOC mm = config.mmVelocityFOC
                    .withVelocity(velocity.in(Units.RevolutionsPerSecond));
            motor.setControl(mm);
        }
    }

    /**
     * Closed-loop Velocity with torque control (requires Pro)
     *
     * @param velocity Mesure of Velocity in Terms of Angle; Converted to Rotations
     *                 Per Second
     */
    public void setVelocityTorqueCurrentFOC(AngularVelocity velocity) {
        if (attached) {
            VelocityTorqueCurrentFOC output = config.velocityTorqueCurrentFOC
                    .withVelocity(velocity.in(Units.RotationsPerSecond));
            motor.setControl(output);
        }
    }

    /**
     * Closed-loop velocity control with voltage compensation
     *
     * @param velocity Mesure of Velocity in Terms of Angle; Converted to Rotations
     *                 Per Second
     */
    public void setVelocity(AngularVelocity velocity) {
        if (attached) {
            VelocityVoltage output = config.velocityControl.withVelocity(velocity.in(Units.RotationsPerSecond));
            motor.setControl(output);
        }
    }

    /**
     * Closed-loop Position Voltage Control
     *
     * @param position Measure of Aggregate Rotation; Converted to Rotations
     */
    public void setPositionVoltage(Angle position) {
        if (attached) {
            PositionVoltage mm = config.positionVoltage.withPosition(position.in(Units.Rotations));
            motor.setControl(mm);
        }
    }

    /**
     * Closed-loop Position FOC Control
     *
     * @param position Measure of Aggregate Rotation; Converted to Rotations
     */
    public void setPositionFOC(Angle position) {
        if (attached) {
            PositionTorqueCurrentFOC mm = config.positionFOC.withPosition(position.in(Units.Rotations));
            motor.setControl(mm);
        }
    }

    /**
     * Closed-loop Position Motion Magic with torque control (requires Pro)
     *
     * @param position Measure of Aggregate Rotation; Converted to Rotations
     */
    public void setMMPositionFOC(Angle position) {
        if (attached) {
            MotionMagicTorqueCurrentFOC mm = config.mmPositionFOC.withPosition(position.in(Units.Rotations));
            motor.setControl(mm);
        }
    }

    /**
     * Closed-loop Position Motion Magic
     *
     * @param position Measure of Aggregate Rotation; Converted to Rotations
     */
    public void setMMPosition(Angle position) {
        if (attached) {
            MotionMagicVoltage mm = config.mmPositionVoltage.withPosition(position.in(Units.Rotations));
            motor.setControl(mm);
        }
    }

    /**
     * Closed-loop Position Motion Magic
     *
     * @param position Measure of Aggregate Rotation; Converted to Rotations
     */
    public void setMMPosition(DoubleSupplier position) {
        if (attached) {
            MotionMagicVoltage mm = config.mmPositionVoltage.withPosition(position.getAsDouble());
            motor.setControl(mm);
        }
    }

    /**
     * Closed-loop Position Motion Magic using a slot other than 0
     *
     * @param position rotations
     * @param slot     gains slot
     */
    public void setMMPosition(Angle position, int slot) {
        if (attached) {
            MotionMagicVoltage mm = config.mmPositionVoltageSlot.withSlot(slot)
                    .withPosition(position.in(Units.Rotations));
            motor.setControl(mm);
        }
    }

    /**
     * Open-loop Percent output control with voltage compensation
     *
     * @param percent fractional units between -1 and +1
     */
    public void setPercentOutput(double percent) {
        if (attached) {
            VoltageOut output = config.voltageControl.withOutput(config.voltageCompSaturation * percent);
            motor.setControl(output);
        }
    }

    public void setBrakeMode(boolean isInBrake) {
        if (attached) {
            config.configNeutralBrakeMode(isInBrake);
            config.applyTalonConfig(motor);
        }
    }

    public void toggleReverseSoftLimit(boolean enabled) {
        if (attached) {
            double threshold = config.talonConfig.SoftwareLimitSwitch.ReverseSoftLimitThreshold;
            config.configReverseSoftLimit(threshold, enabled);
            config.applyTalonConfig(motor);
        }
    }

    public void toggleTorqueCurrentLimit(Current enabledLimit, boolean enabled) {
        if (attached) {
            if (enabled) {
                config.configForwardTorqueCurrentLimit(enabledLimit);
                config.configReverseTorqueCurrentLimit(enabledLimit);
                config.applyTalonConfig(motor);
            } else {
                config.configForwardTorqueCurrentLimit(Units.Amps.of(300));
                config.configReverseTorqueCurrentLimit(Units.Amps.of(300));
                config.applyTalonConfig(motor);
            }
        }
    }

    public static class Config {
        public String title;
        public int id;
        public TalonFXConfiguration talonConfig;
        public double voltageCompSaturation; // 12V by default

        public MotionMagicVelocityTorqueCurrentFOC mmVelocityFOC = new MotionMagicVelocityTorqueCurrentFOC(0);
        public MotionMagicTorqueCurrentFOC mmPositionFOC = new MotionMagicTorqueCurrentFOC(0);
        public MotionMagicVelocityVoltage mmVelocityVoltage = new MotionMagicVelocityVoltage(0);
        public MotionMagicVoltage mmPositionVoltage = new MotionMagicVoltage(0);
        public MotionMagicVoltage mmPositionVoltageSlot = new MotionMagicVoltage(0).withSlot(1);
        public VoltageOut voltageControl = new VoltageOut(0);
        public VelocityVoltage velocityControl = new VelocityVoltage(0);
        public PositionVoltage positionVoltage = new PositionVoltage(0);
        public PositionTorqueCurrentFOC positionFOC = new PositionTorqueCurrentFOC(0);
        public VelocityTorqueCurrentFOC velocityTorqueCurrentFOC = new VelocityTorqueCurrentFOC(0);
        public DutyCycleOut percentOutput = new DutyCycleOut(
                0); // Percent Output control using percentage of supply voltage //Should
        // normally use VoltageOut

        public Config(String title, int id, String canbus) {
            this.title = title;
            this.voltageCompSaturation = 12.0;
            this.id = id;
            talonConfig = new TalonFXConfiguration();

            /* Put default config settings for all mechanisms here */
            talonConfig.HardwareLimitSwitch.ForwardLimitEnable = false;
            talonConfig.HardwareLimitSwitch.ReverseLimitEnable = false;
        }

        public void applyTalonConfig(TalonFX talon) {
            StatusCode result = talon.getConfigurator().apply(talonConfig);
            if (!result.isOK()) {
                DriverStation.reportWarning(
                        "Could not apply config changes to " + title + "\'s motor ", false);
            }
        }

        public void configVoltageCompensation(Voltage voltageCompSaturation) {
            this.voltageCompSaturation = voltageCompSaturation.in(Units.Volts);
        }

        public void configCounterClockwise_Positive() {
            talonConfig.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;
        }

        public void configClockwise_Positive() {
            talonConfig.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
        }

        public void configSupplyCurrentLimit(
                Current supplyLimit, boolean enabled) {
            talonConfig.CurrentLimits.SupplyCurrentLimit = supplyLimit.in(Units.Amps);
            talonConfig.CurrentLimits.SupplyCurrentLimitEnable = enabled;
        }

        public void configStatorCurrentLimit(Current statorLimit, boolean enabled) {
            talonConfig.CurrentLimits.StatorCurrentLimit = statorLimit.in(Units.Amps);
            talonConfig.CurrentLimits.StatorCurrentLimitEnable = enabled;
        }

        public void configForwardTorqueCurrentLimit(Current currentLimit) {
            talonConfig.TorqueCurrent.PeakForwardTorqueCurrent = currentLimit.in(Units.Amps);
        }

        public void configReverseTorqueCurrentLimit(Current currentLimit) {
            talonConfig.TorqueCurrent.PeakReverseTorqueCurrent = currentLimit.in(Units.Amps);
        }

        /**
         * configured motor duty cycle control type dead
         * 
         * @param deadband 0.0-0.25
         */
        public void configNeutralDeadband(double deadband) {
            talonConfig.MotorOutput.DutyCycleNeutralDeadband = deadband;
        }

        public void configPeakOutput(double forward, double reverse) {
            talonConfig.MotorOutput.PeakForwardDutyCycle = forward;
            talonConfig.MotorOutput.PeakReverseDutyCycle = reverse;
        }

        public void configForwardSoftLimit(double threshold, boolean enabled) {
            talonConfig.SoftwareLimitSwitch.ForwardSoftLimitThreshold = threshold;
            talonConfig.SoftwareLimitSwitch.ForwardSoftLimitEnable = enabled;
        }

        public void configReverseSoftLimit(double threshold, boolean enabled) {
            talonConfig.SoftwareLimitSwitch.ReverseSoftLimitThreshold = threshold;
            talonConfig.SoftwareLimitSwitch.ReverseSoftLimitEnable = enabled;
        }

        // Configure optional motion magic velocity parameters
        public void configMotionMagicVelocity(double acceleration, double feedforward) {
            mmVelocityFOC = mmVelocityFOC.withAcceleration(acceleration).withFeedForward(feedforward);
            mmVelocityVoltage = mmVelocityVoltage.withAcceleration(acceleration).withFeedForward(feedforward);
        }

        // Configure optional motion magic position parameters
        public void configMotionMagicPosition(double feedforward) {
            mmPositionFOC = mmPositionFOC.withFeedForward(feedforward);
            mmPositionVoltage = mmPositionVoltage.withFeedForward(feedforward);
        }

        /**
         * @param cruiseVelocity defines the passive running velocity of the motor, rps
         * @param acceleration   acceleration in rps
         * @param jerk           will slow down curve, rps
         */
        public void configMotionMagic(AngularVelocity cruiseVelocity, double acceleration, double jerk) {
            talonConfig.MotionMagic.MotionMagicCruiseVelocity = cruiseVelocity.in(Units.RotationsPerSecond);
            talonConfig.MotionMagic.MotionMagicAcceleration = acceleration;
            talonConfig.MotionMagic.MotionMagicJerk = jerk;
        }

        // This is the ratio of rotor rotations to the mechanism's output.
        // If a remote sensor is used this a ratio of sensor rotations to the
        // mechanism's output.
        public void configGearRatio(double gearRatio) {
            talonConfig.Feedback.SensorToMechanismRatio = gearRatio;
        }

        public double getGearRatio() {
            return talonConfig.Feedback.SensorToMechanismRatio;
        }

        /**
         * @param setBrakeMode true enables break mode, false enables coast
         */
        public void configNeutralBrakeMode(boolean setBrakeMode) {
            if (setBrakeMode) {
                talonConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;
            } else {
                talonConfig.MotorOutput.NeutralMode = NeutralModeValue.Coast;
            }
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
            talonConfigFeedbackPID(slot, kP, kI, kD);
        }

        public void configVelocityPIDGains(int slot, double kS, double kP, double kI, double kD) {
            talonConfigVelocityPID(slot, kS, kP, kI, kD);
        }

        /**
         * Defaults to slot 0
         *
         * @param kS
         * @param kV
         * @param kA
         * @param kG
         */
        public void configFeedForwardGains(double kS, double kV, double kA, double kG) {
            configFeedForwardGains(0, kS, kV, kA, kG);
        }

        public void configFeedForwardGains(int slot, double kS, double kV, double kA, double kG) {
            talonConfigFeedForward(slot, kV, kA, kS, kG);
        }

        public void configFeedbackSensorSource(FeedbackSensorSourceValue source) {
            configFeedbackSensorSource(source, 0);
        }

        public void configFeedbackSensorSource(FeedbackSensorSourceValue source, double offset) {
            talonConfig.Feedback.FeedbackSensorSource = source;
            talonConfig.Feedback.FeedbackRotorOffset = offset;
        }

        /**
         * Defaults to slot 0
         *
         * @param isArm
         */
        public void configGravityType(boolean isArm) {
            configGravityType(0, isArm);
        }

        public void configGravityType(int slot, boolean isArm) {
            GravityTypeValue gravityType = isArm ? GravityTypeValue.Arm_Cosine : GravityTypeValue.Elevator_Static;
            if (slot == 0) {
                talonConfig.Slot0.GravityType = gravityType;
            } else if (slot == 1) {
                talonConfig.Slot1.GravityType = gravityType;
            } else if (slot == 2) {
                talonConfig.Slot2.GravityType = gravityType;
            } else {
                DriverStation.reportWarning("MechConfig: Invalid slot", false);
            }
        }

        // Configure the TalonFXConfiguration feed forward gains
        private void talonConfigFeedForward(int slot, double kV, double kA, double kS, double kG) {
            if (slot == 0) {
                talonConfig.Slot0.kV = kV;
                talonConfig.Slot0.kA = kA;
                talonConfig.Slot0.kS = kS;
                talonConfig.Slot0.kG = kG;
            } else if (slot == 1) {
                talonConfig.Slot1.kV = kV;
                talonConfig.Slot1.kA = kA;
                talonConfig.Slot1.kS = kS;
                talonConfig.Slot1.kG = kG;
            } else if (slot == 2) {
                talonConfig.Slot2.kV = kV;
                talonConfig.Slot2.kA = kA;
                talonConfig.Slot2.kS = kS;
                talonConfig.Slot2.kG = kG;
            } else {
                DriverStation.reportWarning("MechConfig: Invalid FeedForward slot", false);
            }
        }

        private void talonConfigVelocityPID(int slot, double kS, double kP, double kI, double kD) {
            if (slot == 0) {
                talonConfig.Slot0.kS = kS;
                talonConfig.Slot0.kP = kP;
                talonConfig.Slot0.kI = kI;
                talonConfig.Slot0.kD = kD;
            } else if (slot == 1) {
                talonConfig.Slot1.kS = kS;
                talonConfig.Slot1.kP = kP;
                talonConfig.Slot1.kI = kI;
                talonConfig.Slot1.kD = kD;
            } else if (slot == 2) {
                talonConfig.Slot2.kS = kS;
                talonConfig.Slot2.kP = kP;
                talonConfig.Slot2.kI = kI;
                talonConfig.Slot2.kD = kD;
            } else {
                DriverStation.reportWarning("MechConfig: Invalid Feedback slot", false);
            }
        }

        private void talonConfigFeedbackPID(int slot, double kP, double kI, double kD) {
            if (slot == 0) {
                talonConfig.Slot0.kP = kP;
                talonConfig.Slot0.kI = kI;
                talonConfig.Slot0.kD = kD;
            } else if (slot == 1) {
                talonConfig.Slot1.kP = kP;
                talonConfig.Slot1.kI = kI;
                talonConfig.Slot1.kD = kD;
            } else if (slot == 2) {
                talonConfig.Slot2.kP = kP;
                talonConfig.Slot2.kI = kI;
                talonConfig.Slot2.kD = kD;
            } else {
                DriverStation.reportWarning("MechConfig: Invalid Feedback slot", false);
            }
        }
    }
}