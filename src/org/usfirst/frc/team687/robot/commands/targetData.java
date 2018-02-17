package org.usfirst.frc.team687.robot.commands;

import org.usfirst.frc.team687.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class targetData extends Command {

    public targetData() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
	requires(Robot.targetDetect);
	setTimeout(4);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
	Robot.targetDetect.setStatus(true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
	Robot.targetDetect.setStatus(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
