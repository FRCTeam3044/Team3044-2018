/*Contains the code that controls the robot during the autonomous period.
 *Currently contains 4 different types of auto, baseline cross, placing in switch from the center,
 *placing in the switch from one of the sides and placing in the scale from one of the sides.
 *The choosing of which one to run is in robot.java.
 */
package org.usfirst.frc.team3044.robot;

import org.usfirst.frc.team3044.Reference.*;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Autonomous {
	public static Effectors comp = Effectors.getInstance();

	static DifferentialDrive myDrive;

	// Creates a timer that can be used to trigger the next event.
	static Timer time = new Timer();

	static int leftStart;
	static int rightStart;

	// Sets the auto states to 0, not needed unless we use switch statements.
	/*
	 * int baseline = 0;
	 * int centerSwitch = 0;
	 * int sideScale = 0;
	 */

	public void autonomousInit() {
		myDrive = comp.myDrive;

		/*
		 * baseline = 0;
		 * centerSwitch = 0;
		 * sideScale = 0;
		 */
		time.reset();
		time.start();
	}

	// Contains the auto for crossing the baseline.
	public static void baseline() {
		while (time.get() < 2) {
			myDrive.tankDrive(.5, .5, false);
		}
		myDrive.tankDrive(0.0, 0.0, false);
	}

	// Contains the auto for placing a cube in the switch from the center.
	public static void centerSwitch(Boolean mirror) {
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
		while (-actualValue(leftStart, Effectors.getInstance().leftFrontDrive.getSensorCollection().getAnalogIn()) < 5000) {
			myDrive.tankDrive(.5, .56, false);
		}
		resetEncoders();
		while (Math.abs(actualValue(leftStart, Effectors.getInstance().leftFrontDrive.getSensorCollection().getAnalogIn())) < 300) {
			myDrive.tankDrive(invert(-.5, mirror), invert(.5, mirror), false);
		}
		resetEncoders();
		while (-actualValue(leftStart, Effectors.getInstance().leftFrontDrive.getSensorCollection().getAnalogIn()) < 500) {
			myDrive.tankDrive(.5, .56, false);
		}
		resetEncoders();
		while (Math.abs(actualValue(leftStart, Effectors.getInstance().leftFrontDrive.getSensorCollection().getAnalogIn())) < 300) {
			myDrive.tankDrive(invert(.5, mirror), invert(-.5, mirror), false);
		}
		resetEncoders();
		while (-actualValue(leftStart, Effectors.getInstance().leftFrontDrive.getSensorCollection().getAnalogIn()) < 500) {
			myDrive.tankDrive(.5, .56, false);
		}
		while (comp.ds.isAutonomous()) {
			myDrive.tankDrive(0.0, 0.0, false);
		}

	}

	// Contains the auto for placing a cube in the switch from the side.
	public static void sideSwitch(Boolean mirror) {
		resetEncoders();
		while (-actualValue(leftStart, Effectors.getInstance().leftFrontDrive.getSensorCollection().getAnalogIn()) < 5000) {
			myDrive.tankDrive(.5, .56, false);
		}
		resetEncoders();
		while (Math.abs(actualValue(leftStart, Effectors.getInstance().leftFrontDrive.getSensorCollection().getAnalogIn())) < 300) {
			myDrive.tankDrive(invert(.5, mirror), invert(-.5, mirror), false);
		}
		resetEncoders();
		while (-actualValue(leftStart, Effectors.getInstance().leftFrontDrive.getSensorCollection().getAnalogIn()) < 500) {
			myDrive.tankDrive(.5, .56, false);
		}
		while (comp.ds.isAutonomous()) {
			myDrive.tankDrive(0.0, 0.0, false);
		}
	}

	// Contains the auto for placing a cube in the scale from the side.
	public static void sideScale(Boolean mirror) {
		baseline(); // No scale code yet so it will just do baseline.
	}

	/**
	 * A function for inverting the speed of a turn for autos that are mirror images.
	 * 
	 * @param value
	 *            The number to invert.
	 * @param mirror
	 *            Defaults false, set true if going or starting on the right.
	 * @return The value, possibly inverted depending on mirror.
	 */
	public static double invert(double value, Boolean mirror) {
		if (mirror) {
			value = value * -1;
		}
		return value;
	}

	public static void resetEncoders() {
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

	public static int actualValue(int startingValue, int readValue) {
		return readValue - startingValue;
	}
}