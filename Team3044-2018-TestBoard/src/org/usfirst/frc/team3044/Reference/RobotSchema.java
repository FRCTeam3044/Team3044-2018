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

		return map;

	}

	private Map<String, Integer> loadCanTalonMap() {
		Map<String, Integer> map = new HashMap<String, Integer>();

		// Device name, CAN Talon ID

		// Drive
		map.put("leftFrontDrive", 0);
		map.put("rightFrontDrive", 2);
		map.put("leftBackDrive", 1);
		map.put("rightBackDrive", 3);

		// Elevator
		map.put("elevator1", 12);
		map.put("elevator2", 13);

		// Intake
		map.put("leftSweep", 4);
		map.put("rightSweep", 5);
		map.put("wristMotor", 14);

		return map;
	}

}
