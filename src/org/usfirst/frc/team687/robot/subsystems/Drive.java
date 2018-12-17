package org.usfirst.frc.team687.robot.subsystems;

import org.usfirst.frc.team687.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Drive extends Subsystem {

    private final TalonSRX m_leftMaster, m_leftSlave, m_rightMaster, m_rightSlave;

    public void initDefaultCommand() {
        
    }
    
    public Drive() {
    	m_leftMaster = new TalonSRX(RobotMap.kLeftMasterTalonID);
    	m_rightMaster = new TalonSRX(RobotMap.kRightMasterTalonID);
    	
    	m_leftSlave = new TalonSRX(RobotMap.kLeftSlaveTalon1ID);
    	m_rightSlave = new TalonSRX(RobotMap.kRightSlaveTalon1ID);
    }
    
    public void setPower(double leftPower, double rightPower) {
    	m_leftMaster.set(ControlMode.PercentOutput, leftPower);
    	m_leftSlave.set(ControlMode.PercentOutput, leftPower);
    	
    	m_rightMaster.set(ControlMode.PercentOutput, rightPower);
    	m_rightSlave.set(ControlMode.PercentOutput, rightPower);

    }
    
    public void testDrive() {
    	setPower(.25, -.25);
    }
    
    public void stopDrive() {
    	setPower(0,0);
    }
    
    
}

