package org.usfirst.frc.team3044.robot;

import org.usfirst.frc.team3044.Reference.Effectors;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
	Drive drive = new Drive();
	Elevator elevator = new Elevator();
	Intake intake = new Intake();
	Autonomous autonomous = new Autonomous();
	private Effectors comp = Effectors.getInstance();

	// Creates variables for autonomous selection.
	// TODO: For some reason, this only works in the java dashboard.
	final String startCenter = "Start Center";
	final String startLeft = "Start Left";
	final String startRight = "Start Right";
	final String startCenterTwoCubes = "Start Center Two Cubes";
	final String startLeftTwoCubes = "Start Left Two Cubes";
	final String startRightTwoCubes = "Start Right Two Cubes";
	final String Baseline = "Baseline";
	final String Test = "Test";
	String autoSelected;
	SendableChooser<String> chooser = new SendableChooser<>();

	// Data from the FMS about switch and scale placement.
	String gameData;

	/*
	 * NetworkTableEntry avg; NetworkTableEntry left; NetworkTableEntry right;
	 */

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		// Initializes effectors.
		Effectors.getInstance().init();
		elevator.elevatorInit();
		drive.driveInit();
		intake.intakeInit();
		autonomous.autonomousInit();

		// Sends the auto choices to the dashboard.
		chooser.setDefaultOption("Start Center", startCenter);
		chooser.addOption("Start Left", startLeft);
		chooser.addOption("Start Right", startRight);
		chooser.addOption("Start Center Two Cubes", startCenterTwoCubes);
		chooser.addOption("Start Left Two Cubes", startLeftTwoCubes);
		chooser.addOption("Start Right Two Cubes", startRightTwoCubes);
		chooser.addOption("Baseline", Baseline);
		chooser.addOption("Test", Test);
		SmartDashboard.putData("Auto choices", chooser);

		CameraServer.getInstance().startAutomaticCapture().setResolution(640, 480);

		comp.leftFrontDrive.setNeutralMode(NeutralMode.Coast);
		comp.rightFrontDrive.setNeutralMode(NeutralMode.Coast);
		comp.leftBackDrive.setNeutralMode(NeutralMode.Coast);
		comp.rightBackDrive.setNeutralMode(NeutralMode.Coast);

		/*
		 * NetworkTableInstance inst = NetworkTableInstance.getDefault();
		 * NetworkTable table = inst.getTable("datatable"); avg =
		 * table.getEntry("avg"); left = table.getEntry("left"); right =
		 * table.getEntry("right");
		 */
	}

	@Override
	public void robotPeriodic() {

	}

	@Override
	public void autonomousInit() {
		autonomous.autonomousInit();
		elevator.elevatorInit();

		// Gets the autonomous mode that has been selected and prints it out.
		autoSelected = chooser.getSelected();
		System.out.println("Auto selected: " + autoSelected);

		// Gets the string from FMS that has the position of switch and scale.
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		SmartDashboard.putString("DB/String 6", "Game data: " + String.valueOf(gameData));

		comp.leftFrontDrive.setNeutralMode(NeutralMode.Brake);
		comp.rightFrontDrive.setNeutralMode(NeutralMode.Brake);
		comp.leftBackDrive.setNeutralMode(NeutralMode.Brake);
		comp.rightBackDrive.setNeutralMode(NeutralMode.Brake);
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		// The mirror variable assumes that the robot will always go to or start
		// on the left unless told to go to the right.

		Autonomous.delay();

		/*
		 * This switch statement contains all the autonomous options. The case
		 * ran depends on which auto was chosen by the drivers. Within each
		 * case, the game data is used to determine exactly which auto is run.
		 */
		switch (autoSelected) {

		case startCenter:
		default:
			if (gameData.charAt(0) == 'L') {
				// Left auto code
				Autonomous.centerLeftSwitch();
			} else {
				// Right auto code
				Autonomous.mirror = true;
				Autonomous.centerRightSwitch();
			}
			break;

		case startLeft:
			if (gameData.charAt(0) == 'L') {
				// Left switch auto code
				Autonomous.sideSwitch();
			} else if (gameData.charAt(1) == 'L') {
				// Left scale auto code
				Autonomous.sideScale();
			} else {
				// Something else, maybe cross field, maybe just auto line.
				Autonomous.baseline();
			}
			break;

		case startRight:
			Autonomous.mirror = true;
			if (gameData.charAt(0) == 'R') {
				// Right switch auto code
				Autonomous.sideSwitch();
			} else if (gameData.charAt(1) == 'R') {
				// Right scale auto code
				Autonomous.sideScale();
			} else {
				// Something else, maybe cross field, maybe just auto line.
				Autonomous.baseline();
			}
			break;

		case startCenterTwoCubes:
			if (gameData.charAt(0) == 'L') {
				// Left auto code
				Autonomous.centerTwoCubes();
			} else {
				// Right auto code
				Autonomous.mirror = true;
				Autonomous.centerTwoCubes();
			}
			break;

		case startLeftTwoCubes:
			if (gameData.charAt(0) == 'L') {
				// Left switch auto code
				Autonomous.sideSwitchTwoCubes();
			} else if (gameData.charAt(1) == 'L') {
				// Left scale auto code
				Autonomous.sideScale();
			} else {
				// Something else, maybe cross field, maybe just auto line.
				Autonomous.baseline();
			}
			break;

		case startRightTwoCubes:
			Autonomous.mirror = true;
			if (gameData.charAt(0) == 'R') {
				// Right switch auto code
				Autonomous.sideSwitchTwoCubes();
			} else if (gameData.charAt(1) == 'R') {
				// Right scale auto code
				Autonomous.sideScale();
			} else {
				// Something else, maybe cross field, maybe just auto line.
				Autonomous.baseline();
			}
			break;

		case Baseline:
			Autonomous.baseline();
			break;

		case Test:
			Autonomous.test();
			break;
		}
		comp.myDrive.tankDrive(Autonomous.leftSetSpeed, Autonomous.rightSetSpeed, false);

		/*
		 * avg.setDouble(Autonomous.average());
		 * left.setDouble(Autonomous.leftEncoder());
		 * right.setDouble(Autonomous.rightEncoder());
		 */
	}

	// Initializes subsystems.
	@Override
	public void teleopInit() {
		// Resets the encoders and timer.
		Effectors.getInstance().leftFrontDrive.setSelectedSensorPosition(0, 0, 0);
		Effectors.getInstance().rightFrontDrive.setSelectedSensorPosition(0, 0, 0);
		Effectors.getInstance().elevator2.setSelectedSensorPosition(0, 0, 0);
		Autonomous.time.reset();

		comp.leftFrontDrive.setNeutralMode(NeutralMode.Brake);
		comp.rightFrontDrive.setNeutralMode(NeutralMode.Brake);
		comp.leftBackDrive.setNeutralMode(NeutralMode.Brake);
		comp.rightBackDrive.setNeutralMode(NeutralMode.Brake);
	}

	/**
	 * This function is called periodically during operator control
	 */
	// Runs subsystems
	@Override
	public void teleopPeriodic() {
		// For testing encoders, will be moved to a different class.
		/*
		 * int leftAnalogPos =
		 * Effectors.getInstance().leftFrontDrive.getSensorCollection().
		 * getAnalogIn(); SmartDashboard.putString("DB/String 2",
		 * "leftAnalogPos: " + String.valueOf(leftAnalogPos)); int
		 * rightAnalogPos =
		 * Effectors.getInstance().rightFrontDrive.getSensorCollection().
		 * getAnalogIn(); SmartDashboard.putString("DB/String 3",
		 * "rightAnalogPos: " + String.valueOf(rightAnalogPos)); int
		 * wristEncoderPos =
		 * Effectors.getInstance().wristMotor.getSensorCollection().getAnalogIn(
		 * ); SmartDashboard.putString("DB/String 4", "wristEncoderPos: " +
		 * String.valueOf(wristEncoderPos));
		 */

		int elevatorEncoderPos = Effectors.getInstance().elevator2.getSensorCollection().getQuadraturePosition();
		SmartDashboard.putString("DB/String 4", "elevator: " + String.valueOf(elevatorEncoderPos));
		SmartDashboard.putString("DB/String 9", "Current: " + String.valueOf(comp.pdp.getTotalCurrent()));

		drive.drivePeriodic();
		elevator.elevatorPeriodic();
		intake.intakePeriodic();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		// This actually does work. You just have to run the java dashboard.
		LiveWindow.run();
	}

	@Override
	public void disabledInit() {
		Autonomous.resetEncoders();

		// DON'T USE AT COMPETITION:
		/*
		 * comp.leftFrontDrive.setNeutralMode(NeutralMode.Coast);
		 * comp.rightFrontDrive.setNeutralMode(NeutralMode.Coast);
		 * comp.leftBackDrive.setNeutralMode(NeutralMode.Coast);
		 * comp.rightBackDrive.setNeutralMode(NeutralMode.Coast);
		 */
	}

	@Override
	public void disabledPeriodic() {
		SmartDashboard.putString("DB/String 0", "Auto: " + String.valueOf(chooser.getSelected()));
		SmartDashboard.putString("DB/String 5", "Delay: " + String.valueOf(SmartDashboard.getNumber("DB/Slider 0", 0)));

		SmartDashboard.putString("DB/String 9", "Average: " + String.valueOf(Autonomous.average()));
		int elevatorEncoderPos = Effectors.getInstance().elevator2.getSensorCollection().getQuadraturePosition();
		SmartDashboard.putString("DB/String 4", "elevator: " + String.valueOf(elevatorEncoderPos));
	}
}
