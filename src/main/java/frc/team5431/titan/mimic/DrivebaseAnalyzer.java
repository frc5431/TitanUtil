package frc.team5431.titan.mimic;

/**
 * @author Ryan Hirasaki
 */
interface DrivebaseAnalyzer {
    // Get Speed Functions
    public double getLeftSpeed();
    public double getRightSpeed();

    // Get Encoder Count Functions
    public double getLeftEncoderCount();
    public double getRightEncoderCount();

    // Get Encoder Distance Functions
    public double getLeftDistance();
    public double getRightDistance();

    // Get Heading
    public float getHeading();

    // Mimic stuff
    public void setHome();
}