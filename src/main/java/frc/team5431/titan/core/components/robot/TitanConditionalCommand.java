package frc.team5431.titan.core.components.robot;

import java.util.function.Function;

public class TitanConditionalCommand<T extends TitanRobot<T>> extends TitanCommand<T>{
    private final Function<T, Boolean> func;

    public TitanConditionalCommand(final Function<T, Boolean> func) {
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
