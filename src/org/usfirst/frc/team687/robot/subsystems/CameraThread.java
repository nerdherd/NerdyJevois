package org.usfirst.frc.team687.robot.subsystems;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;
//import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CameraThread extends Subsystem implements Runnable {
    private SerialPort cam;
    private boolean send;
    private String sendValue;
    private Thread stream;
    String[] parts;
    private double target_centroid_pixel, target_length_pixel;
//    private double contourID;
    
    public CameraThread(int baud, SerialPort.Port port){
	send = false;
	sendValue = "None";
    	cam = new SerialPort(115200, SerialPort.Port.kUSB);
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
//		cam.readString();
//		System.out.println("data status is true!!!");
		String read = cam.readString();
		if(read.charAt(0) == '/'){
		    parts = dataParse(read);
//		    contourID = Integer.parseInt(getData(0));
		    target_centroid_pixel = Math.abs(Double.parseDouble(getData(3)));
		    target_length_pixel = Math.abs(Double.parseDouble(getData(6)));
//    		    System.out.println(getData(1)); //contour 
//    		    System.out.println(getData(2)); //area
//    		    SmartDashboard.putNumber("x coordinate: ", Double.parseDouble(getData(3))); //x-coordinate
//    		    System.out.println(getData(4)); //y-coordinate
//    		    System.out.println(getData(5)); //height
//    		    System.out.println(getData(6)); //width
		} else {
		    System.out.println(read);
		}
	    }
	}
    }
    
    public double getTargetX(){
	return target_centroid_pixel;
    }
    
    public double getTargetLength(){
	return target_length_pixel;
    }
    
    public void end(){
	stream.interrupt();
    }
    
    private void sendCommand(String value){
	sendValue = value + "\n";
	send = true;
    }
    
    
    
    private String[] dataParse(String input){
//	System.out.println("THIS IS THE WHOLE INPUT: " + input);
	return input.split("/");
    }
    
//    private double getData(int data){
    private String getData(int data){
//	System.out.println("THIS IS THE PARSED DATA: " + parts[data]);
	return parts[data];
//	return Double.parseDouble(parts[data]);
    }
    
    public void ping(){
	sendCommand("ping");
    }
    
//    public double getContourID(){
//	return contourID;
//    }
    
    public double getTargetCentroidXPixel(){
	return target_centroid_pixel;
    }
    
    public double getTargetLengthPixel(){
	return target_length_pixel;
    }
    
    public void init(){
	streamoff();
	log();
	streamon();
//	noAutoExp();
    }
    
    public void setMapping(){
	sendCommand("setmapping2 YUYV 320 240 59.9 YUYV 320 240 59.9 JeVois HSVDetector");
	Timer.delay(0.1);
    }
    
    public void setAbsExp(){
   	sendCommand("setcam absexp 800");
   	Timer.delay(0.1);
       }
    
    public void log(){
	sendCommand("setpar serlog All");
	Timer.delay(0.1);
	sendCommand("setpar serout All");
	Timer.delay(0.1);
    }
    
    public void streamon(){
	sendCommand("streamon");
	Timer.delay(0.1);
    }

    public void streamoff(){
	sendCommand("streamoff");
	Timer.delay(0.1);
    }
    
    public void noAutoExp(){
	sendCommand("setcam autoexp 0");
	Timer.delay(0.1);
    }
    
    @Override
    protected void initDefaultCommand() {
	
    }
    
}