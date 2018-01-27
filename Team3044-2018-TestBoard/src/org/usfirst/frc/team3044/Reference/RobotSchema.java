package org.usfirst.frc.team3044.Reference;

import java.util.Map;
import java.util.HashMap;

// TODO: Update all talon ID's
public class RobotSchema {

	public Map<String, Integer> canTalonMap;
	public Map<String, SolenoidDefinition> solenoidMap;

	public RobotSchema() {
		this.canTalonMap = loadCanTalonMap();
		this.solenoidMap = loadSolenoidMap();
	}

	private Map<String, SolenoidDefinition> loadSolenoidMap() {
		Map<String, SolenoidDefinition> map = new HashMap<String, SolenoidDefinition>();

		// map.put("example", new SolenoidDefinition(61,4));

		// Intake
		map.put("leftIntake", new SolenoidDefinition(61, -1));
		map.put("rightIntake", new SolenoidDefinition(61, -1));
		map.put("retractIntake", new SolenoidDefinition(61, -1));

		return map;

	}

	private Map<String, Integer> loadCanTalonMap() {
		Map<String, Integer> map = new HashMap<String, Integer>();

		// Device name, CAN Talon ID

		// Drive
		map.put("leftFrontDrive", null);
		map.put("rightFrontDrive", null);
		map.put("leftBackDrive", null);
		map.put("rightBackDrive", null);

		// Elevator
		map.put("elevator1", null);
		map.put("elevator2", null);

		return map;
	}

}
