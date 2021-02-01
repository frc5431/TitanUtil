import static org.junit.Assert.assertEquals;

import org.junit.Test;

import frc.team5431.titan.core.joysticks.utils.CompassPOV;
import frc.team5431.titan.core.joysticks.utils.ZoneTools;

public class EnumZone {
    // simple wrapper to POVZone.find which will loop around to find itself
    // no real application other that to test the find function
    private <T extends ZoneTools> ZoneTools getter(T v) {
        return ZoneTools.find(v.getClass(), v.getPosition());
    }

    @Test
    public void pov() {
        assertEquals(getter(CompassPOV.NORTH), CompassPOV.NORTH);
        assertEquals(getter(CompassPOV.SOUTH), CompassPOV.SOUTH);
        assertEquals(getter(CompassPOV.EAST), CompassPOV.EAST);
        assertEquals(getter(CompassPOV.WEST), CompassPOV.WEST);

        assertEquals(getter(CompassPOV.NORTHEAST), CompassPOV.NORTHEAST);
        assertEquals(getter(CompassPOV.SOUTHEAST), CompassPOV.SOUTHEAST);
        assertEquals(getter(CompassPOV.NORTHWEST), CompassPOV.NORTHWEST);
        assertEquals(getter(CompassPOV.SOUTHWEST), CompassPOV.SOUTHWEST);
    }
}
