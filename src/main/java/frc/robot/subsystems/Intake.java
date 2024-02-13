// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
//import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.k_intake;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;


public class Intake extends SubsystemBase {
  private CANSparkMax m_intakeMotor;
  private String m_statusMessage;
  private Ultrasonic m_rangeFinder; //= new Ultrasonic(k_intake.rangeFinderDIOTriggerPin, k_intake.rangeFinderDIOEchoPin);
  private RelativeEncoder m_relativeEncoder;
  private double m_rotationalVelocity;
  private double m_distance;

  /** Creates a new ExampleSubsystem. */
  public Intake() {
    m_statusMessage = "Intake has been constructed.";
    m_intakeMotor = new CANSparkMax(k_intake.CANMaxId, MotorType.kBrushless);
    m_relativeEncoder = m_intakeMotor.getEncoder();
    m_rotationalVelocity = 0;

    
    m_rangeFinder = new Ultrasonic(k_intake.rangeFinderDIOTriggerPin, k_intake.rangeFinderDIOEchoPin);
    Shuffleboard.getTab("Sensors").add(m_rangeFinder);
    
  }

  public void setStatus(String statusMessage){
    m_statusMessage = statusMessage;
  }
  
  public void spin(double power){
    m_statusMessage = "Intake is spinning at " + Math.round(power*100) + "% allowed power";
    m_intakeMotor.set(power * k_intake.IntakeMaxSpeed);
  }

  public void halt() {
    m_statusMessage = "Intake is resting.";
    m_intakeMotor.set(0);
  }
  
  public double getDistance() {
    return m_distance;    
  }  
  
  public double getSpeed() {
    return m_rotationalVelocity;    
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    m_rotationalVelocity = m_relativeEncoder.getVelocity();
    m_distance = m_rangeFinder.getRangeInches();
    m_rangeFinder.ping();    // Ping for next measurement
 
    SmartDashboard.putString("Intake Status:", m_statusMessage);
    SmartDashboard.putNumber("Rotational Velocity:", this.m_rotationalVelocity);
    SmartDashboard.putNumber("Range Finder Distance (inches):", this.getDistance());
    SmartDashboard.putNumber("Distance[mm]", m_rangeFinder.getRangeMM());
    SmartDashboard.putNumber("Distance[inch]", m_rangeFinder.getRangeInches());

   }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
