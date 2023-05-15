package frc.team5431.titan.core.joysticks;

import edu.wpi.first.wpilibj.event.EventLoop;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.CommandGenericHID;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * A version of {@link HoriSteeringWheel} with {@link Trigger} factories for command-based.
 *
 * @see HoriSteeringWheel
 */
@SuppressWarnings("MethodName")
public class CommandHoriSteeringWheel extends CommandGenericHID {
  private final HoriSteeringWheel m_hid;

  /**
   * Construct an instance of a controller.
   *
   * @param port The port index on the Driver Station that the controller is plugged into.
   */
  public CommandHoriSteeringWheel(int port) {
    super(port);
    m_hid = new HoriSteeringWheel(port);
  }

  /**
   * Get the underlying GenericHID object.
   *
   * @return the wrapped GenericHID object
   */
  @Override
  public HoriSteeringWheel getHID() {
    return m_hid;
  }

  public Trigger back() {
    return back(CommandScheduler.getInstance().getDefaultButtonLoop());
  }

  public Trigger back(EventLoop loop) {
    return m_hid.back(loop).castTo(Trigger::new);
  }

  public Trigger start() {
    return start(CommandScheduler.getInstance().getDefaultButtonLoop());
  }

  public Trigger start(EventLoop loop) {
    return m_hid.start(loop).castTo(Trigger::new);
  }

  public Trigger l1() {
    return l1(CommandScheduler.getInstance().getDefaultButtonLoop());
  }

  public Trigger l1(EventLoop loop) {
    return m_hid.l1(loop).castTo(Trigger::new);
  }

  public Trigger r1() {
    return r1(CommandScheduler.getInstance().getDefaultButtonLoop());
  }

  public Trigger r1(EventLoop loop) {
    return m_hid.r1(loop).castTo(Trigger::new);
  }

  public Trigger l3() {
    return l3(CommandScheduler.getInstance().getDefaultButtonLoop());
  }

  public Trigger l3(EventLoop loop) {
    return m_hid.l3(loop).castTo(Trigger::new);
  }

  public Trigger r3() {
    return r3(CommandScheduler.getInstance().getDefaultButtonLoop());
  }

  public Trigger r3(EventLoop loop) {
    return m_hid.r3(loop).castTo(Trigger::new);
  }

  public Trigger a() {
    return a(CommandScheduler.getInstance().getDefaultButtonLoop());
  }

  public Trigger a(EventLoop loop) {
    return m_hid.a(loop).castTo(Trigger::new);
  }

  public Trigger b() {
    return b(CommandScheduler.getInstance().getDefaultButtonLoop());
  }

  public Trigger b(EventLoop loop) {
    return m_hid.b(loop).castTo(Trigger::new);
  }

  public Trigger x() {
    return x(CommandScheduler.getInstance().getDefaultButtonLoop());
  }

  public Trigger x(EventLoop loop) {
    return m_hid.x(loop).castTo(Trigger::new);
  }

  public Trigger y() {
    return y(CommandScheduler.getInstance().getDefaultButtonLoop());
  }

  public Trigger y(EventLoop loop) {
    return m_hid.y(loop).castTo(Trigger::new);
  }

  public Trigger l2() {
    return l2(CommandScheduler.getInstance().getDefaultButtonLoop());
  }

  public Trigger l2(double threshold) {
    return l2(CommandScheduler.getInstance().getDefaultButtonLoop(), threshold);
  }

  public Trigger l2(EventLoop loop) {
    return m_hid.l2(loop).castTo(Trigger::new);
  }

  public Trigger l2(EventLoop loop, double threshold) {
    return m_hid.l2(threshold, loop).castTo(Trigger::new);
  }

  public Trigger r2() {
    return r2(CommandScheduler.getInstance().getDefaultButtonLoop());
  }

  public Trigger r2(double threshold) {
    return r2(CommandScheduler.getInstance().getDefaultButtonLoop(), threshold);
  }

  public Trigger r2(EventLoop loop) {
    return m_hid.r2(loop).castTo(Trigger::new);
  }

  public Trigger r2(EventLoop loop, double threshold) {
    return m_hid.r2(threshold, loop).castTo(Trigger::new);
  }

  public Trigger leftPaddle() {
    return m_hid.leftPaddle(CommandScheduler.getInstance().getDefaultButtonLoop()).castTo(Trigger::new);
  }

  public Trigger rightPaddle() {
    return m_hid.rightPaddle(CommandScheduler.getInstance().getDefaultButtonLoop()).castTo(Trigger::new);
  }

  public double getWheel() {
    return m_hid.getWheel();
  }

  public double getPaddles() {
    return m_hid.getPaddles();
  }

  public double getLeftPedal() {
    return m_hid.getLeftPedal();
  }

  public double getRightPedal() {
    return m_hid.getRightPedal();
  }

  public double getL2() {
    return m_hid.getL2();
  }

  public double getR2() {
    return m_hid.getR2();
  }
}

