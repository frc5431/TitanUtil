
import static org.junit.Assert.assertEquals;

import org.junit.*;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team5431.titan.core.robot.ThreadedCommand;

public class ThreadedCommandTest {

    @Test(expected =  NullPointerException.class)
    public void nullCommand() {
        new ThreadedCommand(null, 0.0);
    }

    @SuppressWarnings("null")
    @Test(expected = NullPointerException.class)
    public void nullPeriod() {
        double testPeriod = (double) ((Object) null);
        new ThreadedCommand(new InstantCommand(() -> {}), testPeriod);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativePeriod() {
        new ThreadedCommand(new InstantCommand(() -> {}), -0.02);
    }

    @Test
    public void sameRequirments() {
        SubsystemBase dummyBase = new SubsystemBase() {
        };
        InstantCommand dummyCommand = new InstantCommand(() -> {}, dummyBase);
        ThreadedCommand testCommand = new ThreadedCommand(dummyCommand, 0.02);

        assertEquals(dummyCommand.getRequirements(), testCommand.getRequirements());
    }

    @Test
    public void copyPeriod() {

        double dummyPeriodValue1 = 0.05;   // 50hz
        double dummyPeriodValue2 = 0.01;   // 100hz
        double dummyPeriodValue3 = 0.0083; // 120hz
        double dummyPeriodValue4 = 0.005;  // 200hz

        InstantCommand dummyCommand = new InstantCommand(() -> {});

        ThreadedCommand testCommand1 = new ThreadedCommand(dummyCommand, dummyPeriodValue1);
        assert(testCommand1.getPeriod() == dummyPeriodValue1);

        ThreadedCommand testCommand2 = new ThreadedCommand(dummyCommand, dummyPeriodValue2);
        assert(testCommand2.getPeriod() == dummyPeriodValue2);

        ThreadedCommand testCommand3 = new ThreadedCommand(dummyCommand, dummyPeriodValue3);
        assert(testCommand3.getPeriod() == dummyPeriodValue3);

        ThreadedCommand testCommand4 = new ThreadedCommand(dummyCommand, dummyPeriodValue4);
        assert(testCommand4.getPeriod() == dummyPeriodValue4);
        
    }

}