package org.usfirst.frc.team3044.robot;

import org.usfirst.frc.team3044.Reference.*;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;


import edu.wpi.first.wpilibj.Talon;

public class Drive {
	FirstController controller = FirstController.getInstance();
	private Effectors comp = Effectors.getInstance();
	public WPI_TalonSRX leftDrive;
	public WPI_TalonSRX rightDrive;
	double slider;
	//SmartDashboard dash=new SmartDashboard();

	public void motorMoverInit() {
		leftDrive = comp.leftDrive;
		rightDrive = comp.rightDrive;

		leftDrive.set(0);
		rightDrive.set(0);

		//rightDrive.enableBrakeMode(true);
		//leftDrive.enableBrakeMode(true);

	}

	public void motorMoverPeriodic() {
		double y1 = -deadband(controller.getLeftY());
		double y2 = deadband(controller.getRightY());

		tankDrive(y1, y2);
		
		//slider=dash.getNumber("DB/Slider 0", 0);
		double testSpeed = SmartDashboard.getNumber("DB/Slider 1", 0.9);  //TODO: Not working.
		System.out.println(testSpeed);
		

		
	}

	public void tankDrive(double y1, double y2) {
		leftDrive.set(y1);
		rightDrive.set(y2);
	}

	public double deadband(double value) {
		if (Math.abs(value) < .15) {
			return 0;
		} else {
			return value;
		}
	}

}
