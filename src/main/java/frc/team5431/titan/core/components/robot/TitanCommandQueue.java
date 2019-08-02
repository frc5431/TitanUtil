package frc.team5431.titan.core.components.robot;

import java.util.Collection;
import java.util.LinkedList;

import frc.team5431.titan.core.components.TitanLogger;

public class TitanCommandQueue<T extends TitanRobot<T>> extends LinkedList<TitanCommand<T>> {
    /**
     * 
     */
    public static final long serialVersionUID = 1L;

    public TitanCommandQueue(){
        super();
    }

    public TitanCommandQueue(final Collection<TitanCommand<T>> col){
        super(col);
    }

    public void init(final T robot) {
        // Initialize the first command
        final TitanCommand<T> initCommand = peek();
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

        final TitanCommand<T> command = peek();
        final TitanCommand.CommandResult result = command.update(robot);
        if (result == TitanCommand.CommandResult.IN_PROGRESS) {
            return true;
        } else {
            final double secondsElapsed = command.getSecondsElapsed();
            TitanLogger.l("Finished %s (Seconds: %.2f)", command.getName(), secondsElapsed);
            command.done(robot);
            if (result == TitanCommand.CommandResult.COMPLETE) {
                remove();
                final TitanCommand<T> nextCommand = peek();
                if (nextCommand != null) {
                    nextCommand.startTimer();
                    nextCommand.init(robot);
                    TitanLogger.l("Starting %s (%s)", nextCommand.getName(), nextCommand.getProperties());
                } else {
                    return false;
                }
            } else if (result == TitanCommand.CommandResult.CLEAR_QUEUE) {
                clear();
                TitanLogger.l("Cleared queue");
            } else if (result == TitanCommand.CommandResult.RESTART_COMMAND) {
                command.startTimer();
                command.init(robot);
            }
        }

        return true;
    }

    public boolean done(final T robot){
        if(!isEmpty()){
            final TitanCommand<T> command = peek();
            command.done(robot);
            clear();
            return true;
        }
        return false;
    }
}