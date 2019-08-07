package frc.team5431.titan.core;

import java.util.function.Consumer;
import java.util.function.Function;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * Namespace for TitanUtil
 */
public final class Titan{

	private Titan() {
	}

	/*
		These are extentions of the components to prevent breakage
	*/
	public static class Joystick extends frc.team5431.titan.core.components.joysticks.Joystick {
		public Joystick(int port) {
			super(port);
		}
	}

	public static class FSi6S extends frc.team5431.titan.core.components.joysticks.FSi6S {
		public FSi6S(int port) {
			super(port);
		}
	}

	public static class Xbox extends frc.team5431.titan.core.components.joysticks.Xbox {
		public Xbox(int port) {
			super(port);
		}
	}

	public static class LogitechExtreme3D extends frc.team5431.titan.core.components.joysticks.LogitechExtreme3D {
		public LogitechExtreme3D(int port) {
			super(port);
		}
	}

	public static class AssignableJoystick<T extends Robot<T>> extends frc.team5431.titan.core.components.joysticks.AssignableJoystick<T> {
		public AssignableJoystick(int port) {
			super(port);
		}
	}

	public static class Toggle extends frc.team5431.titan.core.components.Toggle{}

	public static class Pot extends frc.team5431.titan.core.components.Pot {
		public Pot(int port) {
			super(port);
		}
	}

	public static class Solenoid extends frc.team5431.titan.core.components.Solenoid.Single {
		public Solenoid(int channel) {
			super(channel);
		}
		public Solenoid(final int moduleNumber, final int channel) {
			super(moduleNumber, channel);
		}
	}

	public static class DoubleSolenoid extends frc.team5431.titan.core.components.Solenoid.Double {
		public DoubleSolenoid(final int forwardChannel, final int reverseChannel){
			super(forwardChannel, reverseChannel);
		}
		public DoubleSolenoid(final int moduleNumber, final int forwardChannel, final int reverseChannel){
			super(moduleNumber, forwardChannel, reverseChannel);
		}
	};

	public static class Lidar extends frc.team5431.titan.core.components.Lidar{
		public Lidar(final int source) {
			this(new DigitalInput(source));
		}
		public Lidar(DigitalSource source) {
			super(source);
		}
	}

	public static abstract class Component<T extends Robot<T>> extends frc.team5431.titan.core.components.robot.Component<T>{}
	
	public static abstract class Robot<T extends Robot<T>> extends frc.team5431.titan.core.components.robot.Robot<T>{}

	public static abstract class Command<T extends Robot<T>> extends frc.team5431.titan.core.components.robot.Command<T>{}

	public static class WaitCommand<T extends Robot<T>> extends frc.team5431.titan.core.components.robot.WaitCommand<T>{
		public WaitCommand(long ms) {
			super(ms);
		}
	}

	public static class ClearQueueCommand<T extends Robot<T>> extends frc.team5431.titan.core.components.robot.ClearQueueCommand<T>{}

	public static class SpeedCommand<T extends Robot<T>> extends frc.team5431.titan.core.components.robot.SpeedCommand<T> {
		public SpeedCommand(double speed, long durationMS, SpeedController controller) {
			super(speed, durationMS, controller);
		}
	}

	public static class ConsumerCommand<T extends Robot<T>> extends frc.team5431.titan.core.components.robot.ConsumerCommand<T> {
		public ConsumerCommand(Consumer<T> consumer) {
			super(consumer);
		}
	}

	public static class ConditionalCommand<T extends Robot<T>> extends frc.team5431.titan.core.components.robot.ConditionalCommand<T> {
		public ConditionalCommand(Function<T, Boolean> func) {
			super(func);
		}
	}

	public static class CommandQueue<T extends Robot<T>> extends frc.team5431.titan.core.components.robot.CommandQueue<T> {
		private static final long serialVersionUID = CommandQueue.serialVersionUID;
	}

	public static class ParallelCommandGroup<T extends Robot<T>> extends frc.team5431.titan.core.components.robot.ParallelCommandGroup<T>{}

	public static class Mimic extends frc.team5431.titan.core.components.Mimic {}

	public static class Math extends frc.team5431.titan.core.components.Calc {}
}
