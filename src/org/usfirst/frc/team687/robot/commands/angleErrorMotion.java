package org.usfirst.frc.team687.robot.commands;

import org.usfirst.frc.team687.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class angleErrorMotion extends Command {

    private double angleTolerance;
    
    public angleErrorMotion(double angleTolerance) {
	this.angleTolerance = angleTolerance;
	
	requires(Robot.targetDetect);
	setTimeout(3.0);
    }

    protected void initialize() {
	
    }

    protected void execute() {
	System.out.println(Robot.targetDetect.angleMotorOutput());
    }

    protected boolean isFinished() {
        return isTimedOut() || (Robot.targetDetect.getError() < angleTolerance) ? true : false;
    }

    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
