package frc.team5431.titan.core.components;

import java.util.*;
import java.util.function.Consumer;

import java.util.ArrayList;
import java.util.function.Function;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.TimedRobot;

public class TitanRobot {

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
		public static final long serialVersionUID = 1L;

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
			final TitanRobot.Command.CommandResult result = command.update(robot);
			if (result == TitanRobot.Command.CommandResult.IN_PROGRESS) {
				return true;
			} else {
				final double secondsElapsed = command.getSecondsElapsed();
				TitanLogger.l("Finished %s (Seconds: %.2f)", command.getName(), secondsElapsed);
				command.done(robot);
				if (result == TitanRobot.Command.CommandResult.COMPLETE) {
					remove();
					final Command<T> nextCommand = peek();
					if (nextCommand != null) {
						nextCommand.startTimer();
						nextCommand.init(robot);
						TitanLogger.l("Starting %s (%s)", nextCommand.getName(), nextCommand.getProperties());
					} else {
						return false;
					}
				} else if (result == TitanRobot.Command.CommandResult.CLEAR_QUEUE) {
					clear();
					TitanLogger.l("Cleared queue");
				} else if (result == TitanRobot.Command.CommandResult.RESTART_COMMAND) {
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

}