package frc.team5431.titan.mimic;

import frc.team5431.titan.core.components.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import static frc.team5431.titan.mimic.Stepper.mimicFile;

public class Observer {
    private static FileOutputStream log = null;
    /*
    private static boolean homed = false;
    private static boolean switched = false;
    */
    private static boolean saved = true;

    public static void prepare(final String fileName) {
        final String fName = String.format(mimicFile, fileName);
        try {
            if (Files.deleteIfExists(new File(fName).toPath())) {
                Logger.e("Deleted previous pathfinding data");
            }
            log = new FileOutputStream(fName);
            saved = false;
            Logger.l("Created new pathfinding file");
        } catch (IOException e) {
            Logger.ee("Mimic", e);
        }
    }

    /*public static void addStep(final Robot robot, final double driveVals[]) {
        try {
            final double lDistance = robot.getDriveBase().getLeftDistance();
            final double rDistance = robot.getDriveBase().getRightDistance();
            final float angle = robot.getDriveBase().getNavx().getYaw();
            final double leftPower = driveVals[0];
            final double rightPower = driveVals[1];
            boolean home = robot.getTeleop().getXbox().getRawButton(Titan.Xbox.Button.START);
            boolean switchShoot = robot.getTeleop().getXbox().getRawAxis(Titan.Xbox.Axis.TRIGGER_RIGHT) > 0.5;

            if(home && !homed) {
                robot.getDriveBase().setHome();
            }

            if(switchShoot && !switched) {
                robot.getIntake().shootCube();
                switchShoot = true;
            } else switchShoot = false;

            //@TODO FIX THE CRAP
            if(robot.getIntake().getState() == IntakeState.STAY_UP || switchShoot) {
                if(!saved) log.write(new Stepper(lDistance, rDistance, angle, leftPower, rightPower, home, switchShoot).toString().getBytes(StandardCharsets.US_ASCII));
            }

            homed = home;
            switched = switchShoot;
        } catch (Exception e) {
            Titan.ee("Mimic", e);
        }
    }*/

    public static void saveMimic() {
        try {
            if (log == null || saved) return;
            Logger.l("Finished observing");
            log.flush();
            log.close();
            saved = true;
            Logger.l("Saved the mimic data");
        } catch (IOException e) {
            Logger.ee("Mimic", e);
        }
    }
}