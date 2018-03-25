/*Code for driving the robot. 
 *Calls from FirstController for the inputs and Effectors for the outputs.
 */
package org.usfirst.frc.team3044.robot;

import org.usfirst.frc.team3044.Reference.*;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Drive {
	FirstController firstController = FirstController.getInstance();
	private Effectors comp = Effectors.getInstance();

	DifferentialDrive myDrive;

	PIDController leftPID;
	PIDController rightPID;

	double Kp=1; 
	double Ki=1; 
	double Kd=1; 
	

	public void driveInit() {
		// Calls on the WPIlib DifferentialDrive from Effectors.
		myDrive = comp.myDrive;
		
		// Create PIDSource objects 
		PIDSource leftPIDSource = new WPI_TalonPIDSource(comp.leftFrontDrive, PIDSourceType.kRate); 
		PIDSource rightPIDSource = new WPI_TalonPIDSource(comp.rightFrontDrive, PIDSourceType.kRate); 
		// Create PIDOutput objects 
		PIDOutput leftPIDOutput = new WPI_TalonPIDOutput(comp.leftFrontDrive); 
		PIDOutput rightPIDOutput =  new WPI_TalonPIDOutput (comp.rightFrontDrive); 
		
		// Hook up source and outputs to PIDController 
		leftPID = new PIDController(Kp, Ki, Kd, leftPIDSource, leftPIDOutput, 50);
		rightPID = new PIDController(Kp,Ki,Kd, rightPIDSource, rightPIDOutput, 50); 
		
		// Initialize setpoints to 0 
		leftPID.setSetpoint(0); 
		rightPID.setSetpoint(0); 
		
		// Start the PID loop 
		leftPID.enable();
		rightPID.enable();
	}

	public void drivePeriodic() {
		// Names and defines values used to read the input from the joysticks of the first controller.
		double y1 = .8 * firstController.getLeftY();
		double y2 = .8 * firstController.getRightY();

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
