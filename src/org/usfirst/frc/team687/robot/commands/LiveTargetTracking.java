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
		m_rotPGains = new PGains(0.010, 0.12, 1.0);
	}

	@Override
	protected void execute() {
		double robotAngle = (360 - Robot.drive.getCurrentYaw()) % 360;
		double relativeAngleError = Robot.jevois.getAngularTargetError(); // get from JeVois
//		double processingTime = VisionAdapter.getInstance().getProcessedTime();
//		double absoluteDesiredAngle = relativeAngleError + Robot.drive.timeMachineYaw(processingTime); // latency compensation
		double absoluteDesiredAngle = relativeAngleError + Robot.drive.getCurrentYaw();
		double error = absoluteDesiredAngle - robotAngle;
		error = (error > 180) ? error - 360 : error;
		error = (error < -180) ? error + 360 : error;

		double power = m_rotPGains.getP() * error;
		power = NerdyMath.threshold(power, m_rotPGains.getMinPower(), m_rotPGains.getMaxPower());
		if (Math.abs(error) <= Constants.kDriveRotationDeadband) {
			power = 0;
		}

		Robot.drive.setPower(power, power);
	}

	@Override
	protected boolean isFinished() {
	        return isTimedOut() || (Robot.jevois.getAngularTargetError() < Constants.kAngleTolerance) ? true : false;
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
