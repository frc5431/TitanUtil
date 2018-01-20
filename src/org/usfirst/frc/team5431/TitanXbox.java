package org.usfirst.frc.team5431;

public class TitanXbox extends TitanJoystick{
	
	public static enum Button{
		//ordered correctly, so ordinal reflects real mapping
		A, B, X, Y, BUMPER_L, BUMPER_R, BACK, START;
	}
	
	public static enum Axis{
		LEFT_X, LEFT_Y, TRIGGER_LEFT, TRIGGER_RIGHT, RIGHT_X, RIGHT_Y 
	}
	
	public TitanXbox(int port) {
		super(port);
	}
	
	public boolean getRawButton(final Button but) {
		return getRawButton(but.ordinal() + 1);
	}
	
	public double getRawAxis(final Axis axis) {
		return getRawAxis(axis.ordinal());
	}

}
