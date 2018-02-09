/*Code for driving the robot. 
 *Calls from FirstController for the inputs and Effectors for the outputs.
 */
package org.usfirst.frc.team3044.robot;

import org.usfirst.frc.team3044.Reference.*;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;

public class Drive {
	FirstController firstController = FirstController.getInstance();
	SecondController secondController = SecondController.getInstance();
	private Effectors comp = Effectors.getInstance();
	
	DifferentialDrive myDrive;
	SpeedControllerGroup m_left;
	SpeedControllerGroup m_right;
	
	AnalogInput leftEncoder;
	AnalogInput rightEncoder;
	int leftBits;
	int rightBits;
	
	PIDController leftPID;
	PIDController rightPID;

	public void driveInit() {
		// Calls on the WPIlib DifferentialDrive from Effectors.
		myDrive = comp.myDrive;

		// Calls on the encoders from Effectors.
		leftEncoder = comp.leftEncoder;
		rightEncoder = comp.rightEncoder;
		leftEncoder.setAverageBits(2);
		rightEncoder.setAverageBits(2);

		// Sets up the PID Loop.
		leftPID = new PIDController(1, 1, 1, leftEncoder, m_left, 50);
		rightPID = new PIDController(1, 1, 1, rightEncoder, m_right, 50);
	}

	public void drivePeriodic() {
		// Names and defines values used to read the input from the joysticks of the first controller.
		double y1 = firstController.getLeftY(); // Shouldn't be here
		double y2 = firstController.getRightY();

		// Calls the function that runs the tank drive and uses values from the joysticks.
		builtInDrive(-y1, -y2);

		// Pulls values from the encoders from Effectors.
		leftBits = leftEncoder.getAverageBits();
		rightBits = rightEncoder.getAverageBits();

		// Displays values from the encoders.
		SmartDashboard.putString("DB/String 0", "leftBits: " + String.valueOf(leftBits));
		SmartDashboard.putString("DB/String 1", "rightBits: " + String.valueOf(rightBits));

		// slider=dash.getNumber("DB/Slider 0", 0);
		// double testSpeed = SmartDashboard.getNumber("DB/Slider 1", 0.9); // TODO: Not working.
		// System.out.println(testSpeed);

	}

	public void builtInDrive(double y1, double y2) {
		// This should be all we need to move the robot using PID.
		// leftPID.setSetpoint(y1);
		// rightPID.setSetpoint(y2);

		// If not, we'll need to use this.
		// Sets power to the motor groups from Effectors based on the input values from the sticks.
		myDrive.tankDrive(y1, y2, true);
	}
}
