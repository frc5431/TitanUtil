package frc.team5431.titan.core.sensors;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;
import frc.team5431.titan.core.misc.Logger;

import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorSensorV3;

public class ColorSensor extends ColorSensorV3 {
    private Color[] m_Colors;
    private double m_Confidence;
    private final ColorMatch m_colorMatcher = new ColorMatch();

    /**
     * 
     * @param port       This is the the port of the sensor, you can grab it with
     *                   (I2C.Port.kOnboard)
     * @param confidence This is a double in the range of [0.0 to 1.0] which
     *                   the color sensor will use to make its prediction with.
     * @param colors     This is an array of colors which you will pass to the
     *                   ColorSensor
     *                   so the program can create the proper predition of which
     *                   color it sees.
     */
    public ColorSensor(I2C.Port port, double confidence, Color[] colors) {
        super(port);
        m_Colors = colors.clone();
        setConfidence(confidence);
        for (Color color : m_Colors) {
            m_colorMatcher.addColorMatch(color);
        }
    }

    /**
     * Alternative Constructor which sets the I2C device by default
     * this only works when on the RoboRIO port.
     * 
     * @param confidence This is a double in the range of [0.0 to 1.0] which
     *                   the color sensor will use to make its prediction with.
     * @param colors     This is an array of colors which you will pass to the
     *                   ColorSensor
     *                   so the program can create the proper predition of which
     *                   color it sees.
     */
    public ColorSensor(double confidence, Color[] colors) {
        this(I2C.Port.kOnboard, confidence, colors);
    }

    public void setConfidence(double confidence) {
        assert (confidence >= 0.0);
        assert (confidence <= 1.0);
        m_Confidence = confidence;
    }

    /**
     * 
     * @return the integer returned will be equal to the position in the color
     *         array passes to the constructor. Will return -1 if the confidence in
     *         not high enough.
     */
    public int findColorMatch() {
        int position = -1;

        Color detected = getColor();

        for (int i = 0; i < m_Colors.length; i++) {
            ColorMatchResult match = m_colorMatcher.matchClosestColor(detected);
            if (match.color == m_Colors[i]) {
                if (match.confidence >= m_Confidence)
                    position = i;
            }
        }

        if (position == -1)
            Logger.e("WARNING: No Color Found in TitanUtil's Color Sensor");

        return position;
    }
}