/*----------------------------------------------------------------------------*/
  /* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team687.robot;

import org.usfirst.frc.team687.robot.subsystems.Jevois;
import org.usfirst.frc.team687.robot.subsystems.Streamer;
import org.usfirst.frc.team687.robot.subsystems.Drive;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * 
 */
public class Robot extends TimedRobot {

	public static Drive drive;
	public static Jevois jevois;
	public static Subsystem livestream;
	
	public static OI oi;

	@Override
	public void robotInit() {
	    jevois = new Jevois(115200, SerialPort.Port.kUSB);
	    livestream = new Streamer();
	    drive = new Drive();
	    oi = new OI();
	}

	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void autonomousInit() {
		
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {

	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {

	    
		Scheduler.getInstance().run();
		SmartDashboard.putNumber("Target Distance Error: ", jevois.getDistanceTargetError());
		SmartDashboard.putNumber("Target Angle Error: ", jevois.getAngularTargetError());
		SmartDashboard.putNumber("Contour ID: ", jevois.getContourID()); // 1st in list
		SmartDashboard.putNumber("X coord: ", jevois.getTargetX()); // 4th
		SmartDashboard.putNumber("Y coord: ", jevois.getTargetY()); // 3rd
		SmartDashboard.putNumber("Height: ", jevois.getTargetHeight()); // 6th
		SmartDashboard.putNumber("Length: ", jevois.getTargetLength()); // 5th
		SmartDashboard.putNumber("Area: ", jevois.getTargetArea()); // 2nd

	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
    public void testPeriodic() {
    }
}
