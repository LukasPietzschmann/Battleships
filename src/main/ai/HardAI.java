package ai;

import logic.Logic;
import logic.Map;
import logic.Player;
import logic.Ship;

import java.util.ArrayList;
import java.util.Random;

public class HardAI extends PlayableAI {
	private int minSize = 2;
	ArrayList<Ship> ships;
	
	public HardAI(Player player, Logic logic, Map map) {
		super(player, logic, map);
		ships = logic.getAvailableShips();
		genMinSize();
	}
	
	//TODO Rand um versunkene Schiffe platzieren und berücksichtigen
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
			}while(x % minSize != 0 || y % minSize != 0 || enemyMap[y][x] != NOT_SHOT);
			
			enemyMap[y][x] = ALREADY_SHOT;
			if(logic.shoot(x, y, player) == null) return false;
			currMission = new Mission(x, y, map, enemyMap);
			return true;
		}
		
		x = currMission.getNextX();
		y = currMission.getNextY();
		
		enemyMap[y][x] = ALREADY_SHOT;
		Ship ship = logic.shoot(x, y, player);
		if(ship == null) {
			currMission.wasHit(false, enemyMap);
			return false;
		}
		if(ship.isAlive()) currMission.wasHit(true, enemyMap);
		else {
			currMission = null;
			genMinSize();
		}
		
		return true;
	}
	
	private void genMinSize() {
		int min = Integer.MAX_VALUE;
		for(Ship ship : ships) if(ship.getSize() < min) min = ship.getSize();
		minSize = min;
	}
}
