package org.strykeforce.thirdcoast.swerve;

public abstract interface MotorControllerWrapper {

  public enum DriveMode {
    OPEN_LOOP,
    CLOSED_LOOP,
    TELEOP,
    TRAJECTORY,
    AZIMUTH,
  }

  

  /* Sets motor controller output using current drive mode */
  public abstract void set(double output);

  /* Sets current sensor position */
  public abstract void setSensorPosition(double position);

  /* Sets motor controller output to neutral */
  public abstract void setNeutralOutput();

  /* Returns motor controller data */
  public abstract double getPosition();

  public abstract double getAbsPosition();

  public abstract double getVelocity();

  public abstract double getOutput();

  public abstract double getCurrent();

  public abstract String toString();

  public abstract int getDeviceID();

  public void setDriveMode(DriveMode mode);
}
