/**Justin Sheehan
 * 1/27/18
 * FRC Team 3044
 * Raises and lowers the elevator and activates the piston that stops the chain when we have climbed fully.
 */

package org.usfirst.frc.team3044.robot;

import org.usfirst.frc.team3044.Reference.*;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Elevator {

	// Calls on second controller from SecondController and on talons for the elevator from Effectors.
	SecondController controller = SecondController.getInstance();
	public WPI_TalonSRX elevator1;
	public WPI_TalonSRX elevator2;
	public DoubleSolenoid elevatorBrake;
	private Effectors comp = Effectors.getInstance();
	private boolean brakeToggle;

	public void elevatorInit() {
		elevator1 = comp.elevator1;
		elevator2 = comp.elevator2;
		elevatorBrake = comp.elevatorBrake;
		brakeToggle = false;
	}

	public void elevatorPeriodic() {
		moveElevator();
		brakeElevator();
	}

	private void moveElevator() {
		if (brakeToggle == true) {
			elevator1.set(0);
			elevator2.set(0);
			// Moves elevator if brake toggle
		} else {
			elevator1.set(controller.getRightY());
			elevator2.set(-controller.getRightY());
		}
	}

	private void brakeElevator() {
		// Activates the brake if the X button is pressed, disengages it otherwise.
		if (controller.getRawButton(SecondController.BUTTON_X)) {
			brakeToggle = !brakeToggle;
		}

		if (brakeToggle) {
			elevatorBrake.set(DoubleSolenoid.Value.kForward);
		} else {
			elevatorBrake.set(DoubleSolenoid.Value.kReverse);
		}

	}
}
