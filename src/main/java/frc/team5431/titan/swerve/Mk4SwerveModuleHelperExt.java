package frc.team5431.titan.swerve;

import com.swervedrivespecialties.swervelib.*;
import com.swervedrivespecialties.swervelib.ctre.*;
import com.swervedrivespecialties.swervelib.rev.*;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;

public final class Mk4SwerveModuleHelperExt {
    private Mk4SwerveModuleHelperExt() {
    }

    private static DriveControllerFactory<?, Integer> getFalcon500DriveFactory(
            Mk4ModuleConfigurationExt configuration) {
        return new Falcon500DriveControllerFactoryBuilder()
                .withVoltageCompensation(configuration.getNominalVoltage())
                .withCurrentLimit(configuration.getDriveCurrentLimit())
                .build();
    }

    private static SteerControllerFactory<?, Falcon500SteerConfiguration<CanCoderAbsoluteConfiguration>> getFalcon500SteerFactory(
            Mk4ModuleConfigurationExt configuration) {
        return new Falcon500SteerControllerFactoryBuilder()
                .withVoltageCompensation(configuration.getNominalVoltage())
                .withPidConstants(configuration.getSteerKP(), configuration.getSteerKI(), configuration.getSteerKD())
                .withMotionMagic(configuration.getSteerMMkV(), configuration.getSteerMMkA(),
                        configuration.getSteerMMkS())
                .withCurrentLimit(configuration.getSteerCurrentLimit())
                .build(new CanCoderFactoryBuilder()
                        .withReadingUpdatePeriod(100)
                        .build());
    }

    private static DriveControllerFactory<?, Integer> getNeoDriveFactory(Mk4ModuleConfigurationExt configuration) {
        return new NeoDriveControllerFactoryBuilder()
                .withVoltageCompensation(configuration.getNominalVoltage())
                .withCurrentLimit(configuration.getDriveCurrentLimit())
                .build();
    }

    private static SteerControllerFactory<?, NeoSteerConfiguration<CanCoderAbsoluteConfiguration>> getNeoSteerFactory(
            Mk4ModuleConfigurationExt configuration) {
        return new NeoSteerControllerFactoryBuilder()
                .withVoltageCompensation(configuration.getNominalVoltage())
                .withPidConstants(configuration.getSteerKP(), configuration.getSteerKI(), configuration.getSteerKD())
                .withCurrentLimit(configuration.getSteerCurrentLimit())
                .build(new CanCoderFactoryBuilder()
                        .withReadingUpdatePeriod(100)
                        .build());
    }

    /**
     * Creates a Mk4 swerve module that uses Falcon 500s for driving and steering.
     * Module information is displayed in the specified ShuffleBoard container.
     *
     * @param container        The container to display module information in.
     * @param configuration    Module configuration parameters to use.
     * @param gearRatio        The gearing configuration the module is in.
     * @param driveMotorPort   The CAN ID of the drive Falcon 500.
     * @param steerMotorPort   The CAN ID of the steer Falcon 500.
     * @param steerEncoderPort The CAN ID of the steer CANCoder.
     * @param steerOffset      The offset of the CANCoder in radians.
     * @return The configured swerve module.
     */
    public static SwerveModule createFalcon500(
            ShuffleboardLayout container,
            Mk4ModuleConfigurationExt configuration,
            GearRatio gearRatio,
            int driveMotorPort,
            int steerMotorPort,
            int steerEncoderPort,
            double steerOffset) {
        return new SwerveModuleFactory<>(
                gearRatio.getConfiguration(),
                getFalcon500DriveFactory(configuration),
                getFalcon500SteerFactory(configuration)).create(
                        container,
                        driveMotorPort,
                        new Falcon500SteerConfiguration<>(
                                steerMotorPort,
                                new CanCoderAbsoluteConfiguration(steerEncoderPort, steerOffset)));
    }

