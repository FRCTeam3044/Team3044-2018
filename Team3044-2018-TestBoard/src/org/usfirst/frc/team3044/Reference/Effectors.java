package org.usfirst.frc.team3044.Reference;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Effectors {

	private static Effectors instance = null;
	// Drive
	public WPI_TalonSRX leftFrontDrive;
	public WPI_TalonSRX rightFrontDrive;
	public WPI_TalonSRX leftBackDrive;
	public WPI_TalonSRX rightBackDrive;

	SpeedControllerGroup m_left;
	SpeedControllerGroup m_right;

	public DifferentialDrive myDrive;

	// Elevator
	public WPI_TalonSRX elevator1;
	public WPI_TalonSRX elevator2;

	// Intake
	public Solenoid pistonLeft;
	public Solenoid pistonRight;
	public Solenoid intakeRetract;
	public WPI_TalonSRX leftSweep;
	public WPI_TalonSRX rightSweep;

	// public Solenoid example;

	private Effectors() {
	}

	public static Effectors getInstance() {
		if (instance == null) {
			instance = new Effectors();
		}
		return instance;
	}

	public void init() {
		RobotSchema robotSchema = new RobotSchema();

		// Drive
		leftFrontDrive = new WPI_TalonSRX(robotSchema.canTalonMap.get("leftFrontDrive"));
		rightFrontDrive = new WPI_TalonSRX(robotSchema.canTalonMap.get("rightFrontDrive"));
		leftBackDrive = new WPI_TalonSRX(robotSchema.canTalonMap.get("leftFrontDrive"));
		rightBackDrive = new WPI_TalonSRX(robotSchema.canTalonMap.get("rightFrontDrive"));

		// TODO: I don't know if this will work, it is spark vs talon.
		m_left = new SpeedControllerGroup(leftFrontDrive, leftBackDrive);
		m_right = new SpeedControllerGroup(rightFrontDrive, rightBackDrive);

		myDrive = new DifferentialDrive(m_left, m_right);

		// Elevator
		elevator1 = new WPI_TalonSRX(robotSchema.canTalonMap.get("elevator1"));
		elevator2 = new WPI_TalonSRX(robotSchema.canTalonMap.get("elevator1"));

		// Intake
		pistonLeft = new Solenoid(robotSchema.solenoidMap.get("pistonLeft").talonID, robotSchema.solenoidMap.get("pistonLeft").pcmChannel);
		pistonRight = new Solenoid(robotSchema.solenoidMap.get("pistonRight").talonID, robotSchema.solenoidMap.get("pistonRight").pcmChannel);
		intakeRetract = new Solenoid(robotSchema.solenoidMap.get("intakeRetract").talonID, robotSchema.solenoidMap.get("intakeRetract").pcmChannel);
		leftSweep = new WPI_TalonSRX(robotSchema.canTalonMap.get("elevator1"));
		rightSweep = new WPI_TalonSRX(robotSchema.canTalonMap.get("elevator1"));

		// example = new Solenoid(robotSchema.solenoidMap.get("example").talonID, robotSchema.solenoidMap.get("example").pcmChannel);
	}
}
