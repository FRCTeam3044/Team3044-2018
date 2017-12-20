package org.usfirst.frc.team3044.Reference;

import java.util.Map;
import java.util.HashMap; 

// TODO: Update all talon ID's
public class RobotSchema {
	
	public Map<String,Integer> canTalonMap; 
	public Map<String,SolenoidDefinition> solenoidMap; 
	
	public RobotSchema()
	{
		this.canTalonMap=loadCanTalonMap(); 
		this.solenoidMap=loadSolenoidMap();
	}
	
	private Map<String,SolenoidDefinition> loadSolenoidMap()
	{
		Map<String,SolenoidDefinition> map = new HashMap<String,SolenoidDefinition>(); 
		
		//map.put("example", new SolenoidDefinition(61,4)); 
		
		return map; 
		
	}
	
	private Map<String,Integer> loadCanTalonMap()
	{
		Map<String, Integer> map = new HashMap<String,Integer>();
		
		// Device name, CAN Talon ID 
		
		// Drive 
		map.put("leftDrive", 1); 
		map.put("rightDrive", 2); 
		
		return map; 
	}
	
}