    /**
     * Creates a Mk4 swerve module that uses Falcon 500s for driving and steering.
     * Module information is displayed in the specified ShuffleBoard container.
     *
     * @param container        The container to display module information in.
     * @param gearRatio        The gearing configuration the module is in.
     * @param driveMotorPort   The CAN ID of the drive Falcon 500.
     * @param steerMotorPort   The CAN ID of the steer Falcon 500.
     * @param steerEncoderPort The CAN ID of the steer CANCoder.
     * @param steerOffset      The offset of the CANCoder in radians.
     * @return The configured swerve module.
     */
    public static SwerveModule createFalcon500(
            ShuffleboardLayout container,
            GearRatio gearRatio,
            int driveMotorPort,
            int steerMotorPort,
            int steerEncoderPort,
            double steerOffset) {
        return createFalcon500(container, Mk4ModuleConfigurationExt.getDefaultFalcon500(), gearRatio,
                driveMotorPort, steerMotorPort,
                steerEncoderPort, steerOffset);
    }

    /**
     * Creates a Mk4 swerve module that uses Falcon 500s for driving and steering.
     *
     * @param configuration    Module configuration parameters to use.
     * @param gearRatio        The gearing configuration the module is in.
     * @param driveMotorPort   The CAN ID of the drive Falcon 500.
     * @param steerMotorPort   The CAN ID of the steer Falcon 500.
     * @param steerEncoderPort The CAN ID of the steer CANCoder.
     * @param steerOffset      The offset of the CANCoder in radians.
     * @return The configured swerve module.
     */
    public static SwerveModule createFalcon500(
            Mk4ModuleConfigurationExt configuration,
            GearRatio gearRatio,
            int driveMotorPort,
            int steerMotorPort,
            int steerEncoderPort,
            double steerOffset) {
        return new SwerveModuleFactory<>(
                gearRatio.getConfiguration(),
                getFalcon500DriveFactory(configuration),
                getFalcon500SteerFactory(configuration)).create(
                        driveMotorPort,
                        new Falcon500SteerConfiguration<>(
                                steerMotorPort,
                                new CanCoderAbsoluteConfiguration(steerEncoderPort, steerOffset)));
    }

    /**
     * Creates a Mk4 swerve module that uses Falcon 500s for driving and steering.
     *
     * @param gearRatio        The gearing configuration the module is in.
     * @param driveMotorPort   The CAN ID of the drive Falcon 500.
     * @param steerMotorPort   The CAN ID of the steer Falcon 500.
     * @param steerEncoderPort The CAN ID of the steer CANCoder.
     * @param steerOffset      The offset of the CANCoder in radians.
     * @return The configured swerve module.
     */
    public static SwerveModule createFalcon500(
            GearRatio gearRatio,
            int driveMotorPort,
            int steerMotorPort,
            int steerEncoderPort,
            double steerOffset) {
        return createFalcon500(Mk4ModuleConfigurationExt.getDefaultFalcon500(), gearRatio, driveMotorPort,
                steerMotorPort,
                steerEncoderPort, steerOffset);
    }

    /**
     * Creates a Mk4 swerve module that uses NEOs for driving and steering.
     * Module information is displayed in the specified ShuffleBoard container.
     *
     * @param container        The container to display module information in.
     * @param configuration    Module configuration parameters to use.
     * @param gearRatio        The gearing configuration the module is in.
     * @param driveMotorPort   The CAN ID of the drive NEO.
     * @param steerMotorPort   The CAN ID of the steer NEO.
     * @param steerEncoderPort The CAN ID of the steer CANCoder.
     * @param steerOffset      The offset of the CANCoder in radians.
     * @return The configured swerve module.
     */
    public static SwerveModule createNeo(
            ShuffleboardLayout container,
            Mk4ModuleConfigurationExt configuration,
            GearRatio gearRatio,
            int driveMotorPort,
            int steerMotorPort,
            int steerEncoderPort,
            double steerOffset) {
        return new SwerveModuleFactory<>(
                gearRatio.getConfiguration(),
                getNeoDriveFactory(configuration),
                getNeoSteerFactory(configuration)).create(
                        container,
                        driveMotorPort,
                        new NeoSteerConfiguration<>(
                                steerMotorPort,
                                new CanCoderAbsoluteConfiguration(steerEncoderPort, steerOffset)));
    }

