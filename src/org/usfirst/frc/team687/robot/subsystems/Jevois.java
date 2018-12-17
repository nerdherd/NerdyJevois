package org.usfirst.frc.team687.robot.subsystems;

import org.usfirst.frc.team687.robot.Constants;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Jevois extends Subsystem implements Runnable {

	public int contour;
	public double targetAreaPx, targetYCenterPx, targetXCenterPx, targetLengthPx, targetHeightPx, imageWidth,
			imageHeight, focalLength;

	public String[] data;
	public String command;

	public SerialPort cam;

	public Thread jevoisStream;

	public Jevois(int baudRate, SerialPort.Port port) {
		cam = new SerialPort(baudRate, port);
	}

	public void run() {
		while (jevoisStream.isAlive()) { // set thread in a while loop to continuously get data
			Timer.delay(0.01);
			
			data = cam.readString().split("/");
			System.out.println(cam.readString()); // delet this after testing

			contour = Integer.parseInt(data[1]);
			targetAreaPx = Double.parseDouble(data[2]);
			targetYCenterPx = Double.parseDouble(data[3]); // notice y is before x because the 
			targetXCenterPx = Double.parseDouble(data[4]); // Jevois is on its side when mounted
			targetLengthPx = Double.parseDouble(data[5]);
			targetHeightPx = Double.parseDouble(data[6]);
		}
	}

	// note: we don't subtract pixel by centroid pixel
	// since the JeVois provides pixels to target

//	public void endStream() {
//		jevoisStream.interrupt();
//	}
	
	public void printData(double value) {
		System.out.println(value);
	}
	
	public double getAngularError() {
		return pixelsToDegrees(targetXCenterPx);
	}
	
	public double pixelsToDegrees(double px) {
		return Math.signum(px) * Math.atan(px / Constants.kFocalLength); // double check signum. extra or nah
	}

	public double getContour() {
		return contour;
	}

	public double getTargetAreaPx() {
		return targetAreaPx;
	}

	public double getTargetYCenterPx() {
		return targetYCenterPx;
	}

	public double getTargetXCenterPx() {
		return targetXCenterPx;
	}

	public double getTargetLengthPx() {
		return targetLengthPx;
	}

	public double getTargetHeightPx() {
		return targetHeightPx;
	}

	public void initDefaultCommand() {

	}
}
