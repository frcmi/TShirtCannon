// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package first.robot;

import first.robot.constants.DriverConstants;
import org.wpilib.opmode.OpMode;
import org.wpilib.opmode.Teleop;
import static org.wpilib.units.Units.Degrees;

@Teleop
public class CannonTeleop implements OpMode {
  Robot robot;

  public CannonTeleop(Robot robot) {
    this.robot = robot;
    robot.controller.faceDown().onTrue(robot.pivotMechanism.setAngle(DriverConstants.Angle1));
    robot.controller.faceLeft().onTrue(robot.pivotMechanism.setAngle(DriverConstants.Angle2));
    robot.controller.faceRight().onTrue(robot.pivotMechanism.setAngle(DriverConstants.Angle3));
    robot.controller.faceUp().onTrue(robot.pivotMechanism.setAngle(DriverConstants.Angle4));
    robot.controller.leftBumper().whileTrue(robot.pivotMechanism.setAngle(robot.pivotMechanism.getAngle().plus(Degrees.of(0.1))));
    robot.controller.rightBumper().whileTrue(robot.pivotMechanism.setAngle(robot.pivotMechanism.getAngle().plus(Degrees.of(-0.1))));

    robot.controller.leftTrigger().and(robot.controller.dpadDown()).onTrue(robot.cannonMechanism.shoot1());
    robot.controller.leftTrigger().and(robot.controller.dpadLeft()).onTrue(robot.cannonMechanism.shoot2());
    robot.controller.leftTrigger().and(robot.controller.dpadUp()).onTrue(robot.cannonMechanism.shoot3());
    robot.controller.leftTrigger().and(robot.controller.dpadRight()).onTrue(robot.cannonMechanism.shoot4());
  }

  @Override
  public void periodic() {
    robot.drivetrain.arcadeDrive(-robot.controller.getLeftY(), robot.controller.getRightX());
  }
}
