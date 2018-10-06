package org.usfirst.frc.team687.robot.subsystems;

import org.usfirst.frc.team687.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Drive extends Subsystem {

    private final TalonSRX m_leftMaster, m_rightMaster, m_leftSlave1, m_rightSlave1;
    // public AHRS m_nav;

    public Drive() {
	// m_nav = new AHRS(SerialPort.Port.kMXP);
//	m_leftMaster = new TalonSRX(RobotMap.kLeftMasterTalonID); // renew ports
//	m_rightMaster = new TalonSRX(RobotMap.kRightMasterTalonID);
//
//	m_leftSlave1 = new TalonSRX(RobotMap.kLeftSlaveTalon1ID);
//	m_rightSlave1 = new TalonSRX(RobotMap.kRightSlaveTalon1ID);
	
	
	m_rightSlave1 = new TalonSRX(2);
	m_rightMaster = new TalonSRX(1);

	m_leftMaster = new TalonSRX(4); // renew ports
	m_leftSlave1 = new TalonSRX(6);

    }

    public void initDefaultCommand() {
    }

//     public double getCurrentYaw() {
////     return m_nav.getYaw();
//     }
    
     public void resetNavX(){
//     m_nav.reset();
     }

    public void setPower(double leftPower, double rightPower) {
	m_leftMaster.set(ControlMode.PercentOutput, leftPower);
	m_rightMaster.set(ControlMode.PercentOutput, rightPower);

	m_leftSlave1.set(ControlMode.PercentOutput, leftPower);
	m_rightSlave1.set(ControlMode.PercentOutput, rightPower);
	
	SmartDashboard.putNumber("Right Power", rightPower);
	SmartDashboard.putNumber("Left Power ", leftPower);
    }

    public void stopDrive() {
	setPower(0.0, 0.0);
    }
    
    public void testDrive(){
	setPower(.25, -.25);
    }
    
    public void printPower(){
	
    }
}