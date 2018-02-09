/* Primarily sets names for inputs to be used later in the code for convenience and legebility.
 * Starts by creating names and attaching a type to them (talon, analog, solenoid, etc.)
 * After names are set they are attached to a mapped location as is defined in the RobotSchema.
 */

package org.usfirst.frc.team3044.Reference;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.AnalogInput;
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

	// Drive motor groups
	SpeedControllerGroup m_left;
	SpeedControllerGroup m_right;

	// WPI tank drive 
	public DifferentialDrive myDrive;
	
	// Encoders
	public AnalogInput leftEncoder;
	public AnalogInput rightEncoder;

	// Elevator
	public WPI_TalonSRX elevator1;
	public WPI_TalonSRX elevator2;

	// Intake
	public Solenoid pistonLeft;
	public Solenoid pistonRight;
	public WPI_TalonSRX retractIntake;
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

		// Sets talons for drive and defines them as such 
		leftFrontDrive = new WPI_TalonSRX(robotSchema.canTalonMap.get("leftFrontDrive"));
		rightFrontDrive = new WPI_TalonSRX(robotSchema.canTalonMap.get("rightFrontDrive"));
		leftBackDrive = new WPI_TalonSRX(robotSchema.canTalonMap.get("leftBackDrive"));
		rightBackDrive = new WPI_TalonSRX(robotSchema.canTalonMap.get("rightBackDrive"));
		
		// Sets groups for drive talons to later be used in the WPI tank drive 
		m_left = new SpeedControllerGroup(leftFrontDrive, leftBackDrive);
		m_right = new SpeedControllerGroup(rightFrontDrive, rightBackDrive);

		// Uses talon groups to define motors used in WPI tank drive 
		myDrive = new DifferentialDrive(m_left, m_right);
		
		// Defines encoder inputs
		leftEncoder = new AnalogInput(1);
		rightEncoder = new AnalogInput(3);

		// Sets talons for elevator motors 
		elevator1 = new WPI_TalonSRX(robotSchema.canTalonMap.get("elevator1"));
		elevator2 = new WPI_TalonSRX(robotSchema.canTalonMap.get("elevator2"));

		// Sets talons and solenoids used to open, close and pull in the intake 
		pistonLeft = new Solenoid(robotSchema.solenoidMap.get("pistonLeft").talonID, robotSchema.solenoidMap.get("pistonLeft").pcmChannel);
		pistonRight = new Solenoid(robotSchema.solenoidMap.get("pistonRight").talonID, robotSchema.solenoidMap.get("pistonRight").pcmChannel);
		retractIntake = new WPI_TalonSRX(robotSchema.canTalonMap.get("retractIntake"));
		
		// Sets talons for motors used in intake system
		leftSweep = new WPI_TalonSRX(robotSchema.canTalonMap.get("leftSweep"));
		rightSweep = new WPI_TalonSRX(robotSchema.canTalonMap.get("rightSweep"));

		// example = new Solenoid(robotSchema.solenoidMap.get("example").talonID, robotSchema.solenoidMap.get("example").pcmChannel);
	}
}
