package org.strykeforce.thirdcoast.swerve;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;

public class TalonFXWrapper extends TalonBaseWrapper {
  private TalonFX talonFX;

  public TalonFXWrapper(MotorControllerConfig config, int id) {
    this.id = id;
    isAzimuth = config.isAzimuth;
    talonFX = new TalonFX(id);

    TalonFXConfiguration talonFXConfig = new TalonFXConfiguration();
    talonFXConfig.primaryPID.selectedFeedbackSensor = getCTREFeedbackDevice(config.feedbackSensor);
    talonFXConfig.supplyCurrLimit.enable = true;
    talonFXConfig.supplyCurrLimit.triggerThresholdCurrent = config.continuousCurrentLimit;
    talonFXConfig.supplyCurrLimit.triggerThresholdTime = 1.0;
    talonFXConfig.supplyCurrLimit.currentLimit = config.peakCurrentLimit;

    talonFXConfig.statorCurrLimit.enable = true;
    talonFXConfig.statorCurrLimit.triggerThresholdCurrent = config.continuousCurrentLimit;
    talonFXConfig.statorCurrLimit.triggerThresholdTime = 1.0;
    talonFXConfig.statorCurrLimit.currentLimit = config.peakCurrentLimit;

    talonFXConfig.slot0.kP = config.slot0.kP;
    talonFXConfig.slot0.kI = config.slot0.kI;
    talonFXConfig.slot0.kD = config.slot0.kD;
    talonFXConfig.slot0.kF = config.slot0.kF;
    talonFXConfig.slot0.integralZone = (int) config.slot0.kIZone;
    talonFXConfig.slot0.allowableClosedloopError = (int) config.slot0.kAllowableError;

    talonFXConfig.slot1.kP = config.slot1.kP;
    talonFXConfig.slot1.kI = config.slot1.kI;
    talonFXConfig.slot1.kD = config.slot1.kD;
    talonFXConfig.slot1.kF = config.slot1.kF;
    talonFXConfig.slot1.integralZone = (int) config.slot1.kIZone;
    talonFXConfig.slot1.allowableClosedloopError = (int) config.slot1.kAllowableError;

    talonFXConfig.slot2.kP = config.slot2.kP;
    talonFXConfig.slot2.kI = config.slot2.kI;
    talonFXConfig.slot2.kD = config.slot2.kD;
    talonFXConfig.slot2.kF = config.slot2.kF;
    talonFXConfig.slot2.integralZone = (int) config.slot2.kIZone;
    talonFXConfig.slot2.allowableClosedloopError = (int) config.slot2.kAllowableError;

    talonFXConfig.slot3.kP = config.slot3.kP;
    talonFXConfig.slot3.kI = config.slot3.kI;
    talonFXConfig.slot3.kD = config.slot3.kD;
    talonFXConfig.slot3.kF = config.slot3.kF;
    talonFXConfig.slot3.integralZone = (int) config.slot3.kIZone;
    talonFXConfig.slot3.allowableClosedloopError = (int) config.slot3.kAllowableError;

    talonFXConfig.motionAcceleration = (int) config.motionAcceleration;
    talonFXConfig.motionCruiseVelocity = (int) config.motionCruiseVelocity;
    talonFXConfig.velocityMeasurementWindow = 64;
    talonFXConfig.voltageCompSaturation = config.voltageCompensation;

    talonFX.configAllSettings(talonFXConfig);
    talonFX.enableVoltageCompensation(true);
    talonFX.setNeutralMode(getCTRENeutralMode(config.neutralMode));
  }

  public void set(double output) {
    talonFX.set(getCTREControlMode(), output);
  }

  public void setSensorPosition(double position) {
    talonFX.setSelectedSensorPosition((int) position);
  }

  public void setNeutralOutput() {
    set(0.0);
  }

  public double getPosition() {
    return talonFX.getSelectedSensorPosition();
  }

  public double getAbsPosition() {
    return talonFX.getSelectedSensorPosition();
  }

  public double getVelocity() {
    return talonFX.getSelectedSensorVelocity();
  }

  public double getOutput() {
    return talonFX.getMotorOutputVoltage();
  }

  public double getCurrent() {
    return talonFX.getStatorCurrent();
  }
}
