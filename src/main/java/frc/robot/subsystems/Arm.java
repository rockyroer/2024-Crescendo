// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.k_arm;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;


public class Arm extends SubsystemBase {
  private CANSparkMax m_armMotor;
  private String m_statusMessage;
  private final RelativeEncoder m_angleEncoder;
  
  /** Creates a new ExampleSubsystem. */
  public Arm() {
    m_armMotor = new CANSparkMax(k_arm.CANMaxId, MotorType.kBrushless);
    m_angleEncoder = m_armMotor.getEncoder();
    m_statusMessage = "Arm has been constructed.";
  }

  public void move(double speed){
    m_statusMessage = "Arm is moving at speed" + speed;
    m_armMotor.set(speed * k_arm.ArmMaxSpeed);
  }

  public void halt() {
    m_statusMessage = "Arm is resting.";
    m_armMotor.set(0);
  }
  
  public double getPosition() {
    return m_angleEncoder.getPosition();
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    //    SmartDashboard.putNumber("Arm Controller Encoder:", m_arm.getEncoder().getPosition());
    SmartDashboard.putString("Arm Status:", m_statusMessage);
    SmartDashboard.putNumber("Arm Encoder: ", m_angleEncoder.getPosition());
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