    /**
     * Creates a Mk4 swerve module that uses NEOs for driving and steering.
     * Module information is displayed in the specified ShuffleBoard container.
     *
     * @param container        The container to display module information in.
     * @param gearRatio        The gearing configuration the module is in.
     * @param driveMotorPort   The CAN ID of the drive NEO.
     * @param steerMotorPort   The CAN ID of the steer NEO.
     * @param steerEncoderPort The CAN ID of the steer CANCoder.
     * @param steerOffset      The offset of the CANCoder in radians.
     * @return The configured swerve module.
     */
    public static SwerveModule createNeo(
            ShuffleboardLayout container,
            GearRatio gearRatio,
            int driveMotorPort,
            int steerMotorPort,
            int steerEncoderPort,
            double steerOffset) {
        return createNeo(container, Mk4ModuleConfigurationExt.getDefaultNEO(), gearRatio, driveMotorPort,
                steerMotorPort,
                steerEncoderPort, steerOffset);
    }

    /**
     * Creates a Mk4 swerve module that uses NEOs for driving and steering.
     *
     * @param configuration    Module configuration parameters to use.
     * @param gearRatio        The gearing configuration the module is in.
     * @param driveMotorPort   The CAN ID of the drive NEO.
     * @param steerMotorPort   The CAN ID of the steer NEO.
     * @param steerEncoderPort The CAN ID of the steer CANCoder.
     * @param steerOffset      The offset of the CANCoder in radians.
     * @return The configured swerve module.
     */
    public static SwerveModule createNeo(
            Mk4ModuleConfigurationExt configuration,
            GearRatio gearRatio,
            int driveMotorPort,
            int steerMotorPort,
            int steerEncoderPort,
            double steerOffset) {
        return new SwerveModuleFactory<>(
                gearRatio.getConfiguration(),
                getNeoDriveFactory(configuration),
                getNeoSteerFactory(configuration)).create(
                        driveMotorPort,
                        new NeoSteerConfiguration<>(
                                steerMotorPort,
                                new CanCoderAbsoluteConfiguration(steerEncoderPort, steerOffset)));
    }

    /**
     * Creates a Mk4 swerve module that uses NEOs for driving and steering.
     *
     * @param gearRatio        The gearing configuration the module is in.
     * @param driveMotorPort   The CAN ID of the drive NEO.
     * @param steerMotorPort   The CAN ID of the steer NEO.
     * @param steerEncoderPort The CAN ID of the steer CANCoder.
     * @param steerOffset      The offset of the CANCoder in radians.
     * @return The configured swerve module.
     */
    public static SwerveModule createNeo(
            GearRatio gearRatio,
            int driveMotorPort,
            int steerMotorPort,
            int steerEncoderPort,
            double steerOffset) {
        return createNeo(Mk4ModuleConfigurationExt.getDefaultNEO(), gearRatio, driveMotorPort, steerMotorPort,
                steerEncoderPort,
                steerOffset);
    }

    /**
     * Creates a Mk4 swerve module that uses a Falcon 500 for driving and a NEO for
     * steering.
     * Module information is displayed in the specified ShuffleBoard container.
     *
     * @param container        The container to display module information in.
     * @param configuration    Module configuration parameters to use.
     * @param gearRatio        The gearing configuration the module is in.
     * @param driveMotorPort   The CAN ID of the drive Falcon 500.
     * @param steerMotorPort   The CAN ID of the steer NEO.
     * @param steerEncoderPort The CAN ID of the steer CANCoder.
     * @param steerOffset      The offset of the CANCoder in radians.
     * @return The configured swerve module.
     */
    public static SwerveModule createFalcon500Neo(
            ShuffleboardLayout container,
            Mk4ModuleConfigurationExt configuration,
            GearRatio gearRatio,
            int driveMotorPort,
            int steerMotorPort,
            int steerEncoderPort,
            double steerOffset) {
        return new SwerveModuleFactory<>(
                gearRatio.getConfiguration(),
                getFalcon500DriveFactory(configuration),
                getNeoSteerFactory(configuration)).create(
                        container,
                        driveMotorPort,
                        new NeoSteerConfiguration<>(
                                steerMotorPort,
                                new CanCoderAbsoluteConfiguration(steerEncoderPort, steerOffset)));
    }

