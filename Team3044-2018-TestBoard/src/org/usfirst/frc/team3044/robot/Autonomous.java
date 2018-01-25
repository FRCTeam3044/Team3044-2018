package org.usfirst.frc.team3044.robot;

import org.usfirst.frc.team3044.Reference.*;

public class Autonomous {

	public void centerSwitch(Boolean mirror) {

	}

	public void sideSwitch(Boolean mirror) {

	}

	public void sideScale(Boolean mirror) {

	}

	/**
	 * A function for inverting the speed of a turn for autos that are mirror images.
	 * 
	 * @param value The number to invert.
	 * @param mirror Defaults false, set true if going or starting on the right.
	 * @return The value, possibly inverted depending on mirror.
	 */
	public double invert(double value, Boolean mirror) {
		if (mirror) {
			value = value * -1;
		}
		return value;
	}
}