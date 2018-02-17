package org.usfirst.frc.team687.robot.subsystems;

import org.usfirst.frc.team687.robot.RobotMap;
import org.usfirst.frc.team687.util.CameraThread;
import org.usfirst.frc.team687.util.NerdyPID;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class TargetDetector extends Subsystem {
    
    /** _actual means field dimensions
     *  _pixel means camera pixel dimensions
     */

    private AHRS navx;
    private NerdyPID pid;
    private CameraThread jevois;
	
    private double camera_FOV_pixel = 360;
    private double camera_FOV_degree = 65;

    //JeVois Values
//    public double target_centroid_pixel;
//    public double target_length_pixel;
//    public double contourID;
	
    public TargetDetector(){
	init();
    }
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public boolean getStatus(){
	return jevois.getStatus();
    }
    
    public boolean isAlive(){
	return jevois.isAlive();
    }
    
    private void init(){
	jevois = new CameraThread();
	pid = new NerdyPID();
    	navx = new AHRS(SerialPort.Port.kMXP);
    }
    
    public void update(){
//	jevois.isAlive();
//	contourID = jevois.getData(0);
//	target_centroid_pixel = jevois.getData(2);
//	target_length_pixel = jevois.getData(5);
    }
    
    public double angleMotorOutput(){
	double power = pid.calculate(getTargetAngle(jevois.getTargetLengthPixel()));
	return power;
    }
    
    public double targetMotorOutput(){
	double power = pid.calculate(getTargetDistance(jevois.getTargetLengthPixel()));
	return power;
    }
    
    public double getTargetDistance(double target_length_pixel_m){
    	double target[] = getTargetCenter(target_length_pixel_m);
    	double distance = hypotenuseCalc(target[0], target[1]);
    	return distance;
    }
    
    private double[] getTargetCenter(double target_length_pixel_m) {
//	double camera_FOV_inch = pixelToInch(camera_FOV_pixel, cube_length_inch);
//	double target_inch = pixelToInch(target_centroid_pixel, cube_length_inch);
//	double error_inch = target_inch - 0.5 * camera_FOV_inch;

    	double[] target_coordinate_inch = new double[1];
	
	double x_error_pixel = Math.abs(jevois.getTargetCentroidPixel() - (camera_FOV_pixel/2));
	target_coordinate_inch[0] = pixelToInch(x_error_pixel, RobotMap.CUBE_LENGTH_INCH);
	
//    	double ratio_pixel_to_actual = target_length_pixel/cube_length_inch;
//    	double X_length_pixel = Math.abs(target_centroid_pixel - (camera_FOV_pixel/2));
//    	double X_length_actual = ratio_actual_to_pixel * X_length_pixel; //x
    	
//    	double ratio_degree_to_actual = camera_FOV_degree/camera_FOV_pixel;
//    	double beta = ratio_degree_to_actual * target_length_pixel; //b
    	
    	target_coordinate_inch[1] = target_coordinate_inch[0]/Math.tan(pixelToDegree(target_length_pixel_m)); //y
    	
    	return target_coordinate_inch;
    }
    
    private double getTargetAngle(double target_length_pixel) {
    	double alpha = navx.getYaw();
    	double beta = pixelToDegree(target_length_pixel);
    	
    	return beta+alpha;
    }
    
    private double pixelToDegree(double pixel){
	double degree = pixel*camera_FOV_degree/camera_FOV_pixel;
	return degree;
    }
    
    private double pixelToInch(double input , double reference){
	double inch = camera_FOV_pixel * reference / input;
	return inch;
    }
    
    private double hypotenuseCalc(double a, double b){
	double c = Math.sqrt(Math.pow(a,2) + Math.pow(b,2));
	return c;
    }
    
    //debugging
    public double getError(){
	return pid.getError();
    }
    
    public void ping(){
	jevois.ping();
    }
    
    public void streamon(){
	jevois.streamon();
    }
    
    public void streamoff(){
	jevois.streamoff();
    }
    
    public void log(){
	jevois.log();
    }
    
    public void setMapping(){
	jevois.setMapping();
    }

    public void end() {
	jevois.end();
    }
    
    public void setStatus(boolean status){
	jevois.setStatus(status);
    }
}

