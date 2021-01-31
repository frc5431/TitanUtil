import static org.junit.Assert.*;
import org.junit.*;

import frc.team5431.titan.pathfinder.DriveConfig;

public class PathFinderConstBuilder {

    // Use Random values
    public static final double ksVolts = Double.MAX_VALUE;
    public static final double kvVoltSecondsPerMeter = Double.MIN_VALUE;
    public static final double kaVoltSecondsSquaredPerMeter = Double.MIN_NORMAL;
    public static final double kTrackwidthMeters = Double.NEGATIVE_INFINITY;
    public static final double kRamseteB = Double.NaN;
    public static final double kRamseteZeta = Double.POSITIVE_INFINITY;
    public static final double kPDriveVel = 6;

    @Test
    public void builder() {
        DriveConfig cfg = new DriveConfig.Builder() //
                .setVolts(ksVolts) //
                .setVoltsSpeed(kvVoltSecondsPerMeter) //
                .setVoltsAccel(kaVoltSecondsSquaredPerMeter) //
                .setTrackwidth(kTrackwidthMeters) //
                .setRamseteB(kRamseteB) //
                .setRamseteZeta(kRamseteZeta) //
                .setPDriveVel(kPDriveVel) //
                .build();
        assertEquals(cfg.ksVolts, ksVolts, 0);
        assertEquals(cfg.kvVoltSecondsPerMeter, kvVoltSecondsPerMeter, 0);
        assertEquals(cfg.kaVoltSecondsSquaredPerMeter, kaVoltSecondsSquaredPerMeter, 0);
        assertEquals(cfg.kTrackwidthMeters, kTrackwidthMeters, 0);
        assertEquals(cfg.kRamseteB, kRamseteB, 0);
        assertEquals(cfg.kRamseteZeta, kRamseteZeta, 0);
        assertEquals(cfg.kPDriveVel, kPDriveVel, 0);
    }
}
