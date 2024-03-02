// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.Constants.k_arm;
import frc.robot.Constants.k_logitech;
import frc.robot.commands.DriveForward;
//import frc.robot.Constants.k_xbox;
import frc.robot.commands.EjectNote;
import frc.robot.commands.MoveArmToPosition;
import frc.robot.commands.RunIntakeTillNote;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  
  /* DECLARE THE SUBSTEMS: 
     // The robot's subsystems and commands are declared here...
  */
  private final CommandXboxController m_driverController;
  private final CommandXboxController m_systemController;
  private final Arm m_Arm;
  private final Intake m_Intake;
  private final DriveSubsystem m_Drive;
  private final Climber m_Climber;
  
  private SendableChooser<Command> m_autoModeChooser;
   
  // Declare grabber substystem ?
  
  
  
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    /* CONSTRUCT THE SUBSYSTEMS */
    m_Arm = new Arm();  
    m_Intake = new Intake();   
    m_Drive = new DriveSubsystem();
    m_Climber = new Climber();

    m_driverController = new CommandXboxController(OperatorConstants.kDriverControllerPort);
    m_systemController = new CommandXboxController(OperatorConstants.kSystemControllerPort);
  
    /* This sets up our autoonomous mode chooser.  */
    m_autoModeChooser = new SendableChooser<Command>();
    m_autoModeChooser.setDefaultOption("One Second", new DriveForward(0.8, 1000, m_Drive));
    m_autoModeChooser.addOption("Five Seconds", new DriveForward(0.8, 5000, m_Drive));
    m_autoModeChooser.addOption("Backwards Two Secons", new DriveForward(-0.8, 2000, m_Drive));
    SmartDashboard.putData("Choose Auto Command", m_autoModeChooser);  


    setDefaultCommands();
    configureJoystickButtonBindings(); 
  }

  private void setDefaultCommands() {
    // set Arm default command
    m_Arm.setDefaultCommand(new RunCommand(() -> 
      m_Arm.move(
        m_systemController.getRawAxis(k_logitech.rightXaxis)), 
      m_Arm));
    
    // set Chassis default command
    m_Drive.setDefaultCommand(new RunCommand(() -> 
    m_Drive.drive(
      m_driverController.getRawAxis(k_logitech.leftXaxis),
      m_driverController.getRawAxis(k_logitech.leftYaxis), 
      m_driverController.getRawAxis(k_logitech.rightXaxis),
      m_driverController.getRawAxis(k_logitech.rightYaxis)), 
    m_Drive));

    // set Intake default command
    m_Intake.setDefaultCommand(new RunCommand(() -> 
      m_Intake.spin(
        m_systemController.getRawAxis(k_logitech.leftXaxis)), 
      m_Intake));

    // set Climber Default command
    m_Climber.setDefaultCommand(new RunCommand(() -> 
      m_Climber.move(
        m_systemController.getRawAxis(k_logitech.leftYaxis),
        m_systemController.getRawAxis(k_logitech.rightYaxis)),        
      m_Climber));
  }


  private void configureJoystickButtonBindings() {
    new Trigger(m_systemController.a()).onTrue(new RunIntakeTillNote(m_Intake));
    new Trigger(m_systemController.b()).onTrue(new EjectNote(m_Intake));
    new Trigger(m_systemController.start()).onTrue(new InstantCommand(m_Arm::resetEncoder, m_Arm));
    new Trigger(m_systemController.x()).onTrue(new MoveArmToPosition(k_arm.ArmUpPosition, m_Arm));
    new Trigger(m_systemController.y()).onTrue(new MoveArmToPosition(k_arm.ArmScoringPostion, m_Arm));    
  }

  public Command getAutonomousCommand() {
    return m_autoModeChooser.getSelected();
  }  
}
