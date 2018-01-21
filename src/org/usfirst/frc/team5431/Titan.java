package org.usfirst.frc.team5431;

import edu.wpi.first.wpilibj.DriverStation;

/**
 * Namespace for TitanUtil
 */
public final class Titan {
	private Titan() {}
	
	/**
	 * Custom joystick class that is identical to the WPILib version except it has deadzone management
	 */
	public static class Joystick extends edu.wpi.first.wpilibj.Joystick {
		private double deadzoneMin = 0.0f, deadzoneMax = 0.0f;

		public Joystick(final int port) {
			super(port);
		}

		public double getDeadzoneMin() {
			return deadzoneMin;
		}

		public void setDeadzoneMin(final double deadzoneMin) {
			this.deadzoneMin = deadzoneMin;
		}

		public double getDeadzoneMax() {
			return deadzoneMax;
		}

		public void setDeadzoneMax(final double deadzoneMax) {
			this.deadzoneMax = deadzoneMax;
		}

		public void setDeadzone(final double min, final double max) {
			setDeadzoneMin(min);
			setDeadzoneMax(max);
		}

		public void setDeadzone(final double deadzone) {
			setDeadzone(-deadzone, deadzone);
		}

		@Override
		public double getRawAxis(final int axis) {
			final double val = super.getRawAxis(axis);
			if (val >= deadzoneMin && val <= deadzoneMax) {
				return 0.0;
			} else {
				return val;
			}
		}

	}
	
	public static class DroneController extends Titan.Joystick {
		public static enum SwitchPosition {
			DOWN, NEUTRAL, UP
		}

		public static enum Switch {
			A, B, C, D
		}
		
		public static enum Axis{
			RIGHT_X, RIGHT_Y, LEFT_Y, LEFT_X
		}

		public DroneController(int port) {
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

	public static class Xbox extends Titan.Joystick{
		
		public static enum Button{
			//ordered correctly, so ordinal reflects real mapping
			A, B, X, Y, BUMPER_L, BUMPER_R, BACK, START;
		}
		
		public static enum Axis{
			LEFT_X, LEFT_Y, TRIGGER_LEFT, TRIGGER_RIGHT, RIGHT_X, RIGHT_Y 
		}
		
		public Xbox(int port) {
			super(port);
		}
		
		public boolean getRawButton(final Button but) {
			return getRawButton(but.ordinal() + 1);
		}
		
		public double getRawAxis(final Axis axis) {
			return getRawAxis(axis.ordinal());
		}
	}
	
	public static class Toggle {
		private boolean isToggled = false;
		private int prevButton = 0;

		public boolean isToggled(final boolean buttonState) {
			if ((buttonState ? 1 : 0) > prevButton) {
				isToggled = !isToggled;
			}
			prevButton = buttonState ? 1 : 0;
			return isToggled;
		}
	}
	
	public static class GameData {
		public static enum Position{
			LEFT, RIGHT;
			
			static Position fromGameData(final char value) {
				if(value == 'L') {
					return Position.LEFT;
				}else if(value == 'R'){
					return Position.RIGHT;
				}else {
					throw new IllegalArgumentException("Illegal Game Data Character: " + value);
				}
			}
		}
		
		private Position allianceSwitch, scale, opponentSwitch;
		
		public void init() {
			final String gameData = DriverStation.getInstance().getGameSpecificMessage();
			if(gameData.length() != 3) {
				throw new IllegalArgumentException("Illegal Game Data String: " + gameData);
			}
			allianceSwitch = Position.fromGameData(gameData.charAt(0));
			scale = Position.fromGameData(gameData.charAt(1));
			opponentSwitch = Position.fromGameData(gameData.charAt(2));
		}

		public Position getScale() {
			return scale;
		}

		public Position getOpponentSwitch() {
			return opponentSwitch;
		}

		public Position getAllianceSwitch() {
			return allianceSwitch;
		}
	}

}
