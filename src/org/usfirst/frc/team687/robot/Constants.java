package org.usfirst.frc.team687.robot;

public class Constants {
	
	// FOV and resolution values are switched since JeVois is mounted sideways
	
	public final static double kScreenWidth = 240;
	public final static double kScreenHeight = 320;
	
	public final static double kHorizontalFOV = 39.66;	
	public final static double kVerticalFOV = 51.36;
	
	public final static double kFocalLength = kScreenWidth / (2 * Math.tan(Constants.kHorizontalFOV / 2));

	public static double kDriveRotationDeadband = 0.5;
	
	//added to avoid compile errors on util
	public static double kWheelDiameter = 0;
	public static double kJoystickDeadband = 0;
}
