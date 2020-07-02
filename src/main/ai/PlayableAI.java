package ai;

import logic.Logic;
import logic.Map;
import logic.Player;
import logic.Ship;

import java.io.IOException;

public abstract class PlayableAI {
	protected static final int NO_SHIP = 1;
	protected static final int MABY_SHIP = 0;
	protected Player player;
	protected Logic logic;
	protected Map map;
	protected int[][] enemyMap;
	/**
	 * Die letzte x-Koordinate an der getroffen wurde, oder -1, falls sie nicht existiert.
	 */
	protected int lastXPos = -1;
	/**
	 * Die letzte y-Koordinate an der getroffen wurde, oder -1, falls sie nicht existiert.
	 */
	protected int lastYPos = -1;
	/**
	 * Die letzte Richtung in die gegangen wurde, oder {@code null}, falls sie nicht existiert.
	 */
	protected Ship.Direction lastDir = null;
	
	protected Mission currMission = null;
	
	public PlayableAI(Player player, Logic logic, Map map) {
		enemyMap = new int[map.getSize()][map.getSize()];
		for(int i = 0; i < map.getSize(); i++) for(int j = 0; j < map.getSize(); j++) enemyMap[i][j] = MABY_SHIP;
		this.player = player;
		this.logic = logic;
		this.map = map;
	}
	
	protected abstract boolean makeMove();
	
	/**
	 * Spiegelt die Richtung. Bsp: {@code Direction.north} wird gespielgelt zu {@code Direction.south}.
	 *
	 * @param dir Die zu spiegelnde {@link Ship.Direction}.
	 * @return Die gespiegelte {@link Ship.Direction}.
	 */
	protected Ship.Direction mirrorDirection(Ship.Direction dir) {
		switch(dir) {
			case north:
				return Ship.Direction.south;
			case south:
				return Ship.Direction.north;
			case east:
				return Ship.Direction.west;
			case west:
				return Ship.Direction.east;
		}
		
		return Ship.Direction.north;
	}
	
	/**
	 * Überprüft ob in die angegebene {@link Ship.Direction} gegangen werden kann, ohne über zu Grenzen der {@link #map}
	 * zu laufen.
	 *
	 * @param dir Die {@link Ship.Direction} in die gegangen wird.
	 * @param x Die x-Koordinate von der aus sich bewegt wird.
	 * @param y Die y-Koordinate von der aus sich bewegt wird.
	 * @return {@code true}, falls es möglich ist in diese {@link Ship.Direction} zu laufen. Sonst {@code false}.
	 */
	protected boolean isValidDirection(Ship.Direction dir, int x, int y) {
		return map.isInMap(getNewXKoord(dir, x), getNewYKoord(dir, y));
	}
	
	/**
	 * Schießt auf das nächste Feld in der angegebenen {@link Ship.Direction}. Es wird nicht überprüft, ob man beim
	 * schießen in diese {@link Ship.Direction} die Grenzen der {@link #map} einhält! Dazu muss voher {@link
	 * #isValidDirection(Ship.Direction, int, int)} aufgeruen werden.
	 *
	 * @param dir Die {@link Ship.Direction} in die geschossen wird.
	 * @param x Die x-Koordinate von der aus in die {@link Ship.Direction} geschossen wird.
	 * @param y Die y-Koordinate von der aus in die {@link Ship.Direction} geschossen wird.
	 * @return Das konkrete {@link Ship} das getroffen wurde, oder {@code null}, falls nicht getroffen wurde.
	 */
	protected Ship shootInDirection(Ship.Direction dir, int x, int y) {
		return logic.shoot(getNewXKoord(dir, x), getNewYKoord(dir, y), player);
	}
	
	/**
	 * Berechnet die nächste x-Koordinate in die angegebene {@link Ship.Direction}.
	 *
	 * @param dir Die {@link Ship.Direction} in die gegangen wird.
	 * @param x Die alte x-Koordinate.
	 * @return Eine neue x-Koordinate.
	 */
	protected int getNewXKoord(Ship.Direction dir, int x) {
		switch(dir) {
			case east:
				return x + 1;
			case west:
				return x - 1;
			default:
				return x;
		}
	}
	
	/**
	 * Berechnet die nächste y-Koordinate in die angegebene {@link Ship.Direction}.
	 *
	 * @param dir Die {@link Ship.Direction} in die gegangen wird.
	 * @param y Die alte y-Koordinate.
	 * @return Eine neue y-Koordinate.
	 */
	protected int getNewYKoord(Ship.Direction dir, int y) {
		switch(dir) {
			case north:
				return y - 1;
			case south:
				return y + 1;
			default:
				return y;
		}
	}
}
