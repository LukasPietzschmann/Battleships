package ai;

import logic.Logic;

import logic.Map;
import logic.Player;
import logic.Ship;
import java.util.Random;

public class MediumAI extends PlayableAI {
	public MediumAI(Player player, Logic logic, Map map) {
		super(player, logic, map);
	}
	
	/**
	 * {@inheritDoc} Die MediumAI schießt zufällig, bis sie ein Schiff getroffen hat. Dann wird sie um die getroffenen
	 * Koordinaten herum schießen.
	 *
	 * @return {@inheritDoc}
	 */
	@Override
	protected boolean makeMove() {
		try {
			Thread.sleep(WAIT_TIME);
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		int x, y;
		if(currMission == null) {
			Random rnd = new Random();
			do {
				x = rnd.nextInt(map.getSize());
				y = rnd.nextInt(map.getSize());
			}while(enemyMap[y][x] != NOT_SHOT);
			enemyMap[y][x] = ALREADY_SHOT;
			if(logic.shoot(x, y, player) == null) return false;
			currMission = new Mission(x, y, map, enemyMap);
			return true;
		}
		
		x = currMission.getNextX();
		y = currMission.getNextY();
		
		Ship ship = logic.shoot(x, y, player);
		enemyMap[y][x] = ALREADY_SHOT;
		if(ship == null) {
			currMission.wasHit(false, enemyMap);
			return false;
		}
		if(ship.isAlive()) currMission.wasHit(true, enemyMap);
		else currMission = null;
		
		return true;
	}
}
