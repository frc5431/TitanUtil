package frc.team5431.titan.core;

import java.util.*;
import java.util.function.Supplier;
import java.util.function.Consumer;

import java.util.ArrayList;
import java.util.function.Function;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.TimedRobot;

import frc.team5431.titan.core.components.*;

import frc.team5431.titan.core.components.TitanRobot.*;

/**
 * Namespace for TitanUtil
 */
public final class Titan{

	private Titan() {
	}

	/*
		These are extentions of the components to prevent breakage
	*/
	public static class Joystick extends TitanJoystick.Joystick {
		public Joystick(int port) {
			super(port);
		}
	}

	public static class FSi6S extends TitanJoystick.FSi6S {
		public FSi6S(int port) {
			super(port);
		}
	}

	public static class Xbox extends TitanJoystick.Xbox {
		public Xbox(int port) {
			super(port);
		}
	}

	public static class LogitechExtreme3D extends TitanJoystick.LogitechExtreme3D{
		public LogitechExtreme3D(int port) {
			super(port);
		}
	}

	public static class Pot extends TitanPot {
		public Pot(int port) {
			super(port);
		}
	}

	public static class Solenoid extends TitanSolenoid {
		public Solenoid(int channel) {
			super(channel);
		}
		public Solenoid(final int moduleNumber, final int channel) {
			super(moduleNumber, channel);
		}
	}

	public static class DoubleSolenoid extends TitanDoubleSolenoid{
		public DoubleSolenoid(final int forwardChannel, final int reverseChannel){
			super(forwardChannel, reverseChannel);
		}
		public DoubleSolenoid(final int moduleNumber, final int forwardChannel, final int reverseChannel){
			super(moduleNumber, forwardChannel, reverseChannel);
		}
	};

	public static class AssignableJoystick<T extends Robot<T>> extends TitanJoystick.Joystick {
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

	public static class Lidar extends TitanLidar{
		public Lidar(final int source) {
			this(new DigitalInput(source));
		}
		public Lidar(DigitalSource source) {
			super(source);
		}
	}

	public static abstract class TitanRobot.Robot Robot;

	public static class Mimic extends TitanMimic {}

	public static boolean approxEquals(final double a, final double b, final double epsilon) {
		if (a == b) {
			return true;
		}

		return Math.abs(a - b) < epsilon;
	}

	public static double lerp(final double a, final double b, final double f){
		return a + f * (b - a);
	}

	public static double clamp(final double val, final double min, final double max){
		return Math.max(min, Math.min(max, val));
	}
}
