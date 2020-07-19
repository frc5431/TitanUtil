package frc.team5431.titan.core.joysticks;

/**
 * Custom Xbox class that has enums for controller bindings
 * deadzone management
 */
open class Xbox:Joystick {
    constructor(port: Int) : super(port);

    /**
     * Enums for Button Bindings
     */
    enum class Button:ButtonZone {
        // ordered correctly, so ordinal reflects real mapping
        A, B, X, Y, BUMPER_L, BUMPER_R, BACK, START
    }

    /**
     * Enums for Axis Bindings
     */
    enum class Axis:AxisZone {
        LEFT_X, LEFT_Y, TRIGGER_LEFT, TRIGGER_RIGHT, RIGHT_X, RIGHT_Y
    }
}