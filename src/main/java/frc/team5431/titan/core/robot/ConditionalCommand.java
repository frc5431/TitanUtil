package frc.team5431.titan.core.robot;

import java.util.function.Function;

public class ConditionalCommand<T extends Robot<T>> extends Command<T>{
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
