/**Justin Sheehan
 * 1/27/18
 * FRC Team 3044
 * Raises and lowers the elevator and activates the piston that stops the chain when we have climbed fully.
 */

package org.usfirst.frc.team3044.robot;

import org.usfirst.frc.team3044.Reference.*;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Elevator {

	// Calls on second controller from SecondController and on talons for the elevator from Effectors.
	SecondController controller = SecondController.getInstance();
	public static WPI_TalonSRX elevator1;
	public static WPI_TalonSRX elevator2;
	public DoubleSolenoid elevatorBrake;
	public DigitalInput elevatorLimit;
	private Effectors comp = Effectors.getInstance();
	private boolean brakeToggle;

	public void elevatorInit() {
		elevator1 = comp.elevator1;
		elevator2 = comp.elevator2;
		elevatorBrake = comp.elevatorBrake;
		elevatorLimit = comp.elevatorLimit;
		brakeToggle = false;
	}

	public void elevatorPeriodic() {
		testLimitSwitch();
		moveElevator();
		brakeElevator();
	}

	private void testLimitSwitch() {
		if (elevatorLimit.get()) {
			brakeToggle = true;
			resetEncoders();
		}
	}
	
	private void moveElevator() {
		//Stops the elevator from moving if the brake toggle is pressed.	
		if (brakeToggle == true) {
			elevator1.set(0);
			elevator2.set(0);
		// Moves elevator if brake toggle is not activated
		} else {
			elevator1.set(controller.getRightY());
			elevator2.set(-controller.getRightY());
		}
	}

	private void brakeElevator() {
		// Toggles the brake when X button is pressed.
		if (controller.getRawButton(SecondController.BUTTON_X)) {
			brakeToggle = !brakeToggle;
		}

		if (brakeToggle) {
			elevatorBrake.set(DoubleSolenoid.Value.kForward);
		} else {
			elevatorBrake.set(DoubleSolenoid.Value.kReverse);
		}

	}
	
	public static void resetEncoders() {
		// Resets the encoders to 0.
		Effectors.getInstance().elevator1.setSelectedSensorPosition(0, 0, 0);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		elevator1 = Effectors.getInstance().leftFrontDrive.getSensorCollection().getAnalogIn();
	}
}
