package org.usfirst.frc.team687.robot;

import org.usfirst.frc.team687.robot.commands.ping;
import org.usfirst.frc.team687.robot.commands.streamoff;
import org.usfirst.frc.team687.robot.commands.streamon;
import org.usfirst.frc.team687.robot.commands.log;
import org.usfirst.frc.team687.robot.commands.resetNavX;
import org.usfirst.frc.team687.robot.commands.LiveTargetTracking;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * 
 */
public class OI {
	
	Joystick joy;
	JoystickButton ping, streamoff, streamon, log, resetNavX, liveTargetTrack; 
	
	public OI() {
		joy = new Joystick(0);
		
//		ping = new JoystickButton(joy, 1);
//		ping.whenPressed(new ping());
//		
//		streamoff = new JoystickButton(joy,2);
//		streamoff.whenPressed(new streamoff());
//		
//		streamon = new JoystickButton(joy,3);
//		streamon.whenPressed(new streamon());
//		
//		log = new JoystickButton(joy, 4);
//		log.whenPressed(new log());
		
		resetNavX = new JoystickButton(joy,3);
		resetNavX.whenPressed(new resetNavX());
		
		liveTargetTrack = new JoystickButton(joy, 1);
		liveTargetTrack.whenPressed(new LiveTargetTracking());
	}
}