package org.usfirst.frc.team5431;

public class TitanDroneController extends TitanJoystick {

	public static enum SwitchPosition {
		DOWN, NEUTRAL, UP
	}

	public static enum Switch {
		A, B, C, D
	}
	
	public static enum Axis{
		RIGHT_X, RIGHT_Y, LEFT_Y, LEFT_X
	}

	public TitanDroneController(int port) {
		super(port);
	}

	public SwitchPosition getSwitch(final Switch swit) {
		switch (swit) {
		default:
		case A:
			return getRawButton(1) ? SwitchPosition.UP : SwitchPosition.DOWN;
		case B: {
			final boolean top = getRawButton(3), bottom = getRawButton(2);
			if (top) {
				return SwitchPosition.UP;
			} else if (bottom) {
				return SwitchPosition.DOWN;
			} else {
				return SwitchPosition.NEUTRAL;
			}
		}
		case C: {
			final boolean top = getRawButton(5), bottom = getRawButton(4);
			if (top) {
				return SwitchPosition.UP;
			} else if (bottom) {
				return SwitchPosition.DOWN;
			} else {
				return SwitchPosition.NEUTRAL;
			}
		}
		case D:
			return getRawButton(6) ? SwitchPosition.UP : SwitchPosition.DOWN;
		}
	}
	
	public boolean getBackLeft() {
		return getRawButton(7);
	}
	
	public boolean getBackRight() {
		return getRawButton(8);
	}
	
	public double getRawAxis(final Axis axis) {
		return getRawAxis(axis.ordinal());
	}
}
