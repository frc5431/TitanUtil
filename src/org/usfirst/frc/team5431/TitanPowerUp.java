package org.usfirst.frc.team5431;

import edu.wpi.first.wpilibj.DriverStation;

public class TitanPowerUp {
	public static enum Position{
		LEFT, RIGHT;
		
		static Position fromGameData(final char value) {
			if(value == 'L') {
				return Position.LEFT;
			}else if(value == 'R'){
				return Position.RIGHT;
			}else {
				throw new IllegalArgumentException("Illegal Game Data Character: " + value);
			}
		}
	}
	
	private Position allianceSwitch, scale, opponentSwitch;
	
	public void init() {
		final String gameData = DriverStation.getInstance().getGameSpecificMessage();
		if(gameData.length() != 3) {
			throw new IllegalArgumentException("Illegal Game Data String: " + gameData);
		}
		allianceSwitch = Position.fromGameData(gameData.charAt(0));
		scale = Position.fromGameData(gameData.charAt(1));
		opponentSwitch = Position.fromGameData(gameData.charAt(2));
	}

	public Position getScale() {
		return scale;
	}

	public Position getOpponentSwitch() {
		return opponentSwitch;
	}

	public Position getAllianceSwitch() {
		return allianceSwitch;
	}
}
