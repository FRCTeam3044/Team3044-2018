/* Primarily sets names for inputs to be used later in the code for convenience and legibility.
 * Starts by creating names and attaching a type to them (talon, analog, solenoid, etc.)
 * After names are set they are attached to a mapped location as is defined in the RobotSchema.
 */

package org.usfirst.frc.team3044.Reference;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Effectors {

	public DigitalOutput R = new DigitalOutput(0);

	

	private static Effectors instance = null;

	// Robot components and driver station we can use to get information from.
	Compressor compressor = new Compressor();
	PowerDistributionPanel pdp = new PowerDistributionPanel();
	DriverStation ds = DriverStation.getInstance();

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
	public Encoder elevatorEncoder;
	public Encoder wristEncoder;

	// Elevator
	public WPI_TalonSRX elevator1;
	public WPI_TalonSRX elevator2;
	public Solenoid elevatorBrake;

	// Intake
	public Solenoid pistonLeft;
	public Solenoid pistonRight;
	public WPI_TalonSRX wristMotor;
	public WPI_TalonSRX leftSweep;
	public WPI_TalonSRX rightSweep;

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

		compressor.setClosedLoopControl(true);
		// compressor.stop(); //Use this to stop the compressor.
		// double current = pdp.getCurrent(1); //Use this to get the current
		// pdp.getTotal<something> //Or this
		// time = DriverStation.getInstance().getMatchTime(); //Maybe stop compressor in last 15 seconds.

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
		rightEncoder = new AnalogInput(0);
		elevatorEncoder = new Encoder(null, null, false, Encoder.EncodingType.k4X);
		wristEncoder = new Encoder(null, null, false, Encoder.EncodingType.k4X);

		// Sets talons for elevator motors
		elevator1 = new WPI_TalonSRX(robotSchema.canTalonMap.get("elevator1"));
		elevator2 = new WPI_TalonSRX(robotSchema.canTalonMap.get("elevator2"));
		elevatorBrake = new Solenoid(robotSchema.solenoidMap.get("elevatorBrake").talonID, robotSchema.solenoidMap.get("elevatorBrake").pcmChannel);

		// Sets talons and solenoids used to open, close and pull in the intake
		pistonLeft = new Solenoid(robotSchema.solenoidMap.get("pistonLeft").talonID, robotSchema.solenoidMap.get("pistonLeft").pcmChannel);
		pistonRight = new Solenoid(robotSchema.solenoidMap.get("pistonRight").talonID, robotSchema.solenoidMap.get("pistonRight").pcmChannel);
		wristMotor = new WPI_TalonSRX(robotSchema.canTalonMap.get("wristMotor"));

		// Sets talons for motors used in intake system
		leftSweep = new WPI_TalonSRX(robotSchema.canTalonMap.get("leftSweep"));
		rightSweep = new WPI_TalonSRX(robotSchema.canTalonMap.get("rightSweep"));

		// example = new Solenoid(robotSchema.solenoidMap.get("example").talonID, robotSchema.solenoidMap.get("example").pcmChannel);
	}
}
