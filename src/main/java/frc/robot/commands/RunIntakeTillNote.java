// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
import frc.robot.Constants.k_intake;
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.Command;


/** Run Intake Till Note
 *   This command turns the intake motors on and runs until a note is recieved.
 *    It's currently set up to see a note by way of a ultrasonic sensor. 
 *    The motors will run until the sensor see somehting that's a specified
 *    distance away -- this distance is specified in Constants.
 *    
 *    It might be worth considering if this command could be turned off
 *     by using the speed of the intake motors -- since a note would
 *     likely slow the rollers down -- but this is not implemented.
 *    
 *    Something should be added to assure that this command actually stops,
 *    even if the ultrasonic sensor stops working -- which is quite likely
 *    since its a fragile device. 
 */
public class RunIntakeTillNote extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Intake m_intake;

  /**
   * Constructs a new RunIntakeTillNote command.
   * @param subsystem The subsystem used by this command.
   */
  public RunIntakeTillNote(Intake intake) {
    m_intake = intake;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_intake);
  }

  @Override
  public void initialize() {
    m_intake.setStatus("I'm about to suck in a juicy node.");
    System.out.println("Starting RunIntakeTilNote");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    System.out.println("Executing RunIntakeTillNote");
    m_intake.spin(1);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_intake.setStatus("Mmmm... node...");
    System.out.println("Ending RunIntakeTillNote");
    m_intake.halt();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    double d = m_intake.getDistance();
    System.out.println("Checking if finished with RunIntakeTillNote" + d);
    return (m_intake.getDistance() < k_intake.rangeToStopMotionInInches);
  }
}
