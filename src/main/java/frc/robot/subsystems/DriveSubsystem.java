// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.robot.Constants.k_Drive;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveSubsystem extends SubsystemBase {
  private Spark m_leftMotors, m_rightMotors;
  private DifferentialDrive m_drive;
  private Encoder m_leftEncoder, m_rightEncoder;
  private ADXRS450_Gyro m_gyro;
  private String m_statusMessage;
  private final Field2d m_field = new Field2d();
  private final DifferentialDriveOdometry m_Odometry;
  private int drivingMode;
  private SlewRateLimiter m_SlewRateLimiter; // limits acceleration 

  /** Constructs a new Drive Subsystem. */
  public DriveSubsystem() {
    m_leftMotors = new Spark(k_Drive.leftMotors);
    m_rightMotors = new Spark(k_Drive.rightMotors);
    m_drive = new DifferentialDrive(m_leftMotors, m_rightMotors);
    m_leftMotors.setInverted(true);
    m_rightMotors.setInverted(false);
    m_gyro = new ADXRS450_Gyro();
    m_SlewRateLimiter = new SlewRateLimiter(k_Drive.AccelerationSlewRateLimiterValue);
    
    m_leftEncoder = new Encoder(k_Drive.leftEncoderChannelA,k_Drive.leftEncoderChannelB,false, CounterBase.EncodingType.k4X);
    m_rightEncoder = new Encoder(k_Drive.rightEncoderChannelA,k_Drive.rightEncoderChannelB,true, CounterBase.EncodingType.k4X);
    m_leftEncoder.setDistancePerPulse(1.0 / 2000.0 * 2.0 * Math.PI * Units.inchesToMeters(3));
    m_rightEncoder.setDistancePerPulse(1.0 / 2000.0 * 2.0 * Math.PI * Units.inchesToMeters(3));

    drivingMode = k_Drive.DrivingMode.arcadeDrive;
    
    m_statusMessage = "Drive Subsystem Constructed";
    resetEncoders();
    m_Odometry = new DifferentialDriveOdometry(m_gyro.getRotation2d(), m_leftEncoder.getDistance(), m_rightEncoder.getDistance());
    SmartDashboard.putData("Field", m_field);
  }

  public void resetEncoders() {
    m_leftEncoder.reset();
    m_rightEncoder.reset();
  }

  public void drive(double joystickLevel1, double joystickLevel2) {
    if (drivingMode == k_Drive.DrivingMode.arcadeDrive) {
      m_drive.arcadeDrive(joystickLevel1, joystickLevel2);
    } else if (drivingMode == k_Drive.DrivingMode.arcadeDriveReversed) {
      m_drive.arcadeDrive(-1*joystickLevel1, joystickLevel2);
    } else if (drivingMode == k_Drive.DrivingMode.tankDrive) {
      m_drive.tankDrive(joystickLevel1, joystickLevel2);
    } else if (drivingMode == k_Drive.DrivingMode.fieldOriented) {
      this.fieldOrientedDrive(joystickLevel1,joystickLevel2);
    }
    else if (drivingMode == k_Drive.DrivingMode.arcadeDriveLimitedAcceleration) {
      m_drive.arcadeDrive(m_SlewRateLimiter.calculate(joystickLevel1), joystickLevel2);
    }
  }

  public void changeDrivingMode(){
    this.drivingMode = (drivingMode + 1) % k_Drive.nDrivingModesAvaiable;
  }

  private void fieldOrientedDrive(double joystickLevel1, double joystickLevel2) {
    
  }

  private String drivingModeString() {
    String x = "Unknown";
    if (this.drivingMode == k_Drive.DrivingMode.arcadeDrive) x = "Arcade Drive";
    if (this.drivingMode == k_Drive.DrivingMode.arcadeDriveReversed) x = "Arcade Drive Reversed";
    if (this.drivingMode == k_Drive.DrivingMode.tankDrive) x = "Tank Drive";
    if (this.drivingMode == k_Drive.DrivingMode.fieldOriented) x = "Field Orientated";
    if (this.drivingMode == k_Drive.DrivingMode.gryoAssisted) x = "Gyro Assisted";
    if (this.drivingMode == k_Drive.DrivingMode.arcadeDriveLimitedAcceleration) x = "Arcade Drive - Slew Limited";
    return x;
  }
  /* 
  private void arcadeDrive(double speed, double rotation) {
    m_drive.arcadeDrive(speed, rotation);
  }

  private void tankDrive(double leftspeed, double rightspeed) {
    m_drive.tankDrive(leftspeed, rightspeed);
  }
  */


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putString("Drive Status", m_statusMessage);
    SmartDashboard.putNumber("Left Distnace (unknown unit):", m_leftEncoder.getDistance());
    SmartDashboard.putNumber("Right Distance: (unknown unit)", m_rightEncoder.getDistance());
    SmartDashboard.putNumber("Left Rate: (unknown unit)", m_leftEncoder.getRate());
    SmartDashboard.putNumber("Right Rate: (unknown unit)", m_rightEncoder.getRate());
    SmartDashboard.putNumber("Gyro Value: (unknown unit)", m_gyro.getAngle());
    
    m_field.setRobotPose(m_Odometry.getPoseMeters());
    m_Odometry.update(m_gyro.getRotation2d(), m_leftEncoder.getDistance(), m_rightEncoder.getDistance());
  }
}
