// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package first.robot;

import static org.wpilib.units.Units.Degrees;

import org.wpilib.command3.Scheduler;
import org.wpilib.command3.button.CommandGamepad;
import org.wpilib.drive.DifferentialDrive;
import org.wpilib.framework.OpModeRobot;
import org.wpilib.hardware.bus.CANPort;
import org.wpilib.telemetry.Telemetry;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;

import first.robot.constants.DriverConstants;
import first.robot.mechanisms.CannonMechanism;
import first.robot.mechanisms.PivotMechanism;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

public class Robot extends OpModeRobot {
  private final SparkMax leftLeader = new SparkMax(CANPort.CAN_S0, 9, MotorType.kBrushless);
  private final SparkMax leftFollower = new SparkMax(CANPort.CAN_S0, 14, MotorType.kBrushless);
  private final SparkMax rightLeader = new SparkMax(CANPort.CAN_S0, 11, MotorType.kBrushless);
  private final SparkMax rightFollower = new SparkMax(CANPort.CAN_S0, 13, MotorType.kBrushless);

  private final SparkMaxConfig leftConfig = new SparkMaxConfig();
  private final SparkMaxConfig rightConfig = new SparkMaxConfig();
  private final SparkMaxConfig invertedMotor = new SparkMaxConfig();

  private final Scheduler scheduler = Scheduler.getDefault();

  public final DifferentialDrive drivetrain =
      new DifferentialDrive(leftLeader::setThrottle, rightLeader::setThrottle);

  final PivotMechanism pivotMechanism = new PivotMechanism();
  final CannonMechanism cannonMechanism = new CannonMechanism();

  final CommandGamepad controller =
      new CommandGamepad(DriverConstants.DRIVER_CONTROLLER_PORT);

  CannonTeleop teleop = new CannonTeleop(this);

  public Robot() {
    invertedMotor.inverted(true);
    leftConfig.follow(leftLeader);
    rightConfig.follow(rightLeader);

    rightLeader.configure(invertedMotor, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    leftFollower.configure(leftConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    rightFollower.configure(rightConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    // pivotMechanism.setDefaultCommand(pivotMechanism.setAngle(Degrees.of(0)));
  }

  @Override
  public void robotPeriodic() {
    teleop.periodic();
    pivotMechanism.periodic();

    Scheduler.getDefault().run();

    Telemetry.log("Scheduler", scheduler, Scheduler.proto);
  }
}
