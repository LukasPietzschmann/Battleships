package ai;

import logic.Logic;
import logic.Map;
import logic.Player;
import logic.Ship;

import java.util.ArrayList;
import java.util.Random;

/**
 * Die Klasse HardAI modelliert die schwerste AI.
 */
public class HardAI extends PlayableAI {
	private int minSize = 2;
	ArrayList<Ship> ships;
	/**
	 * Initialisiert die HardAI
	 * @param player Referenz auf die von {@link Player} erbende Klasse. In diesem Fall meistens {@link AI}.
	 * @param logic "zurück-Referenz" auf die {@link Logic}.
	 * @param map Das eigene Spielfeld.
	 */
	public HardAI(Player player, Logic logic, Map map) {
		super(player, logic, map);
		ships = logic.getAvailableShips();
		genMinSize();
	}
	
	/**
	 * {@inheritDoc} Die HardAI schießt zufällig im Abstand vom kleinsten derzeit nicht versenkten Schiff.
	 * Hat die AI ein Schiff gefunden, verstcht sie dieses gezielt zu zerstören.
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
	
	/**
	 * Bestimmt das kleinste noch lebende Schiff.
	 */
	private void genMinSize() {
		int min = Integer.MAX_VALUE;
		for(Ship ship : ships) if(ship.getSize() < min) min = ship.getSize();
		minSize = min;
	}
	
	/**
	 * Gibt an, ob die gewünschte Koordinate den Abstand des kleinsten nicht versenkten Schiffs zu jedem anderen Schuss einhält.
	 * @param x die gewünschte x Kooridnate.
	 * @param y die gewünschte y Kooridnate.
	 * @return {@code true}, falls der Abstand eingehalten wird, sonst {@code false}.
	 */
	private boolean isOnMinGrid(int x, int y){
		for(int i = 0; i < minSize; i++) {
			if(y % minSize == i && x % minSize == i) return true;
		}
		
		return false;
	}
}
