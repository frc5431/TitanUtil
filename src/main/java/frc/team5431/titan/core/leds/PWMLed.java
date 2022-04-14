package frc.team5431.titan.core.leds;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;

public class PWMLed {

    private AddressableLED led;
    private AddressableLEDBuffer buffer;

    private int m_rainbowFirstPixelHue = 0;

    public PWMLed(int pwmPort, int ledLength) {
        this.led = new AddressableLED(pwmPort);
        this.led.setLength(ledLength);
        this.led.start();

        this.buffer = new AddressableLEDBuffer(ledLength);
    }

    public void setColor(Color color) {
        for (int i = 0; i < buffer.getLength(); i++) {
            buffer.setLED(i, color);
        }

        led.setData(buffer);
    }

    public void setRGB(int r, int g, int b) {
        for (int i = 0; i < buffer.getLength(); i++) {
            buffer.setRGB(i, r, g, b);
        }

        led.setData(buffer);
    }

    public void rainbow() {
        // For every pixel
        for (var i = 0; i < buffer.getLength(); i++) {
            // Calculate the hue - hue is easier for rainbows because the color
            // shape is a circle so only one value needs to precess
            int hue = (m_rainbowFirstPixelHue + (i * 180 / buffer.getLength())) % 180;
            // Set the value
            buffer.setHSV(i, hue, 255, 128);
        }
        // Increase by to make the rainbow "move"
        m_rainbowFirstPixelHue += 3;
        // Check bounds
        m_rainbowFirstPixelHue %= 180;
    }
}
