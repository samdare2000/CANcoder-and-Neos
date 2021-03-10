package org.strykeforce.thirdcoast.swerve;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.TimedRobot;
import org.strykeforce.thirdcoast.swerve.MotorControllerConfig.AzimuthMotorController;
import org.strykeforce.thirdcoast.swerve.MotorControllerConfig.DriveMotorController;
import org.strykeforce.thirdcoast.swerve.MotorControllerConfig.FeedbackSensor;

public class SwerveDriveConfig {

  /**
   * NavX gyro connected to MXP SPI port, used for field-oriented driving. If null, field-oriented
   * driving is disabled.
   */
  public AHRS gyro;

  /** Initialize with four initialized wheels, in order from wheel 0 to wheel 3. */
  public Wheel[] wheels;

  /** Wheel base length from front to rear of robot. */
  public double length = 1.0;

  /** Wheel base width from left to right of robot. */
  public double width = 1.0;

  /** Max Drive Motor Velocity for closed-loop control */
  public double driveSetpointMax = 5500.0;

  /**
   * Robot period is the {@code TimedRobot} period in seconds, defaults to {@code
   * TimedRobot.kDefaultPeriod}.
   */
  public double robotPeriod = TimedRobot.kDefaultPeriod;

  /** Factor to correct gyro lag when simultaneously applying azimuth and drive. */
  public double gyroRateCoeff = 0.0;

  /** Log gyro errors, set to false if too spammy. */
  public boolean gyroLoggingEnabled = true;

  /**
   * Summarize Talon configuration errors. If false, will log error messages as each error is
   * encountered.
   */
  public boolean summarizeTalonErrors = false;

  /* if wheels form an x pattern when only applying yaw (right x stick), change to false: hardware dependent */
  public boolean invertError = true;

  /* number of ticks per revolution, defaults to 4096 for CTRE magencoder */
  public int azimuthTicks = 4096;

  /** Configs for the azimuth and drive motor controllers * */
  public MotorControllerConfig azimuthConfig =
      new MotorControllerConfig(AzimuthMotorController.SPARK_MAX, FeedbackSensor.CAN_CODER);

  public MotorControllerConfig driveConfig =
      new MotorControllerConfig(DriveMotorController.SPARK_MAX, FeedbackSensor.INTEGRATED_SENSOR);

  public Wheel[] getWheels() {
    Wheel[] wheels = new Wheel[4];

    for (int i = 0; i < 4; i++) {
      // Azimuth
      MotorControllerWrapper azimuth;

      switch (azimuthConfig.azimuthController) {
        case VICTOR_SPX:
          azimuth = new VictorSPXWrapper(azimuthConfig, i);
          break;
        case TALON_SRX:
          azimuth = new TalonSRXWrapper(azimuthConfig, i);
          break;
        case SPARK_MAX:
          azimuth = new SparkMaxWrapper(azimuthConfig, i);
          break;
        default:
          azimuth = new TalonSRXWrapper(azimuthConfig, i);
          break;
      }

      // Drive
      MotorControllerWrapper drive;

      switch (driveConfig.driveController) {
        case SPARK_MAX:
          drive = new SparkMaxWrapper(driveConfig, i + 10);
          break;
        case TALON_FX:
          drive = new TalonFXWrapper(driveConfig, i + 10);
          break;
        default:
          drive = new SparkMaxWrapper(driveConfig, i + 10);
          break;
      }

      Wheel wheel = new Wheel(azimuth, drive, driveSetpointMax, azimuthTicks, invertError);

      wheels[i] = wheel;
    }

    return wheels;
  }
}
