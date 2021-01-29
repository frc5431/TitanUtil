package frc.team5431.titan.pathweaver;

import java.io.IOException;
import java.nio.file.Path;

import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.team5431.titan.core.misc.Logger;
import edu.wpi.first.wpilibj2.command.RamseteCommand;

/**
 * @author Ryan Hirasaki
 * 
 */
public class PathWeaver {
    private static final String NAMESPACE = "frc.team5431.titan.pathweaver";

    public static enum Status {
        UNLOADED, LOADED, ERROR
    }

    private final RamseteController ramseteController;
    private final SimpleMotorFeedforward motorFeedForward;
    private final DifferentialDriveKinematics driveKinematics;
    private final PIDController velocityPID;

    private Trajectory trajectory;
    private Status status = Status.UNLOADED;

    /**
     * @param config data for setting up math
     */
    public PathWeaver(final DriveConfig config) {
        this.motorFeedForward = new SimpleMotorFeedforward(config.ksVolts, config.kvVoltSecondsPerMeter,
                config.kaVoltSecondsSquaredPerMeter);
        this.driveKinematics = new DifferentialDriveKinematics(config.kTrackwidthMeters);

        this.ramseteController = new RamseteController(config.kRamseteB, config.kRamseteZeta);

        // Not sure why we need this but docs say so...
        this.velocityPID = new PIDController(config.kPDriveVel, 0, 0);
    }

    /**
     * @param config data for setting up math
     * @param json   path in filesystem to alternative robot path
     */
    public PathWeaver(final DriveConfig config, final String json) {
        this(config); // Call simple constructor
        loadAlternativePath(json);
    }

    /**
     * Call getCommand() for new trajectory
     * 
     * @param json path in filesystem to alternative robot patt
     */
    public void loadAlternativePath(final String json) {
        try {
            Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(json);
            this.trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
            this.status = Status.LOADED;
        } catch (IOException e) {
            Logger.ee(NAMESPACE, e);
            this.trajectory = null;
            this.status = Status.ERROR;
        }
    }

    /**
     * Generates a new command on call for playing back a path from PathWeaver
     * 
     * @param subsystem
     * @param drivebase
     * @return New Command object
     */
    public Command getCommand(Subsystem subsystem, DrivebaseCallback drivebase) {
        if (status == Status.LOADED) {
            assert (trajectory != null);
            return new RamseteCommand(this.trajectory, // Loaded Path
                    drivebase::getPose, // callback
                    this.ramseteController, // IDK
                    this.motorFeedForward, // IDK
                    this.driveKinematics, // IDK
                    drivebase::getWheelSpeeds, // callback
                    this.velocityPID, // PID
                    this.velocityPID, // PID
                    drivebase::tankDriveVolts, // callback
                    subsystem).andThen(() -> drivebase.tankDriveVolts(0, 0));
        } else if (status == Status.ERROR) {
            Logger.e("Cannot run unlocatable path");
        }

        return new CommandBase() {
        };
    }

    public Status getStatus() {
        return status;
    }
}
