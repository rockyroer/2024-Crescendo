// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.hal.CANAPITypes.CANDeviceType;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.k_Drive;
import frc.robot.Constants.k_arm;
import com.revrobotics.CANSparkBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.ControlType;


public class Arm extends SubsystemBase {
  private CANSparkMax m_armMotorLeft, m_armMotorRight;
  private RelativeEncoder m_angleEncoderLeft, m_angleEncoderRight;
  private SparkPIDController m_armPIDController; 
  private String m_statusMessage;
  private boolean m_manualMode;
  
  /** Constructs a new Arm */
  public Arm() {
    m_armMotorLeft = new CANSparkMax(k_arm.LeftCANMaxId, MotorType.kBrushless);  // leader
    m_armMotorRight = new CANSparkMax(k_arm.RightCANMaxId, MotorType.kBrushless);  // follower
    //m_armMotorRight.follow(m_armMotorLeft);
   
    m_manualMode = true;

    m_angleEncoderLeft = m_armMotorLeft.getEncoder();
    m_angleEncoderRight = m_armMotorRight.getEncoder();
    m_armPIDController = m_armMotorLeft.getPIDController();
    
    
    
    // These values are all set in constants - and must be "tuned" to work properly. 
    //m_armPIDController.setP(k_arm.kArmAngleP);
    //m_armPIDController.setI(k_arm.kArmAngleI);
    //m_armPIDController.setD(k_arm.kArmAngleD);
    //m_armPIDController.setIZone(k_arm.kArmAngleIz);
    //m_armPIDController.setFF(k_arm.kArmAngleFF);
    //m_armPIDController.setOutputRange(k_arm.ArmAngleMinimumOutput, k_arm.ArmAngleMaximumOutput);
    //m_armMotorLeft.burnFlash();

    m_statusMessage = "Arm has been constructed.";
  }

  public void move(double joystickLevel){
    if (m_manualMode) {
      m_statusMessage = "Arm is at " + Math.round(joystickLevel*100) + "% of max power of " + k_arm.ArmMaxSpeed;
      m_armMotorLeft.set(joystickLevel * k_arm.ArmMaxSpeed);
      m_armMotorRight.set(joystickLevel * -k_arm.ArmMaxSpeed);

    } else {
      m_statusMessage = "Arm in Encoder Mode - move joystick > 50% to override";
      if ((joystickLevel > 0.5) || (joystickLevel < -0.5)) {
        m_manualMode = true;
        m_armPIDController.setReference(joystickLevel * k_arm.ArmMaxSpeed, CANSparkBase.ControlType.kCurrent);
      }
    }
  }

  public void resetEncoder(){
    m_angleEncoderLeft.setPosition(0);
    m_angleEncoderRight.setPosition(0);
    
  }

  public void setArm(double desiredAngle) {
    System.out.println("Setting desired arm point (reference angle) to " + desiredAngle);
    m_statusMessage = "Arm operating with encoders set to " + desiredAngle;
    m_manualMode = false;
    m_armPIDController.setReference(desiredAngle, CANSparkBase.ControlType.kPosition);
  }

  public void halt() {
    m_statusMessage = "Arm is resting.";
    m_armMotorLeft.set(0);
  }
  
  public double getPosition() {
    return m_angleEncoderLeft.getPosition();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    //    SmartDashboard.putNumber("Arm Controller Encoder:", m_arm.getEncoder().getPosition());
    SmartDashboard.putString("Arm Status:", m_statusMessage);
    SmartDashboard.putBoolean("Manual Mdoe:", m_manualMode);
    SmartDashboard.putNumber("Arm Encoder Left Leader: (unknown units) ", m_angleEncoderLeft.getPosition());
    SmartDashboard.putNumber("Arm Encoder Right: (unknown units) ", m_angleEncoderRight.getPosition());
  }


  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
