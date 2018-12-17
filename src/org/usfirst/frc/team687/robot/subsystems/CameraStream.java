package org.usfirst.frc.team687.robot.subsystems;

import org.opencv.core.Mat;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class CameraStream extends Subsystem implements Runnable{
	
	UsbCamera cam;
	CvSource outputStream;
	CvSink cvSink;
	Mat img;
	
	public CameraStream() {
		 cam = CameraServer.getInstance().startAutomaticCapture();
         cam.setResolution(320, 240); // double check resolution values
         
         img = new Mat();
         
         cvSink = CameraServer.getInstance().getVideo();
         outputStream = CameraServer.getInstance().putVideo("Camera Stream", 320, 240);
	}
	
	public void run() {
		// uncomment while loop when we established that data is transmitting

		if (cvSink.grabFrame(img) == 0) { // if there's an error 
			outputStream.notifyError(cvSink.getError()); // say what the error is
		}
//		while((cvSink.grabFrame(img) != 0)) { 
			outputStream.putFrame(img); //otherwise display the image
//		}
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

