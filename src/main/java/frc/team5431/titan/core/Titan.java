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

	public static abstract class Component<T extends Robot<T>>{
		public abstract void init(final T robot);
	
		public abstract void periodic(final T robot);
	
		public abstract void disabled(final T robot);
	
		public void tick(final T robot){
			//do nothing
		}
	}

	public static abstract class Robot<T extends Robot<T>> extends TimedRobot{
		public abstract List<Component<T>> getComponents();
	}

	public static abstract class Command<T extends Robot<T>> {
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
	
	public static class WaitCommand<T extends Robot<T>> extends Command<T> {

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
	
	public static class ClearQueueCommand<T extends Robot<T>> extends Command<T>{

		public ClearQueueCommand(){
			name = "ClearQueueCommand";
			properties = "Clears the command queue";
		}

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
	
	public static class SpeedCommand<T extends Robot<T>> extends Command<T> {
		private final SpeedController controller;
		private final double speed;
		private final long durationMS;
		private long startTime;
		
		public SpeedCommand(final double speed, final long durationMS, final SpeedController controller) {
			this.controller = controller;
			this.speed = speed;
			this.durationMS = durationMS;

			name = "SpeedCommand";
			properties = String.format("Speed: %f; Duration: %d", speed, durationMS);
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

	public static class ConsumerCommand<T extends Robot<T>> extends Command<T> {
		private final Consumer<T> consumer;
		
		public ConsumerCommand(final Consumer<T> consumer) {
			this.consumer = consumer;

			name = "ConsumerCommand";
			properties = "Runs a Consumer";
		}

		@Override
		public void init(final T robot) {
		}

		@Override
		public CommandResult update(T robot) {
			consumer.accept(robot);

			return CommandResult.COMPLETE;
		}

		@Override
		public void done(final T robot) {
		}
	}

	public static class ConditionalCommand<T extends Robot<T>> extends Command<T>{
		private final Function<T, Boolean> func;

		public ConditionalCommand(final Function<T, Boolean> func) {
			this.func = func;

			name = "ConditionalCommand";
			properties = "Completes when a supplied Function returns true";
		}

		@Override
		public void init(final T robot) {
		}

		@Override
		public CommandResult update(final T robot) {
			if(func.apply(robot)){
				return CommandResult.COMPLETE;
			}else{
				return CommandResult.IN_PROGRESS;
			}
		}

		@Override
		public void done(final T robot) {
		}
	}

	public static class CommandQueue<T extends Robot<T>> extends LinkedList<Command<T>> {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public CommandQueue(){
			super();
		}

		public CommandQueue(final Collection<Command<T>> col){
			super(col);
		}

		public void init(final T robot) {
			// Initialize the first command
			final Command<T> initCommand = peek();
			if (initCommand != null) {
				initCommand.startTimer();
				initCommand.init(robot);
				TitanLogger.l("Starting with %s (%s)", initCommand.getName(), initCommand.getProperties());
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
				TitanLogger.l("Finished %s (Seconds: %.2f)", command.getName(), secondsElapsed);
				command.done(robot);
				if (result == Titan.Command.CommandResult.COMPLETE) {
					remove();
					final Command<T> nextCommand = peek();
					if (nextCommand != null) {
						nextCommand.startTimer();
						nextCommand.init(robot);
						TitanLogger.l("Starting %s (%s)", nextCommand.getName(), nextCommand.getProperties());
					} else {
						return false;
					}
				} else if (result == Titan.Command.CommandResult.CLEAR_QUEUE) {
					clear();
					TitanLogger.l("Cleared queue");
				} else if (result == Titan.Command.CommandResult.RESTART_COMMAND) {
					command.startTimer();
					command.init(robot);
				}
			}

			return true;
		}

		public boolean done(final T robot){
			if(!isEmpty()){
				final Command<T> command = peek();
				command.done(robot);
				clear();
				return true;
			}
			return false;
		}
	}

	public static class ParallelCommandGroup<T extends Robot<T>> extends Command<T>{
		private final List<CommandQueue<T>> queues = new ArrayList<>();

		private boolean init = false;

		public ParallelCommandGroup(){
			name = "ParallelCommandGroup";
			properties = "Runs a group of commands in parallel";
		}

		public void addCommand(final Command<T> command){
			final CommandQueue<T> newQueue = new CommandQueue<T>();
			newQueue.add(command);
			addQueue(newQueue);
		}

		public void addQueue(final CommandQueue<T> queue){
			if(init){
				throw new IllegalArgumentException("Can't add new commands to a ParallelCommandGroup after init() was called");
			}
			queues.add(queue);
		}

		public void addQueue(final List<Command<T>> list){
			addQueue(new CommandQueue<T>(list));
		}

		public void init(final T robot){
			for(final CommandQueue<T> queue : queues){
				queue.init(robot);
			}
			init = true;
		}

		public CommandResult update(final T robot){
			final Iterator<CommandQueue<T>> queueIter = queues.iterator();
			while(queueIter.hasNext()){
				final CommandQueue<T> queue = queueIter.next();
				if(!queue.update(robot)){
					queueIter.remove();
				}
			}

			if(queues.isEmpty()){
				return CommandResult.COMPLETE;
			}

			return CommandResult.IN_PROGRESS;
		}

		public void done(final T robot){
			for(final CommandQueue<T> queue : queues){
				queue.done(robot);
			}
		}

		public boolean isEmpty(){
			return queues.isEmpty();
		}
	}

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
