package org.usfirst.frc.team3044.Reference;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX; 

public class WPI_TalonPIDOutput implements edu.wpi.first.wpilibj.PIDOutput {

	private WPI_TalonSRX _talonSRX; 
	
	public WPI_TalonPIDOutput(WPI_TalonSRX talonSRX) {
		this._talonSRX=talonSRX; 
	}
	
	@Override
	public void pidWrite(double output) {
		_talonSRX.set(output);
	}

}
