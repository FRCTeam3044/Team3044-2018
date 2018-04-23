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
	private Effectors comp = Effectors.getInstance();
	static SecondController controller = SecondController.getInstance();
	public static DoubleSolenoid armsPiston;
	public static WPI_TalonSRX wristMotor;
	public static WPI_TalonSRX leftSweep;
	public static WPI_TalonSRX rightSweep;

	public void intakeInit() {
		leftSweep = comp.leftSweep;
		rightSweep = comp.rightSweep;
		armsPiston = comp.armsPiston;
		wristMotor = comp.wristMotor;

	}

	public void intakePeriodic() {

		// Gets the Y value on the left stick.
		double y1 = controller.getLeftY();

		// Calls functions that take the block in and out, open and close the intake
		// arms, and move the wrist up and down.
		intakeWheels(y1);
		intakeArms(controller.getRawButton(SecondController.BUTTON_B),
				controller.getRawButton(SecondController.BUTTON_A));
		wristMovement();

	}

	// Function to take the block in and out, values doubled to get to full power
	// faster.
	public static void intakeWheels(double speed) {
		leftSweep.set(-speed * 1.5);
		rightSweep.set(speed * 2);
	}

	/**
	 * Function to open and close the intake arms. Defaults closes, only opens on
	 * button press.
	 * 
	 * @param button
	 *            True for arms to open with no intake.
	 * @param autoButton
	 *            True for arms to open with auto intake, false otherwise.
	 */
	public static void intakeArms(boolean button, boolean autoButton) {
		// Open intake when button is pressed.
		if (button) {
			armsPiston.set(DoubleSolenoid.Value.kReverse);
		} else if (autoButton) {
			armsPiston.set(DoubleSolenoid.Value.kReverse);
			if (Math.abs(controller.getLeftY()) < .15) {
				intakeWheels(1);
			}
		} else {// Closes intake when not pressed.
			armsPiston.set(DoubleSolenoid.Value.kForward);
		}
	}

	// Function to move the wrist up and down.
	void wristMovement() {
		// Pulls the intake up when the right trigger is activated.
		if (controller.getTriggerRight()) {
			wristMotor.set(0.7);
		}
		// Drops the intake down when left trigger is activated.
		else if (controller.getTriggerLeft()) {
			wristMotor.set(-0.3);
		} else {
			wristMotor.set(0);
		}
	}
}
