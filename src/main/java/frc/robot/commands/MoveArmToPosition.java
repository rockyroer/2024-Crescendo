// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm;

/** Move Arm To Position
 *  This command (when it's finished) will allow us to set the arm 
 *   at a desiredAngle, specified when calling the command.
 *  This command uses the Arm subsystem.
 *  It uses the SparkMax Controllers SmartMotion
 *    and must be 'tuned' to work properly.  These tuning values
 *    are specified in constants.  As of 2-12-24, it has not been tuned  
 *    and this command DOES NOT work...
 */
public class MoveArmToPosition extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Arm m_arm;
  private final double m_desiredAngle;
  
  public MoveArmToPosition(double desiredAngle, Arm arm) {
    m_arm = arm;
    m_desiredAngle = desiredAngle;
    
    addRequirements(arm);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("Calling SetArm to " + m_desiredAngle);
    m_arm.setArm(m_desiredAngle);
  }

  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}