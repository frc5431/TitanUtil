import java.io.IOException;
import java.nio.file.Path;

import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.RamseteCommand;

/**
 * @author Ryan Hirasaki
 * 
 */
public class Characterize {
    private DifferentialDriveKinematics driveKinematics;
    private DifferentialDriveVoltageConstraint driveConstraint;
    private SimpleMotorFeedforward motorFeedForward;
    private TrajectoryConfig tragectoryConfig;
    private RamseteController ramseteController;
    private Trajectory trajectory;
    private PIDController velocityPID;

    /**
     * @param json path in filesystem to alternative robot path
     * @param config data for setting up math
     */
    public Characterize(String json, final DriveConfig config) throws IOException {
        // Throw early and not do setup if not needed
        loadAlternativePath(json);

        this.motorFeedForward = new SimpleMotorFeedforward(
            config.ksVolts,
            config.kvVoltSecondsPerMeter,
            config.kaVoltSecondsSquaredPerMeter);
        this.driveKinematics = new DifferentialDriveKinematics(config.kTrackwidthMeters);
        this.driveConstraint = new DifferentialDriveVoltageConstraint(
            this.motorFeedForward,
            this.driveKinematics,
            10); // What does this do int do?
        this.tragectoryConfig = new TrajectoryConfig(
                config.kMaxSpeedMetersPerSecond,
                config.kMaxAccelerationMetersPerSecondSquared)
            // Add kinematics to ensure max speed is actually obeyed
            .setKinematics(this.driveKinematics)
            // Apply the voltage constraint
            .addConstraint(this.driveConstraint);
        this.ramseteController = new RamseteController(config.kRamseteB, config.kRamseteZeta);
        
        // Not sure why we need this but docs say so...
        this.velocityPID = new PIDController(config.kPDriveVel, 0, 0);
    }

    /**
     * @param json path in filesystem to alternative robot path
     */
    public void loadAlternativePath(String json) throws IOException {
        Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(json);
        this.trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
    }

    public Command getCommand(Subsystem subsystem, DrivebaseCallback drivebase) {
        return new RamseteCommand(
            this.trajectory,
            drivebase::getPose, // callback
            this.ramseteController,
            this.motorFeedForward,
            this.driveKinematics,
            drivebase::getWheelSpeeds, // callback
            this.velocityPID,
            this.velocityPID,
            drivebase::tankDriveVolts, // callback
            subsystem
        ).andThen(() -> drivebase.tankDriveVolts(0, 0));
    }
}
