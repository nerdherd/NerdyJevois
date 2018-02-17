package org.usfirst.frc.team687.util;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;
//import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

public class CameraThread extends Subsystem implements Runnable {
    private SerialPort cam;
    private boolean send;
    private String sendValue;
    private Thread stream;
    String[] parts;
    private boolean dataStatus;
    private boolean dataParsing;
    private double target_centroid_pixel;
    private double target_length_pixel;
//    private double contourID;
    private boolean streamStatus;
    
    public CameraThread(){
	dataParsing = false;
	dataStatus = false;
	streamStatus = false;
	send = false;
	sendValue = "None";
    	cam = new SerialPort(115200, SerialPort.Port.kUSB);
	stream = new Thread(this);
	stream.start();
    }
    
    public void run() {
//	init();
	while(stream.isAlive()){
	    if(send){
		cam.writeString(sendValue);
		send = false;
	    }
//	    if(dataStatus && cam.getBytesReceived()>0){
	    if(cam.getBytesReceived()>0){
//		cam.readString();
		System.out.println("data status is true!!!");
		String read = cam.readString();
		if(read.charAt(0) == '/'){
		    parts = dataParse(read);
//		contourID = Integer.parseInt(getData(0));
		target_centroid_pixel = Double.parseDouble(getData(2));
		target_length_pixel = Double.parseDouble(getData(5));
//		    System.out.println(getData(0));
//    		    System.out.println(getData(2));
//    		    System.out.println(getData(5));
		} else {
		    System.out.println(read);
		}
	    }
//	    } else if(!dataStatus && cam.getBytesReceived()>0) {
//		System.out.println("data status is false!!!");
//		System.out.println(cam.readString());
//	    }
//	    if(cam.getBytesReceived()>0){
//		System.out.println("Value Received: " + cam.readString());
//		dataStatus = true;
////		cam.readString();
////		dataParse(cam.readString());
//	    } else {
////		System.out.println("RUNNING AT LEAST....");
//		dataStatus = false;
//	    }
	}
    }
    
    public void setStatus(boolean dataStatus){
	this.dataStatus = dataStatus;
    }
    
    public boolean getStatus(){
	return dataStatus;
    }
    
    public boolean isAlive(){
	return stream.isAlive();
    }
    
    public void end(){
	stream.interrupt();
    }
    
    private void sendCommand(String value){
	sendValue = value;
	send = true;
    }
    
//    private String sendCommand(String command){
//	cam.writeString(command);
//	return cam.readString();
//    }
    
    private String[] dataParse(String input){
	System.out.println("THIS IS THE INPUT: " + input);
//	this.parts = input.split("/");
	dataParsing = true;
	return input.split("/");
//	for (String data : parts) System.out.println(data);
    }
    
//    private double getData(int data){
    private String getData(int data){
	System.out.println("THIS IS THE DATA: " + parts[data]);
	return parts[data];
//	return Double.parseDouble(parts[data]);
    }
    
    public void ping(){
	sendCommand("ping\n");
    }
    
//    public double getContourID(){
//	return contourID;
//    }
    
    public double getTargetCentroidPixel(){
	return target_centroid_pixel;
    }
    
    public double getTargetLengthPixel(){
	return target_length_pixel;
    }
    
//    public void init(){
//	sendCommand("setmapping 0\n");
//	sendCommand("setmapping2 YUYV 320 240 59.9 YUYV 320 240 59.9 JeVois HSVDetector\n");
//	Timer.delay(0.01);
//	sendCommand("setpar serlog All\n");
//	Timer.delay(0.01);
//	sendCommand("setpar serout All\n");
//	Timer.delay(0.01);
//	sendCommand("streamon\n");
//	Timer.delay(0.01);
//    }
    
    public void setMapping(){
	sendCommand("setmapping2 YUYV 320 240 59.9 YUYV 320 240 59.9 JeVois HSVDetector\n");
	Timer.delay(0.01);
    }
    
    public void log(){
	sendCommand("setpar serlog All\n");
	Timer.delay(0.01);
	sendCommand("setpar serout All\n");
	Timer.delay(0.01);
    }
    
    public void streamon(){
	sendCommand("streamon\n");
	Timer.delay(0.01);
    }

    public void streamoff(){
	sendCommand("streamoff\n");
	Timer.delay(0.01);
    }
    
    @Override
    protected void initDefaultCommand() {
	// TODO Auto-generated method stub
	
    }
    
}