/**Garrett
 * 1-27-18
 * team 3044
 */

package org.usfirst.frc.team3044.robot;

import org.usfirst.frc.team3044.Reference.*;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;

public class Intake {
	
	//calls on second controller from Second Cotroller and the solenoids and talons form Effectors 
	SecondController controller = SecondController.getInstance();
	public Solenoid pistonLeft;
	public Solenoid pistonRight;
	public WPI_TalonSRX retractIntake;
	public WPI_TalonSRX leftSweep;
	public WPI_TalonSRX rightSweep;
	private Effectors comp = Effectors.getInstance();
	
	public void intakeInit() {
		leftSweep = comp.leftSweep;
		rightSweep = comp.rightSweep;
		pistonLeft = comp.pistonLeft;
		pistonRight = comp.pistonRight;
		retractIntake = comp.retractIntake;
		
	}
	public void intakePeriodic() {
		
		//sets power to the sweeper motors based on input value of the Y value on the left stick
		double y1 = controller.getLeftY();
		leftSweep.set(y1);
		rightSweep.set(-y1);
		//opens inatke when the left of the d-pad is activated 
		if (controller.getDPadLeft()) {
			pistonLeft.set(true);
			pistonRight.set(true);
		}
		//closes intake when the right of the d-pad is activated 
		if (controller.getDPadRight()) {
			pistonLeft.set(false);
			pistonRight.set(false);
		}
		//pulls the intake up when the top of the d-pad is activated 
		if (controller.getDPadUp()) {
			retractIntake.set(-0.5);
		}
		//drops the intake down whent he bottom of the d-pad is acitvated 
		if (controller.getDPadDown()) {
			retractIntake.set(0.5);
		}
	}
}
