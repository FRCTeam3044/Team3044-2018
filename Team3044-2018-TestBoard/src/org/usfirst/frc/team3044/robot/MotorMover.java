package org.usfirst.frc.team3044.robot;

import org.usfirst.frc.team3044.Reference.*;

import com.ctre.CANTalon;

public class MotorMover {
	FirstController controller = FirstController.getInstance();
	private Effectors comp = Effectors.getInstance();
	public CANTalon leftDrive;
	public CANTalon rightDrive;

	public void MotorMoverInit() {
		leftDrive = comp.leftDrive;
		rightDrive = comp.rightDrive;

		leftDrive.set(0);
		rightDrive.set(0);

		rightDrive.enableBrakeMode(true);
		leftDrive.enableBrakeMode(true);

	}

	public void MotorMoverPeriodic() {
		double x1 = -deadband(controller.getLeftX());
		double x2 = deadband(controller.getRightX());

	}

	public void tankDrive(double x1, double x2) {
		leftDrive.set(x1);
		rightDrive.set(x2);
	}

	public double deadband(double value) {
		if (Math.abs(value) < .15) {
			return 0;
		} else {
			return value;
		}
	}

}
