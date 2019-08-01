package frc.team5431.titan.core;

public final class TitanJoystick {
    /**
	 * Custom joystick class that is identical to the WPILib version except it has
	 * deadzone management
	 */
	public static class Joystick extends edu.wpi.first.wpilibj.Joystick {
		private double deadzoneMin = 0.0f, deadzoneMax = 0.0f;
		
		public interface AxisZone{
        }
        
        public interface ButtonZone {
        }

        public double getRawAxis(final AxisZone value) {
            return getRawAxis(((Enum<?>) value).ordinal());
        }

        public boolean getRawButton(final ButtonZone value) {
            return getRawButton(((Enum<?>) value).ordinal() + 1);
        }

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

	public static class FSi6S extends TitanJoystick.Joystick {
        public enum SwitchPosition implements ButtonZone {
			DOWN, NEUTRAL, UP
		}

        public enum Switch implements ButtonZone {
			A, B, C, D
		}

        public enum Axis implements AxisZone {
			RIGHT_X, RIGHT_Y, LEFT_Y, LEFT_X
		}

		public FSi6S(int port) {
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
	}

	public static class Xbox extends TitanJoystick.Joystick {
        public Xbox(int port) {
            super(port);
        }

        public enum Button implements ButtonZone {
            // ordered correctly, so ordinal reflects real mapping
            A, B, X, Y, BUMPER_L, BUMPER_R, BACK, START
        }

        public enum Axis implements AxisZone {
            LEFT_X, LEFT_Y, TRIGGER_LEFT, TRIGGER_RIGHT, RIGHT_X, RIGHT_Y
        }
    }
	
	public static class LogitechExtreme3D extends TitanJoystick.Joystick {
		public static enum Button implements ButtonZone{
			TRIGGER, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, ELEVEN, TWELVE;
		}
		
		public static enum Axis implements AxisZone {
			X, Y, Z, SLIDER;
		}
		
		public LogitechExtreme3D(final int port) {
			super(port);
		}
	}
}