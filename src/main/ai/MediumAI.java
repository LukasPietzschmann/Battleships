package ai;

import logic.Logic;

import logic.Map;
import logic.Player;
import logic.Ship;
import logic.Ship.Direction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
		int x, y;
		if(currMission == null) {
			Random rnd = new Random();
			do {
				x = rnd.nextInt(map.getSize());
				y = rnd.nextInt(map.getSize());
			}while(enemyMap[y][x] == NO_SHIP);
			
			if(logic.shoot(x, y, player) == null) return false;
			currMission = new Mission(x, y, map);
			return true;
		}
		
		x = currMission.getNextX();
		y = currMission.getNextY();
		
		Ship ship = logic.shoot(x, y, player);
		if(ship == null) {
			currMission.wasHit(false);
			return false;
		}
		enemyMap[y][x] = NO_SHIP;
		if(ship.isAlive()) currMission.wasHit(true);
		else currMission = null;
		
		return true;
	}
}
