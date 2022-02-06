package frc.team5431.titan.mimic;

import frc.team5431.titan.core.joysticks.Xbox;
import frc.team5431.titan.core.misc.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static frc.team5431.titan.mimic.Stepper.mimicFile;

/**
 * @author David Smerkous
 * @deprecated since 2022.3.1.0
 */
@Deprecated
public class Observer {
    private static FileOutputStream log = null;

    private static boolean homed = false;
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

    public static void addStep(final DrivebaseAnalyzer drivebase, final double driveVals[], final Xbox xbox) {
        try {
            final double lDistance = drivebase.getLeftDistance();
            final double rDistance = drivebase.getRightDistance();
            final float angle = drivebase.getHeading();

            final double leftPower = driveVals[0];
            final double rightPower = driveVals[1];
            boolean home = xbox.getRawButton(Xbox.Button.START);

            if (home && !homed) {
                drivebase.setHome();
            }

            if (!saved)
                log.write(new Stepper(lDistance, rDistance, angle, leftPower, rightPower, home).toString()
                        .getBytes(StandardCharsets.UTF_16));

            homed = home;
        } catch (Exception e) {
            Logger.ee("Mimic", e);
        }
    }

    public static void saveMimic() {
        try {
            if (log == null || saved)
                return;
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