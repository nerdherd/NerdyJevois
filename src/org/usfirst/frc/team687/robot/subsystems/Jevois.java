package org.usfirst.frc.team687.robot.subsystems;

import org.usfirst.frc.team687.robot.Constants;
import org.usfirst.frc.team687.robot.Robot;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Jevois extends Subsystem implements Runnable {
    private SerialPort cam;
    private boolean send;
    private String sendValue;
    private Thread stream;
    String[] parts;
    public double focalLength;
    
    //Jevois Serial Output Data
    private double contour_id, target_area_pixel, target_centroid_X_pixel, target_centroid_Y_pixel,
    target_height_pixel, target_length_pixel;
        
    private double angularTargetError, distanceTargetError;
    
    public Jevois(int baud, SerialPort.Port port){
	focalLength = (Constants.kHorizonalPixels/2)/Math.tan(Constants.kHorizonalFOV/2);
	send = false;
	sendValue = "None";
    	cam = new SerialPort(baud, port);
	stream = new Thread(this);
	stream.start();
    }
    
    public void run() {
	while(stream.isAlive()){
	    Timer.delay(0.5);
	    if(send){
		cam.writeString(sendValue);
		send = false;
	    }
	    if(cam.getBytesReceived()>0){
		String read = cam.readString();
		if(read.charAt(0) == '/'){
		    parts = dataParse(read);
		    contour_id = Integer.parseInt(getData(1));
		    target_area_pixel = Double.parseDouble(getData(2));
		    target_centroid_Y_pixel = Double.parseDouble(getData(3));
		    target_centroid_X_pixel = Double.parseDouble(getData(4));
		    target_length_pixel = Double.parseDouble(getData(5));
		    target_height_pixel = Double.parseDouble(getData(6));
		} else {
		    System.out.println(read);
		}
	    }
	}
    }
    
    //Robot functionalities
    public double getAngularTargetError(){
//	System.out.println("angular Target Error:" + pixelToDegree(getTargetX()));
//	angularTargetError = pixelToDegree(getTargetX());
	return pixelToDegree(getTargetX());
    }
    
    private double pixelToDegree(double pixel){
	double sign = pixel > 0 ? 1 : -1;
	double degree = sign* Math.atan(Math.abs(pixel/focalLength));
	return degree;
    }
    
    public double getDistanceTargetError(){
	double alpha = Math.atan(Robot.jevois.getTargetY()/focalLength);
	double beta = Constants.kCameraMountAngle + alpha;
	double centroidVar = (-alpha/45) + 1;
	double centroidHeight = Constants.kCubeHeight*centroidVar;
	distanceTargetError = centroidHeight*Math.tan(beta);
//	distanceTargetError = (Constants.kCameraMountHeight-Constants.kCubeHeight)*(Math.tan((Constants.kVerticalFOV/2)-(Constants.kVerticalFOV*Robot.jevois.getTargetY()/Constants.kVerticalPixels)+(Constants.kCameraMountAngle-(Constants.kVerticalFOV/2))));
	return distanceTargetError;
    }
    
    //get Jevois values
    public double getContourID(){
	return contour_id;
    }
    
    public double getTargetX(){
	return target_centroid_X_pixel;
    }
    
    public double getTargetY(){
	return target_centroid_Y_pixel;
    }
    
    public double getTargetLength(){
	return target_length_pixel;
    }
    
    public double getTargetHeight(){
	return target_height_pixel;
    }
    
    public double getTargetArea(){
	return target_area_pixel;
    }
    
    public void end(){
	stream.interrupt();
    }
    
    
    //jevois serial commands
    private void sendCommand(String value){
	sendValue = value + "\n";
	send = true;
	Timer.delay(0.1);
    }
    
    private String[] dataParse(String input){
	return input.split("/");
    }
    
    private String getData(int data){
	return parts[data];
    }
    
    public void ping(){
	sendCommand("ping");
    }
    
    public void init(){
	streamoff();
	log();
	streamon();
    }
    
    public void setMapping(){
	sendCommand("setmapping2 YUYV 320 240 59.9 YUYV 320 240 59.9 JeVois HSVDetector");
    }
    
    public void log(){
	sendCommand("setpar serlog All");
	sendCommand("setpar serout All");
    }
    
    public void streamon(){
	sendCommand("streamon");
    }

    public void streamoff(){
	sendCommand("streamoff");
    }
    
    @Override
    protected void initDefaultCommand() {
	
    }
    
}