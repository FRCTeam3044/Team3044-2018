/**Garrett
 * 1-27-18
 * Team 3044
 * This controls the intake: it will take the block in and out, open and close the intake arms, and move the wrist up and down.
 */

package org.usfirst.frc.team3044.robot;

import org.usfirst.frc.team3044.Reference.*;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Intake {

	// Calls on second controller from Second Controller and the solenoids and talons form Effectors.
	SecondController controller = SecondController.getInstance();
	public DoubleSolenoid pistonLeft;
	public DoubleSolenoid pistonRight;
	public WPI_TalonSRX wristMotor;
	public WPI_TalonSRX leftSweep;
	public WPI_TalonSRX rightSweep;
	private Effectors comp = Effectors.getInstance();

	public void intakeInit() {
		leftSweep = comp.leftSweep;
		rightSweep = comp.rightSweep;
		pistonLeft = comp.pistonLeft;
		pistonRight = comp.pistonRight;
		wristMotor = comp.wristMotor;

	}

	public void intakePeriodic() {

		// Sets power to the sweeper motors based on input value of the Y value on the left stick.
		double y1 = controller.getLeftY();

		// Calls functions that take the block in and out, open and close the intake arms, and move the wrist up and down.
		intakeWheels(y1);
		intakeGrab();
		wristMovement();

	}

	// Function to take the block in and out.
	void intakeWheels(double speed) {
		leftSweep.set(speed);
		rightSweep.set(-speed);
	}

	// Function to open and close the intake arms.
	void intakeGrab() {
		// Opens intake when the left of the d-pad is activated.
		if (controller.getDPadLeft()) {
			pistonLeft.set(DoubleSolenoid.Value.kForward);
			pistonRight.set(DoubleSolenoid.Value.kForward);
		}
		// Closes intake when the right of the d-pad is activated.
		if (controller.getDPadRight()) {
			pistonLeft.set(DoubleSolenoid.Value.kReverse);
			pistonRight.set(DoubleSolenoid.Value.kReverse);
		}
	}

	// Function to move the wrist up and down.
	void wristMovement() {
		// Pulls the intake up when the top of the d-pad is activated.
		if (controller.getDPadUp()) {
			wristMotor.set(-0.5);
		}
		// Drops the intake down when he bottom of the d-pad is activated.
		if (controller.getDPadDown()) {
			wristMotor.set(0.5);
		}
	}
}
