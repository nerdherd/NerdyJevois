package org.usfirst.frc.team687.robot.commands;

import org.usfirst.frc.team687.robot.Robot;
import org.usfirst.frc.team687.util.NerdyPID;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TargetApproaching extends Command {

    double h, alpha, beta, hc, x, Y, Z;
    NerdyPID pid;
    
    public TargetApproaching() {
	SmartDashboard.putString("Current Command", "TargetApproaching");
	h = 42; //mount_height
	alpha = 70; //mount_angle
	beta = 65;//FOV_angle_Y
	hc = 11; //cube_height
	Y = 320; //FOV_pixel_Y
	requires(Robot.drive);
	pid = new NerdyPID();
	pid.setPID(0.001,0,0.001);
	setTimeout(3.0);
    }

    protected void initialize() {
    }

    protected void execute() {
	x = (h-hc)*(Math.tan((beta/2)-(beta*Robot.targetDetect.getTargetY()/Y)+(alpha-(beta/2))));
	System.out.println(x);
//	pid.calculate(x);
    }

    protected boolean isFinished() {
        return isTimedOut();
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
