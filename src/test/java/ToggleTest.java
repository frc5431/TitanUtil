import static org.junit.Assert.*;
import org.junit.*;

import frc.team5431.titan.core.misc.Toggle;

public class ToggleTest {

    @Test
    public void flip() {
        Toggle toggle = new Toggle();

        // emulate joystick button press to enable
        for (int x = 0; x < 100; x++) {
            toggle.update(true);
            assertTrue(toggle.getState());
        }
        // emulate joystick button release
        for (int x = 0; x < 100; x++) {
            toggle.update(false);
            assertTrue(toggle.getState());
        }
        // emulate joystick button press to disable
        for (int x = 0; x < 100; x++) {
            toggle.update(true);
            assertFalse(toggle.getState());
        }
    }
}