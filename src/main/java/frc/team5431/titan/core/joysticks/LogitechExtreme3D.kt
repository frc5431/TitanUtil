package frc.team5431.titan.core.joysticks;

/**
 * Custom LogitechExtreme3D class that has enums for controller bindings
 * deadzone management
 */
open class LogitechExtreme3D : Joystick {
    /**
     * Enums for Button Bindings
     */
    enum class Button:ButtonZone{
        TRIGGER, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, ELEVEN, TWELVE;
    }
    
    /**
     * Enums for Axis Bindings
     */
    enum class Axis:AxisZone {
        X, Y, Z, SLIDER;
    }
    
    constructor(port : Int) : super(port);
}