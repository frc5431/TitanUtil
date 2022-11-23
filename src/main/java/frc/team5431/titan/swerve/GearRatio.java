package frc.team5431.titan.swerve;

public record GearRatio(double wheelDiameter, double driveReduction, boolean driveInverted, double steerReduction,
        boolean steerInverted) {

    public static final GearRatio MK3_STANDARD = new GearRatio(
            0.1016,
            (14.0 / 50.0) * (28.0 / 16.0) * (15.0 / 60.0),
            true,
            (15.0 / 32.0) * (10.0 / 60.0),
            true);
    public static final GearRatio MK3_FAST = new GearRatio(
            0.1016,
            (16.0 / 48.0) * (28.0 / 16.0) * (15.0 / 60.0),
            true,
            (15.0 / 32.0) * (10.0 / 60.0),
            true);

    public static final GearRatio MK4_L1 = new GearRatio(
            0.10033,
            (14.0 / 50.0) * (25.0 / 19.0) * (15.0 / 45.0),
            true,
            (15.0 / 32.0) * (10.0 / 60.0),
            true);
    public static final GearRatio MK4_L2 = new GearRatio(
            0.10033,
            (14.0 / 50.0) * (27.0 / 17.0) * (15.0 / 45.0),
            true,
            (15.0 / 32.0) * (10.0 / 60.0),
            true);
    public static final GearRatio MK4_L3 = new GearRatio(
            0.10033,
            (14.0 / 50.0) * (28.0 / 16.0) * (15.0 / 45.0),
            true,
            (15.0 / 32.0) * (10.0 / 60.0),
            true);
    public static final GearRatio MK4_L4 = new GearRatio(
            0.10033,
            (16.0 / 48.0) * (28.0 / 16.0) * (15.0 / 45.0),
            true,
            (15.0 / 32.0) * (10.0 / 60.0),
            true);

    public static final GearRatio MK4I_L1 = new GearRatio(
            0.10033,
            (14.0 / 50.0) * (25.0 / 19.0) * (15.0 / 45.0),
            true,
            (14.0 / 50.0) * (10.0 / 60.0),
            false);
    public static final GearRatio MK4I_L2 = new GearRatio(
            0.10033,
            (14.0 / 50.0) * (27.0 / 17.0) * (15.0 / 45.0),
            true,
            (14.0 / 50.0) * (10.0 / 60.0),
            false);
    public static final GearRatio MK4I_L3 = new GearRatio(
            0.10033,
            (14.0 / 50.0) * (28.0 / 16.0) * (15.0 / 45.0),
            true,
            (14.0 / 50.0) * (10.0 / 60.0),
            false);
}
