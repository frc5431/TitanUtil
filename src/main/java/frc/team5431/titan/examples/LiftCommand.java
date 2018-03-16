package frc.team5431.titan.examples;

import frc.team5431.titan.core.Titan;
import frc.team5431.titan.core.TitanRobot;

import static frc.team5431.titan.examples.LiftComponent.LiftSensors.LIFT_STOP;
import static frc.team5431.titan.examples.TitanUtilRobot.Components.LIFT;

public class LiftCommand extends Titan.Command<TitanRobot<TitanUtilRobot>> {
    public final boolean toTop;

    public LiftCommand(final boolean t) {
        toTop = t;
    }

    @Override
    public void init(final TitanRobot<TitanUtilRobot> robot) {
        name = "LiftCommand";
        properties = "Lifting something up!";

        Titan.l("Called once!");

        //Stop the lift
        robot.getComponent(LIFT).set(LiftComponent.Lift.STOP);
    }

    @Override
    public CommandResult update(final TitanRobot<TitanUtilRobot> robot) {
        Titan.l("Called on every update!");

        if (robot.getBooleanData(LIFT, LIFT_STOP, false)) {

        }

        if (((LiftComponent) robot.getComponent(LIFT)).atTop) {
            return CommandResult.COMPLETE;
        } else {
            robot.getComponent(LIFT).set((toTop) ? LiftComponent.Lift.UP : LiftComponent.Lift.DOWN);
        }

        return CommandResult.IN_PROGRESS;
    }

    @Override
    public void done(final TitanRobot robot) {
        Titan.l("Command completed");
        robot.getComponent(LIFT).set(LiftComponent.Lift.STOP);
    }
}
