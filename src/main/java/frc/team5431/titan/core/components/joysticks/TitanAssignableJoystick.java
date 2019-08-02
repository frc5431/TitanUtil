package frc.team5431.titan.core.components.joysticks;

import java.util.Map;
import java.util.HashMap;
import java.util.function.Supplier;

import frc.team5431.titan.core.components.robot.TitanCommandQueue;
import frc.team5431.titan.core.components.robot.TitanRobot;

public class TitanAssignableJoystick<T extends TitanRobot<T>> extends TitanJoystick {
    private final Map<Integer, Supplier<TitanCommandQueue<T>>> assignments = new HashMap<>();
    private final TitanCommandQueue<T> currentQueue = new TitanCommandQueue<>();

    public void update(final T robot) {
        // Update all of the button commands
        for (final Integer button : assignments.keySet()) {
            getRawButton(button, true); // Call the queue update on the specified button
        }

        currentQueue.update(robot);
    }

    public TitanAssignableJoystick(final int port) {
        super(port);
    }

    public boolean getRawButton(final int but, boolean update) {
        final boolean value = super.getRawButton(but);
        if (assignments.containsKey(but) && value && update) {
            currentQueue.clear();

            // call the associated function from the index in the map and then add it to the
            // queue
            currentQueue.addAll(assignments.get(but).get());// get
        }

        return value;
    }

    public void assign(final int button, final Supplier<TitanCommandQueue<T>> generator) {
        assignments.put(button, generator);
    }

    public void assign(final ButtonZone button, final Supplier<TitanCommandQueue<T>> generator) {
        assign(((Enum<?>) button).ordinal(), generator);
    }
}