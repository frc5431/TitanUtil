// package frc.team5431.titan.core.misc;

// import java.util.HashMap;
// import java.util.Set;
// import java.util.function.Consumer;
// import java.util.function.Supplier;

// import com.pathplanner.lib.auto.AutoBuilder;
// import com.pathplanner.lib.controllers.PPHolonomicDriveController;
// import com.pathplanner.lib.path.PathPlannerPath;
// import com.pathplanner.lib.util.HolonomicPathFollowerConfig;
// import com.pathplanner.lib.util.PIDConstants;
// import com.pathplanner.lib.util.ReplanningConfig;

// import edu.wpi.first.math.geometry.Pose2d;
// import edu.wpi.first.math.geometry.Rotation2d;
// import edu.wpi.first.math.kinematics.ChassisSpeeds;
// import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
// import edu.wpi.first.wpilibj.smartdashboard.FieldObject2d;
// import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import edu.wpi.first.wpilibj2.command.Command;
// import edu.wpi.first.wpilibj2.command.Subsystem;
// import edu.wpi.first.wpilibj2.command.SubsystemBase;

// public class AutonMagic {

// public String[] paths(String[] autos) {
// return autos;
// }

// private final SendableChooser<Command> chooser = new SendableChooser<>();
// private AutoBuilder autoBuilder;

// /**
// * @param autos name of auton paths, to be replaced with file location
// * @param drivebase drivebase as returned from system
// * @param estimatedPosition estimated position of the drivebase
// * @param Odometry reset odometry, need to check
// * @param robotRelChassisSpeed desired speed of drivebase
// * @param currentChassisSpeed current speed of drivebase, need to check
// * @param maxSpeed maximum speed of auton path, can be set in GUI
// * @param driveBaseRadius drivebase radius from center in meters
// * @param initialReplanning if true, the path can be replanned at the start of
// path following if the robot is not already at the starting point
// * @param dynamicReplanning if true, the can path be replanned if the error
// grows too large or if a large error spike happens while following the path
// * @param hotUpdate if true, paths do not have to be deployed to robot to be
// updated
// */
// public AutonMagic(String[] autos, PIDConstants transPidConstants,
// PIDConstants rotPidConstants, SubsystemBase drivebase, Pose2d
// estimatedPosition,
// Consumer<Pose2d> Odometry, ChassisSpeeds robotRelChassisSpeed,
// Consumer<ChassisSpeeds> currentChassisSpeed,
// double maxSpeed, double driveBaseRadius, boolean initialReplanning, boolean
// dynamicReplanning) {

// autoBuilder = new AutoBuilder();

// autoBuilder.configureHolonomic(
// estimatedPosition,
// Odometry,
// robotRelChassisSpeed, // ChassisSpeeds supplier. MUST BE ROBOT RELATIVE
// currentChassisSpeed,
// new HolonomicPathFollowerConfig(
// transPidConstants,
// rotPidConstants,
// maxSpeed,
// driveBaseRadius,
// new ReplanningConfig()
// ),
// drivebase);

// for (String pathName : autos) {
// chooser.addOption(pathName, getAuto(pathName, 0, 0));
// }

// SmartDashboard.putData("Auton", chooser);
// }

// public Command getAuto(String pathName, double yawRot, double rot2Degree) {
// PathPlannerPath pathGroup = PathPlannerPath.fromPathFile(pathName);
// SmartDashboard.putData("Auton", chooser);

// }

// // Command setGyroCommand = new Command();
// // Rotation2d rot2d = new Rotation2d();

// @Override
// public void initialize() {
// double rotationDegree = rot2Degree;
// }

// @Override
// public boolean isFinished() {
// // Wait until returned value matches set value
// return Math.abs(yawRot) < 1;
// }

// @Override
// public Set<Subsystem> getRequirements() {
// return Set.of();
// //return setGyroCommand;
// // .andThen(autoBuilder.fullAuto(pathGroup));
// }

// public Command procureAuton() {
// return chooser.getSelected();
// }

// }
