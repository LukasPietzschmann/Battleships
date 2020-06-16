package ai;

import logic.Logic;
import logic.Map;
import logic.Player;
import logic.Ship;

import java.util.ArrayList;
import java.util.Random;

public class HardAI extends PlayableAI {
	private int minSize;
	ArrayList<Ship> ships;
	
	public HardAI(Player player, Logic logic, Map map) {
		super(player, logic, map);
		ships = logic.getAvailableShips();
		genMinSize();
	}
	
	@Override
	public boolean makeMove() {
		Random rnd = new Random();
		int x, y;
		Ship.Direction dir;
		
		if(lastDir == null && lastXPos == -1 && lastYPos == -1) {
			// Alles beliebig setzen
			do {
				x = rnd.nextInt(map.getSize());
				y = rnd.nextInt(map.getSize());
				//FIXME canShipBePlaced muss auf ship!=null überprüfen und net auf status == SHIP
			}while(x % minSize != 0 || y % minSize != 0 || !map.canShipBePlaced(new Ship(x, y, Ship.Direction.north, 1)));
			
			if(logic.shoot(x, y, player) == null) return false; // Wenn nicht getroffen wurde
			else {
				// Wenn getroffen wurde, wird der Punkt gespeichert
				lastXPos = x;
				lastYPos = y;
				return true;
			}
		}else if(lastDir != null && lastXPos != -1 && lastYPos != -1) {
			x = lastXPos;
			y = lastYPos;
			if(isValidDirection(lastDir, lastXPos, lastYPos)) dir = lastDir;
			else dir = mirrorDirection(lastDir);
			// Falls ich da weiter machen kann
			Ship ship = shootInDirection(dir, x, y);
			if(ship == null) {
				// Richtung drehen, weil nicht getroffen wurde
				lastDir = mirrorDirection(dir);
				return false;
			}
			if(!ship.isAlive()) {
				// Alles löschen weil man das Schiff versenkt hat
				lastDir = null;
				lastXPos = -1;
				lastYPos = -1;
				for(int i = 0; i < ships.size(); i++) {
					if(ships.get(i).getSize() == ship.getSize()) {
						ships.remove(i);
						break;
					}
				}
				genMinSize();
			}else {
				// Alles Speuchern, da man getroffen hat
				lastXPos = getNewXKoord(dir, x);
				lastYPos = getNewYKoord(dir, y);
			}
			return true;
		}else if(lastDir == null && lastXPos != -1 && lastYPos != -1) {
			while(!isValidDirection(dir = Ship.Direction.north, lastXPos, lastYPos)) {
				dir = Ship.Direction.values()[rnd.nextInt(Ship.Direction.values().length)];
			}
			if(shootInDirection(dir, lastXPos, lastYPos) == null) return false;
			else {
				lastXPos = getNewXKoord(dir, lastXPos);
				lastYPos = getNewYKoord(dir, lastYPos);
				lastDir = dir;
				return true;
			}
		}
		
		System.err.println("Fuck die AI geht doch net einwandfrei");
		return false;
	}
	
	private void genMinSize() {
		int min = Integer.MAX_VALUE;
		for(Ship ship : ships) if(ship.getSize() < min) min = ship.getSize();
	}
}
