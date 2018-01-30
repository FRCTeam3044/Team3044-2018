package org.usfirst.frc.team3044.robot;

import org.usfirst.frc.team3044.Reference.*;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Drive {
	FirstController firstController = FirstController.getInstance();
	SecondController secondController = SecondController.getInstance();
	private Effectors comp = Effectors.getInstance();
	DigitalInput limitSwitchOut = new DigitalInput(6); // Needs to be in reference package
	DifferentialDrive myDrive;
	AnalogInput leftEncoder;
	AnalogInput rightEncoder;
	int leftBits;
	int rightBits;

	public void driveInit() {
		myDrive = comp.myDrive;
		
		leftEncoder = comp.leftEncoder;
		rightEncoder = comp.rightEncoder;
		leftEncoder.setAverageBits(2);
		rightEncoder.setAverageBits(2);
		

	}

	public void drivePeriodic() {
		double y1 = firstController.getLeftY(); // Shouldn't be here
		double y2 = firstController.getRightY();

		builtInDrive(-y1, -y2);
		
		leftBits = leftEncoder.getAverageBits();
		rightBits = rightEncoder.getAverageBits();
		
		SmartDashboard.putString("DB/String 0", "leftBits: " + String.valueOf(leftBits));
		SmartDashboard.putString("DB/String 1", "rightBits: " + String.valueOf(rightBits));

		// slider=dash.getNumber("DB/Slider 0", 0);
		// double testSpeed = SmartDashboard.getNumber("DB/Slider 1", 0.9); // TODO: Not working.
		// System.out.println(testSpeed);

	}

	public void builtInDrive(double y1, double y2) {
		myDrive.tankDrive(y1, y2, true);
	}
}
