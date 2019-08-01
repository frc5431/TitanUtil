package frc.team5431.titan.core;

import java.util.*;
import java.util.function.Supplier;
import java.util.function.Consumer;

import java.util.EnumMap;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.function.Function;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.TimedRobot;

/**
 * Namespace for TitanUtil
 */
public final class Titan{

	private Titan() {
	}

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

	public static class Solenoid extends edu.wpi.first.wpilibj.Solenoid{
		private boolean currentState = false;

		public Solenoid(final int channel){
			super(channel);
		}

		public Solenoid(final int moduleNumber, final int channel) {
			super(moduleNumber, channel);
		}

		@Override
		public void set(final boolean newState){
			if(currentState != newState){
				currentState = newState;
				super.set(newState);
			}
		}
	}

	public static class DoubleSolenoid extends edu.wpi.first.wpilibj.DoubleSolenoid{
		private Value currentState = Value.kOff;

		public DoubleSolenoid(final int forwardChannel, final int reverseChannel){
			super(forwardChannel, reverseChannel);
		}

		public DoubleSolenoid(final int moduleNumber, final int forwardChannel, final int reverseChannel){
			super(moduleNumber, forwardChannel, reverseChannel);
		}

		@Override
		public void set(final Value newState){
			if(currentState != newState){
				currentState = newState;
				super.set(newState);
			}
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
				return -1;
			}
			
			return ((getPeriod() * 1000000.0 / 10.0) + calibrationOffset) * 0.39370079;
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

	public static class Mimic {
		public static enum PropertyType{
			DOUBLE(Double::parseDouble),
			INTEGER(Integer::parseInt),
			BOOLEAN(Boolean::parseBoolean);
	
			final Function<String, Object> converter;
	
			private PropertyType(final Function<String, Object> converter){
				this.converter = converter;
			}
	
			public Object convert(final String in){
				return converter.apply(in);
			}
		}
	
		public static interface PropertyValue<R>{
			public PropertyType getType();
	
			public Object get(final R robot);
		}
	
		public static final String DEFAULT_MIMIC_DIRECTORY = "/media/sda1/";
		public static final String DEFAULT_MIMIC_PATH = DEFAULT_MIMIC_DIRECTORY + "%s.mimic";
		
		public static class Step<PV extends Enum<PV> & PropertyValue<?>> {
			public EnumMap<PV, Object> values;
	
			public Step(final EnumMap<PV, Object> values) {
				this.values = values;
			}
			
			public Step(final String toParse, final Class<PV> clazz) {
				try {
					values = new EnumMap<>(clazz);
					final String parts[] = toParse.split(",");
					for(final PV key : clazz.getEnumConstants()){					
						values.put(key, key.getType().convert(parts[key.ordinal()]));
					}
				} catch (Exception e) {
					TitanLogger.ee("MimicParse", e);
				}
			}

			public EnumMap<PV, Object> getValues(){
				return values;
			}

			public Object get(final PV value){
				return values.get(value);
			}

			public double getDouble(final PV value){
				return (double) get(value);
			}

			public boolean getBoolean(final PV value){
				return (boolean) get(value);
			}

			public int getInteger(final PV value){
				return (Integer) get(value);
			}
			
			public String toString() {
				final StringBuilder builder = new StringBuilder();
				for(final Object obj : values.values()){
					builder.append(obj.toString()).append(",");
				}
				builder.append(System.lineSeparator());
				return builder.toString();
			}
		}
		
		public static class Observer<R, PV extends Enum<PV> & PropertyValue<R>> {
			private FileOutputStream log = null;
			
			public void prepare(final String fileName) {
				final String fName = String.format(DEFAULT_MIMIC_PATH, fileName);
				try {
					if(Files.deleteIfExists(new File(fName).toPath())) {
						TitanLogger.e("Deleted previous Mimic data");
					}
					log = new FileOutputStream(fName);
					TitanLogger.l("Created new Mimic file");
				} catch (IOException e) {
					TitanLogger.ee("Mimic", e);
				}
			}
			
