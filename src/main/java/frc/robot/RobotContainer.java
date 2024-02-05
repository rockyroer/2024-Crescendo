// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.Constants.k_xbox;
import frc.robot.commands.Autos;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

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
  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);
  private final CommandXboxController m_systemController =
      new CommandXboxController(OperatorConstants.kSystemControllerPort);
  // Create drive train subsytem ?  
  private final Arm m_Arm;
  // Create grabber substystem ?
  // Create intake subsystem ?
  // Replace with CommandPS4Controller or CommandJoystick if needed
  // The provided example: 
     private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  
  
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    /* CONSTRUCT THE SUBSYSTEMS
     *
     */
    // construct the drive/chassis subsytem
    m_Arm = new Arm();  // construct the arm 
    // construct the intake 
    // construct the grabbers
    // construct the joysticks
    
    
    setDefaultCommands();
    configureJoystickButtonBindings(); 
  }

  private void setDefaultCommands() {
    // set chassis default command
    m_Arm.setDefaultCommand(
      new RunCommand(() -> m_Arm.move(
        m_systemController.getRawAxis(k_xbox.rightYaxis)), m_Arm));
    // set grabber default command
    // set intake default command
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
    new Trigger(m_exampleSubsystem::exampleCondition)
        .onTrue(new ExampleCommand(m_exampleSubsystem));

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
    m_driverController.b().whileTrue(m_exampleSubsystem.exampleMethodCommand());
    
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