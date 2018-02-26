package frc.team5431.titan.examples;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.team5431.titan.core.TitanRobot;


public class LiftComponent extends TitanRobot.Component<TitanUtilRobot, LiftComponent.Lift, LiftComponent.LiftSensors> {
    private WPI_TalonSRX liftTalonSRX;
    private DigitalInput liftStop;
    public boolean atTop = false;

    @Override
    public Boolean sensorData(LiftSensors sensor) {
        return atTop;
    }

    public enum Lift implements TitanRobot.ComponentState {
        STOP, DOWN, UP
    }

    @Override
    public void init(final TitanUtilRobot robot) {
        liftTalonSRX = new WPI_TalonSRX(1); //On robot boot
        liftTalonSRX.setNeutralMode(NeutralMode.Brake);
        liftStop = new DigitalInput(0);
    }

    @Override
    public void update(final TitanUtilRobot robot, final Lift state) {
        atTop = liftStop.get();
        switch (state) {
            case UP:
                if (atTop) {
                    liftTalonSRX.set(0.0);
                } else {
                    liftTalonSRX.set(1.0);
                }
                break;
            case DOWN:
                liftTalonSRX.set(-1.0);
                break;
            case STOP:
                liftTalonSRX.set(0.0);
                break;
        }
    }

    public enum LiftSensors implements TitanRobot.SensorType {
        LIFT_STOP
    }
}
