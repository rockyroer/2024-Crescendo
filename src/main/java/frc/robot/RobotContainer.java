// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.Constants.k_arm;
import frc.robot.Constants.k_logitech;
//import frc.robot.Constants.k_xbox;
import frc.robot.commands.Autos;
import frc.robot.commands.EjectNote;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.MoveArmToPosition;
import frc.robot.commands.RunIntakeTillNote;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.ExampleSubsystem;
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
  private final ExampleSubsystem m_exampleSubsystem;

  SendableChooser<String> mStringChooser = new SendableChooser<>();
  
  // Declare grabber substystem ?
  
  
  
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    /* CONSTRUCT THE SUBSYSTEMS
     *
     */
    m_Arm = new Arm();  // construct the arm 
    m_Intake = new Intake();   // construct the intake 
    m_exampleSubsystem = new ExampleSubsystem();
    m_Drive = new DriveSubsystem();
    // construct the grabber Subssytem
    m_driverController = new CommandXboxController(OperatorConstants.kDriverControllerPort);
    m_systemController = new CommandXboxController(OperatorConstants.kSystemControllerPort);
  
    setDefaultCommands();
    configureJoystickButtonBindings(); 
  }

  private void setDefaultCommands() {
    // set Arm default command
    m_Arm.setDefaultCommand(new RunCommand(() -> 
      m_Arm.move(
        m_systemController.getRawAxis(k_logitech.rightYaxis)), 
      m_Arm));
    
    // set Chassis default command
    m_Drive.setDefaultCommand(new RunCommand(() -> 
      m_Drive.drive(
        m_driverController.getRawAxis(k_logitech.leftYaxis), 
        m_driverController.getRawAxis(k_logitech.rightXaxis)), 
      m_Drive));
    
    // set Intake default command
    m_Intake.setDefaultCommand(new RunCommand(() -> 
      m_Intake.spin(
        m_systemController.getRawAxis(k_logitech.leftXaxis)), 
      m_Intake));

    // set grabber default command

  }


  private void configureJoystickButtonBindings() {
  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    //new Trigger(m_exampleSubsystem::exampleCondition).onTrue(new ExampleCommand(m_exampleSubsystem));

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
  
    new Trigger(m_systemController.a()).onTrue(new RunIntakeTillNote(m_Intake));
    new Trigger(m_systemController.b()).onTrue(new EjectNote(m_Intake));
    new Trigger(m_systemController.x()).onTrue(new MoveArmToPosition(k_arm.ArmUpPosition, m_Arm));
    new Trigger(m_systemController.y()).onTrue(new MoveArmToPosition(k_arm.ArmScoringPostion, m_Arm));

  }

  public Command getAutonomousCommand() {
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  
    // An example command will be run in autonomous
    return Autos.exampleAuto(m_exampleSubsystem);
  }
}
