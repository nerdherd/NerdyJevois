package org.usfirst.frc.team687.robot.subsystems;

import edu.wpi.first.wpilibj.SerialPort;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Drive extends Subsystem {
	
    public TalonSRX l1, l2, l3, r1, r2, r3;
    public AHRS m_nav;

    public Drive() {
	m_nav = new AHRS(SerialPort.Port.kMXP);
	l1 = new TalonSRX(1); // renew ports
	l2 = new TalonSRX(5);
//	l3 = new TalonSRX(2);
	r1 = new TalonSRX(0);
	r2 = new TalonSRX(4);
//	r3 = new TalonSRX(6);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public double getCurrentYaw() {
	return m_nav.getYaw();
    }
    
    public void resetNavX(){
	m_nav.reset();
    }
    
    public void setPower(double leftPower, double rightPower) {
    	r1.set(ControlMode.PercentOutput, rightPower);
    	r2.set(ControlMode.PercentOutput, rightPower);
//    	r3.set(ControlMode.PercentOutput, rightPower);
    	l1.set(ControlMode.PercentOutput, leftPower);
    	l2.set(ControlMode.PercentOutput, leftPower);
//    	l3.set(ControlMode.PercentOutput, leftPower);
    }
    
    public void stopDrive(){
    	setPower(0.0, 0.0);
    }
}