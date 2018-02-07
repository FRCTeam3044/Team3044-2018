/**Garrett
 * 1-27-18
 * Team 3044
 */

package org.usfirst.frc.team3044.robot;

import org.usfirst.frc.team3044.Reference.*;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;

public class Intake {

	// Calls on second controller from Second Controller and the solenoids and talons form Effectors.
	SecondController controller = SecondController.getInstance();
	public Solenoid pistonLeft;
	public Solenoid pistonRight;
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
		leftSweep.set(y1);
		rightSweep.set(-y1);
		// Opens intake when the left of the d-pad is activated.
		if (controller.getDPadLeft()) {
			pistonLeft.set(true);
			pistonRight.set(true);
		}
		// Closes intake when the right of the d-pad is activated.
		if (controller.getDPadRight()) {
			pistonLeft.set(false);
			pistonRight.set(false);
		}
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
