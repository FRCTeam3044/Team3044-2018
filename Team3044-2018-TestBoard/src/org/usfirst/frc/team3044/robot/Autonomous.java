/*Contains the code that controls the robot during the autonomous period.
 *Currently contains 4 different types of auto, baseline cross, placing in switch from the center,
 *placing in the switch from one of the sides and placing in the scale from one of the sides.
 *The choosing of which one to run is in robot.java.
 */
package org.usfirst.frc.team3044.robot;

import org.usfirst.frc.team3044.Reference.*;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Autonomous {
	public static Effectors comp = Effectors.getInstance();

	static DifferentialDrive myDrive;

	// Creates a timer that can be used to trigger the next event.
	static Timer time = new Timer();
	static int state;
	static int twoCubeState;
	static boolean mirror;

	static double leftSetSpeed;
	static double rightSetSpeed;

	static int leftStart;
	static int rightStart;

	// Read from slider 0, input can only be 0-10, delays the start of any auto execution.
	static double delay;
	static boolean delayHappened;

	static int SWITCH_HEIGHT = 18000;
	static double LEFT_SPEED = .40;
	static double RIGHT_SPEED = .45;
	static int TURN_90 = 1000; // First bot: 600
	static int TURN_45 = 250;

	public void autonomousInit() {
		state = -1;
		twoCubeState = -1;
		mirror = false;

		myDrive = comp.myDrive;
		// state = 0; //TODO: Not needed with the delay function?

		resetEncoders();
		Elevator.resetEncoders();
		time.reset();
		time.start();

		delayHappened = false;
		delay = SmartDashboard.getNumber("DB/Slider 0", 0);
		if (delay > 10) {
			delay = 10;
		} else if (delay < 0) {
			delay = 0;
		}
	}

	// Contains the auto for crossing the baseline.
	public static void baseline() {
		switch (state) {
		case 0:
			leftSetSpeed = .5;
			rightSetSpeed = .5;
			if (time.get() > 2) {
				state++;
			}
			break;
		case 1:
			leftSetSpeed = 0;
			rightSetSpeed = 0;
			break;
		}
	}

	// Contains the auto for placing a cube in the switch from the side.
	public static void sideSwitch() {
		switch (state) {
		default:
			leftSetSpeed = 0;
			rightSetSpeed = 0;
			break;
		case 0:
			drive(.4, .4, 7000, 0);
			break;
		case 1:
			turn(.5, -.5, 600, 0);
			if (Elevator.elevatorEncoder() < SWITCH_HEIGHT) {
				Elevator.moveElevator(-.5);
			}
			break;
		case 2:
			checkElevator();
			break;
		case 3:
			drive(.3, .3, 2000, 4);
			break;
		case 4:
			cubeOut();
			break;
		}
	}

	// Auto for starting in the center and driving straight forward to place a cube on the right side of the switch.
	public static void centerRightSwitch() {
		switch (state) {
		default:
			leftSetSpeed = 0;
			rightSetSpeed = 0;
			break;
		case 0:
			drive(LEFT_SPEED, RIGHT_SPEED, 3000, 0);
			if (Elevator.elevatorEncoder() < SWITCH_HEIGHT) {
				// Elevator.moveElevator(-.5); //TODO: Add back
			}
			break;
		case 1:
			// checkElevator(); //TODO: Add back
			elevatorUp();
			break;
		case 2:
			drive(LEFT_SPEED, RIGHT_SPEED, 1800, 2);
			break;
		case 3:
			cubeOut();
			break;
		}
	}

	// Auto for starting in the center and driving to the left to place a cube on the left side of the switch.
	public static void centerLeftSwitch() {
		switch (state) {
		default:
			leftSetSpeed = 0;
			rightSetSpeed = 0;
			break;
		case 0:
			drive(LEFT_SPEED, RIGHT_SPEED, 1000, 0);
			break;
		case 1:
			drive(-LEFT_SPEED, LEFT_SPEED, 500, 0);
			break;
		case 2:
			drive(LEFT_SPEED, RIGHT_SPEED, 5000, 0);
			break;
		case 3:
			drive(RIGHT_SPEED, -RIGHT_SPEED, 600, 0);
			break;
		case 4:
			drive(LEFT_SPEED, RIGHT_SPEED, 1500, 2);
			if (Elevator.elevatorEncoder() < SWITCH_HEIGHT) {
				// Elevator.moveElevator(-.5); //TODO:Add back
			}
			break;
		case 5:
			// checkElevator();//TODO:Add back
			elevatorUp();
			break;
		case 6:
			drive(LEFT_SPEED, RIGHT_SPEED, 1500, 2);
			break;
		case 7:
			cubeOut();
			break;
		}
	}

	// Contains the auto for placing a cube in the scale from the side.
	public static void sideScale() {
		baseline(); // No scale code yet so it will just do baseline.
	}

	public static void centerTwoCubes() {
		switch (twoCubeState) {
		default:
			leftSetSpeed = 0;
			rightSetSpeed = 0;
			break;
		case 0:
			if (mirror) {
				centerRightSwitch();
			} else {
				centerLeftSwitch();
			}
			break;
		case 1:
			state = 0;
			twoCubeState++;
			break;
		case 2:
			centerGetSecond();

		}
	}

	public static void sideSwitchTwoCubes() {
		switch (twoCubeState) {
		default:
			leftSetSpeed = 0;
			rightSetSpeed = 0;
			break;
		case 0:
			sideSwitch(); // Add later, for testing.
			break;
		case 1:
			state = 0;
			twoCubeState++;
			break;
		case 2:
			sideSwitchGetSecond();
			break;

		}
	}

	public static void centerGetSecond() {
		switch (state) {
		default:
			leftSetSpeed = 0;
			rightSetSpeed = 0;
			break;
		case 0:
			drive(-LEFT_SPEED, -RIGHT_SPEED, 2000, 0);
			break;
		case 1:
			elevatorDown();
			break;
		case 2:
			turn(RIGHT_SPEED, -RIGHT_SPEED, TURN_45, 0);
			break;
		case 3:
			cubeIn();
			break;
		case 4:
			drive(-LEFT_SPEED, -RIGHT_SPEED, 2000, 0);
			break;
		case 5:
			turn(-LEFT_SPEED, LEFT_SPEED, TURN_45, 0);
			break;
		case 6:
			elevatorUp();
			break;
		case 7:
			drive(LEFT_SPEED, RIGHT_SPEED, 3500, 4);
			break;
		case 8:
			cubeOut();
			break;
		}
	}

	public static void sideSwitchGetSecond() {

	}

	// For testing anything and everything.
	public static void test() {
		centerGetSecond();
		/*
		 * switch (state) {
		 * default:
		 * leftSetSpeed = 0;
		 * rightSetSpeed = 0;
		 * break;
		 * case 0:
		 * elevatorUp();
		 * break;
		 * case 1:
		 * cubeOut();
		 * break;
		 * case 2:
		 * elevatorDown();
		 * break;
		 * }
		 */
	}

	// -------------------------------------------------------------------------------------------------------------------------
	// -------------------------------------------------------------------------------------------------------------------------

	static void delay() {
		if (time.get() >= delay && !delayHappened) {
			state = 0;
			twoCubeState = 0;
			delayHappened = true;
			time.reset();
			time.start();
		}
	}

	static int leftEncoder() {
		return comp.actualValue(leftStart, Effectors.getInstance().leftFrontDrive.getSensorCollection().getAnalogIn());
	}

	static int rightEncoder() {
		return comp.actualValue(rightStart, Effectors.getInstance().rightFrontDrive.getSensorCollection().getAnalogIn());
	}

	static int average() {
		return (leftEncoder() + rightEncoder()) / 2;
	}

	static int difference() {
		return leftEncoder() - rightEncoder();
	}

	static void resetEncoders() {
		leftSetSpeed = 0;
		rightSetSpeed = 0;
		// Resets the encoders to 0.
		// Effectors.getInstance().leftFrontDrive.setSelectedSensorPosition(0, 0, 0);
		// Effectors.getInstance().rightFrontDrive.setSelectedSensorPosition(0, 0, 0);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		leftStart = Effectors.getInstance().leftFrontDrive.getSensorCollection().getAnalogIn();
		rightStart = Effectors.getInstance().rightFrontDrive.getSensorCollection().getAnalogIn();
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * A function that abstracts the details of driving over a distance with an
	 * optional timeout(0 is none).
	 * 
	 * @param leftSpeed
	 *            The speed for the left motor(-1 to 1).
	 * @param rightSpeed
	 *            The speed for the right motor(-1 to 1).
	 * @param distance
	 *            The encoder distance you want to travel.
	 * @param timeout
	 *            An optional timeout that will end the driving after the specified
	 *            time in seconds. 0 is no timeout.
	 * 
	 * @author Colin
	 */
	static void drive(double leftSpeed, double rightSpeed, int distance, int timeout) {
		leftSetSpeed = leftSpeed;
		rightSetSpeed = rightSpeed;
		if (average() > distance) {
			resetEncoders();
			time.reset();
			time.start();
			state++;
		}
		if (timeout > 0) {
			if (time.get() >= timeout) {
				resetEncoders();
				time.reset();
				time.start();
				state++;
			}
		}
	}

	static void turn(double leftSpeed, double rightSpeed, int distance, int timeout) {
		leftSetSpeed = invert(leftSpeed);
		rightSetSpeed = invert(rightSpeed);
		drive(leftSetSpeed, rightSetSpeed, distance, timeout);
	}

	static void elevatorUp() {
		if (/* Elevator.elevatorEncoder() < SWITCH_HEIGHT && */ time.get() < 1.75) {
			leftSetSpeed = 0;
			rightSetSpeed = 0;
			Elevator.moveElevator(-.75);
		} else {
			Elevator.moveElevator(0);
			resetEncoders();
			time.reset();
			time.start();
			state++;
		}
	}

	static void elevatorDown() {
		if (/* Elevator.elevatorEncoder() > 20 && */ time.get() < 1.5) {
			leftSetSpeed = 0;
			rightSetSpeed = 0;
			Elevator.moveElevator(.75);
		} else {
			Elevator.moveElevator(0);
			state++;
		}
	}

	static void cubeOut() {
		time.reset();
		time.start();
		while (time.get() < .5) {
			comp.wristMotor.set(-0.5);
		}
		comp.wristMotor.set(0);
		time.reset();
		time.start();
		while (time.get() < .5) {
			Intake.intakeWheels(-.25);
		}
		Intake.intakeArms(true, false);
		time.reset();
		time.start();
		while (time.get() < .2) {
			Intake.intakeWheels(-.25);
		}
		Intake.intakeWheels(0);
		time.reset();
		time.start();
		state++;
		twoCubeState++;
	}

	static void cubeIn() {
		if (time.get() < 2.5) {
			Intake.intakeArms(false, true);
			leftSetSpeed = .35;
			rightSetSpeed = .35;
		} else if (time.get() < 3) {
			Intake.intakeArms(false, false);
		} else if (time.get() < 3.2) {
			leftSetSpeed = -.4;
			rightSetSpeed = -.4;
		} else {
			Intake.intakeArms(false, false);
			Intake.intakeWheels(0);
			resetEncoders();
			state++;
		}
	}

	static void checkElevator() {
		leftSetSpeed = 0;
		rightSetSpeed = 0;
		if (Elevator.elevatorEncoder() < SWITCH_HEIGHT) {
			Elevator.moveElevator(-.5);
		} else {
			Elevator.moveElevator(0);
			state++;
		}
	}

	/**
	 * A function for inverting the speed of a turn for autos that are mirror
	 * images. Reads if it is mirror or not from robot.java.
	 * 
	 * @param value
	 *            The number to invert.
	 * @return The value, possibly inverted depending on mirror.
	 * 
	 * @author Colin
	 */
	static double invert(double value) {
		if (mirror) {
			value = value * -1;
		}
		return value;
	}

}