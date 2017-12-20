package org.usfirst.frc.team3044.Reference;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;

public class Effectors {

	private static Effectors instance = null;
	// Drive
	public RobotDrive myDrive;
	public CANTalon leftDrive;
	public CANTalon rightDrive;

	//public Solenoid example;

	private Effectors() {
	}

	public static Effectors getInstance() {
		if (instance == null) {
			instance = new Effectors();
		}
		return instance;
	}

	public void init() {

		// Drive

		RobotSchema robotSchema = new RobotSchema();

		leftDrive = new CANTalon(robotSchema.canTalonMap.get("leftDrive"));
		rightDrive = new CANTalon(robotSchema.canTalonMap.get("rightDrive"));

		//example = new Solenoid(robotSchema.solenoidMap.get("example").talonID, robotSchema.solenoidMap.get("example").pcmChannel);
	}
}
