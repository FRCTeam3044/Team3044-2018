package org.usfirst.frc.team3044.Reference;

public class SolenoidDefinition {
	public int talonID; 
	public int pcmChannel; 
	
	public SolenoidDefinition(int talonID, int  pcmChannel)
	{
		this.talonID = talonID; 
		this.pcmChannel = pcmChannel; 
	}
}
