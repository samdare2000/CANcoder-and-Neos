package org.strykeforce.thirdcoast.swerve;

public class MotorControllerConfig {
  /**
   * peakCurrentLimit -> Talon: peakCurrentLimit, SparkMax: secondaryCurrentLimit
   * continuousCurrentLimit -> Talon: continuousCurrentLimit, SparkMax: smartCurrentLimit
   *
   * <p>motionAcceleration -> Talon: motionAcceleration, SparkMax.PIDController:
   * smartMotionAcceleration motionCruiseVelocity -> Talon: motionCruiseVelocity,
   * SparkMax.PIDController: smartMotionMaxVelocity
   *
   * <p>voltageCompensation -> Talon: voltageCompSaturation, SparkMax: voltageCompensation
   */
  public double peakCurrentLimit,
      motionAcceleration,
      motionCruiseVelocity,
      voltageCompensation = 0.0;

  public int continuousCurrentLimit = 0;

  public Boolean isAzimuth = true;

  /** Supported Feedback Sensors * */
  public enum FeedbackSensor {
    CTRE_MAG_ENCODER,
    CAN_CODER,
    // THRIFTY_CODER,
    INTEGRATED_SENSOR
  }

  public FeedbackSensor feedbackSensor = FeedbackSensor.CTRE_MAG_ENCODER;

  /** Supported Drive Motor Controllers * */
  public enum DriveMotorController {
    SPARK_MAX,
    TALON_FX
  }

  public DriveMotorController driveController = DriveMotorController.SPARK_MAX;

  /** Supported Azimuth Motor Controllers * */
  public enum AzimuthMotorController {
    VICTOR_SPX,
    TALON_SRX,
    SPARK_MAX
  }

  public AzimuthMotorController azimuthController = AzimuthMotorController.TALON_SRX;

  /** Motor Controller Neutral Modes * */
  public enum NeutralMode {
    BRAKE,
    COAST
  }

  public NeutralMode neutralMode = NeutralMode.BRAKE;

  /** update ONLY for Spark Max azimuth controllers with a brushed motor * */
  public enum MotorType {
    BRUSHED,
    BRUSHLESS
  }

  public MotorType motorType = MotorType.BRUSHLESS;

  public MotorControllerConfig(AzimuthMotorController azimuth, FeedbackSensor sensor) {
    // update values for azimuth
    isAzimuth = true;
    azimuthController = azimuth;
    feedbackSensor = sensor;
    neutralMode = NeutralMode.COAST;
    peakCurrentLimit = 30.0;
    continuousCurrentLimit = 15;
    motionAcceleration = 10000.0;
    motionCruiseVelocity = 800.0;
    voltageCompensation = 12.0;
  }

  MotorControllerConfig(DriveMotorController drive, FeedbackSensor sensor) {
    // update values for drive
    isAzimuth = false;
    driveController = drive;
    feedbackSensor = sensor;
    neutralMode = NeutralMode.BRAKE;

    peakCurrentLimit = 80.0;
    continuousCurrentLimit = 60;
    motionAcceleration =
        20000.0; // Should probably never be used, but stops the spark maxes from throwing an error.
    motionCruiseVelocity = 5500.0;
    voltageCompensation = 12.0;
  }

  // 4 slots for each motor controller -- might need to be moved inside constructor
  public PIDFSlot slot0 = new PIDFSlot(isAzimuth);
  public PIDFSlot slot1 = new PIDFSlot(isAzimuth);
  public PIDFSlot slot2 = new PIDFSlot(isAzimuth);
  public PIDFSlot slot3 = new PIDFSlot(isAzimuth);
}
