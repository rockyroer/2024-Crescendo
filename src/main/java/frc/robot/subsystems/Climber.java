// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;
import frc.robot.Constants.k_climber;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase {
  private CANSparkMax m_climberMotorLeft, m_climberMotorRight;
  private RelativeEncoder m_climberEncoderLeft, m_climberEncoderRight;
  private String m_climberStatusMessage;

  
  /** Creates a new Climber. */
  public Climber() {
    m_climberMotorLeft = new CANSparkMax(k_climber.LeftCanID, MotorType.kBrushless);
    m_climberMotorRight = new CANSparkMax(k_climber.RightCanID, MotorType.kBrushless);
    m_climberEncoderLeft = m_climberMotorLeft.getEncoder();
    m_climberEncoderRight = m_climberMotorRight.getEncoder();
      
    m_climberStatusMessage = "Climber has been Constructed";
  }

  public void move(double joystickLevel1, double joystickLevel2){
    m_climberMotorLeft.set(joystickLevel1);
    m_climberMotorRight.set(joystickLevel2);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putString("Climber Status:", m_climberStatusMessage );
    SmartDashboard.putNumber("Left Climber Encoder:", m_climberEncoderLeft.getPosition());
    SmartDashboard.putNumber("Right Climber Encoder:", m_climberEncoderRight.getPosition());
    
  }
}
