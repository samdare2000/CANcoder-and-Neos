package org.strykeforce.thirdcoast.swerve;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.RemoteFeedbackDevice;
import com.ctre.phoenix.motorcontrol.RemoteSensorSource;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.VictorSPXConfiguration;
import com.ctre.phoenix.sensors.CANCoder;

public class VictorSPXWrapper extends TalonBaseWrapper {
  private VictorSPX victorSPX;
  private CANCoder canCoder;

  public VictorSPXWrapper(MotorControllerConfig config, int id) {
    this.id = id;
    isAzimuth = config.isAzimuth;

    victorSPX = new VictorSPX(id);
    canCoder = new CANCoder(id + 20);

    victorSPX.configFactoryDefault();

    VictorSPXConfiguration victorSPXConfig = new VictorSPXConfiguration();
    victorSPXConfig.primaryPID.selectedFeedbackSensor = RemoteFeedbackDevice.RemoteSensor0;

    victorSPXConfig.slot0.kP = config.slot0.kP;
    victorSPXConfig.slot0.kI = config.slot0.kI;
    victorSPXConfig.slot0.kD = config.slot0.kD;
    victorSPXConfig.slot0.kF = config.slot0.kF;
    victorSPXConfig.slot0.integralZone = (int) config.slot0.kIZone;
    victorSPXConfig.slot0.allowableClosedloopError = (int) config.slot0.kAllowableError;

    victorSPXConfig.slot1.kP = config.slot1.kP;
    victorSPXConfig.slot1.kI = config.slot1.kI;
    victorSPXConfig.slot1.kD = config.slot1.kD;
    victorSPXConfig.slot1.kF = config.slot1.kF;
    victorSPXConfig.slot1.integralZone = (int) config.slot1.kIZone;
    victorSPXConfig.slot1.allowableClosedloopError = (int) config.slot1.kAllowableError;

    victorSPXConfig.slot2.kP = config.slot2.kP;
    victorSPXConfig.slot2.kI = config.slot2.kI;
    victorSPXConfig.slot2.kD = config.slot2.kD;
    victorSPXConfig.slot2.kF = config.slot2.kF;
    victorSPXConfig.slot2.integralZone = (int) config.slot2.kIZone;
    victorSPXConfig.slot2.allowableClosedloopError = (int) config.slot2.kAllowableError;

    victorSPXConfig.slot3.kP = config.slot3.kP;
    victorSPXConfig.slot3.kI = config.slot3.kI;
    victorSPXConfig.slot3.kD = config.slot3.kD;
    victorSPXConfig.slot3.kF = config.slot3.kF;
    victorSPXConfig.slot3.integralZone = (int) config.slot3.kIZone;
    victorSPXConfig.slot3.allowableClosedloopError = (int) config.slot3.kAllowableError;

    victorSPXConfig.motionAcceleration = (int) config.motionAcceleration;
    victorSPXConfig.motionCruiseVelocity = (int) config.motionCruiseVelocity;
    victorSPXConfig.velocityMeasurementWindow = 64;
    victorSPXConfig.voltageCompSaturation = config.voltageCompensation;

    victorSPX.configRemoteFeedbackFilter(this.id + 20, RemoteSensorSource.CANCoder, 0, 10);

    victorSPX.configAllSettings(victorSPXConfig);

    victorSPX.enableVoltageCompensation(true);
    victorSPX.setNeutralMode(getCTRENeutralMode(config.neutralMode));
  }

  public void set(double output) {
    victorSPX.set(ControlMode.MotionMagic, output);
  }

  public void setSensorPosition(double position) {
    canCoder.setPosition(position);
    set(position);
  }

  public void setNeutralOutput() {
    set(0.0);
  }

  public double getPosition() {
    return canCoder.getPosition();
  }

  public double getAbsPosition() {
    return canCoder.getAbsolutePosition();
  }

  public double getVelocity() {
    return canCoder.getVelocity();
  }

  public double getOutput() {
    return victorSPX.getMotorOutputVoltage();
  }

  public double getCurrent() {
    return 0.0;
  }
}
