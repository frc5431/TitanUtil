package frc.team5431.titan.core;

import com.sun.tools.javac.Main;
import com.sun.tools.javac.util.Pair;
import edu.wpi.first.wpilibj.IterativeRobot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TitanRobot<T> extends IterativeRobot {
    private final Titan.CommandQueue<T> autoQueue = new Titan.CommandQueue<>();
    private final Titan.CommandQueue<T> teleQueue = new Titan.CommandQueue<>();
    private final List<Titan.AssignableJoystick<TitanRobot>> controllers = new ArrayList<>();
    private final Map<MainComponent, Component<T, ?, ?>> components = new HashMap<>();
    private final Map<Titan.Joystick.AxisGroup, Pair<Integer, Titan.Joystick.AxisZone[]>> axisGroups = new HashMap<>();

    public void addCommand(final int index, final Titan.Joystick.ButtonZone button, final Titan.CommandQueue<TitanRobot> steps) {
        controllers.get(index).assign(button, () -> steps);
    }

    public void addAxisGroup(final int index, final Titan.Joystick.AxisGroup group, final Titan.Joystick.AxisZone... axis) {
        axisGroups.put(group, new Pair<>(index, axis));
    }

    public void addCustom(final int index, final Titan.Joystick.ButtonZone button, final Titan.AssignableJoystick.CustomJoystickControl control) {
        controllers.get(index).assignCustom(button, control);
    }

    public double[] getAxisGroup(final Titan.Joystick.AxisGroup group) {
        final Pair<Integer, Titan.Joystick.AxisZone[]> aGroup = axisGroups.get(group);
        final int index = aGroup.fst;
        final double data[] = new double[aGroup.snd.length];
        for (int ind = 0; ind < aGroup.snd.length; ind++) {
            data[ind] = controllers.get(index).getRawAxis(aGroup.snd[ind]);
        }
        return data;
    }

    /*
     * Control functions
     */
    //Controllers
    public void addController(Titan.AssignableJoystick<TitanRobot> controller) {
        controllers.add(controller);
    }

    public Titan.AssignableJoystick<TitanRobot> getController(final int index) {
        return controllers.get(index);
    }

    //Robot Components
    public void add(final MainComponent map, final Component component) {
        components.put(map, component);
    }

    public Component comp(final MainComponent map) {
        return components.get(map);
    }

    public void setState(final MainComponent map, final ComponentState state) {
        components.get(map).set(state);
    }

    public <G> G getData(final MainComponent map, final SensorType type) {
        try {
            return (G) components.get(map).getData(type);
        } catch (Exception err) {
            Titan.ee("SensorData", err);
            return null;
        }
    }

    public boolean getBooleanData(final MainComponent map, final SensorType type, final boolean defaultValue) {
        final Object ret = getData(map, type);
        if (ret != null) {
            return (boolean) ret;
        } else {
            Titan.e("Returning default boolean value (sensor data error)");
            return defaultValue;
        }
    }

    public double getDoubleData(final MainComponent map, final SensorType type, final double defaultValue) {
        final Object ret = getData(map, type);
        if (ret != null) {
            return (double) ret;
        } else {
            Titan.e("Returning default double value (sensor data error)");
            return defaultValue;
        }
    }

    public int getIntData(final MainComponent map, final SensorType type, final int defaultValue) {
        final Object ret = getData(map, type);
        if (ret != null) {
            return (int) ret;
        } else {
            Titan.e("Returning default int value (sensor data error)");
            return defaultValue;
        }
    }

    public interface MainComponent {
    }

    public interface ComponentState {
    }

    public interface SensorType {
    }

    public static abstract class Component<T, D, E> {
        private ComponentState toRun = null;

        public abstract void init(final T robot);

        public abstract void update(final T robot, final D state);

        public abstract Object sensorData(final E sensor);

        public void set(final ComponentState state) {
            toRun = state;
        }

        public <G> G getData(final SensorType type) {
            try {
                return (G) sensorData((E) type);
            } catch (Exception err) {
                Titan.ee("Component", err);
                return null;
            }
        }

        public D getState() {
            return (D) toRun;
        }
    }

    /*
     * Override functions
     */
    public void init() {
        Titan.l("Override start!");
    }

    public void autoInit() {
        Titan.l("Override autoInit!");
    }

    public void autoUpdate() {
        Titan.l("Override autoUpdate!");
    }

    public void teleInit() {
        Titan.l("Override teleInit!");
    }

    public void teleUpdate() {
        Titan.l("Override teleUpdate!");
    }

    @Override
    public void robotInit() {
        for (final Component component : components.values()) {
            component.init(this);
        }
        init();
    }

    @Override
    public void teleopPeriodic() {
        for (final Titan.AssignableJoystick<TitanRobot> controller : controllers) {
            controller.update(this);
        }

        for (final Component component : components.values()) {
            final ComponentState state = (ComponentState) component.getState();
            if (state != null) {
                component.update(this, component.getState());
            } else {
                Titan.e("A component hasn't been set a state!");
            }
        }
        teleUpdate();
    }

    public void addAuto(Titan.Command<T> command) {
        autoQueue.add(command);
    }

    public void addTele(Titan.Command<T> command) {
        teleQueue.add(command);
    }

    @Override
    public void disabledInit() {

    }

    @Override
    public void autonomousInit() {
        autoInit();
    }

    @Override
    public void teleopInit() {
        teleInit();
    }

    @Override
    public void testInit() {

    }

    @Override
    public void disabledPeriodic() {

    }

    @Override
    public void autonomousPeriodic() {
        autoUpdate();
    }

    @Override
    public void testPeriodic() {

    }
}
