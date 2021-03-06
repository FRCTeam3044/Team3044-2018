package org.usfirst.frc.team3044.Reference;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * Primarily sets names for inputs to be used later in the code for convenience
 * and legibility. Starts by creating names and attaching a type to them (talon,
 * analog, solenoid, etc.) After names are set they are attached to a mapped
 * location as is defined in the RobotSchema.
 */
public class Effectors {

	private static Effectors instance = null;

	// Robot components and driver station we can use to get information from.
	public Compressor compressor = new Compressor(61);
	public PowerDistributionPanel pdp = new PowerDistributionPanel();
	public DriverStation ds = DriverStation.getInstance();

	// Drive
	public WPI_TalonSRX leftFrontDrive;
	public WPI_TalonSRX rightFrontDrive;
	public WPI_TalonSRX leftBackDrive;
	public WPI_TalonSRX rightBackDrive;

	// Drive motor groups, not needed with talon follower.
	// SpeedControllerGroup m_left;
	// SpeedControllerGroup m_right;

	// WPI tank drive
	public DifferentialDrive myDrive;

	// Elevator
	public WPI_TalonSRX elevator1;
	public WPI_TalonSRX elevator2;
	public DoubleSolenoid elevatorBrake;

	// Intake
	public DoubleSolenoid armsPiston;
	public WPI_TalonSRX wristMotor;
	public WPI_TalonSRX leftSweep;
	public WPI_TalonSRX rightSweep;

	int PIDTimeout = 10;

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
		// time = DriverStation.getInstance().getMatchTime(); //Maybe stop
		// compressor in last 15 seconds.

		// Sets talons for drive and defines them as such
		leftFrontDrive = new WPI_TalonSRX(robotSchema.canTalonMap.get("leftFrontDrive"));
		rightFrontDrive = new WPI_TalonSRX(robotSchema.canTalonMap.get("rightFrontDrive"));
		leftBackDrive = new WPI_TalonSRX(robotSchema.canTalonMap.get("leftBackDrive"));
		rightBackDrive = new WPI_TalonSRX(robotSchema.canTalonMap.get("rightBackDrive"));

		// Adds encoders to the motors.
		leftFrontDrive.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, 10);
		rightFrontDrive.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, 10);

		// Sets the back motors to follow the front motors.
		leftBackDrive.follow(leftFrontDrive);
		rightBackDrive.follow(rightFrontDrive);

		// Increases the time taken for the motors to go from 0 to full.
		leftFrontDrive.configOpenloopRamp(.2, 10);
		rightFrontDrive.configOpenloopRamp(.2, 10);
		leftBackDrive.configOpenloopRamp(.2, 10);
		rightBackDrive.configOpenloopRamp(.2, 10);

		// ForPID
		leftFrontDrive.setSensorPhase(true);
		rightFrontDrive.setSensorPhase(true);

		/*
		 * set the peak, nominal outputs, and deadband
		 * leftFrontDrive.configNominalOutputForward(0, PIDTimeout);
		 * leftFrontDrive.configNominalOutputReverse(0, PIDTimeout);
		 * leftFrontDrive.configPeakOutputForward(1, PIDTimeout);
		 * leftFrontDrive.configPeakOutputReverse(-1, PIDTimeout);
		 * set the peak, nominal outputs, and deadband
		 * rightFrontDrive.configNominalOutputForward(0, PIDTimeout);
		 * rightFrontDrive.configNominalOutputReverse(0, PIDTimeout);
		 * rightFrontDrive.configPeakOutputForward(1, PIDTimeout);
		 * rightFrontDrive.configPeakOutputReverse(-1, PIDTimeout);
		 * 
		 * set closed loop gains in slot0
		 * leftFrontDrive.config_kF(0, 0.34, PIDTimeout);
		 * leftFrontDrive.config_kP(0, 0.2, PIDTimeout);
		 * leftFrontDrive.config_kI(0, 0, PIDTimeout);
		 * leftFrontDrive.config_kD(0, 0, PIDTimeout);
		 * set closed loop gains in slot0
		 * rightFrontDrive.config_kF(0, 0.34, PIDTimeout);
		 * rightFrontDrive.config_kP(0, 0.2, PIDTimeout);
		 * rightFrontDrive.config_kI(0, 0, PIDTimeout);
		 * rightFrontDrive.config_kD(0, 0, PIDTimeout);
		 */

		// Sets groups for drive talons to later be used in the WPI tank drive,
		// not needed with the talon follower.
		// m_left = new SpeedControllerGroup(leftFrontDrive, leftBackDrive);
		// m_right = new SpeedControllerGroup(rightFrontDrive, rightBackDrive);

		// Uses front talons to define motors used in WPI tank drive, the back
		// motors move because of the follower.
		myDrive = new DifferentialDrive(leftFrontDrive, rightFrontDrive);

		// Sets talons and solenoid for the elevator.
		elevator1 = new WPI_TalonSRX(robotSchema.canTalonMap.get("elevator1"));
		elevator2 = new WPI_TalonSRX(robotSchema.canTalonMap.get("elevator2"));
		elevatorBrake = new DoubleSolenoid(61, 2, 3);

		// Puts the elevator motors in brake mode so they stay where they are when not being used.
		elevator1.setNeutralMode(NeutralMode.Brake);
		elevator2.setNeutralMode(NeutralMode.Brake);
		
		elevator1.configNeutralDeadband(2, 10);
		elevator2.configNeutralDeadband(2, 10);

		// Sets the encoder for the elevator.
		elevator2.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, 10);

		// Sets talons and solenoids used to open, close and pull in the intake
		leftSweep = new WPI_TalonSRX(robotSchema.canTalonMap.get("leftSweep"));
		rightSweep = new WPI_TalonSRX(robotSchema.canTalonMap.get("rightSweep"));
		armsPiston = new DoubleSolenoid(61, 0, 1);
		wristMotor = new WPI_TalonSRX(robotSchema.canTalonMap.get("wristMotor"));

		wristMotor.configNeutralDeadband(2, 10);
		leftSweep.configNeutralDeadband(2, 10);
		rightSweep.configNeutralDeadband(2, 10);

		// example = new
		// Solenoid(robotSchema.solenoidMap.get("example").talonID,
		// robotSchema.solenoidMap.get("example").pcmChannel);
	}

	public int actualValue(int startingValue, int readValue) {
		return Math.abs(readValue - startingValue);
	}
}
