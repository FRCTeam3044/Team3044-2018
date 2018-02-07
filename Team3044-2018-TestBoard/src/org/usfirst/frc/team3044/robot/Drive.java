package org.usfirst.frc.team3044.robot;

import org.usfirst.frc.team3044.Reference.*;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;

public class Drive {
	FirstController firstController = FirstController.getInstance();
	SecondController secondController = SecondController.getInstance();
	private Effectors comp = Effectors.getInstance();
	DigitalInput limitSwitchOut = new DigitalInput(6); // Needs to be in reference package
	DifferentialDrive myDrive;
	AnalogInput leftEncoder;
	AnalogInput rightEncoder;
	SpeedControllerGroup m_left;
	SpeedControllerGroup m_right;
	PIDController leftPID;
	PIDController rightPID;
	int leftBits;
	int rightBits;

	public void driveInit() {
		myDrive = comp.myDrive;
		
		//Sets up the PID Loop
		leftEncoder = comp.leftEncoder;
		rightEncoder = comp.rightEncoder;
		leftEncoder.setAverageBits(2);
		rightEncoder.setAverageBits(2);
		leftPID = new PIDController(1, 1, 1, leftEncoder, m_left, 50);
		rightPID = new PIDController(1, 1, 1, rightEncoder, m_right, 50);
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
		//This should be all we need to move the robot using PID
		leftPID.setSetpoint(y1);
		rightPID.setSetpoint(y2);
		
		//If not, we'll need to use this
		//myDrive.tankDrive(y1, y2, true);
	}
}
