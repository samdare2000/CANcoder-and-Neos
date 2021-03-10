package frc.robot.subsystem;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.strykeforce.thirdcoast.swerve.SwerveDrive;
import org.strykeforce.thirdcoast.swerve.SwerveDriveConfig;

public class DriveSubsystem extends SubsystemBase {

  private static final double ROBOT_LENGTH = 1.0;
  private static final double ROBOT_WIDTH = 1.0;

  private final SwerveDrive swerve = getSwerve();

  //private final Logger logger = LoggerFactory.getLogger(this.getClass());

  public DriveSubsystem() {
    swerve.setFieldOriented(true);
    zeroAzimuths();
  }

  

  public void drive(double forward, double strafe, double yaw) {
    swerve.drive(forward, strafe, yaw);
  }

  public void zeroGyro() {
    AHRS gyro = swerve.getGyro();
    gyro.setAngleAdjustment(0);
    double adj = gyro.getAngle() % 360;
    gyro.setAngleAdjustment(-adj);
    //logger.info("resetting gyro: ({})", adj);
  }

  public void zeroAzimuths() {
    swerve.zeroAzimuthEncoders();
  }

  public void saveAzimuthPositions() {
    swerve.saveAzimuthPositions();
  }

  // Swerve configuration

  private SwerveDrive getSwerve() {
    SwerveDriveConfig config = new SwerveDriveConfig();
    
    config.gyro = new AHRS(SPI.Port.kMXP);
    config.length = ROBOT_LENGTH;
    config.width = ROBOT_WIDTH;
    config.gyroLoggingEnabled = true;
    config.summarizeTalonErrors = false;
    
    /* Update Motor controller configs before calling get wheels*/
    // EX. config.driveConfig.slot0.kP = 3.0;
    
    config.wheels = config.getWheels();
    return new SwerveDrive(config);
  }
}
