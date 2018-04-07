package org.usfirst.frc.team687.robot.commands;

import edu.wpi.first.wpilibj.SerialPort;
import org.usfirst.frc.team687.robot.Constants;
import org.usfirst.frc.team687.robot.Robot;
import org.usfirst.frc.team687.util.NerdyMath;
import org.usfirst.frc.team687.util.PGains;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Live vision tracking (follows vision target around, no end)
 * 
 * @author tedlin
 *
 */

public class LiveTargetTracking extends Command {

	private PGains m_rotPGains;

	/**
	 * @param isHighGear
	 */
	public LiveTargetTracking() {
		requires(Robot.drive);
		setTimeout(3.0);
	}

	@Override
	protected void initialize() {
		SmartDashboard.putString("Current Command", "LiveTargetTracking");
		m_rotPGains = new PGains(0.003, 0.12, 1.0); //kp ,min pow, max pow
	}

	@Override
	protected void execute() {
		double relativeAngleError = Robot.jevois.getAngularTargetError(); //relative error in degrees to target
//		double processingTime = VisionAdapter.getInstance().getProcessedTime();
//		double absoluteDesiredAngle = relativeAngleError + Robot.drive.timeMachineYaw(processingTime); // latency compensation
		

		double power = m_rotPGains.getP() * relativeAngleError;
		power = NerdyMath.threshold(power, m_rotPGains.getMinPower(), m_rotPGains.getMaxPower());
		if (Math.abs(relativeAngleError) <= Constants.kDriveRotationDeadband) {
			power = 0;
		}

		Robot.drive.setPower(power, power);
	}

	@Override
	protected boolean isFinished() {
	    return false;
//	    return isTimedOut() || (Robot.jevois.getAngularTargetError() < Constants.kAngleTolerance) ? true : false;
	}

	@Override
	protected void end() {
		Robot.drive.stopDrive();
	}

	@Override
	protected void interrupted() {
		end();
	}
}