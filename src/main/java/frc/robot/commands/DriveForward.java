// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveSubsystem;

public class DriveForward extends Command {
  private final DriveSubsystem m_chassis;
  private double m_speed;
  private double m_time;
  private double m_startTime;
  private double m_gryoSetPoint;


  /** Creates a new DriveForward. */
  public DriveForward(double speed, double timeInMilliSeconds, DriveSubsystem chassis) {
    m_chassis = chassis;
    addRequirements(m_chassis);    // Use addRequirements() here to declare subsystem dependencies.
  
    m_speed = speed;
    m_time = timeInMilliSeconds;
    m_gryoSetPoint = 0;
  }



  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("Starting Drive Forward");

    m_chassis.arcadeDrive(0, 0);
    m_gryoSetPoint = m_chassis.getGyroValue();
    m_startTime = System.currentTimeMillis();
    SmartDashboard.putString("Drive Forward Command:", "Initialized");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    System.out.println("Executing Drive Forward");
    double turningValue = (m_gryoSetPoint - m_chassis.getGyroValue()) *0.05;
    m_chassis.arcadeDrive(m_speed, turningValue);
    SmartDashboard.putString("Drive Forward Command:", "Executing");
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("Ending Drive Forward");
    m_chassis.arcadeDrive(0, 0);
    SmartDashboard.putString("Drive Forward Command:", "Ended");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished()
  {
    double elapsedTime = System.currentTimeMillis() - m_startTime;
    return (elapsedTime > m_time);
  }
}
