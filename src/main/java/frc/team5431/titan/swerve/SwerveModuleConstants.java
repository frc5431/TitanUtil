package frc.team5431.titan.swerve;

import edu.wpi.first.math.geometry.Translation2d;

public record SwerveModuleConstants(int moduleNum, Translation2d centerOffset, CanDevice driveMotor,
        CanDevice steerMotor, CanDevice encoder, double encoderOffset) {
}
