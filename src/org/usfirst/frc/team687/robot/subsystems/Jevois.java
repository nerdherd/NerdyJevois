package org.usfirst.frc.team687.robot.subsystems;

import org.usfirst.frc.team687.robot.Constants;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Jevois extends Subsystem implements Runnable {
    private SerialPort cam;
    private boolean send;
    private String sendValue;
    private Thread stream;
    String[] parts;
    
    //Jevois Serial Output Data
    private double contour_id, target_area_pixel, target_centroid_X_pixel, target_centroid_Y_pixel,
    target_height_pixel, target_length_pixel;
        
    public double focalLength;
    
    public Jevois(int baud, SerialPort.Port port){
	focalLength = (Constants.kVerticalPixels/2)/Math.tan(Constants.kHorizonalFOV/2);
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
    
    public double angularTargetError(){
	System.out.println("angular Target Error:" + pixelToDegree(getTargetX()));
	return pixelToDegree(getTargetX());
    }
    
    private double pixelToDegree(double pixel){
	double sign = pixel > 0 ? 1 : -1;
	double degree = sign* Math.atan(Math.abs(pixel/focalLength));
	return degree;
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
    
    public void end(){
	stream.interrupt();
    }
    
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
    
//    public double getContourID(){
//	return contourID;
//    }
    
    public double getTargetLengthPixel(){
	return target_length_pixel;
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