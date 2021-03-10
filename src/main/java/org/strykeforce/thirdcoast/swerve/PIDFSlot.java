package org.strykeforce.thirdcoast.swerve;

public class PIDFSlot {
  public double kP, kI, kD, kF, kIZone, kAllowableError, kMaxIAccum = 0.0;

  public PIDFSlot(Boolean azimuth) {
    if (azimuth) {
      kP = 2.0;
      kI = 0.0;
      kD = 30.0;
      kF = 0.0;
      kIZone = 0.0;
      kAllowableError = 0.0;
      kMaxIAccum = 0.0;
    } else {
      kP = 2.0;
      kI = 0.0;
      kD = 30.0;
      kF = 0.0;
      kIZone = 0.0;
      kAllowableError = 0.0;
      kMaxIAccum = 0.0;
    }
  }
}
