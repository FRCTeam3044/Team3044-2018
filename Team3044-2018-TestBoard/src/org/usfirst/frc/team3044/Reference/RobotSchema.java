package org.usfirst.frc.team3044.Reference;
import java.util.Properties;
import java.util.Map;
import java.io.FileInputStream;
import java.util.Enumeration;
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
		map.put("pistonLeft", new SolenoidDefinition(61, 1));
		map.put("pistonRight", new SolenoidDefinition(61, 2));
		map.put("retractIntake", new SolenoidDefinition(61, 3));
		
		return map;

	}
	
	
	/* (Nick) I put this in as an example of how to read the robot config from a text file (properties file)
	 
	 The current schema would look like this in the properties file: 
	 
	 pistonLeft=Solenoid:61:1 
	 pistonRight=Solenoid:61:2 
	 retractIntake=Solenoid:61:3 
	 leftFrontDrive=Talon:1
	 rightFrontDrive=Talon:3 
	 leftBackDrive=Talon:2 
	 rightBackDrive=Talon:4 
	 
	 
	 * */

	private void LoadSchemaFromFile(String filePath) 
	{
		Map<String, Integer> canMap = new HashMap<String,Integer>(); 
		Map<String, SolenoidDefinition> solenoidMap  = new HashMap<String, SolenoidDefinition>(); 
		
		Properties prop = new Properties(); 
		
		try 
		{ 
			FileInputStream input = new FileInputStream(filePath);
			prop.load(input);
			input.close();
		}
		catch (java.io.IOException e3)
		{
			System.out.println("Error loading schema configuration:" + e3.getMessage());
			return; 
		}
		
		Enumeration<?> e = prop.propertyNames();
		while (e.hasMoreElements()) 
		{
			String key = (String) e.nextElement();
			String value = prop.getProperty(key);
		
			String configLine[] = value.split(":"); 
			
			switch (configLine[0].toLowerCase())
			{
				case "talon":
					// Should have 2 elements 
					if (configLine.length !=2 )
					{
						System.out.println("Invalid configuration format for Talon " + key + ":" + value);
					}
					else
					{
						try { 
						this.canTalonMap.put( key,Integer.parseInt(configLine[1])); 
						}
						catch (Exception e1) 
						{
							System.out.println("Error parsing CAN configuration for " + key + ": " + e1.getMessage());
						}
					}
					break; 
				case "solenoid": 
					// Has 3 elements 
					if (configLine.length != 3)
					{
						System.out.println("Invalid configuration format for Solenoid " + key + ": " + value);
					}
					else
					{
						try
						{
							this.solenoidMap.put(key, new SolenoidDefinition(
									Integer.parseInt(configLine[1]), 
									Integer.parseInt(configLine[2])
									));
						}
						catch (Exception e2)
						{
							System.out.println("Error parsing Solenoid configuration for " + key + ": " + e2.getMessage());
						}
					}
					break; 
			}
		} 
		
		
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
		
		//Intake
		map.put("leftSweep", 7);
		map.put("rightSweep", 8);

		return map;
	}

}
