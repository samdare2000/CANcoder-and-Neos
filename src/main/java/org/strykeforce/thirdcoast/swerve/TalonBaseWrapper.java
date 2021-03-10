package org.strykeforce.thirdcoast.swerve;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import java.text.DecimalFormat;

public abstract class TalonBaseWrapper implements MotorControllerWrapper {
  protected boolean isAzimuth = false;
  protected int id = -1;
  protected int slot = 0;
  protected DriveMode driveMode = DriveMode.TELEOP;

  FeedbackDevice getCTREFeedbackDevice(MotorControllerConfig.FeedbackSensor sensor) {
    if (!isAzimuth) {
      return FeedbackDevice.IntegratedSensor;
    }

    switch (sensor) {
      case CTRE_MAG_ENCODER:
        return FeedbackDevice.PulseWidthEncodedPosition;
      case CAN_CODER:
        return FeedbackDevice.RemoteSensor0;
        // case THRIFTY_CODER:
        //    return FeedbackDevice.PulseWidthEncodedPosition;

        /* @TODO: Add check for is Azimuth and error handling */
      case INTEGRATED_SENSOR:
        return FeedbackDevice.IntegratedSensor;
      default:
        return FeedbackDevice.None;
    }
  }

  NeutralMode getCTRENeutralMode(MotorControllerConfig.NeutralMode mode) {
    switch (mode) {
      case BRAKE:
        return NeutralMode.Brake;
      case COAST:
        return NeutralMode.Coast;
      default:
        return NeutralMode.Coast;
    }
  }

  ControlMode getCTREControlMode() {
    if (isAzimuth) {
      // Talon Azimuth defaults to Motion Magic
      return ControlMode.MotionMagic;
    }

    switch (driveMode) {
      case OPEN_LOOP:
        return ControlMode.PercentOutput;
      case CLOSED_LOOP:
        return ControlMode.Velocity;
      case TELEOP:
        return ControlMode.PercentOutput;
      case TRAJECTORY:
        return ControlMode.Velocity;
      case AZIMUTH:
        return ControlMode.PercentOutput;
      default:
        return ControlMode.PercentOutput;
    }
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

  public void setDriveMode(DriveMode mode)
  {
    driveMode = mode;
  }
}
