package frc.robot.command;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystem.DriveSubsystem;

public final class TeleOpDriveCommand extends CommandBase {

  private static final double DEADBAND = 0.05;
  private static final DriveSubsystem DRIVE = RobotContainer.DRIVE;
  private static final XboxController controls = RobotContainer.CONTROLS;

  public TeleOpDriveCommand() {
    addRequirements(DRIVE);
  }

  @Override
  public void execute() {
    double forward = deadband(controls.getRawAxis(1));
    double strafe = deadband(-controls.getRawAxis(0));
    double yaw = deadband(-controls.getRawAxis(4));

    DRIVE.drive(forward, strafe, yaw);
  }

  @Override
  public void end(boolean interrupted) {
    DRIVE.drive(0.0, 0.0, 0.0);
  }

  private double deadband(double value) {
    if (Math.abs(value) < DEADBAND) return 0.0;
    return value;
  }
}
