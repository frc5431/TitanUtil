package frc.team5431.titan.core.robot;

import java.util.Set;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;

/**
 * 
 * This class will allow you to wrap a command inside a seperate thread and
 * still act as a command
 * 
 * @deprecated This is a bad idea; alternatively, look at
 *             https://docs.wpilib.org/en/stable/docs/software/convenience-features/scheduling-functions.html
 * 
 * @author Ryan Hirasaki
 */
@Deprecated
public class ThreadedCommand implements Command {

    private static class CommandExecuter extends Thread {

        private final long period;
        private Command baseCommand;

        public CommandExecuter(Command baseCommand, double period) {
            this.period = (long) period * 1000;
            this.baseCommand = baseCommand;
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted() && !baseCommand.isFinished()) {
                    baseCommand.execute();

                    try {
                        Thread.sleep(period);
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            } finally {
                baseCommand = null;
            }
        }
    }

    private final double period;
    private final Command baseCommand;
    private final CommandExecuter commandExecuter;

    /**
     * Creates A threaded command that will call a specified command at a different
     * rate than the command scheduler would normally allow.
     * 
     * @param baseCommand the command that will be running in a seperate thread
     * @param period      period of time between the execute calls. If zero, then
     *                    the command will be ran as fast as possible
     * 
     * @throws IllegalArgumentException thrown when the period is less than zero
     * @throws NullPointerException     thrown if the base command or period is null
     */
    public ThreadedCommand(CommandBase baseCommand, double period) {
        if (period < 0)
            throw new IllegalArgumentException();
        if ((Object) period == null)
            throw new NullPointerException();
        if (baseCommand == null)
            throw new NullPointerException();

        this.baseCommand = baseCommand;
        this.period = period;

        commandExecuter = new CommandExecuter(baseCommand, period);
    }

    @Override
    public void initialize() {
        // Run base command init
        baseCommand.initialize();
    }

    @Override
    public void execute() {
        if (!commandExecuter.isAlive())
            commandExecuter.start();
    }

    @Override
    public void end(boolean interrupted) {
        commandExecuter.interrupt();
        baseCommand.end(interrupted);
    }

    @Override
    public String getName() {
        return String.format("Threaded %s", baseCommand.getName());
    }

    @Override
    public boolean runsWhenDisabled() {
        return baseCommand.runsWhenDisabled();
    }

    @Override
    public Set<Subsystem> getRequirements() {
        return baseCommand.getRequirements();
    }

    public double getPeriod() {
        return period;
    }
}