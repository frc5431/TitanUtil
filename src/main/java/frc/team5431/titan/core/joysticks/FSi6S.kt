package frc.team5431.titan.core.joysticks;

/**
 * Custom FSi6S class that has enums for controller bindings
 * deadzone management
 */
open class FSi6S : Joystick {
    /**
     * Enums for Switch Position Bindings
     */
    enum class SwitchPosition:ButtonZone {
        DOWN, NEUTRAL, UP
    }

    /**
     * Enums for Button Bindings
     */
    enum class Switch:ButtonZone {
        A, B, C, D
    }

    /**
     * Enums for Axis Bindings
     */
    enum class Axis:AxisZone {
        RIGHT_X, RIGHT_Y, LEFT_Y, LEFT_X
    }

    constructor(port: Int) : super(port);

    fun getSwitch(swit:Switch):SwitchPosition {
        when (swit) {
            FSi6S.Switch.A -> return if (getRawButton(1)) SwitchPosition.UP else SwitchPosition.DOWN
            FSi6S.Switch.B -> {
                val top = getRawButton(3)
                val bottom = getRawButton(2)
                if (top)
                {
                    return SwitchPosition.UP
                }
                else if (bottom)
                {
                    return SwitchPosition.DOWN
                }
                else
                {
                    return SwitchPosition.NEUTRAL
                }
            }
            FSi6S.Switch.C -> {
                val top = getRawButton(5)
                val bottom = getRawButton(4)
                if (top)
                {
                    return SwitchPosition.UP
                }
                else if (bottom)
                {
                    return SwitchPosition.DOWN
                }
                else
                {
                    return SwitchPosition.NEUTRAL
                }
            }
            FSi6S.Switch.D -> return if (getRawButton(6)) SwitchPosition.UP else SwitchPosition.DOWN
            else -> return if (getRawButton(1)) SwitchPosition.UP else SwitchPosition.DOWN
        }
    }

    public fun getBackLeft(): Boolean {
        return getRawButton(7);
    }

    public fun getBackRight(): Boolean {
        return getRawButton(8);
    }
}
