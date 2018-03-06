/*----------------------------------------------------------------------------*/

/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team687.robot;

import org.usfirst.frc.team687.robot.commands.angleErrorMotion;
import org.usfirst.frc.team687.robot.commands.distanceErrorMotion;
import org.usfirst.frc.team687.robot.commands.ping;
import org.usfirst.frc.team687.robot.commands.setMapping;
import org.usfirst.frc.team687.robot.commands.streamoff;
import org.usfirst.frc.team687.robot.commands.streamon;
import org.usfirst.frc.team687.robot.commands.log;
import org.usfirst.frc.team687.robot.commands.init;
import org.usfirst.frc.team687.robot.commands.resetNavX;
import org.usfirst.frc.team687.robot.commands.absexp;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	Joystick joy;
	JoystickButton ping, streamoff, streamon, log, setMapping, angleErrorMotion, distanceErrorMotion, resetNavX,absexp, init; 
	
	//2, 4, 3, 11
	
	public OI() {
		joy = new Joystick(0);
		
		ping = new JoystickButton(joy, 1);
		ping.whenPressed(new ping());
		
		streamoff = new JoystickButton(joy,2);
		streamoff.whenPressed(new streamoff());
		
		streamon = new JoystickButton(joy,3);
		
		streamon.whenPressed(new streamon());
		
		log = new JoystickButton(joy, 4);
		log.whenPressed(new log());
		
		setMapping = new JoystickButton(joy, 5);
		setMapping.whenPressed(new setMapping());
		
		angleErrorMotion = new JoystickButton(joy, 6);
		angleErrorMotion.whenPressed(new angleErrorMotion(RobotMap.ANGLE_TOLERANCE));
//		angleErrorMotion = new Joystick(new angularErrorMotion)
		
		distanceErrorMotion = new JoystickButton(joy,7);
//		distanceErrorMotion.whenPressed(new distanceErrorMotion(RobotMap.DISTANCE_TOLERANCE));
		
		resetNavX = new JoystickButton(joy,9);
		resetNavX.whenPressed(new resetNavX());
		
		absexp = new JoystickButton(joy,10);
		absexp.whenPressed(new absexp());
		
		init = new JoystickButton(joy, 8);
		init.whenPressed(new init());
	}
	
	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);

	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());
}
