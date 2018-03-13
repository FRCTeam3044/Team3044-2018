/*Contains the code that controls the robot during the autonomous period.
 *Currently contains 4 different types of auto, baseline cross, placing in switch from the center,
 *placing in the switch from one of the sides and placing in the scale from one of the sides.
 *The choosing of which one to run is in robot.java.
 */
package org.usfirst.frc.team3044.robot;

import org.usfirst.frc.team3044.Reference.*;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Autonomous {
	public static Effectors comp = Effectors.getInstance();

	static DifferentialDrive myDrive;

	// Creates a timer that can be used to trigger the next event.
	static Timer time = new Timer();
	static int state;

	static double leftSetSpeed;
	static double rightSetSpeed;

	static int leftStart;
	static int rightStart;

	static boolean mirror = false;
	// Read from slider 0, input can only be 0-10, delays the start of baseline execution.
	static double delay;

	static int SWITCH_HEIGHT; // TODO: Get correct number.

	// Sets the auto states to 0, not needed unless we use switch statements.
	/*
	 * int baseline = 0;
	 * int centerSwitch = 0;
	 * int sideScale = 0;
	 */

	public void autonomousInit() {
		myDrive = comp.myDrive;
		state = 0;

		/*
		 * baseline = 0;
		 * centerSwitch = 0;
		 * sideScale = 0;
		 */
		resetEncoders();
		time.reset();
		time.start();

		delay = SmartDashboard.getNumber("DB/Slider 0", 0);
		if (delay > 10) {
			delay = 10;
		} else if (delay < 0) {
			delay = 0;
		}
	}

	// Contains the auto for crossing the baseline.
	public static void baseline() {
		while (time.get() < delay) {
			myDrive.tankDrive(0, 0, false);
		}
		while (time.get() < delay + 2) {
			myDrive.tankDrive(.5, .6, false);
		}
		myDrive.tankDrive(0.0, 0.0, false);
	}

	// Contains the auto for placing a cube in the switch from the center.
	public static void centerSwitch() {
		/*
		 * while (-actualValue(leftStart, Effectors.getInstance().leftFrontDrive.getSensorCollection().getAnalogIn()) < 2000) {
		 * //myDrive.tankDrive(.5, .5, false);
		 * comp.leftFrontDrive.set(ControlMode.Velocity, .2 * 4096 * 500.0 / 600);
		 * comp.rightFrontDrive.set(ControlMode.Velocity, -.2 * 4096 * 500.0 / 600);
		 * }
		 * resetEncoders();
		 * while (Math.abs(actualValue(leftStart, Effectors.getInstance().leftFrontDrive.getSensorCollection().getAnalogIn())) < 450) {
		 * //myDrive.tankDrive(invert(-.5, mirror), invert(.5, mirror), false);
		 * comp.leftFrontDrive.set(ControlMode.Velocity, -.2 * 4096 * 500.0 / 600);
		 * comp.rightFrontDrive.set(ControlMode.Velocity, -.2 * 4096 * 500.0 / 600);
		 * }
		 * while (comp.ds.isAutonomous()) {
		 * myDrive.tankDrive(0.0, 0.0, false);
		 * }
		 */
		resetEncoders();
		while (comp.actualValue(leftStart, Effectors.getInstance().leftFrontDrive.getSensorCollection().getAnalogIn()) < 2500) {
			myDrive.tankDrive(.5, .56, false);
		}
		resetEncoders();
		while (Math.abs(comp.actualValue(leftStart, Effectors.getInstance().leftFrontDrive.getSensorCollection().getAnalogIn())) < 300) {
			myDrive.tankDrive(invert(-.5), invert(.5), false);
		}
		resetEncoders();
		while (comp.actualValue(leftStart, Effectors.getInstance().leftFrontDrive.getSensorCollection().getAnalogIn()) < 500) {
			myDrive.tankDrive(.5, .56, false);
		}
		resetEncoders();
		while (comp.actualValue(leftStart, Effectors.getInstance().leftFrontDrive.getSensorCollection().getAnalogIn()) < 300) {
			myDrive.tankDrive(invert(.5), invert(-.5), false);
		}
		resetEncoders();
		elevatorUp();
		time.reset();
		time.start();
		while (comp.actualValue(leftStart, Effectors.getInstance().leftFrontDrive.getSensorCollection().getAnalogIn()) < 500 && time.get() < 2) {
			myDrive.tankDrive(.5, .56, false);
		}
		cubeOut();
		while (comp.ds.isAutonomous()) {
			myDrive.tankDrive(0.0, 0.0, false);
		}

	}

	// Contains the auto for placing a cube in the switch from the side.
	public static void sideSwitch() {
		resetEncoders();
		while (comp.actualValue(rightStart, Effectors.getInstance().rightFrontDrive.getSensorCollection().getAnalogIn()) < 6500) {
			myDrive.tankDrive(.4, .46, false);
		}
		resetEncoders();
		while (Math.abs(comp.actualValue(rightStart, Effectors.getInstance().rightFrontDrive.getSensorCollection().getAnalogIn())) < 500) {
			myDrive.tankDrive(invert(.5), invert(-.5), false);
		}
		resetEncoders();
		elevatorUp();
		time.reset();
		time.start();
		while (comp.actualValue(rightStart, Effectors.getInstance().rightFrontDrive.getSensorCollection().getAnalogIn()) < 500 && time.get() < 2) {
			myDrive.tankDrive(.2, .26, false);
		}
		cubeOut();
		while (comp.ds.isAutonomous()) {
			myDrive.tankDrive(0.0, 0.0, false);
		}
	}

	public static void centerRightSwitch() {
		switch (state) {
		default:
			leftSetSpeed = 0;
			rightSetSpeed = 0;
			break;
		case 0:
			drive(.4, .48, 3000, 0);
			if (Elevator.elevatorEncoder() < SWITCH_HEIGHT) {
				Elevator.moveElevator(.5);
			}
			break;
		case 1:
			checkElevator();
			break;
		case 2:
			drive(.3, .38, 1800, 2);
			break;
		case 3:
			cubeOut();
			break;
		}
		myDrive.tankDrive(leftSetSpeed, rightSetSpeed, false);
	}

	public static void centerLeftSwitch() {
		switch (state) {
		default:
			leftSetSpeed = 0;
			rightSetSpeed = 0;
			break;
		case 0:
			drive(.3, .38, 1000, 0);
			break;
		case 1:
			drive(-.3, .3, 680, 0);
			break;
		case 2:
			drive(.4, .48, 5500, 0);
			break;
		case 3:
			drive(.3, -.3, 680, 0);
			break;
		case 4:
			drive(.3, .38, 1500, 0);
			if (Elevator.elevatorEncoder() < SWITCH_HEIGHT) {
				Elevator.moveElevator(.5);
			}
			break;
		case 5:
			checkElevator();
			break;
		case 6:
			drive(.3, .38, 1500, 2);
			break;
		case 7:
			cubeOut();
			break;
		}
		myDrive.tankDrive(leftSetSpeed, rightSetSpeed, false);
	}

	public static void test() {

		myDrive.tankDrive(leftSetSpeed, rightSetSpeed, false);

		SmartDashboard.putString("DB/String 1", "Average: " + String.valueOf(average()));
		SmartDashboard.putString("DB/String 2", "Difference: " + String.valueOf(difference()));
	}

	// Contains the auto for placing a cube in the scale from the side.
	public static void sideScale() {
		baseline(); // No scale code yet so it will just do baseline.
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
		myDrive.tankDrive(0.0, 0.0, false);
		// Resets the encoders to 0.
		Effectors.getInstance().leftFrontDrive.setSelectedSensorPosition(0, 0, 0);
		Effectors.getInstance().rightFrontDrive.setSelectedSensorPosition(0, 0, 0);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		leftStart = Effectors.getInstance().leftFrontDrive.getSensorCollection().getAnalogIn();
		rightStart = Effectors.getInstance().rightFrontDrive.getSensorCollection().getAnalogIn();
	}

	/**
	 * A function that abstracts the details of driving over a distance with an optional timeout(0 is none).
	 * 
	 * @param leftSpeed
	 *            The speed for the left motor(-1 to 1).
	 * @param rightSpeed
	 *            The speed for the right motor(-1 to 1).
	 * @param distance
	 *            The encoder distance you want to travel.
	 * @param timeout
	 *            An optional timeout that will end the driving after the specified time in seconds. 0 is no timeout.
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
		time.reset();
		time.start();
		while (Elevator.elevatorEncoder() < 200 && time.get() < 2) {
			myDrive.tankDrive(0.0, 0.0, false);
			Elevator.moveElevator(.5);
		}
	}

	static void cubeOut() {
		time.reset();
		time.start();
		while (time.get() < .5) {
			comp.wristMotor.set(-0.3);
		}
		time.reset();
		time.start();
		while (time.get() < 1) {
			Intake.intakeWheels(1);
		}
		Intake.intakeArms(true);
		time.reset();
		time.start();
		while (time.get() < 1) {
			Intake.intakeWheels(1);
		}
	}

	static void checkElevator() {
		if (Elevator.elevatorEncoder() < SWITCH_HEIGHT) {// TODO: Get correct number.
			Elevator.moveElevator(.5);
		} else {
			Elevator.moveElevator(0);
			state++;
		}
	}

	/**
	 * A function for inverting the speed of a turn for autos that are mirror images. Reads if it is mirror or not from robot.java.
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