package ai;

import logic.Logic;

import logic.Map;
import logic.Player;
import logic.Ship;
import java.util.Random;

/**
 * Die Klasse MediumAI modelliert die mittelschwere AI.
 */
public class MediumAI extends PlayableAI {
	/**
	 * Initialisiert die MediumAI
	 * @param player Referenz auf die von {@link Player} erbende Klasse. In diesem Fall meistens {@link AI}.
	 * @param logic "zurück-Referenz" auf die {@link Logic}.
	 * @param map Das eigene Spielfeld.
	 */
	public MediumAI(Player player, Logic logic, Map map) {
		super(player, logic, map);
	}
	
	/**
	 * {@inheritDoc} Die MediumAI schießt zufällig, bis sie ein Schiff getroffen hat. Dann wird sie geziehlt versuchen
	 * das Schiff zu versenken.
	 *
	 * @return {@inheritDoc}
	 */
	@Override
	protected Ship makeMove() {
		try {
			Thread.sleep(WAIT_TIME);
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		int x, y;
		if(currMission == null) {
			Random rnd = new Random();
			int counter = 0;
			do {
				counter++;
				x = rnd.nextInt(map.getSize());
				y = rnd.nextInt(map.getSize());
			}while(enemyMap[y][x] != NOT_SHOT && counter < Math.pow(logic.getSize(), 2) * 2);
			enemyMap[y][x] = ALREADY_SHOT;
			Ship ship;
			if((ship = logic.shoot(x, y, player)) != null) currMission = new Mission(x, y, map, enemyMap);
			return ship;
		}
		
		x = currMission.getNextX();
		y = currMission.getNextY();
		
		Ship ship = logic.shoot(x, y, player);
		enemyMap[y][x] = ALREADY_SHOT;
		if(ship == null) {
			currMission.wasHit(false, enemyMap);
			return null;
		}
		if(ship.isAlive()) currMission.wasHit(true, enemyMap);
		else currMission = null;
		
		return ship;
	}
}
