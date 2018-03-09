/*Code for driving the robot. 
 *Calls from FirstController for the inputs and Effectors for the outputs.
 */
package org.usfirst.frc.team3044.robot;

import org.usfirst.frc.team3044.Reference.*;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.PIDController;

public class Drive {
	FirstController firstController = FirstController.getInstance();
	private Effectors comp = Effectors.getInstance();

	DifferentialDrive myDrive;

	PIDController leftPID;
	PIDController rightPID;

	public void driveInit() {
		// Calls on the WPIlib DifferentialDrive from Effectors.
		myDrive = comp.myDrive;

		// Sets up the PID Loop.
		// leftPID = new PIDController(1, 1, 1, comp.leftFrontDrive., comp.leftFrontDrive, 50);
		// rightPID = new PIDController(1, 1, 1, rightEncoder, comp.rightFrontDrive, 50);
	}

	public void drivePeriodic() {
		// Names and defines values used to read the input from the joysticks of the first controller.
		double y1 = firstController.getLeftY();
		double y2 = firstController.getRightY();

		// Calls the function that runs the tank drive and uses values from the joysticks.
		builtInDrive(-y1, -y2);
	}

	public void builtInDrive(double y1, double y2) {
		// This should be all we need to move the robot using PID.
		// leftPID.setSetpoint(y1);
		// rightPID.setSetpoint(y2);

		// If not, we'll need to use this.
		// Sets power to the motor groups from Effectors based on the input values from the sticks.
		myDrive.tankDrive(y1, y2, true);
		// myDrive.arcadeDrive(y1, firstController.getRightX(), true);
		// comp.leftFrontDrive.set(ControlMode.Velocity, y1 * 4096 * 500.0 / 600);
		// comp.rightFrontDrive.set(ControlMode.Velocity, -y2 * 4096 * 500.0 / 600);
	}
}
