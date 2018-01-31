/**Garrett
 * 1-27-18
 * team 3044
 */

package org.usfirst.frc.team3044.robot;

import org.usfirst.frc.team3044.Reference.*;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;

public class Intake {
	
	SecondController controller = SecondController.getInstance();
	public Solenoid pistonLeft;
	public Solenoid pistonRight;
	public Solenoid intakeRetract;
	public WPI_TalonSRX leftSweep;
	public WPI_TalonSRX rightSweep;
	private Effectors comp = Effectors.getInstance();
	
	public void intakeInit() {
		leftSweep = comp.leftSweep;
		rightSweep = comp.rightSweep;
		pistonLeft = comp.pistonLeft;
		pistonRight = comp.pistonRight;
		intakeRetract = comp.intakeRetract;
		
	}
	public void intakePeriodic() {
		double y1 = controller.getLeftY();
		leftSweep.set(y1);
		rightSweep.set(-y1);
		if (controller.getDPadLeft()) {
			pistonLeft.set(true);
			pistonRight.set(true);
		}
		if (controller.getDPadRight()) {
			pistonLeft.set(false);
			pistonRight.set(false);
		}
		if (controller.getDPadUp()) {
			intakeRetract.set(false);
		}
		if (controller.getDPadDown()) {
			intakeRetract.set(true);
		}
	}
}
