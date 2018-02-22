package org.usfirst.frc.team3044.robot;

import org.usfirst.frc.team3044.Reference.*;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.*;
public class Arduino {

	I2C i2c;
	byte[] toSend = new byte[1];
	DigitalInput limitSwitch;
	
	public void arduinoInit ()
	{
		limitSwitch = new DigitalInput(1);
	}
	
	public void arduinoPeriodic ()
	{	
		while(limitSwitch.get()); {
			Effectors.getInstance().R.set(true);
		}
		Effectors.getInstance().R.set(false);
		}
	}

