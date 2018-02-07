package org.usfirst.frc.team3044.Reference;

import java.util.Map;
import java.util.HashMap;

// TODO: Update all talon ID's.
public class RobotSchema {

	// Creates Talon and Solenoid maps.
	public Map<String, Integer> canTalonMap;
	public Map<String, SolenoidDefinition> solenoidMap;

	public RobotSchema() {

		// Attaches definition to the previously named maps.
		this.canTalonMap = loadCanTalonMap();
		this.solenoidMap = loadSolenoidMap();
	}

	private Map<String, SolenoidDefinition> loadSolenoidMap() {
		Map<String, SolenoidDefinition> map = new HashMap<String, SolenoidDefinition>();

		// map.put("example", new SolenoidDefinition(61,4));

		// Intake
		map.put("pistonLeft", new SolenoidDefinition(61, 1));
		map.put("pistonRight", new SolenoidDefinition(61, 2));

		return map;

	}

	private Map<String, Integer> loadCanTalonMap() {
		Map<String, Integer> map = new HashMap<String, Integer>();

		// Device name, CAN Talon ID

		// Drive
		map.put("leftFrontDrive", 1);
		map.put("rightFrontDrive", 3);
		map.put("leftBackDrive", 2);
		map.put("rightBackDrive", 4);

		// Elevator
		map.put("elevator1", 5);
		map.put("elevator2", 6);

		// Intake
		map.put("leftSweep", 7);
		map.put("rightSweep", 8);
		map.put("wristMotor", 9);

		return map;
	}

}
