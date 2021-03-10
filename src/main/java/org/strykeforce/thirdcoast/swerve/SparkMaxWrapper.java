package org.strykeforce.thirdcoast.swerve;

import com.ctre.phoenix.sensors.CANCoder;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.strykeforce.thirdcoast.swerve.MotorControllerConfig.FeedbackSensor;

import edu.wpi.first.wpilibj.controller.PIDController;

import com.revrobotics.ControlType;
import com.revrobotics.EncoderType;
import java.text.DecimalFormat;

public class SparkMaxWrapper implements MotorControllerWrapper {
  private CANSparkMax sparkMax;
  private CANPIDController pidController;
  private CANEncoder canEncoder;
  private CANCoder encoder;
  private PIDController PIDController;

  private boolean isAzimuth = false;
  private int id = -1;
  private int slot = 0;
  private int TICKS = 4096;

  private DriveMode driveMode = DriveMode.TELEOP;
  private FeedbackSensor feedbackSensor = FeedbackSensor.INTEGRATED_SENSOR;
  private Boolean remoteSensor = false;

  private MotorType getRevMotorType(MotorControllerConfig.MotorType type) {
    switch (type) {
      case BRUSHED:
        return MotorType.kBrushed;
      case BRUSHLESS:
        return MotorType.kBrushless;
      default:
        return MotorType.kBrushless;
    }
  }

  /* @TODO: fix rev sensors */
  private EncoderType getRevFeedbackDevice(MotorControllerConfig.FeedbackSensor sensor) {
    switch (sensor) {
      case CAN_CODER:
        return EncoderType.kNoSensor;
    }
    
    return EncoderType.kHallSensor;
  }

  private IdleMode getRevIdleMode(MotorControllerConfig.NeutralMode mode) {
    switch (mode) {
      case BRAKE:
        return IdleMode.kBrake;
      case COAST:
        return IdleMode.kCoast;
      default:
        return IdleMode.kCoast;
    }
  }

  private ControlType getRevControlType() {
    if (isAzimuth) {
      if (remoteSensor)
      {
        return ControlType.kDutyCycle;
      }
      return ControlType.kSmartMotion;
    }

    switch (driveMode) {
      case OPEN_LOOP:
        return ControlType.kDutyCycle;
      case CLOSED_LOOP:
        return ControlType.kVelocity;
      case TELEOP:
        return ControlType.kDutyCycle;
      case TRAJECTORY:
        return ControlType.kVelocity;
      case AZIMUTH:
        return ControlType.kDutyCycle;
      default:
        return ControlType.kDutyCycle;
    }
  }

  public SparkMaxWrapper(MotorControllerConfig config, int id) {
    isAzimuth = config.isAzimuth;
    this.id = id;

    sparkMax = new CANSparkMax(id, getRevMotorType(config.motorType));
    pidController = sparkMax.getPIDController();
    feedbackSensor = config.feedbackSensor;

    sparkMax.restoreFactoryDefaults();

    if (feedbackSensor == FeedbackSensor.INTEGRATED_SENSOR) {
      remoteSensor = false;
      pidController.setP(config.slot0.kP, 0);
      pidController.setI(config.slot0.kI, 0);
      pidController.setD(config.slot0.kD, 0);
      pidController.setFF(config.slot0.kF, 0);
      pidController.setIZone(config.slot0.kIZone, 0);
      pidController.setIMaxAccum(config.slot0.kMaxIAccum, 0);
      pidController.setSmartMotionMaxVelocity(config.motionCruiseVelocity, 0);
      pidController.setSmartMotionMaxAccel(config.motionAcceleration, 0);
      pidController.setSmartMotionAllowedClosedLoopError(config.slot0.kAllowableError, 0);

      pidController.setP(config.slot1.kP, 1);
      pidController.setI(config.slot1.kI, 1);
      pidController.setD(config.slot1.kD, 1);
      pidController.setFF(config.slot1.kF, 1);
      pidController.setIZone(config.slot1.kIZone, 1);
      pidController.setIMaxAccum(config.slot1.kMaxIAccum, 1);
      pidController.setSmartMotionMaxVelocity(config.motionCruiseVelocity, 1);
      pidController.setSmartMotionMaxAccel(config.motionAcceleration, 1);
      pidController.setSmartMotionAllowedClosedLoopError(config.slot1.kAllowableError, 1);

      pidController.setP(config.slot2.kP, 2);
      pidController.setI(config.slot2.kI, 2);
      pidController.setD(config.slot2.kD, 2);
      pidController.setFF(config.slot2.kF, 2);
      pidController.setIZone(config.slot2.kIZone, 2);
      pidController.setIMaxAccum(config.slot2.kMaxIAccum, 2);
      pidController.setSmartMotionMaxVelocity(config.motionCruiseVelocity, 2);
      pidController.setSmartMotionMaxAccel(config.motionAcceleration, 2);
      pidController.setSmartMotionAllowedClosedLoopError(config.slot2.kAllowableError, 2);

      pidController.setP(config.slot3.kP, 3);
      pidController.setI(config.slot3.kI, 3);
      pidController.setD(config.slot3.kD, 3);
      pidController.setFF(config.slot3.kF, 3);
      pidController.setIZone(config.slot3.kIZone, 3);
      pidController.setIMaxAccum(config.slot3.kMaxIAccum, 3);
      pidController.setSmartMotionMaxVelocity(config.motionCruiseVelocity, 3);
      pidController.setSmartMotionMaxAccel(config.motionAcceleration, 3);
      pidController.setSmartMotionAllowedClosedLoopError(config.slot3.kAllowableError, 3);
      canEncoder = sparkMax.getEncoder();
    } else {
      //CANCoder feedback device
      remoteSensor = true;
      encoder = new CANCoder(id + 20);
      PIDController = new PIDController(config.slot0.kP, config.slot0.kI, config.slot0.kD);
    }

    

    sparkMax.setSmartCurrentLimit(config.continuousCurrentLimit);
    sparkMax.setSecondaryCurrentLimit(config.peakCurrentLimit);
    sparkMax.enableVoltageCompensation(config.voltageCompensation);
    sparkMax.setIdleMode(getRevIdleMode(config.neutralMode));
  }

  public void set(double output) {
    if (remoteSensor)
    {
      double tmp_output = PIDController.calculate(getPosition(), output) / 4096.0;
      output = tmp_output > 1.0 ? 1.0 : tmp_output < -1.0 ? -1.0 : tmp_output;
    }
    pidController.setReference(output, getRevControlType(), slot);
  }

  public void setSensorPosition(double position) {
    encoder.setPosition(position);
  }

  public void setNeutralOutput() {
    set(0.0);
  }

  public double getPosition() {
    return encoder.getPosition();
  }

  public double getAbsPosition() {
    return encoder.getAbsolutePosition();
  }

  public double getVelocity() {
    return encoder.getVelocity();
  }

  public double getOutput() {
    return sparkMax.getBusVoltage() * sparkMax.getAppliedOutput();
  }

  public double getCurrent() {
    return sparkMax.getOutputCurrent();
  }

  @Override
  public String toString() {
    DecimalFormat df = new DecimalFormat("#.0000");
    return df.format(id)
        + ", "
        + df.format(getPosition())
        + ", "
        + df.format(getAbsPosition())
        + ", "
        + df.format(getVelocity())
        + ", "
        + df.format(getOutput())
        + ", "
        + df.format(getCurrent())
        + ", ";
  }

  public int getDeviceID() {
    return id;
  }

  public void setDriveMode(DriveMode mode) {
    driveMode = mode;
  }
}
