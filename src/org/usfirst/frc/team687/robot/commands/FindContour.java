package org.usfirst.frc.team687.robot.commands;

import org.usfirst.frc.team687.robot.Constants;
import org.usfirst.frc.team687.robot.Robot;
import org.usfirst.frc.team687.robot.commands.LiveTargetTracking;


import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class FindContour extends CommandGroup {
    
    private double kP = 0.012;

    public FindContour() {
	requires(Robot.drive);
	requires(Robot.jevois);
    }


    protected void initialize() {
	
    }

    protected void execute() {
	double relativeAngleError = Robot.jevois.getAngularTargetError();
	
	double power = kP * Math.abs(relativeAngleError);

	if(Math.abs(relativeAngleError) <= Constants.kDriveRotationDeadband || Math.abs(relativeAngleError) >= 25 ){
        	if(Robot.jevois.getContourID() < 0) {
        	    Robot.drive.setPower(power, -power);
        	} else {
        	    addSequential(new LiveTargetTracking());
        	}
	}
    }

 
    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }


    protected void interrupted() {
    }
}
