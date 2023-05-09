package frc.team5431.titan.core.joysticks;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.event.BooleanEvent;
import edu.wpi.first.wpilibj.event.EventLoop;

public class HoriSteeringWheel extends GenericHID {
    /** Represents a digital button on a HORI Steering Wheel. */
    public enum Button {
        kA(1),
        kB(2),
        kX(3),
        kY(4),
        kL1(5),
        kR1(6),
        kBack(7),
        kStart(8),
        kL3(9),
        kR3(10);

        public final int value;

        Button(int value) {
            this.value = value;
        }

        /**
         * Get the human-friendly name of the button, matching the relevant methods. This is done by
         * stripping the leading `k`, and if not a Bumper button append `Button`.
         *
         * <p>Primarily used for automated unit tests.
         *
         * @return the human-friendly name of the button.
         */
        @Override
        public String toString() {
            var name = this.name().substring(1); // Remove leading `k`
            return name + "Button";
        }
    }

    /** Represents an axis on an XboxController. */
    public enum Axis {
        kWheel(0),
        kPaddles(1),
        kLeftPedal(2),
        kRightPedal(3),
        kL2(4),
        kR2(5);

        public final int value;

        Axis(int value) {
            this.value = value;
        }

        /**
         * Get the human-friendly name of the axis, matching the relevant methods. This is done by
         * stripping the leading `k`, and if a trigger axis append `Axis`.
         *
         * <p>Primarily used for automated unit tests.
         *
         * @return the human-friendly name of the axis.
         */
        @Override
        public String toString() {
            var name = this.name().substring(1); // Remove leading `k`
            return name;
        }
    }

    /**
     * Construct an instance of a controller.
     *
     * @param port The port index on the Driver Station that the controller is plugged into.
     */
    public HoriSteeringWheel(final int port) {
        super(port);
    }

    public double getWheel() {
        return getRawAxis(Axis.kWheel.value);
    }

    /**
     * Return value of the paddles axis. -1 for left paddle, 1 for right paddle, and 0 for no paddle.
     * 
     * @return value of the paddles axis
     */
    public double getPaddles() {
        return getRawAxis(Axis.kPaddles.value);
    }

    public double getLeftPedal() {
        return getRawAxis(Axis.kLeftPedal.value);
    }

    public double getRightPedal() {
        return getRawAxis(Axis.kRightPedal.value);
    }

    public double getL2() {
        return getRawAxis(Axis.kL2.value);
    }

    public double getR2() {
        return getRawAxis(Axis.kR2.value);
    }


    public boolean getAButton() {
        return getRawButton(Button.kA.value);
    }

    public boolean getAButtonPressed() {
        return getRawButtonPressed(Button.kA.value);
    }

    public boolean getAButtonReleased() {
        return getRawButtonReleased(Button.kA.value);
    }

    /**
     * Constructs an event instance around the A button's digital signal.
     *
     * @param loop the event loop instance to attach the event to.
     * @return an event instance representing the A button's digital signal attached to the given
     *     loop.
     */
    @SuppressWarnings("MethodName")
    public BooleanEvent a(EventLoop loop) {
        return new BooleanEvent(loop, this::getAButton);
    }

    public boolean getBButton() {
        return getRawButton(Button.kB.value);
    }

    public boolean getBButtonPressed() {
        return getRawButtonPressed(Button.kB.value);
    }

    public boolean getBButtonReleased() {
        return getRawButtonReleased(Button.kB.value);
    }

    /**
     * Constructs an event instance around the B button's digital signal.
     *
     * @param loop the event loop instance to attach the event to.
     * @return an event instance representing the B button's digital signal attached to the given
     *     loop.
     */
    @SuppressWarnings("MethodName")
    public BooleanEvent b(EventLoop loop) {
        return new BooleanEvent(loop, this::getBButton);
    }

    public boolean getXButton() {
        return getRawButton(Button.kX.value);
    }

    public boolean getXButtonPressed() {
        return getRawButtonPressed(Button.kX.value);
    }

