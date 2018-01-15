package org.usfirst.frc.team3044.Reference;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;

public class FirstController {
	private static FirstController instance = null;

	private Joystick firstJoy;

	public static int BUTTON_X = 3;
	public static int BUTTON_Y = 4;
	public static int BUTTON_B = 2;
	public static int BUTTON_A = 1;

	public static int BUTTON_RB = 6;
	public static int BUTTON_LB = 5;
	public static int BUTTON_BACK = 7;
	public static int BUTTON_START = 8;

	private FirstController() {
		firstJoy = new Joystick(0);
	}

	public static FirstController getInstance() {
		if (instance == null) {
			instance = new FirstController();
		}
		return instance;
	}

	public double getLeftX() {
		return firstJoy.getRawAxis(0);
	}

	public double getLeftY() {
		return firstJoy.getRawAxis(1);
	}

	public double getRightX() {
		return firstJoy.getRawAxis(4);
	}

	public double getRightY() {
		return firstJoy.getRawAxis(5);
	}

	public boolean getTriggerRight() {
		return Math.abs(firstJoy.getRawAxis(3)) > 0.1;
	}

	public boolean getTriggerLeft() {
		return Math.abs(firstJoy.getRawAxis(2)) > 0.1;
	}

	public boolean getDPadLeft() {
		if (firstJoy.getPOV() == 270) {
			return true;
		} else {
			return false;
		}
	}

	public boolean getDPadRight() {
		if (firstJoy.getPOV() == 90) {
			return true;
		} else {
			return false;
		}
	}

	public boolean getDPadUp() {
		if (firstJoy.getPOV() == 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean getDPadDown() {
		if (firstJoy.getPOV() == 180) {
			return true;
		} else {
			return false;
		}
	}

	public boolean getDPadOther() {
		if (firstJoy.getPOV() == -1) {
			return true;
		} else {
			return false;
		}
	}

	public boolean getRawButton(int num) {
		if (!DriverStation.getInstance().isAutonomous()) {
			return firstJoy.getRawButton(num);

		} else {
			return false;
		}
	}
}
