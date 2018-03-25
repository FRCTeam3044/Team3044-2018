package org.usfirst.frc.team3044.Reference;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.PIDSourceType; 

public class WPI_TalonPIDSource implements edu.wpi.first.wpilibj.PIDSource {

	private WPI_TalonSRX _talonSRX; 
	private PIDSourceType _pidSourceType; 
	
	public WPI_TalonPIDSource(WPI_TalonSRX talonSRX,PIDSourceType pidSourceType ) {
		this._talonSRX = talonSRX; 
		this._pidSourceType = pidSourceType; 
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		this._pidSourceType = pidSource ; 
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return _pidSourceType;
	}

	@Override
	public double pidGet() {
		return _talonSRX.getSensorCollection().getAnalogInVel(); 
	}
}