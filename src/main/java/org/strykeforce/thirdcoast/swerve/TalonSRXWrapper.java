package org.strykeforce.thirdcoast.swerve;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;

public class TalonSRXWrapper extends TalonBaseWrapper {
  private TalonSRX talonSRX;

  public TalonSRXWrapper(MotorControllerConfig config, int id) {
    isAzimuth = config.isAzimuth;
    this.id = id;

    talonSRX = new TalonSRX(id);

    talonSRX.configFactoryDefault();

    TalonSRXConfiguration talonSRXConfig = new TalonSRXConfiguration();
    talonSRXConfig.primaryPID.selectedFeedbackSensor = getCTREFeedbackDevice(config.feedbackSensor);
    talonSRXConfig.continuousCurrentLimit = config.continuousCurrentLimit;
    talonSRXConfig.peakCurrentDuration = 1;
    talonSRXConfig.peakCurrentLimit = (int) config.peakCurrentLimit;

    talonSRXConfig.slot0.kP = config.slot0.kP;
    talonSRXConfig.slot0.kI = config.slot0.kI;
    talonSRXConfig.slot0.kD = config.slot0.kD;
    talonSRXConfig.slot0.kF = config.slot0.kF;
    talonSRXConfig.slot0.integralZone = (int) config.slot0.kIZone;
    talonSRXConfig.slot0.allowableClosedloopError = (int) config.slot0.kAllowableError;

    talonSRXConfig.slot1.kP = config.slot1.kP;
    talonSRXConfig.slot1.kI = config.slot1.kI;
    talonSRXConfig.slot1.kD = config.slot1.kD;
    talonSRXConfig.slot1.kF = config.slot1.kF;
    talonSRXConfig.slot1.integralZone = (int) config.slot1.kIZone;
    talonSRXConfig.slot1.allowableClosedloopError = (int) config.slot1.kAllowableError;

    talonSRXConfig.slot2.kP = config.slot2.kP;
    talonSRXConfig.slot2.kI = config.slot2.kI;
    talonSRXConfig.slot2.kD = config.slot2.kD;
    talonSRXConfig.slot2.kF = config.slot2.kF;
    talonSRXConfig.slot2.integralZone = (int) config.slot2.kIZone;
    talonSRXConfig.slot2.allowableClosedloopError = (int) config.slot2.kAllowableError;

    talonSRXConfig.slot3.kP = config.slot3.kP;
    talonSRXConfig.slot3.kI = config.slot3.kI;
    talonSRXConfig.slot3.kD = config.slot3.kD;
    talonSRXConfig.slot3.kF = config.slot3.kF;
    talonSRXConfig.slot3.integralZone = (int) config.slot3.kIZone;
    talonSRXConfig.slot3.allowableClosedloopError = (int) config.slot3.kAllowableError;

    talonSRXConfig.motionAcceleration = (int) config.motionAcceleration;
    talonSRXConfig.motionCruiseVelocity = (int) config.motionCruiseVelocity;
    talonSRXConfig.velocityMeasurementWindow = 64;
    talonSRXConfig.voltageCompSaturation = config.voltageCompensation;

    talonSRX.configAllSettings(talonSRXConfig);
    talonSRX.enableCurrentLimit(true);
    talonSRX.enableVoltageCompensation(true);
    talonSRX.setNeutralMode(getCTRENeutralMode(config.neutralMode));
  }

  public void set(double output) {
    talonSRX.set(getCTREControlMode(), output);
  }

  public void setSensorPosition(double position) {
    talonSRX.setSelectedSensorPosition((int) position);
    set(position);
  }

  public void setNeutralOutput() {
    set(0.0);
  }

  public double getPosition() {
    return talonSRX.getSelectedSensorPosition(0);
  }

  public double getAbsPosition() {
    return talonSRX.getSensorCollection().getPulseWidthPosition();
  }

  public double getVelocity() {
    return talonSRX.getSelectedSensorVelocity();
  }

  public double getOutput() {
    return talonSRX.getMotorOutputVoltage();
  }

  public double getCurrent() {
    return talonSRX.getStatorCurrent();
  }
}
