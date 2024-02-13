// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 1;
    public static final int kSystemControllerPort = 0;
  }

  public static class k_arm{
    public static final int CANMaxId = 2;
    public static final double ArmMaxSpeed = 0.2;
    public static final double kArmAngleP = 0.004;    // Make changes here
    public static final double kArmAngleI = 0;
    public static final double kArmAngleD = 0;
    public static final double kArmAngleIz = 0;
    public static final double kArmAngleFF = 0.0014;    // and here
    public static final double ArmAngleMinimumOutput = -1;
    public static final double ArmAngleMaximumOutput = 1;
    public static final double ArmUpPosition = 20;
    public static final double ArmDownPosition = -50;
    public static final double ArmScoringPostion = 60;
  }


  public static class k_intake{
    public static final int CANMaxId = 6;
    public static final double IntakeMaxSpeed = 0.5;
    public static final int rangeFinderDIOTriggerPin = 1;
    public static final int rangeFinderDIOEchoPin = 2;
    public static final double rangeToStopMotionInInches = 2;
    public static final double ejectSpeed = -0.2;
    public static final double ejectTimeMilliseconds = 1000;  
  }

  public final class k_xbox {
    public static final int buttonX = 1;
    public static final int buttonY = 4;
    public static final int buttonA = 2;
    public static final int buttonB = 3;
    public static final int buttonLeftBumper = 5;
    public static final int buttonRightBumber = 6;
    public static final int buttonLeftLowerBumper = 7;
    public static final int buttonRightLowerBumber = 8;
    public static final int buttonBack = 9;
    public static final int buttonStart = 10;
    public static final int leftXaxis = 0;
    public static final int leftYaxis = 1;
    public static final int rightXaxis = 2;
    public static final int rightYaxis = 3;
  }

  public final class k_logitech {
    public static final int buttonX = 3;
    public static final int buttonY = 4;
    public static final int buttonA = 1;
    public static final int buttonB = 2;
    public static final int buttonLeftBumper = 5;
    public static final int buttonRightBumber = 6;
    //public static final int buttonLeftLowerBumper = 7;
    //public static final int buttonRightLowerBumber = 8;
    public static final int buttonBack = 7;
    public static final int buttonStart = 8;
    public static final int buttonLeftJoystick = 9;
    public static final int buttonRightJoystick = 10;
    public static final int leftXaxis = 0;
    public static final int leftYaxis = 1;
    public static final int leftTrigger = 4;   // left Trigger is 2 for Roer's home 
    public static final int rightTrigger = 5;  // left Trigger is 3 for Roer's Home
    public static final int rightXaxis = 2;    // right x asxis is 4 for roer's homew
    public static final int rightYaxis = 3;    // righty axsis is 5 for roer's home
  }
}
