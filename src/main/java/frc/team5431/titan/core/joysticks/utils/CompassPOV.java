package frc.team5431.titan.core.joysticks.utils;

/**
 * @author Ryan Hirasaki
 */
public enum CompassPOV implements ZoneTools {
    NONE(-1), NORTH(0), NORTHEAST(45), EAST(90), SOUTHEAST(135), //
    SOUTH(180), SOUTHWEST(225), WEST(270), NORTHWEST(315);

    private int coord;

    CompassPOV(int coord) {
        this.coord = coord;
    }

    @Override
    public int getPosition() {
        return coord;
    }
}
