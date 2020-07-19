import static org.junit.Assert.*;
import org.junit.*;

import frc.team5431.titan.core.misc.Toggle;

public class ToggleTest {

    @Test
    public void flip() {
        Toggle toggle = new Toggle();

        for (int x = 0; x < 100; x++) {
            toggle.isToggled(true);
            assertTrue(toggle.getState());
        }
        toggle.isToggled(false);
        assertTrue(toggle.getState());
    }

    @Test
    public void kotlinGeneratedGettersAndSetters() {
        Toggle toggle = new Toggle();


        for (int x = 0; x < 100; x++) {
            toggle.setState(false);
            assertFalse(toggle.getState());

            toggle.setState(true);
            assertTrue(toggle.getState());
        }
    }
}