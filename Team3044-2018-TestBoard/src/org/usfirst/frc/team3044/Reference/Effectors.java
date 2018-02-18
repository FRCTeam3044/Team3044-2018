package org.usfirst.frc.team3044.Reference;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Primarily sets names for inputs to be used later in the code for convenience and legibility.
 * Starts by creating names and attaching a type to them (talon, analog, solenoid, etc.)
 * After names are set they are attached to a mapped location as is defined in the RobotSchema.
 */
public class Effectors {

	private static Effectors instance = null;

	// Robot components and driver station we can use to get information from.
	public Compressor compressor = new Compressor();
	public PowerDistributionPanel pdp = new PowerDistributionPanel();
	public DriverStation ds = DriverStation.getInstance();

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
	public Encoder elevatorEncoder;
	public Encoder wristEncoder;

	// Elevator
	public WPI_TalonSRX elevator1;
	public WPI_TalonSRX elevator2;
	public DoubleSolenoid elevatorBrake;

	// Intake
	public DoubleSolenoid pistonLeft;
	public DoubleSolenoid pistonRight;
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

		leftFrontDrive.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, 0);
		rightFrontDrive.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, 0);
		
		leftFrontDrive.setNeutralMode(NeutralMode.Coast);
		rightFrontDrive.setNeutralMode(NeutralMode.Coast);
		leftBackDrive.setNeutralMode(NeutralMode.Coast);
		rightBackDrive.setNeutralMode(NeutralMode.Coast);
		
		leftFrontDrive.configOpenloopRamp(.2, 0);
		rightFrontDrive.configOpenloopRamp(.2, 0);
		leftBackDrive.configOpenloopRamp(.2, 0);
		rightBackDrive.configOpenloopRamp(.2, 0);
		
		// Untested
		/*
		 * 
		 * leftBackDrive.set(com.ctre.phoenix.motorcontrol.ControlMode.Follower, robotSchema.canTalonMap.get("leftFrontDrive"));
		 */

		// Sets groups for drive talons to later be used in the WPI tank drive
		//m_left = new SpeedControllerGroup(leftFrontDrive, leftBackDrive);
		//m_right = new SpeedControllerGroup(rightFrontDrive, rightBackDrive);

		// Uses talon groups to define motors used in WPI tank drive
		myDrive = new DifferentialDrive(leftFrontDrive, rightFrontDrive);
		leftBackDrive.follow(leftFrontDrive);
		rightBackDrive.follow(rightFrontDrive);

		// Defines encoder inputs
		// elevatorEncoder = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
		// wristEncoder = new Encoder(0, 1, false, Encoder.EncodingType.k4X);

		// Sets talons for elevator motors
		elevator1 = new WPI_TalonSRX(robotSchema.canTalonMap.get("elevator1"));
		elevator2 = new WPI_TalonSRX(robotSchema.canTalonMap.get("elevator2"));
		elevatorBrake = new DoubleSolenoid(61, 3, 4);

		// Sets talons and solenoids used to open, close and pull in the intake
		pistonLeft = new DoubleSolenoid(61, 1, 6);
		pistonRight = new DoubleSolenoid(61, 2, 5);
		wristMotor = new WPI_TalonSRX(robotSchema.canTalonMap.get("wristMotor"));

		// Sets talons for motors used in intake system
		leftSweep = new WPI_TalonSRX(robotSchema.canTalonMap.get("leftSweep"));
		rightSweep = new WPI_TalonSRX(robotSchema.canTalonMap.get("rightSweep"));

		// example = new Solenoid(robotSchema.solenoidMap.get("example").talonID, robotSchema.solenoidMap.get("example").pcmChannel);
	}
}
