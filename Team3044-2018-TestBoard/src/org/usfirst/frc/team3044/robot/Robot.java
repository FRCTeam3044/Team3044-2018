package org.usfirst.frc.team3044.robot;

import org.usfirst.frc.team3044.Reference.Effectors;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	Drive drive = new Drive();
	Elevator elevator = new Elevator();
	Intake intake = new Intake();
	private Effectors comp = Effectors.getInstance();

	// Creates variables for autonomous selection.
	final String startCenter = "Start Center";
	final String startLeft = "Start Left";
	final String startRight = "Start Right";
	String autoSelected;
	SendableChooser<String> chooser = new SendableChooser<>();

	// Data from the FMS about switch and scale placement.
	String gameData;

	// Assumes the robot always starts/goes left in auto, changes to true if right side.
	Boolean mirror = false;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		// Initializes effectors.
		Effectors.getInstance().init();

		// Sends the auto choices to the dashboard.
		chooser.addDefault("Start Center", startCenter);
		chooser.addObject("Start Left", startLeft);
		chooser.addObject("Start Right", startRight);
		SmartDashboard.putData("Auto choices", chooser);
	}

	@Override
	public void autonomousInit() {
		// Gets the autonomous mode that has been selected.
		autoSelected = chooser.getSelected();

		System.out.println("Auto selected: " + autoSelected);

		// Gets the string that has the position of switch and scale.
		gameData = DriverStation.getInstance().getGameSpecificMessage();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		// The mirror variable assumes that the robot will always go to or start on the left unless told to go to the right.

		/*
		 * This switch statement contains all the autonomous options. The case ran depends on which auto was chosen by the drivers.
		 * Within each case, the game data is used to determine exactly which auto is run.
		 */
		switch (autoSelected) {

		case startCenter:
		default:
			if (gameData.charAt(0) == 'L') {
				// Left auto code
				Autonomous.centerSwitch(mirror);
			} else {
				// Right auto code
				mirror = true;
				Autonomous.centerSwitch(mirror);
			}
			break;

		case startLeft:
			if (gameData.charAt(0) == 'L') {
				// Left switch auto code
				Autonomous.sideSwitch(mirror);
			} else if (gameData.charAt(1) == 'L') {
				// Left scale auto code
				Autonomous.sideScale(mirror);
			} else {
				// Something else, maybe cross field, maybe just auto line.
				Autonomous.baseline();
			}
			break;

		case startRight:
			mirror = true;
			if (gameData.charAt(0) == 'R') {
				// Right switch auto code
				Autonomous.sideSwitch(mirror);
			} else if (gameData.charAt(1) == 'R') {
				// Right scale auto code
				Autonomous.sideScale(mirror);
			} else {
				// Something else, maybe cross field, maybe just auto line.
				Autonomous.baseline();
			}
			break;
		}
	}

	// Initializes subsystems.
	public void teleopInit() {
		drive.driveInit();
		elevator.elevatorInit();
		intake.intakeInit();
		Effectors.getInstance().leftFrontDrive.setSelectedSensorPosition(0, 0, 0);
		Effectors.getInstance().rightFrontDrive.setSelectedSensorPosition(0, 0, 0);
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	// Runs subsystems
	public void teleopPeriodic() {
		int leftAnalogPos = Effectors.getInstance().leftFrontDrive.getSensorCollection().getAnalogIn();
		SmartDashboard.putString("DB/String 2", "leftAnalogPos: " + String.valueOf(leftAnalogPos));
		int rightAnalogPos = Effectors.getInstance().rightFrontDrive.getSensorCollection().getAnalogIn();
		SmartDashboard.putString("DB/String 3", "rightAnalogPos: " + String.valueOf(rightAnalogPos));
		
		SmartDashboard.putString("DB/String 4", "current of 0: " + String.valueOf(comp.pdp.getCurrent(0)));
		
		drive.drivePeriodic();
		elevator.elevatorPeriodic();
		intake.intakePeriodic();
		

	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
	
	public void disabledPeriodic() {
		
		
	}
}
