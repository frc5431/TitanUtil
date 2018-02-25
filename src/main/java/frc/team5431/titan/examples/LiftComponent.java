package frc.team5431.titan.examples;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.team5431.titan.core.TitanRobot;


public class LiftComponent extends TitanRobot.Component {
    private WPI_TalonSRX liftTalonSRX;
    private DigitalInput liftStop;
    public boolean atTop = false;

    public enum Lift implements TitanRobot.ComponentRun<LiftComponent> {
        STOP {
            @Override
            public void run(final LiftComponent c) {
                c.liftTalonSRX.set(0.0);
            }
        }, DOWN {
            @Override
            public void run(final LiftComponent c) {
                c.liftTalonSRX.set(-1.0);
            }
        }, UP {
            @Override
            public void run(final LiftComponent c) {
                if (c.atTop) {
                    c.liftTalonSRX.set(0.0);
                } else {
                    c.liftTalonSRX.set(1.0);
                }
            }
        }
    }

    @Override
    public void init() {
        liftTalonSRX = new WPI_TalonSRX(1); //On robot boot
        liftTalonSRX.setNeutralMode(NeutralMode.Brake);
        liftStop = new DigitalInput(0);
    }

    @Override
    public void update() {
        //THIS IS CALLED BEFORE RUN (PUT SENSOR UPDATE STUFF HERE)
        atTop = liftStop.get();
    }
}
