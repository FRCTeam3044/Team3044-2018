/**Justin Sheehan
 * 1/27/18
 * FRC Team 3044
 * Raises and lowers the elevator using the Y and A buttons.
 */

package org.usfirst.frc.team3044.robot;

import org.usfirst.frc.team3044.Reference.*;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Elevator {
	
	SecondController controller = SecondController.getInstance();
	public WPI_TalonSRX elevator1;
	public WPI_TalonSRX elevator2;
	private Effectors comp = Effectors.getInstance();
	
	public void elevatorInit() {
		elevator1 = comp.elevator1;
		elevator2 = comp.elevator2;
	}
	
	public void elevatorPeriodic() {
		if (controller.getRawButton(controller.BUTTON_Y)) {
			elevator1.set(0.5);
			elevator2.set(-0.5);
		} else if (controller.getRawButton(controller.BUTTON_A)) {
			elevator1.set(-0.5);
			elevator2.set(0.5);
		} else {
			elevator1.set(0);
			elevator2.set(0);
		}
	}
}
