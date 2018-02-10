/**Justin Sheehan
 * 1/27/18
 * FRC Team 3044
 * Raises and lowers the elevator using the Y and A buttons.
 */

package org.usfirst.frc.team3044.robot;

import org.usfirst.frc.team3044.Reference.*;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;

public class Elevator {

	// Calls on second controller from SecondController and on talons for the elevator from Effectors.
	SecondController controller = SecondController.getInstance();
	public WPI_TalonSRX elevator1;
	public WPI_TalonSRX elevator2;
	public Solenoid elevatorBrake;
	private Effectors comp = Effectors.getInstance();
	private boolean toggle;

	public void elevatorInit() {
		elevator1 = comp.elevator1;
		elevator2 = comp.elevator2;
		elevatorBrake = comp.elevatorBrake;
		toggle = false;
	}

	public void elevatorPeriodic() {
		moveElevator();
		brakeElevator();
	}
	
	private void moveElevator() {
		if (toggle == true) {
			elevator1.set(0);
			elevator2.set(0);
			// Raises elevator up when Y button is pressed.
		} else if (controller.getRawButton(controller.BUTTON_Y)) {
			elevator1.set(0.5);
			elevator2.set(-0.5);

			// Lowers elevator when the A button is pressed if the Y button is not being pressed.
		} else if (controller.getRawButton(controller.BUTTON_A)) {
			elevator1.set(-0.5);
			elevator2.set(0.5);

			// Turns off all power to elevator motors if neither button is pressed.
		} else {
			elevator1.set(0);
			elevator2.set(0);
		}	
	}
	
	private void brakeElevator() {
		//Activates the brake if the B button is pressesd, disengages it otherwise.
		if (toggle == true) {
			toggle = false;
		} else {
			toggle = true;
		}
		
		elevatorBrake.set(toggle);
	}
}