    /**
     * Creates a Mk4 swerve module that uses a Falcon 500 for driving and a NEO for
     * steering.
     * Module information is displayed in the specified ShuffleBoard container.
     *
     * @param container        The container to display module information in.
     * @param gearRatio        The gearing configuration the module is in.
     * @param driveMotorPort   The CAN ID of the drive Falcon 500.
     * @param steerMotorPort   The CAN ID of the steer NEO.
     * @param steerEncoderPort The CAN ID of the steer CANCoder.
     * @param steerOffset      The offset of the CANCoder in radians.
     * @return The configured swerve module.
     */
    public static SwerveModule createFalcon500Neo(
            ShuffleboardLayout container,
            GearRatio gearRatio,
            int driveMotorPort,
            int steerMotorPort,
            int steerEncoderPort,
            double steerOffset) {
        return createFalcon500Neo(container, Mk4ModuleConfigurationExt.getDefaultNEO(), gearRatio, driveMotorPort,
                steerMotorPort,
                steerEncoderPort, steerOffset);
    }

    /**
     * Creates a Mk4 swerve module that uses a Falcon 500 for driving and a NEO for
     * steering.
     *
     * @param configuration    Module configuration parameters to use.
     * @param gearRatio        The gearing configuration the module is in.
     * @param driveMotorPort   The CAN ID of the drive Falcon 500.
     * @param steerMotorPort   The CAN ID of the steer NEO.
     * @param steerEncoderPort The CAN ID of the steer CANCoder.
     * @param steerOffset      The offset of the CANCoder in radians.
     * @return The configured swerve module.
     */
    public static SwerveModule createFalcon500Neo(
            Mk4ModuleConfigurationExt configuration,
            GearRatio gearRatio,
            int driveMotorPort,
            int steerMotorPort,
            int steerEncoderPort,
            double steerOffset) {
        return new SwerveModuleFactory<>(
                gearRatio.getConfiguration(),
                getFalcon500DriveFactory(configuration),
                getNeoSteerFactory(configuration)).create(
                        driveMotorPort,
                        new NeoSteerConfiguration<>(
                                steerMotorPort,
                                new CanCoderAbsoluteConfiguration(steerEncoderPort, steerOffset)));
    }

    /**
     * Creates a Mk4 swerve module that uses a Falcon 500 for driving and a NEO for
     * steering.
     *
     * @param gearRatio        The gearing configuration the module is in.
     * @param driveMotorPort   The CAN ID of the drive Falcon 500.
     * @param steerMotorPort   The CAN ID of the steer NEO.
     * @param steerEncoderPort The CAN ID of the steer CANCoder.
     * @param steerOffset      The offset of the CANCoder in radians.
     * @return The configured swerve module.
     */
    public static SwerveModule createFalcon500Neo(
            GearRatio gearRatio,
            int driveMotorPort,
            int steerMotorPort,
            int steerEncoderPort,
            double steerOffset) {
        return createFalcon500Neo(Mk4ModuleConfigurationExt.getDefaultNEO(), gearRatio, driveMotorPort, steerMotorPort,
                steerEncoderPort, steerOffset);
    }

    /**
     * Creates a Mk4 swerve module that uses a NEO for driving and a Falcon 500 for
     * steering.
     * Module information is displayed in the specified ShuffleBoard container.
     *
     * @param container        The container to display module information in.
     * @param configuration    Module configuration parameters to use.
     * @param gearRatio        The gearing configuration the module is in.
     * @param driveMotorPort   The CAN ID of the drive NEO.
     * @param steerMotorPort   The CAN ID of the steer Falcon 500.
     * @param steerEncoderPort The CAN ID of the steer CANCoder.
     * @param steerOffset      The offset of the CANCoder in radians.
     * @return The configured swerve module.
     */
    public static SwerveModule createNeoFalcon500(
            ShuffleboardLayout container,
            Mk4ModuleConfigurationExt configuration,
            GearRatio gearRatio,
            int driveMotorPort,
            int steerMotorPort,
            int steerEncoderPort,
            double steerOffset) {
        return new SwerveModuleFactory<>(
                gearRatio.getConfiguration(),
                getNeoDriveFactory(configuration),
                getFalcon500SteerFactory(configuration)).create(
                        container,
                        driveMotorPort,
                        new Falcon500SteerConfiguration<>(
                                steerMotorPort,
                                new CanCoderAbsoluteConfiguration(steerEncoderPort, steerOffset)));
    }

