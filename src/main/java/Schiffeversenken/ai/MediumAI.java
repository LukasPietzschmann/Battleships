package Schiffeversenken.ai;

import Schiffeversenken.logic.*;

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
