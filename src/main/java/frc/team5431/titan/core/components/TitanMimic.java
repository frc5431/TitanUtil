package frc.team5431.titan.core.components;

import java.util.List;
import java.util.EnumMap;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.function.Function;

public class TitanMimic {
    public static enum PropertyType {
        DOUBLE(Double::parseDouble), INTEGER(Integer::parseInt), BOOLEAN(Boolean::parseBoolean);

        final Function<String, Object> converter;

        private PropertyType(final Function<String, Object> converter) {
            this.converter = converter;
        }

        public Object convert(final String in) {
            return converter.apply(in);
        }
    }

    public static interface PropertyValue<R> {
        public PropertyType getType();

        public Object get(final R robot);
    }

    public static final String DEFAULT_MIMIC_DIRECTORY = "/media/sda1/";
    public static final String DEFAULT_MIMIC_PATH = DEFAULT_MIMIC_DIRECTORY + "%s.mimic";

    public static class Step<PV extends Enum<PV> & PropertyValue<?>> {
        public EnumMap<PV, Object> values;

        public Step(final EnumMap<PV, Object> values) {
            this.values = values;
        }

        public Step(final String toParse, final Class<PV> clazz) {
            try {
                values = new EnumMap<>(clazz);
                final String parts[] = toParse.split(",");
                for (final PV key : clazz.getEnumConstants()) {
                    values.put(key, key.getType().convert(parts[key.ordinal()]));
                }
            } catch (Exception e) {
                TitanLogger.ee("MimicParse", e);
            }
        }

        public EnumMap<PV, Object> getValues() {
            return values;
        }

        public Object get(final PV value) {
            return values.get(value);
        }

        public double getDouble(final PV value) {
            return (double) get(value);
        }

        public boolean getBoolean(final PV value) {
            return (boolean) get(value);
        }

        public int getInteger(final PV value) {
            return (Integer) get(value);
        }

        public String toString() {
            final StringBuilder builder = new StringBuilder();
            for (final Object obj : values.values()) {
                builder.append(obj.toString()).append(",");
            }
            builder.append(System.lineSeparator());
            return builder.toString();
        }
    }

    public static class Observer<R, PV extends Enum<PV> & PropertyValue<R>> {
        private FileOutputStream log = null;

        public void prepare(final String fileName) {
            final String fName = String.format(DEFAULT_MIMIC_PATH, fileName);
            try {
                if (Files.deleteIfExists(new File(fName).toPath())) {
                    TitanLogger.e("Deleted previous Mimic data");
                }
                log = new FileOutputStream(fName);
                TitanLogger.l("Created new Mimic file");
            } catch (IOException e) {
                TitanLogger.ee("Mimic", e);
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
                TitanLogger.ee("Mimic", e);
            }
        }

        public boolean isRecording() {
            return log != null;
        }

        public boolean save() {
            try {
                if (!isRecording())
                    return false;
                TitanLogger.l("Finished observing");
                log.flush();
                log.close();
                log = null;
                TitanLogger.l("Saved the Mimic data");
                return true;
            } catch (IOException e) {
                TitanLogger.ee("Mimic", e);
            }
            return false;
        }
    }

    public static <PV extends Enum<PV> & PropertyValue<?>> List<Step<PV>> load(final String fileName,
            final Class<PV> clazz) {
        final ArrayList<Step<PV>> pathData = new ArrayList<>();
        final String fName = String.format(DEFAULT_MIMIC_PATH, fileName);
        try (final BufferedReader reader = new BufferedReader(new FileReader(fName))) {
            TitanLogger.l("Loading the Mimic file " + fileName);
            if (!Files.exists(new File(fName).toPath())) {
                TitanLogger.e("The requested Mimic data was not found");
            }

            Step<PV> lastStep = null;
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    pathData.add(lastStep = new Step<PV>(line, clazz));
                } catch (Exception e) {
                    TitanLogger.ee("MimicData", e);
                }
            }

            if (lastStep != null) {
                for (int i = 0; i < 25; ++i) {
                    pathData.add(lastStep);
                }
            }

            TitanLogger.l("Loaded the Mimic file");
        } catch (IOException e) {
            TitanLogger.ee("Mimic", e);
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
