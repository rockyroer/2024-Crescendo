// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.hal.CANAPITypes.CANDeviceType;
//import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.k_arm;

import com.revrobotics.CANSparkBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.ControlType;

public class Arm extends SubsystemBase {
  private CANSparkMax m_armMotor;
  private RelativeEncoder m_angleEncoder;
  private SparkPIDController m_armPIDController; 
  private String m_statusMessage;
  
  /** Creates a new ExampleSubsystem. */
  public Arm() {
    m_armMotor = new CANSparkMax(k_arm.CANMaxId, MotorType.kBrushless);
    m_angleEncoder = m_armMotor.getEncoder();
    m_armPIDController = m_armMotor.getPIDController();
    
    // These values are all set in constants - and must be "tuned" to work properly.  
    m_armPIDController.setP(k_arm.kArmAngleP);
    m_armPIDController.setI(k_arm.kArmAngleI);
    m_armPIDController.setD(k_arm.kArmAngleD);
    m_armPIDController.setIZone(k_arm.kArmAngleIz);
    m_armPIDController.setFF(k_arm.kArmAngleFF);
    m_armPIDController.setOutputRange(k_arm.ArmAngleMinimumOutput, k_arm.ArmAngleMaximumOutput);
    

    m_armMotor.burnFlash();
    m_statusMessage = "Arm has been constructed.";
  }

  public void move(double speed){
    m_statusMessage = "Arm is at " + Math.round(speed*100) + "% of max power of " + k_arm.ArmMaxSpeed;
    m_armMotor.set(speed * k_arm.ArmMaxSpeed);
  }

  public void resetEncoder(){
    m_angleEncoder.setPosition(0);
  }

  public void setArm(double desiredAngle){
    System.out.println("Setting desired arm point (reference angle) to " + desiredAngle);
    m_armPIDController.setReference(desiredAngle, CANSparkBase.ControlType.kSmartMotion);
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
