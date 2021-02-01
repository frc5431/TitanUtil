import static org.junit.Assert.assertEquals;

import org.junit.Test;

import frc.team5431.titan.core.joysticks.utils.CompassPOV;

public class EnumZone {
    private CompassPOV conversion(CompassPOV v) {
        return CompassPOV.find(CompassPOV.getPOV(v));
    }

    @Test
    public void pov() {
        assertEquals(conversion(CompassPOV.NORTH), CompassPOV.NORTH);
        assertEquals(conversion(CompassPOV.SOUTH), CompassPOV.SOUTH);
        assertEquals(conversion(CompassPOV.EAST), CompassPOV.EAST);
        assertEquals(conversion(CompassPOV.WEST), CompassPOV.WEST);

        assertEquals(conversion(CompassPOV.NORTHEAST), CompassPOV.NORTHEAST);
        assertEquals(conversion(CompassPOV.SOUTHEAST), CompassPOV.SOUTHEAST);
        assertEquals(conversion(CompassPOV.NORTHWEST), CompassPOV.NORTHWEST);
        assertEquals(conversion(CompassPOV.SOUTHWEST), CompassPOV.SOUTHWEST);
    }
}
