package frc.team5431.titan.core;

import edu.wpi.first.wpilibj.IterativeRobot;
import sun.rmi.rmic.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

public class TitanRobot<T> extends IterativeRobot {
    private final Titan.CommandQueue<T> autoQueue = new Titan.CommandQueue<>();
    private final Titan.CommandQueue<T> teleQueue = new Titan.CommandQueue<>();
    private List<Titan.AssignableJoystick<T>> controllers = new ArrayList<>();
    public static HashMap<MainComponent, Component> components = new HashMap<>();

    public interface MainComponent {
    }

    public interface ComponentRun<D> {
        void run(final D c);
    }

    public static abstract class Component {
        private ComponentRun toRun = null;

        public abstract void init();

        public abstract void update();

        public void set(final ComponentRun r) {
            toRun = r;
        }

        public void run() {
            toRun.run(this);
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

    /*
     * Control functions
     */
    //Controllers
    public void addController(Titan.AssignableJoystick<T> controller) {
        controllers.add(controller);
    }

    public Titan.AssignableJoystick<T> getController(final int index) {
        return controllers.get(index);
    }

    public void addControllerCommand(final int index, final Enum<?> button, final Titan.CommandQueue<TitanRobot> steps) {
        controllers.get(index).assign(button, (Supplier<Titan.CommandQueue<T>>) steps); //@TODO FIX THIS
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

    @Override
    public void robotInit() {
        for (final Component component : components.values()) {
            component.init();
        }
        init();
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
    public void teleopPeriodic() {
        for (final Component component : components.values()) {
            component.update();
            component.run();
        }
        teleUpdate();
    }

    @Override
    public void testPeriodic() {

    }
}