    public boolean getXButtonReleased() {
        return getRawButtonReleased(Button.kX.value);
    }

    /**
     * Constructs an event instance around the X button's digital signal.
     *
     * @param loop the event loop instance to attach the event to.
     * @return an event instance representing the X button's digital signal attached to the given
     *     loop.
     */
    @SuppressWarnings("MethodName")
    public BooleanEvent x(EventLoop loop) {
        return new BooleanEvent(loop, this::getXButton);
    }

    public boolean getYButton() {
        return getRawButton(Button.kY.value);
    }

    public boolean getYButtonPressed() {
        return getRawButtonPressed(Button.kY.value);
    }

    public boolean getYButtonReleased() {
        return getRawButtonReleased(Button.kY.value);
    }

    /**
     * Constructs an event instance around the Y button's digital signal.
     *
     * @param loop the event loop instance to attach the event to.
     * @return an event instance representing the Y button's digital signal attached to the given
     *     loop.
     */
    @SuppressWarnings("MethodName")
    public BooleanEvent y(EventLoop loop) {
        return new BooleanEvent(loop, this::getYButton);
    }

    public boolean getBackButton() {
        return getRawButton(Button.kBack.value);
    }

    public boolean getBackButtonPressed() {
        return getRawButtonPressed(Button.kBack.value);
    }

    public boolean getBackButtonReleased() {
        return getRawButtonReleased(Button.kBack.value);
    }

    /**
     * Constructs an event instance around the Back button's digital signal.
     *
     * @param loop the event loop instance to attach the event to.
     * @return an event instance representing the Back button's digital signal attached to the given
     *     loop.
     */
    @SuppressWarnings("MethodName")
    public BooleanEvent back(EventLoop loop) {
        return new BooleanEvent(loop, this::getBackButton);
    }

    public boolean getStartButton() {
        return getRawButton(Button.kStart.value);
    }

    public boolean getStartButtonPressed() {
        return getRawButtonPressed(Button.kStart.value);
    }

    public boolean getStartButtonReleased() {
        return getRawButtonReleased(Button.kStart.value);
    }

    /**
     * Constructs an event instance around the Start button's digital signal.
     *
     * @param loop the event loop instance to attach the event to.
     * @return an event instance representing the Start button's digital signal attached to the given
     *     loop.
     */
    @SuppressWarnings("MethodName")
    public BooleanEvent start(EventLoop loop) {
        return new BooleanEvent(loop, this::getStartButton);
    }

    public boolean getL1Button() {
        return getRawButton(Button.kL1.value);
    }

    public boolean getL1ButtonPressed() {
        return getRawButtonPressed(Button.kL1.value);
    }

    public boolean getL1ButtonReleased() {
        return getRawButtonReleased(Button.kL1.value);
    }

    /**
     * Constructs an event instance around the L1 button's digital signal.
     *
     * @param loop the event loop instance to attach the event to.
     * @return an event instance representing the L1 button's digital signal attached to the given
     *     loop.
     */
    @SuppressWarnings("MethodName")
    public BooleanEvent l1(EventLoop loop) {
        return new BooleanEvent(loop, this::getL1Button);
    }

    public boolean getR1Button() {
        return getRawButton(Button.kR1.value);
    }

    public boolean getR1ButtonPressed() {
        return getRawButtonPressed(Button.kR1.value);
    }

    public boolean getR1ButtonReleased() {
        return getRawButtonReleased(Button.kR1.value);
    }

    /**
     * Constructs an event instance around the R1 button's digital signal.
     *
     * @param loop the event loop instance to attach the event to.
     * @return an event instance representing the R1 button's digital signal attached to the given
     *     loop.
     */
    @SuppressWarnings("MethodName")
    public BooleanEvent r1(EventLoop loop) {
        return new BooleanEvent(loop, this::getR1Button);
    }

    public boolean getL3Button() {
        return getRawButton(Button.kL3.value);
    }

    public boolean getL3ButtonPressed() {
        return getRawButtonPressed(Button.kL3.value);
    }

