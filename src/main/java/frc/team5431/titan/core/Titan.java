package frc.team5431.titan.core;

import java.util.*;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Namespace for TitanUtil
 */
public final class Titan {
	public static boolean DEBUG = true;

	private Titan() {
	}

	/* Log information */
	public static void l(String base, Object... a) {
		if (DEBUG)
			System.out.println(String.format(base, a));
	}

	/* Log error */
	public static void e(String base, Object... a) {
		if (DEBUG)
			System.err.println(String.format(base, a));
	}

	/* Exception error */
	public static void ee(String namespace, Exception e) {
		if (DEBUG)
			e("%s: %s", namespace, e.getMessage());
	}

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

	public static class FSi6S extends Titan.Joystick {
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

	public static class Xbox extends Titan.Joystick {
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
	
	public static class LogitechExtreme3D extends Titan.Joystick {
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

	public static class AssignableJoystick<T> extends Titan.Joystick {
		private final Map<Integer, Supplier<CommandQueue<T>>> assignments = new HashMap<>();
		private final CommandQueue<T> currentQueue = new CommandQueue<>();

		public void update(final T robot) {
            //Update all of the button commands
            for (final Integer button : assignments.keySet()) {
                getRawButton(button, true); //Call the queue update on the specified button
            }

			currentQueue.update(robot);
		}

        public AssignableJoystick(final int port) {
            super(port);
        }

        public boolean getRawButton(final int but, boolean update) {
			final boolean value = super.getRawButton(but);
            if (assignments.containsKey(but) && value && update) {
				currentQueue.clear();

				//call the associated function from the index in the map and then add it to the queue
				currentQueue.addAll(assignments.get(but).get());//get
			}

			return value;
		}

		public void assign(final int button, final Supplier<CommandQueue<T>> generator) {
			assignments.put(button, generator);
		}

        public void assign(final ButtonZone button, final Supplier<CommandQueue<T>> generator) {
            assign(((Enum<?>) button).ordinal(), generator);
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

		public void setState(final boolean state) {
			isToggled = state;
		}
	}

	public static class Pot extends AnalogInput {
		private double minAngle = 0, maxAngle = 180;
		private double minPotValue = 0, maxPotValue = 4096;
		private double absoluteReset = 0;
		private boolean potDirection = false;

		public Pot(final int port) {
			super(port);
		}

		public double getMinAngle() {
			return minAngle;
		}

		public void setMinAngle(double minAngle) {
			this.minAngle = minAngle;
		}

		public double getMaxAngle() {
			return maxAngle;
		}

		public void setMaxAngle(double maxAngle) {
			this.maxAngle = maxAngle;
		}

		public double getMinPotValue() {
			return minPotValue;
		}

		public void setMinPotValue(double minPotValue) {
			this.minPotValue = minPotValue;
		}

		public double getMaxPotValue() {
			return maxPotValue;
		}

		public void setMaxPotValue(double maxPotValue) {
			this.maxPotValue = maxPotValue;
		}

		public void resetAngle() {
			absoluteReset = getAbsoluteAngle();
			potDirection = absoluteReset > 0; // false == less, true == more
		}

		public double getAngle() {
			final double currentAngle = getAbsoluteAngle();
			return potDirection ? currentAngle - absoluteReset : currentAngle + absoluteReset;
		}

		public double getAbsoluteAngle() {
			return -linearMap(getAverageVoltage(), minPotValue, maxPotValue, minAngle, maxAngle);
		}

		private static double linearMap(final double currentValue, final double minInputValue,
				final double maxInputValue, final double minOutputValue, final double maxOutputValue) {
			return (currentValue - minInputValue) * (maxOutputValue - minOutputValue) / (minInputValue - maxInputValue)
					+ minOutputValue;
		}
	}
	
	public static class Lidar extends Counter{
		private int calibrationOffset = 0;
		
		public Lidar(final int source) {
			this(new DigitalInput(source));
		}
		
		public Lidar(final DigitalSource source) {
			super(source);
			setMaxPeriod(1.0);
			setSemiPeriodMode(true);
			setSamplesToAverage(100);
			reset();
		}
		
		public int getCalibrationOffset() {
			return calibrationOffset;
		}

		public void setCalibrationOffset(final int calibrationOffset) {
			this.calibrationOffset = calibrationOffset;
		}
		/*
		 * @return distance in cm*/
		public double getDistance() {
			if(get() < 1) {
				return 0;
			}
			
			return ((getPeriod() * 1000000.0 / 10.0) * calibrationOffset) * 0.39370079;
		}
	}

	public static abstract class Command<T> {
		public String name = "Command";
		public String properties = "None";
		public long startTime = 0;

        public abstract void init(final T robot);

        public enum CommandResult {
			IN_PROGRESS, COMPLETE, CLEAR_QUEUE, RESTART_COMMAND
        }

		public abstract CommandResult update(final T robot);

		public abstract void done(final T robot);

		public String getName() {
			return name;
		}

		public String getProperties() {
			return properties;
		}

		public void startTimer() {
			startTime = System.currentTimeMillis();
		}

		public long getElapsed() {
			return System.currentTimeMillis() - startTime;
		}

		public double getSecondsElapsed() {
			return getElapsed() / 1000.0;
		}
	}
	
	public static class WaitCommand<T> extends Titan.Command<T> {

		private final long durationMS;
		private long startTime;
		
