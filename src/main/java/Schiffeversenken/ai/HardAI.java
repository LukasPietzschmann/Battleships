package Schiffeversenken.ai;

import Schiffeversenken.logic.*;

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
			}while((!isOnMinGrid(x,y) || enemyMap[y][x] != NOT_SHOT) && counter < Math.pow(logic.getSize(), 2) * 2);
			
			enemyMap[y][x] = ALREADY_SHOT;
			Ship ship;
			if((ship = logic.shoot(x, y, player)) != null) currMission = new Mission(x, y, map, enemyMap);
			return ship;
		}
		
		x = currMission.getNextX();
		y = currMission.getNextY();
		
		enemyMap[y][x] = ALREADY_SHOT;
		Ship ship = logic.shoot(x, y, player);
		if(ship == null) {
			currMission.wasHit(false, enemyMap);
			return null;
		}
		if(ship.isAlive()) currMission.wasHit(true, enemyMap);
		else {
			for(Ship s : ships) {
				if(s.getSize() == ship.getSize()){
					ships.remove(s);
					break;
				}
			}
			currMission = null;
			genMinSize();
		}
		
		return ship;
	}
	
	private void genMinSize() {
		int min = Integer.MAX_VALUE;
		for(Ship ship : ships) if(ship.getSize() < min) min = ship.getSize();
		minSize = min;
	}
	
	private boolean isOnMinGrid(int x, int y){
		for(int i = 0; i < minSize; i++) {
			if(y % minSize == i && x % minSize == i) return true;
		}
		
		return false;
	}
}
