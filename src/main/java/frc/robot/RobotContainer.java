package frc.robot;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.command.TeleOpDriveCommand;
import frc.robot.subsystem.DriveSubsystem;
//import org.strykeforce.thirdcoast.telemetry.TelemetryController;
//import org.strykeforce.thirdcoast.telemetry.TelemetryService;

public class RobotContainer {
  //public static TelemetryService TELEMETRY;
  public static DriveSubsystem DRIVE = new DriveSubsystem();
  public static XboxController CONTROLS = new XboxController(0);

  public RobotContainer() {

    if (RobotBase.isReal()) {

      //TELEMETRY = new TelemetryService(TelemetryController::new);


      //TELEMETRY.start();

      DRIVE.setDefaultCommand(new TeleOpDriveCommand());

      //Zero Gyro Command
      new JoystickButton(CONTROLS, Button.kA.value)
        .whenPressed(() -> DRIVE.zeroGyro());

      //Zero Azimuths Command
      new JoystickButton(CONTROLS, Button.kB.value)
        .whenPressed(() -> DRIVE.zeroAzimuths());
      
      //Save Azimuth zeroes Command
      new JoystickButton(CONTROLS, Button.kX.value)
        .whenPressed(() -> DRIVE.saveAzimuthPositions());
    }
  }
}