    /**
     * Creates a Mk4 swerve module that uses a NEO for driving and a Falcon 500 for
     * steering.
     * Module information is displayed in the specified ShuffleBoard container.
     *
     * @param container        The container to display module information in.
     * @param gearRatio        The gearing configuration the module is in.
     * @param driveMotorPort   The CAN ID of the drive NEO.
     * @param steerMotorPort   The CAN ID of the steer Falcon 500.
     * @param steerEncoderPort The CAN ID of the steer CANCoder.
     * @param steerOffset      The offset of the CANCoder in radians.
     * @return The configured swerve module.
     */
    public static SwerveModule createNeoFalcon500(
            ShuffleboardLayout container,
            GearRatio gearRatio,
            int driveMotorPort,
            int steerMotorPort,
            int steerEncoderPort,
            double steerOffset) {
        return createNeoFalcon500(container, Mk4ModuleConfigurationExt.getDefaultFalcon500(), gearRatio, driveMotorPort,
                steerMotorPort,
                steerEncoderPort, steerOffset);
    }

    /**
     * Creates a Mk4 swerve module that uses a NEO for driving and a Falcon 500 for
     * steering.
     *
     * @param configuration    Module configuration parameters to use.
     * @param gearRatio        The gearing configuration the module is in.
     * @param driveMotorPort   The CAN ID of the drive NEO.
     * @param steerMotorPort   The CAN ID of the steer Falcon 500.
     * @param steerEncoderPort The CAN ID of the steer CANCoder.
     * @param steerOffset      The offset of the CANCoder in radians.
     * @return The configured swerve module.
     */
    public static SwerveModule createNeoFalcon500(
            Mk4ModuleConfigurationExt configuration,
            GearRatio gearRatio,
            int driveMotorPort,
            int steerMotorPort,
            int steerEncoderPort,
            double steerOffset) {
        return new SwerveModuleFactory<>(
                gearRatio.getConfiguration(),
                getNeoDriveFactory(configuration),
                getFalcon500SteerFactory(configuration)).create(
                        driveMotorPort,
                        new Falcon500SteerConfiguration<>(
                                steerMotorPort,
                                new CanCoderAbsoluteConfiguration(steerEncoderPort, steerOffset)));
    }

    /**
     * Creates a Mk4 swerve module that uses a NEO for driving and a Falcon 500 for
     * steering.
     *
     * @param gearRatio        The gearing configuration the module is in.
     * @param driveMotorPort   The CAN ID of the drive NEO.
     * @param steerMotorPort   The CAN ID of the steer Falcon 500.
     * @param steerEncoderPort The CAN ID of the steer CANCoder.
     * @param steerOffset      The offset of the CANCoder in radians.
     * @return The configured swerve module.
     */
    public static SwerveModule createNeoFalcon500(
            GearRatio gearRatio,
            int driveMotorPort,
            int steerMotorPort,
            int steerEncoderPort,
            double steerOffset) {
        return createNeoFalcon500(Mk4ModuleConfigurationExt.getDefaultFalcon500(), gearRatio, driveMotorPort,
                steerMotorPort,
                steerEncoderPort, steerOffset);
    }

    public enum GearRatio {
        L1(SdsModuleConfigurations.MK4_L1),
        L2(SdsModuleConfigurations.MK4_L2),
        L3(SdsModuleConfigurations.MK4_L3),
        L4(SdsModuleConfigurations.MK4_L4);

        private final ModuleConfiguration configuration;

        GearRatio(ModuleConfiguration configuration) {
            this.configuration = configuration;
        }

        public ModuleConfiguration getConfiguration() {
            return configuration;
        }
    }
}
