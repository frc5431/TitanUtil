package frc.team5431.titan.core;

import java.util.function.Consumer;
import java.util.function.Function;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.SpeedController;
import frc.team5431.titan.core.components.TitanSolenoid;
import frc.team5431.titan.core.components.TitanJoystick;
import frc.team5431.titan.core.components.TitanLidar;
import frc.team5431.titan.core.components.TitanMimic;
import frc.team5431.titan.core.components.TitanPot;
import frc.team5431.titan.core.components.TitanRobot;
import frc.team5431.titan.core.components.TitanToggle;

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

	public static class Toggle extends TitanToggle{}

	public static class Pot extends TitanPot {
		public Pot(int port) {
			super(port);
		}
	}

	public static class Solenoid extends TitanSolenoid.Single {
		public Solenoid(int channel) {
			super(channel);
		}
		public Solenoid(final int moduleNumber, final int channel) {
			super(moduleNumber, channel);
		}
	}

	public static class DoubleSolenoid extends TitanSolenoid.Double {
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

	public static abstract class Component<T extends Robot<T>> extends TitanRobot.Component<T>{}
	
	public static abstract class Robot<T extends Robot<T>> extends TitanRobot.Robot<T>{}

	public static abstract class Command<T extends Robot<T>> extends TitanRobot.Command<T>{}

	public static class WaitCommand<T extends Robot<T>> extends TitanRobot.WaitCommand<T>{
		public WaitCommand(long ms) {
			super(ms);
		}
	}

	public static class ClearQueueCommand<T extends Robot<T>> extends TitanRobot.ClearQueueCommand<T>{}

	public static class SpeedCommand<T extends Robot<T>> extends TitanRobot.SpeedCommand<T> {
		public SpeedCommand(double speed, long durationMS, SpeedController controller) {
			super(speed, durationMS, controller);
		}
	}

	public static class ConsumerCommand<T extends Robot<T>> extends TitanRobot.ConsumerCommand<T> {
		public ConsumerCommand(Consumer<T> consumer) {
			super(consumer);
		}
	}

	public static class ConditionalCommand<T extends Robot<T>> extends TitanRobot.ConditionalCommand<T> {
		public ConditionalCommand(Function<T, Boolean> func) {
			super(func);
		}
	}

	public static class CommandQueue<T extends Robot<T>> extends TitanRobot.CommandQueue<T> {
		private static final long serialVersionUID = TitanRobot.CommandQueue.serialVersionUID;
	}

	public static class ParallelCommandGroup<T extends Robot<T>> extends TitanRobot.ParallelCommandGroup<T>{}

	public static class Mimic extends TitanMimic {}

	/*
		These are Generic Functions
	*/
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
