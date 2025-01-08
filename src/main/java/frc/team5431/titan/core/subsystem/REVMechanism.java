
package frc.team5431.titan.core.subsystem;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.MotionMagicTorqueCurrentFOC;
import com.ctre.phoenix6.controls.MotionMagicVelocityTorqueCurrentFOC;
import com.ctre.phoenix6.controls.MotionMagicVelocityVoltage;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.VelocityTorqueCurrentFOC;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.config.MAXMotionConfig;
import com.revrobotics.spark.config.SparkFlexConfig;

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

    /**
     * 
     * Multi-Purpose subsystem class based on 3847 Spectrum's Mechanism class
     * utlizing PID Closed-Loop and Motion Magic positional or velocity control,
     * WPILib Units, and TorqueFOC
     * 
     * Only use one control method per mechanism, PLEASE!
     * 
     * @param attached for if the motor is in use; for when not if build blows up
     *                 the mechanism once more
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
            motor.
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
     * NATALIE, FIX THIS AFTER SAT AND DONT FORGET
     * 
     * Closed-loop Velocity with torque control (requires Pro)
     *
     * @param velocity Mesure of Velocity in Terms of Angle; Use Rotations Per
     *                 Second
     * 
     */
    public void setVelocityTCFOCrpm(AngularVelocity velocity) {
        if (attached) {
            VelocityTorqueCurrentFOC output = config.velocityTorqueCurrentFOC.withVelocity(
                    (velocity.in(Units.RotationsPerSecond)));
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
            config.applyflexConfig(motor);
        }
    }

    public void toggleReverseSoftLimit(boolean enabled) {
        if (attached) {
            double threshold = config.flexConfig.SoftwareLimitSwitch.ReverseSoftLimitThreshold;
            config.configReverseSoftLimit(threshold, enabled);
            config.applyflexConfig(motor);
        }
    }

    public void toggleTorqueCurrentLimit(Current enabledLimit, boolean enabled) {
        if (attached) {
            if (enabled) {
                config.configForwardTorqueCurrentLimit(enabledLimit);
                config.configReverseTorqueCurrentLimit(enabledLimit);
                config.applyflexConfig(motor);
            } else {
                config.configForwardTorqueCurrentLimit(Units.Amps.of(300));
                config.configReverseTorqueCurrentLimit(Units.Amps.of(300));
                config.applyflexConfig(motor);
            }
        }
    }

    public static class Config {
        public String title;
        public int id;
        public SparkFlexConfig flexConfig;
        public double voltageCompSaturation; // 12V by default

        public MAXMotionConfig mmConfig;

        public MotionMagicVelocityTorqueCurrentFOC mmVelocityFOC = new MotionMagicVelocityTorqueCurrentFOC(0);
        public MotionMagicTorqueCurrentFOC mmPositionFOC = new MotionMagicTorqueCurrentFOC(0);
        public MotionMagicVelocityVoltage mmVelocityVoltage = new MotionMagicVelocityVoltage(0);
        public MotionMagicVoltage mmPositionVoltage = new MotionMagicVoltage(0);
        public MotionMagicVoltage mmPositionVoltageSlot = new MotionMagicVoltage(0).withSlot(1);

        public VoltageOut voltageControl = new VoltageOut(0);
        public VelocityVoltage velocityControl = new VelocityVoltage(0);
        public VelocityTorqueCurrentFOC velocityTorqueCurrentFOC = new VelocityTorqueCurrentFOC(0);
        public DutyCycleOut percentOutput = new DutyCycleOut(
                0); // Percent Output control using percentage of supply voltage //Should
        // normally use VoltageOut

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

        public void configSupplyCurrentLimit(Current stallLimit, Current supplyLimit) {
            flexConfig.smartCurrentLimit((int) stallLimit.in(Units.Amps), (int) supplyLimit.in(Units.Amps));
        }

        public void configStatorCurrentLimit(Current stallLimit, Current supplyLimit) {
            flexConfig.secondaryCurrentLimit((int) stallLimit.in(Units.Amps), (int) supplyLimit.in(Units.Amps));
        }

        /**
         * @param forward The maxiumum forward output [0-1]
         * @param reverse The maximum reverse output [-1-0]
         */
        public void configPeakOutput(double forward, double reverse) {
            flexConfig.closedLoop.outputRange(reverse, forward);
        }

        /**
         * @param threshold
         * @param enabled   if false, max output is set to 1
         */
        public void configForwardSoftLimit(double threshold, boolean enabled) {
            if (!enabled) {
                flexConfig.closedLoop.maxOutput(1);
            }
        }

        /**
         * @param threshold
         * @param enabled   if false, max output is set to -1
         */
        public void configReverseSoftLimit(double threshold, boolean enabled) {
            flexConfig.closedLoop.minOutput(threshold);
            if (!enabled) {
                flexConfig.closedLoop.minOutput(-1);
            }
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
            flexConfig.MotionMagic.MotionMagicCruiseVelocity = cruiseVelocity.in(Units.RotationsPerSecond);
            flexConfig.MotionMagic.MotionMagicAcceleration = acceleration;
            flexConfig.MotionMagic.MotionMagicJerk = jerk;
        }

        // This is the ratio of rotor rotations to the mechanism's output.
        // If a remote sensor is used this a ratio of sensor rotations to the
        // mechanism's output.
        public void configGearRatio(double gearRatio) {
            flexConfig.Feedback.SensorToMechanismRatio = gearRatio;
        }

        public double getGearRatio() {
            return flexConfig.Feedback.SensorToMechanismRatio;
        }

        /**
         * @param isInBrake true enables break mode, false enables coast
         */
        public void configNeutralBrakeMode(boolean setBrakeMode) {
            if (setBrakeMode) {
                flexConfig.flexConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;
            } else {
                flexConfig.MotorOutput.NeutralMode = NeutralModeValue.Coast;
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
        public void configFeedForwardGains(double kS, double kV, double kA, double kG) {
            configFeedForwardGains(0, kS, kV, kA, kG);
        }

        public void configFeedForwardGains(int slot, double kS, double kV, double kA, double kG) {
            flexConfigFeedForward(slot, kV, kA, kS, kG);
        }

        public void configFeedbackSensorSource(FeedbackSensorSourceValue source) {
            configFeedbackSensorSource(source, 0);
        }

        public void configFeedbackSensorSource(FeedbackSensorSourceValue source, double offset) {
            flexConfig.Feedback.FeedbackSensorSource = source;
            flexConfig.Feedback.FeedbackRotorOffset = offset;
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
                flexConfig.  .Slot0.GravityType = gravityType;
            } else if (slot == 1) {
                flexConfig.Slot1.GravityType = gravityType;
            } else if (slot == 2) {
                flexConfig.Slot2.GravityType = gravityType;
            } else {
                DriverStation.reportWarning("MechConfig: Invalid slot", false);
            }
        }

        // Configure the TalonFXConfiguration feed forward gains
        private void flexConfigFeedForward(int slot, double kV, double kA, double kS, double kG) {
            if (slot == 0) {
                flexConfig.Slot0.kV = kV;
                flexConfig.Slot0.kA = kA;
                flexConfig.Slot0.kS = kS;
                flexConfig.Slot0.kG = kG;
            } else if (slot == 1) {
                flexConfig.Slot1.kV = kV;
                flexConfig.Slot1.kA = kA;
                flexConfig.Slot1.kS = kS;
                flexConfig.Slot1.kG = kG;
            } else if (slot == 2) {
                flexConfig.Slot2.kV = kV;
                flexConfig.Slot2.kA = kA;
                flexConfig.Slot2.kS = kS;
                flexConfig.Slot2.kG = kG;
            } else {
                DriverStation.reportWarning("MechConfig: Invalid FeedForward slot", false);
            }
        }

        private void flexConfigVelocityPID(int slot, double kS, double kP, double kI, double kD) {
            if (slot == 0) {
                flexConfig.Slot0.kS = kS;
                flexConfig.Slot0.kP = kP;
                flexConfig.Slot0.kI = kI;
                flexConfig.Slot0.kD = kD;
            } else if (slot == 1) {
                flexConfig.Slot1.kS = kS;
                flexConfig.Slot1.kP = kP;
                flexConfig.Slot1.kI = kI;
                flexConfig.Slot1.kD = kD;
            } else if (slot == 2) {
                flexConfig.Slot2.kS = kS;
                flexConfig.Slot2.kP = kP;
                flexConfig.Slot2.kI = kI;
                flexConfig.Slot2.kD = kD;
            } else {
                DriverStation.reportWarning("MechConfig: Invalid Feedback slot", false);
            }
        }

        private void flexConfigFeedbackPID(int slot, double kP, double kI, double kD) {
            if (slot == 0) {
                flexConfig.Slot0.kP = kP;
                flexConfig.Slot0.kI = kI;
                flexConfig.Slot0.kD = kD;
            } else if (slot == 1) {
                flexConfig.Slot1.kP = kP;
                flexConfig.Slot1.kI = kI;
                flexConfig.Slot1.kD = kD;
            } else if (slot == 2) {
                flexConfig.Slot2.kP = kP;
                flexConfig.Slot2.kI = kI;
                flexConfig.Slot2.kD = kD;
            } else {
                DriverStation.reportWarning("MechConfig: Invalid Feedback slot", false);
            }
        }
    }
}