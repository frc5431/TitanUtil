package frc.team5431.titan.core;

import java.util.function.Consumer;
import java.util.function.Function;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.SpeedController;
import frc.team5431.titan.core.components.TitanSolenoid;
import frc.team5431.titan.core.components.joysticks.TitanJoystick;
import frc.team5431.titan.core.components.joysticks.TitanXbox;
import frc.team5431.titan.core.components.joysticks.TitanLogitech;
import frc.team5431.titan.core.components.joysticks.TitanFSi6S;
import frc.team5431.titan.core.components.joysticks.TitanAssignableJoystick;
import frc.team5431.titan.core.components.TitanLidar;
import frc.team5431.titan.core.components.TitanMath;
import frc.team5431.titan.core.components.TitanMimic;
import frc.team5431.titan.core.components.TitanPot;
import frc.team5431.titan.core.components.TitanToggle;
import frc.team5431.titan.core.components.robot.*;
/**
 * Namespace for TitanUtil
 */
public final class Titan{

	private Titan() {
	}

	/*
		These are extentions of the components to prevent breakage
	*/
	public static class Joystick extends TitanJoystick {
		public Joystick(int port) {
			super(port);
		}
	}

	public static class FSi6S extends TitanFSi6S {
		public FSi6S(int port) {
			super(port);
		}
	}

	public static class Xbox extends TitanXbox {
		public Xbox(int port) {
			super(port);
		}
	}

	public static class LogitechExtreme3D extends TitanLogitech {
		public LogitechExtreme3D(int port) {
			super(port);
		}
	}

	public static class AssignableJoystick<T extends Robot<T>> extends TitanAssignableJoystick<T> {
		public AssignableJoystick(int port) {
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

	public static abstract class Component<T extends Robot<T>> extends TitanComponent<T>{}
	
	public static abstract class Robot<T extends Robot<T>> extends TitanRobot<T>{}

	public static abstract class Command<T extends Robot<T>> extends TitanCommand<T>{}

	public static class WaitCommand<T extends Robot<T>> extends TitanWaitCommand<T>{
		public WaitCommand(long ms) {
			super(ms);
		}
	}

	public static class ClearQueueCommand<T extends Robot<T>> extends TitanClearQueueCommand<T>{}

	public static class SpeedCommand<T extends Robot<T>> extends TitanSpeedCommand<T> {
		public SpeedCommand(double speed, long durationMS, SpeedController controller) {
			super(speed, durationMS, controller);
		}
	}

	public static class ConsumerCommand<T extends Robot<T>> extends TitanConsumerCommand<T> {
		public ConsumerCommand(Consumer<T> consumer) {
			super(consumer);
		}
	}

	public static class ConditionalCommand<T extends Robot<T>> extends TitanConditionalCommand<T> {
		public ConditionalCommand(Function<T, Boolean> func) {
			super(func);
		}
	}

	public static class CommandQueue<T extends Robot<T>> extends TitanCommandQueue<T> {
		private static final long serialVersionUID = TitanCommandQueue.serialVersionUID;
	}

	public static class ParallelCommandGroup<T extends Robot<T>> extends TitanParallelCommandGroup<T>{}

	public static class Mimic extends TitanMimic {}

	public static class Math extends TitanMath {}
}
