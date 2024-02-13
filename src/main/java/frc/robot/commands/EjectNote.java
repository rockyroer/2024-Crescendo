// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
import frc.robot.Constants.k_intake;
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.Command;


/** Eject Note
 *  This command is built to run the intake for a specified amount of time to eject a note,
 *  ideally into the amplifier.
 *  The amount of time is specified in the constants
 *  The speed of the ejection is specified in constants. 
*/
public class EjectNote extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Intake m_intake;
  private double m_speed = 0.0, m_time = 0.0;
  private double startTime;

  
  public EjectNote(Intake intake) {
    m_intake = intake;
    m_speed = k_intake.ejectSpeed;
    m_time = k_intake.ejectTimeMilliseconds;

    addRequirements(m_intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_intake.setStatus("About to eject a note.");
    startTime = System.currentTimeMillis();
    System.out.println("Starting Eject Note Command");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_intake.setStatus("Ejecting a note.");
    m_intake.spin(m_speed);
    System.out.println("Executing Eject Node");
    //m_Intake.spin(1);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_intake.setStatus("Ack!");
    System.out.println("Eject Node interupted!");
    m_intake.halt();
  }


  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    double elapsedTime = System.currentTimeMillis() - startTime;
    System.out.println("Checking if Eject Node is finished:" + elapsedTime);
    return (elapsedTime > m_time);
  }
}
