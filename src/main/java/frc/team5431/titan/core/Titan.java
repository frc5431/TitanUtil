package frc.team5431.titan.core;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalSource;

import frc.team5431.titan.core.components.*;
import frc.team5431.titan.core.components.TitanRobot;

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

	public static class Lidar extends TitanLidar{
		public Lidar(final int source) {
			this(new DigitalInput(source));
		}
		public Lidar(DigitalSource source) {
			super(source);
		}
	}

	public static abstract class Robot extends TitanRobot.Robot{}

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