			public void addStep(final R robot, final Class<PV> clazz) {
				try {
					final Step<PV> step = new Step<PV>(new EnumMap<>(clazz));
					for(final PV key : clazz.getEnumConstants()){
						step.values.put(key, key.get(robot));
					}
	
					if(log != null) log.write(step.toString().getBytes(StandardCharsets.US_ASCII));
				} catch (Exception e) {
					TitanLogger.ee("Mimic", e);
				}
			}

			public boolean isRecording(){
				return log != null;
			}
			
			public boolean save() {
				try {
					if(!isRecording()) return false;
					TitanLogger.l("Finished observing");
					log.flush();
					log.close();
					log = null;
					TitanLogger.l("Saved the Mimic data");
					return true;
				} catch (IOException e) {
					TitanLogger.ee("Mimic", e);
				}
				return false;
			}
		}

		public static <PV extends Enum<PV> & PropertyValue<?>> List<Step<PV>> load(final String fileName, final Class<PV> clazz) {
			final ArrayList<Step<PV>> pathData = new ArrayList<>();
			final String fName = String.format(DEFAULT_MIMIC_PATH, fileName);
			try (final BufferedReader reader = new BufferedReader(new FileReader(fName))) {
				TitanLogger.l("Loading the Mimic file " + fileName);
				if(!Files.exists(new File(fName).toPath())) {
					TitanLogger.e("The requested Mimic data was not found");
				}
				
				Step<PV> lastStep = null;
				String line;
				while ((line = reader.readLine()) != null) {
					try {
						pathData.add(lastStep = new Step<PV>(line, clazz));
					} catch (Exception e) {
						TitanLogger.ee("MimicData", e);
					}
				}

				if(lastStep != null){
					for(int i = 0; i < 25; ++i){
						pathData.add(lastStep);
					}
				}
				
				TitanLogger.l("Loaded the Mimic file");
			} catch (IOException e) {
				TitanLogger.ee("Mimic", e);
			}

			return pathData;
		}

		public static <PV extends Enum<PV> & PropertyValue<?>> List<Step<PV>> optimize(final List<Step<PV>> in, final PV leftDistance, final PV rightDistance, final PV leftPower, final PV rightPower, final double tolerance){
			if(in == null || in.isEmpty()){
				return List.of();
			}else if(in.size() == 1){
				return in;
			}

			final List<Step<PV>> out = new ArrayList<>();
			Step<PV> lastStep = in.get(0);
			double drivePowerLeft = lastStep.getDouble(leftPower);
			double drivePowerRight = lastStep.getDouble(rightPower);
			for(int i = 1; i < in.size(); ++i){
				final Step<PV> step = in.get(i);
				final double stepLeftPower = step.getDouble(leftPower);
				final double stepRightPower = step.getDouble(rightPower);

				final double deltaLeft = Math.abs(step.getDouble(leftDistance) - lastStep.getDouble(leftDistance));
				final double deltaRight = Math.abs(step.getDouble(rightDistance) - lastStep.getDouble(rightDistance));
				drivePowerLeft = (drivePowerLeft + stepLeftPower) / 2.0;
				drivePowerRight = (drivePowerRight + stepRightPower) / 2.0;
				
				if(/*i > in.size() - 20 || */deltaLeft >= tolerance || deltaRight >= tolerance){
					final EnumMap<PV, Object> newValues = step.getValues();
					newValues.put(leftPower, drivePowerLeft);
					newValues.put(rightPower, drivePowerRight);
					
					final Step<PV> newStep = new Step<>(newValues);
					out.add(newStep);
					lastStep = newStep;
					
					drivePowerLeft = stepLeftPower;
					drivePowerRight = stepRightPower;
				}
			}
			return out;
		}
	}

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
