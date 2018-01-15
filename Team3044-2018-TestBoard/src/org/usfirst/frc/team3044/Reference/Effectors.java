package org.usfirst.frc.team3044.Reference;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;

public class Effectors {

	private static Effectors instance = null;
	// Drive
	public RobotDrive myDrive;
	public WPI_TalonSRX leftDrive;
	public WPI_TalonSRX rightDrive;

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

		leftDrive = new WPI_TalonSRX(robotSchema.canTalonMap.get("leftDrive"));
		rightDrive = new WPI_TalonSRX(robotSchema.canTalonMap.get("rightDrive"));

		//example = new Solenoid(robotSchema.solenoidMap.get("example").talonID, robotSchema.solenoidMap.get("example").pcmChannel);
	}
}