    public boolean getL3ButtonReleased() {
        return getRawButtonReleased(Button.kL3.value);
    }

    /**
     * Constructs an event instance around the L3 button's digital signal.
     *
     * @param loop the event loop instance to attach the event to.
     * @return an event instance representing the L3 button's digital signal attached to the given
     *     loop.
     */
    @SuppressWarnings("MethodName")
    public BooleanEvent l3(EventLoop loop) {
        return new BooleanEvent(loop, this::getL3Button);
    }

    public boolean getR3Button() {
        return getRawButton(Button.kR3.value);
    }

    public boolean getR3ButtonPressed() {
        return getRawButtonPressed(Button.kR3.value);
    }

    public boolean getR3ButtonReleased() {
        return getRawButtonReleased(Button.kR3.value);
    }

    /**
     * Constructs an event instance around the R3 button's digital signal.
     *
     * @param loop the event loop instance to attach the event to.
     * @return an event instance representing the R3 button's digital signal attached to the given
     *     loop.
     */
    @SuppressWarnings("MethodName")
    public BooleanEvent r3(EventLoop loop) {
        return new BooleanEvent(loop, this::getR3Button);
    }

    /**
     * Constructs an event instance around the axis value of L2. The returned trigger
     * will be true when the axis value is greater than {@code threshold}.
     *
     * @param threshold the minimum axis value for the returned {@link BooleanEvent} to be true. This
     *     value should be in the range [0, 1] where 0 is the unpressed state of the axis.
     * @param loop the event loop instance to attach the event to.
     * @return an event instance that is true when the L2's axis exceeds the provided
     *     threshold, attached to the given event loop
     */
    public BooleanEvent l2(double threshold, EventLoop loop) {
        return new BooleanEvent(loop, () -> getL2() > threshold);
    }

    /**
     * Constructs an event instance around the axis value of the L2. The returned trigger
     * will be true when the axis value is greater than 0.5.
     *
     * @param loop the event loop instance to attach the event to.
     * @return an event instance that is true when the L2's axis exceeds the provided
     *     threshold, attached to the given event loop
     */
    public BooleanEvent l2(EventLoop loop) {
        return l2(0.5, loop);
    }

    /**
     * Constructs an event instance around the axis value of R2. The returned trigger
     * will be true when the axis value is greater than {@code threshold}.
     *
     * @param threshold the minimum axis value for the returned {@link BooleanEvent} to be true. This
     *     value should be in the range [0, 1] where 0 is the unpressed state of the axis.
     * @param loop the event loop instance to attach the event to.
     * @return an event instance that is true when the R2's axis exceeds the provided
     *     threshold, attached to the given event loop
     */
    public BooleanEvent r2(double threshold, EventLoop loop) {
        return new BooleanEvent(loop, () -> getR2() > threshold);
    }

    /**
     * Constructs an event instance around the axis value of the R2. The returned trigger
     * will be true when the axis value is greater than 0.5.
     *
     * @param loop the event loop instance to attach the event to.
     * @return an event instance that is true when the R2's axis exceeds the provided
     *     threshold, attached to the given event loop
     */
    public BooleanEvent r2(EventLoop loop) {
        return r2(0.5, loop);
    }

    /**
     * Constructs an event instance around the axis value of the left paddle. The returned trigger
     * will be true when the axis value is less than -0.5.
     *
     * @param loop the event loop instance to attach the event to.
     * @return an event instance that is true when the paddle's axis indicates left,
     *     attached to the given event loop
     */
    public BooleanEvent leftPaddle(EventLoop loop) {
        return new BooleanEvent(loop, () -> getPaddles() < -0.5);
    }

    /**
     * Constructs an event instance around the axis value of the right paddle. The returned trigger
     * will be true when the axis value is greater than 0.5.
     *
     * @param loop the event loop instance to attach the event to.
     * @return an event instance that is true when the paddle's axis indicates right,
     *     attached to the given event loop
     */
    public BooleanEvent rightPaddle(EventLoop loop) {
        return new BooleanEvent(loop, () -> getPaddles() > 0.5);
    }
}
