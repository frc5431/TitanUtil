package org.usfirst.frc.team5431.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;

public class TitanNavx extends AHRS {
	private double absoluteReset = 0;
	private boolean yawDirection = false;

	public TitanNavx() {
			super(SPI.Port.kMXP);

			reset();
			resetDisplacement();
		}

	public void resetYaw() {
		absoluteReset = getYaw();
		if (absoluteReset <= 0) {
			yawDirection = false; // false == left
		} else {
			yawDirection = true; // true == right
		}
	}

	public double getAbsoluteYaw() {
		if (!yawDirection) {
			return getYaw() + absoluteReset;
		} else {
			return getYaw() - absoluteReset;
		}
	}
}
