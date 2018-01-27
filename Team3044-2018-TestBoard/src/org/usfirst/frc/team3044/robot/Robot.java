package org.usfirst.frc.team3044.robot;

import org.usfirst.frc.team3044.Reference.Effectors;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	Drive drive = new Drive();
	Elevator elevator = new Elevator();
	Intake intake = new Intake();

	final String startCenter = "Start Center";
	final String startLeft = "Start Left";
	final String startRight = "Start Right";
	String autoSelected;
	SendableChooser<String> chooser = new SendableChooser<>();

	String gameData;
	Boolean mirror = false;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		Effectors.getInstance().init();
		chooser.addDefault("Start Center", startCenter);
		chooser.addObject("Start Left", startLeft);
		chooser.addObject("Start Right", startRight);
		SmartDashboard.putData("Auto choices", chooser);
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		autoSelected = chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		System.out.println("Auto selected: " + autoSelected);

		gameData = DriverStation.getInstance().getGameSpecificMessage();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		// The mirror variable assumes that the robot will always go to or start on the left unless told to go to the right.
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

	public void teleopInit() {
		drive.driveInit();
		elevator.elevatorInit();
		intake.intakeInit();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		drive.drivePeriodic();
		elevator.elevatorPeriodic();
		intake.intakePeriodic();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
}
