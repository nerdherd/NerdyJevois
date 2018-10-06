package org.usfirst.frc.team687.robot;

import org.usfirst.frc.team687.robot.commands.streamoff;
import org.usfirst.frc.team687.robot.commands.streamon;
import org.usfirst.frc.team687.robot.commands.LiveTargetTracking;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * 
 */
public class OI {
	
	Joystick joy;
	JoystickButton ping, streamoff, streamon, liveTargetTrack; 
	
	public OI() {
		joy = new Joystick(0);
		
		streamoff = new JoystickButton(joy,2);
		streamoff.whenPressed(new streamoff());
		
		streamon = new JoystickButton(joy,3);
		streamon.whenPressed(new streamon());
			
		liveTargetTrack = new JoystickButton(joy, 1);
		liveTargetTrack.whenPressed(new LiveTargetTracking());
	}
}