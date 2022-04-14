package frc.team5431.titan.core.mimic;

import java.io.FileOutputStream;
import java.util.EnumMap;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import frc.team5431.titan.core.misc.Logger;

/**
 */
public class Observer<R, PV extends Enum<PV> & PropertyValue<R>> {
    private FileOutputStream log = null;

    public void prepare(final String fileName) {
        final String fName = String.format(Mimic.DEFAULT_MIMIC_PATH, fileName);
        try {
            if (Files.deleteIfExists(new File(fName).toPath())) {
                Logger.e("Deleted previous Mimic data");
            }
            log = new FileOutputStream(fName);
            Logger.l("Created new Mimic file");
        } catch (IOException e) {
            Logger.ee("Mimic", e);
        }
    }

    public void addStep(final R robot, final Class<PV> clazz) {
        try {
            final Step<PV> step = new Step<PV>(new EnumMap<>(clazz));
            for (final PV key : clazz.getEnumConstants()) {
                step.values.put(key, key.get(robot));
            }

            if (log != null)
                log.write(step.toString().getBytes(StandardCharsets.US_ASCII));
        } catch (Exception e) {
            Logger.ee("Mimic", e);
        }
    }

    public boolean isRecording() {
        return log != null;
    }

    public boolean save() {
        try {
            if (!isRecording())
                return false;
            Logger.l("Finished observing");
            log.flush();
            log.close();
            log = null;
            Logger.l("Saved the Mimic data");
            return true;
        } catch (IOException e) {
            Logger.ee("Mimic", e);
        }
        return false;
    }
}
