/*Contains the code that controls the robot during the autonomous period.
 *Currently contains 4 different types of auto, baseline cross, placing in switch from the center,
 *placing in the switch from one of the sides and placing in the scale from one of the sides.
 *The choosing of which one to run is in robot.java.
 */
package org.usfirst.frc.team3044.robot;

import org.usfirst.frc.team3044.Reference.*;

import edu.wpi.first.wpilibj.Timer;

public class Autonomous {

	// Allows for calling outputs from Effectors.java by adding out before the previously named talon.
	public Effectors out = Effectors.getInstance();

	// Creates a timer (named time) that can be used to trigger the next event.
	Timer time = new Timer();

	// Sets the auto states to 0.
	int baseline = 0;
	int centerSwitch = 0;
	int sideScale = 0;

	// Contains the auto for crossing the baseline.
	public static void baseline() {

	}

	// Contains the auto for placing a cube in the switch from the center.
	public static void centerSwitch(Boolean mirror) {

	}

	// Contains the auto for placing a cube in the switch from the side.
	public static void sideSwitch(Boolean mirror) {

	}

	// Contains the auto for placing a cube in the scale from the side.
	public static void sideScale(Boolean mirror) {

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
	public double invert(double value, Boolean mirror) {
		if (mirror) {
			value = value * -1;
		}
		return value;
	}
}