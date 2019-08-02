package frc.team5431.titan.core.components.robot;

import java.util.Collection;
import java.util.LinkedList;

import frc.team5431.titan.core.components.Logger;

public class CommandQueue<T extends Robot<T>> extends LinkedList<Command<T>> {
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
            Logger.l("Starting with %s (%s)", initCommand.getName(), initCommand.getProperties());
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
        final Command.CommandResult result = command.update(robot);
        if (result == Command.CommandResult.IN_PROGRESS) {
            return true;
        } else {
            final double secondsElapsed = command.getSecondsElapsed();
            Logger.l("Finished %s (Seconds: %.2f)", command.getName(), secondsElapsed);
            command.done(robot);
            if (result == Command.CommandResult.COMPLETE) {
                remove();
                final Command<T> nextCommand = peek();
                if (nextCommand != null) {
                    nextCommand.startTimer();
                    nextCommand.init(robot);
                    Logger.l("Starting %s (%s)", nextCommand.getName(), nextCommand.getProperties());
                } else {
                    return false;
                }
            } else if (result == Command.CommandResult.CLEAR_QUEUE) {
                clear();
                Logger.l("Cleared queue");
            } else if (result == Command.CommandResult.RESTART_COMMAND) {
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