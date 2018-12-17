package org.usfirst.frc.team687.robot.commands;

import org.usfirst.frc.team687.robot.Constants;
import org.usfirst.frc.team687.robot.Robot;
import org.usfirst.frc.team687.util.NerdyMath;
import org.usfirst.frc.team687.util.PGains;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SnapToTarget extends Command {
	
	private PGains m_rotPGains;

    public SnapToTarget() {
        requires(Robot.drive);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	setTimeout(3.0);
		m_rotPGains = new PGains(0.003, 0.12, 1.0); //kp ,min pow, max pow
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double angularTargetError = Robot.jevois.getAngularError();
    	
    	double power = m_rotPGains.getP() * angularTargetError;
    	power = NerdyMath.threshold(power, m_rotPGains.getMinPower(), m_rotPGains.getMaxPower());
    	if (Math.abs(angularTargetError) <= Constants.kDriveRotationDeadband) {
    	    power = 0;
    	}
    	
    	Robot.drive.setPower(power, power);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