		public WaitCommand(final long ms) {
			name = "WaitStep";
			properties = String.format("Millis %d", ms);
			durationMS = ms;
		}

		@Override
		public void init(final T robot) {
			startTime = System.currentTimeMillis();
		}

		@Override
		public CommandResult update(final T robot) {
			if (System.currentTimeMillis() >= startTime + durationMS) {
				return CommandResult.COMPLETE;
			}

			return CommandResult.IN_PROGRESS;
		}

		@Override
		public void done(final T robot) {
		}
	}
	
	public static class ClearQueueCommand<T> extends Titan.Command<T>{

		@Override
		public void init(final T robot) {
		}

		@Override
		public CommandResult update(final T robot) {
			return CommandResult.CLEAR_QUEUE;
		}

		@Override
		public void done(final T robot) {
		}
	}
	
	public static class SpeedCommand<T> extends Titan.Command<T> {
		private final SpeedController controller;
		private final double speed;
		private final long durationMS;
		private long startTime;
		
		public SpeedCommand(final double speed, final long durationMS, final SpeedController controller) {
			this.controller = controller;
			this.speed = speed;
			this.durationMS = durationMS;
		}

		@Override
		public void init(final T robot) {
			startTime = System.currentTimeMillis();
		}

		@Override
		public CommandResult update(T robot) {
			controller.set(speed);
			
			if (System.currentTimeMillis() >= startTime + durationMS) {
				return CommandResult.COMPLETE;
			}

			return CommandResult.IN_PROGRESS;
		}

		@Override
		public void done(final T robot) {
		}
		
		
	}


	public static class CommandQueue<T> extends LinkedList<Command<T>> {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void init(final T robot) {
			// Initialize the first command
			final Command<T> initCommand = peek();
			if (initCommand != null) {
				initCommand.startTimer();
				initCommand.init(robot);
				Titan.l("Starting with %s (%s)", initCommand.getName(), initCommand.getProperties());
			}
		}

		/*
		 * Returns false if there are no more steps to complete
		 */
		public boolean update(final T robot) {
			if (isEmpty()) {
				return false;
			}

			final Command<T> command = peek();
			final Titan.Command.CommandResult result = command.update(robot);
			if (result == Titan.Command.CommandResult.IN_PROGRESS) {
				return true;
			} else {
				final double secondsElapsed = command.getSecondsElapsed();
				Titan.l("Finished %s (Seconds: %.2f)", command.getName(), secondsElapsed);
				command.done(robot);
				if (result == Titan.Command.CommandResult.COMPLETE) {
					remove();
					final Command<T> nextCommand = peek();
					if (nextCommand != null) {
						nextCommand.startTimer();
						nextCommand.init(robot);
						Titan.l("Starting %s (%s)", nextCommand.getName(), nextCommand.getProperties());
					} else {
						return false;
					}
				} else if (result == Titan.Command.CommandResult.CLEAR_QUEUE) {
					clear();
					Titan.l("Cleared queue");
				} else if (result == Titan.Command.CommandResult.RESTART_COMMAND) {
					command.startTimer();
					command.init(robot);
				}
			}

			return true;
		}
	}

	public static class GameData {
		private String gameData = "";

        public enum Position {
			LEFT, RIGHT, ERROR;

			static Position fromGameData(final char value) {
				if (value == 'L') {
					return Position.LEFT;
				} else if (value == 'R') {
					return Position.RIGHT;
				} else {
					return Position.ERROR;
				}
			}
		}

        public enum FieldObject {
			SWITCH, SCALE, OPPONENT_SWITCH
		}

        public interface SideChooser {
			void left();

			void right();
		}

        public interface ErrorChooser {
			void noData();
		}

		private Position allianceSwitch, scale, opponentSwitch;
		private FieldObject selectedObject;
		private ErrorChooser errorChooser = null;

		public boolean hasData() {
			return hasData("EEE");
		}

		public String getString() {
			return gameData;
		}

		public boolean hasData(String defaultValue) {
			gameData = DriverStation.getInstance().getGameSpecificMessage();
			if (gameData != null) {
				if (gameData.length() == 3)
					return true;
			}
			gameData = defaultValue;
			return false;
		}

		public void init() {
			if (!hasData()) {
				SmartDashboard.putString("Error", "Failed to get game specific message");
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

		public void setSelectedObject(FieldObject fObject) {
			selectedObject = fObject;
		}

		public void setNoDataRun(ErrorChooser errChsr) {
			errorChooser = errChsr;
		}

		public void runSide(SideChooser toRun) {
			switch (selectedObject) {
			case SWITCH:
				if (getAllianceSwitch() == Position.ERROR) {
					errorChooser.noData();
					return;
				}
				if (getAllianceSwitch() == Position.LEFT)
					toRun.left();
				else
					toRun.right();
				break;
			case SCALE:
				if (getScale() == Position.ERROR) {
					errorChooser.noData();
					return;
				}
				if (getScale() == Position.LEFT)
					toRun.left();
				else
					toRun.right();
				break;
			case OPPONENT_SWITCH:
				if (getOpponentSwitch() == Position.ERROR) {
					errorChooser.noData();
					return;
				}
				if (getOpponentSwitch() == Position.LEFT)
					toRun.left();
				else
					toRun.right();
				break;
			}
		}
	}

	public static boolean approxEquals(final double a, final double b, final double epsilon) {
		if (a == b) {
			return true;
		}

		return Math.abs(a - b) < epsilon;
	}
}
