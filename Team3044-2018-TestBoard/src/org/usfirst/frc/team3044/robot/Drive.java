package org.usfirst.frc.team3044.robot;

import org.usfirst.frc.team3044.Reference.*;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Drive {
	FirstController firstController = FirstController.getInstance();
	SecondController secondController = SecondController.getInstance();
	private Effectors comp = Effectors.getInstance();
	DigitalInput limitSwitchOut = new DigitalInput(6); // Needs to be in reference package
	DifferentialDrive myDrive;

	public void driveInit() {
		myDrive = comp.myDrive;

	}

	public void drivePeriodic() {
		double y1 = secondController.getLeftY(); // Shouldn't be here
		double y2 = secondController.getRightY();

		builtInDrive(y1, y2);

		// slider=dash.getNumber("DB/Slider 0", 0);
		// double testSpeed = SmartDashboard.getNumber("DB/Slider 1", 0.9); // TODO: Not working.
		// System.out.println(testSpeed);

	}

	public void builtInDrive(double y1, double y2) {
		myDrive.tankDrive(y1, y2);
	}
}
