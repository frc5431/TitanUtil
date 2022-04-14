package frc.team5431.titan.core.mimic;

import java.util.List;
import java.util.EnumMap;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import frc.team5431.titan.core.misc.Logger;

/**
 * Mimic robot behavior with generated "steps"
 * 
 * @author Ryan Hirasaki
 * @author David Smerkous
 * @author Liav Turkia
 */
public class Mimic {

    public static final String DEFAULT_MIMIC_DIRECTORY = "/media/sda1/";
    public static final String DEFAULT_MIMIC_PATH = DEFAULT_MIMIC_DIRECTORY + "%s.mimic";

    public static <PV extends Enum<PV> & PropertyValue<?>> List<Step<PV>> load(final String fileName,
            final Class<PV> clazz) {
        final ArrayList<Step<PV>> pathData = new ArrayList<>();
        final String fName = String.format(DEFAULT_MIMIC_PATH, fileName);
        try (final BufferedReader reader = new BufferedReader(new FileReader(fName))) {
            Logger.l("Loading the Mimic file " + fileName);
            if (!Files.exists(new File(fName).toPath())) {
                Logger.e("The requested Mimic data was not found");
            }

            Step<PV> lastStep = null;
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    pathData.add(lastStep = new Step<PV>(line, clazz));
                } catch (Exception e) {
                    Logger.ee("MimicData", e);
                }
            }

            if (lastStep != null) {
                for (int i = 0; i < 25; ++i) {
                    pathData.add(lastStep);
                }
            }

            Logger.l("Loaded the Mimic file");
        } catch (IOException e) {
            Logger.ee("Mimic", e);
        }

        return pathData;
    }

    public static <PV extends Enum<PV> & PropertyValue<?>> List<Step<PV>> optimize(final List<Step<PV>> in,
            final PV leftDistance, final PV rightDistance, final PV leftPower, final PV rightPower,
            final double tolerance) {
        if (in == null || in.isEmpty()) {
            return List.of();
        } else if (in.size() == 1) {
            return in;
        }

        final List<Step<PV>> out = new ArrayList<>();
        Step<PV> lastStep = in.get(0);
        double drivePowerLeft = lastStep.getDouble(leftPower);
        double drivePowerRight = lastStep.getDouble(rightPower);
        for (int i = 1; i < in.size(); ++i) {
            final Step<PV> step = in.get(i);
            final double stepLeftPower = step.getDouble(leftPower);
            final double stepRightPower = step.getDouble(rightPower);

            final double deltaLeft = Math.abs(step.getDouble(leftDistance) - lastStep.getDouble(leftDistance));
            final double deltaRight = Math.abs(step.getDouble(rightDistance) - lastStep.getDouble(rightDistance));
            drivePowerLeft = (drivePowerLeft + stepLeftPower) / 2.0;
            drivePowerRight = (drivePowerRight + stepRightPower) / 2.0;

            if (/* i > in.size() - 20 || */deltaLeft >= tolerance || deltaRight >= tolerance) {
                final EnumMap<PV, Object> newValues = step.getValues();
                newValues.put(leftPower, drivePowerLeft);
                newValues.put(rightPower, drivePowerRight);

                final Step<PV> newStep = new Step<>(newValues);
                out.add(newStep);
                lastStep = newStep;

                drivePowerLeft = stepLeftPower;
                drivePowerRight = stepRightPower;
            }
        }
        return out;
    }
}
