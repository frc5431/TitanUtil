package frc.team5431.titan.core.sensors;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;

public class Navx extends AHRS {
	private static enum Direction {
		LEFT, RIGHT
	}

	private double absoluteReset = 0;
	private Direction yawDirection = Direction.LEFT;

	public Navx() {
		super(SPI.Port.kMXP);

		reset();
		resetDisplacement();
	}

	public void resetYaw() {
		absoluteReset = getYaw();
		if (absoluteReset <= 0) {
			yawDirection = Direction.LEFT; // false == left
		} else {
			yawDirection = Direction.RIGHT; // true == right
		}
	}

	public double getAbsoluteYaw() {
		if (yawDirection == Direction.LEFT) {
			return getYaw() + absoluteReset;
		} else {
			return getYaw() - absoluteReset;
		}
	}
}
