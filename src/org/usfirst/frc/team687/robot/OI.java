/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team687.robot;

import org.usfirst.frc.team687.robot.commands.PrintData;
import org.usfirst.frc.team687.robot.commands.TestDrive;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	public Joystick joy;
	public JoystickButton testDrive, printData;
	
	public OI() {
		testDrive = new JoystickButton(joy ,12);
		testDrive.whileHeld(new TestDrive());
		
		printData = new JoystickButton(joy, 11);
		printData.whileHeld(new PrintData());
	}
	
}
