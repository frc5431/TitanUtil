package frc.team5431.titan.core;

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
    private final Map<MainComponent, Component<T, ?>> components = new HashMap<>();
    private final Map<Titan.Joystick.AxisGroup, Pair<Integer, Titan.Joystick.AxisZone[]>> axisGroups = new HashMap<>();

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

    public void addControllerCommand(final int index, final Titan.Joystick.ButtonZone button, final Titan.CommandQueue<TitanRobot> steps) {
        controllers.get(index).assign(button, () -> steps);
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

    public void addControllerAxisGroup(final int index, final Titan.Joystick.AxisGroup group, final Titan.Joystick.AxisZone... axis) {
        axisGroups.put(group, new Pair<>(index, axis));
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

    public void setCompState(final MainComponent map, final ComponentState state) {
        components.get(map).set(state);
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

    //Robot Components
    public void addComponent(final MainComponent map, final Component component) {
        components.put(map, component);
    }

    public Component comp(final MainComponent map) {
        return components.get(map);
    }

    public interface MainComponent {
    }

    public interface ComponentState {
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

    public static abstract class Component<T, D> {
        private ComponentState toRun = null;

        public abstract void init(final T robot);

        public abstract void update(final T robot, final D state);

        public void set(final ComponentState state) {
            toRun = state;
        }

        public D getState() {
            return (D) toRun;
        }
    }

    @Override
    public void testPeriodic() {

    }
}
