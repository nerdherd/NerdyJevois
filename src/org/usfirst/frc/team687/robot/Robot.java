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
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * 
 */
public class Robot extends TimedRobot {
	public static Drive drive;
	public static OI oi;
	public static Jevois jevois;
//	public static TargetDetector targetDetect;
	public static Subsystem livestream;
	
	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
//	    targetDetect = new TargetDetector();
	    jevois = new Jevois(115200, SerialPort.Port.kUSB);
	    livestream = new Streamer();
	    drive = new Drive();
	    oi = new OI();
	    SmartDashboard.putData("Auto mode", m_chooser);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
	    	jevois.end();
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		m_autonomousCommand = m_chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
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
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
	    /*
	     * parts = dataParse(read);
		    contour_id = Integer.parseInt(getData(1));
		    target_area_pixel = Double.parseDouble(getData(2));
		    target_centroid_Y_pixel = Double.parseDouble(getData(3));
		    target_centroid_X_pixel = Double.parseDouble(getData(4));
		    target_length_pixel = Double.parseDouble(getData(5));
		    target_height_pixel = Double.parseDouble(getData(6));
	     */
	    
		Scheduler.getInstance().run();
		SmartDashboard.putNumber("Target Distance Error: ", jevois.getDistanceTargetError());
		SmartDashboard.putNumber("Target Angle Error: ", jevois.getAngularTargetError());
//		SmartDashboard.putNumber("Yaw: ", drive.getCurrentYaw());
		SmartDashboard.putNumber("Contour ID: ", jevois.getContourID());
		SmartDashboard.putNumber("X coord: ", jevois.getTargetX());
		SmartDashboard.putNumber("Y coord: ", jevois.getTargetY());
		SmartDashboard.putNumber("Height: ", jevois.getTargetHeight());
		SmartDashboard.putNumber("Length: ", jevois.getTargetLength());
		SmartDashboard.putNumber("Area: ", jevois.getTargetArea());

	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
