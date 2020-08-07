package ai;

import logic.Direction;
import logic.Map;

import java.util.Random;

/**
 * Die Klasse Mission wird ausschließlich von der {@link MediumAI} und der {@link HardAI} Klasse verwendet um ein
 * bereits gefundenes Schiff zu versenken.
 */
class Mission {
	private enum State {
		firstHit, someHits
	}
	
	private State state;
	private int lastXHit;
	private int lastYHit;
	private int startX;
	private int startY;
	private int nextX;
	private int nextY;
	private Direction lastHitDir;
	private Direction unsureDir;
	private Map map;
	
	/**
	 * Konstruktor zum initialisieren einer Mission.
	 *
	 * @param startX Die x Koordinate an der ein Schiff gefunden wurde.
	 * @param startY Die y Koordinate an der ein Schiff gefunden wurde.
	 * @param map Das eigene Spielfeld, um die Größe zu bestimmen.
	 * @param enemyMap Das Spielfeld des Gegners.
	 */
	public Mission(int startX, int startY, Map map, int[][] enemyMap) {
		this.startX = startX;
		this.startY = startY;
		lastXHit = startX;
		lastYHit = startY;
		
		this.map = map;
		
		state = State.firstHit;
		getNextPoint(true, enemyMap);
	}
	
	/**
	 * Berechnet den Punkt, auf den als nächstes geschossen werden soll.
	 *
	 * @param hit Gibt an, ob der letzte Schuss ein Treffer war.
	 * @param enemyMap Das Spielfeld des Gegners.
	 */
	private void getNextPoint(boolean hit, int[][] enemyMap) {
		if(state == State.firstHit) {
			Random rnd = new Random();
			do {
				unsureDir = Direction.values()[rnd.nextInt(Direction.values().length)];
			}while(!isValidDirection(unsureDir, startX, startY, enemyMap));
			nextX = getXInDirection(unsureDir, startX);
			nextY = getYInDirection(unsureDir, startY);
			lastXHit = nextX;
			lastYHit = nextY;
			return;
		}
		if(state == State.someHits) {
			if(hit) {
				if(isValidDirection(lastHitDir, lastXHit, lastYHit, enemyMap)) {
					nextX = getXInDirection(lastHitDir, lastXHit);
					nextY = getYInDirection(lastHitDir, lastYHit);
					lastXHit = nextX;
					lastYHit = nextY;
					return;
				}else {
					unsureDir = mirrorDirection(lastHitDir);
					if(isValidDirection(unsureDir, startX, startY, enemyMap)) {
						nextX = getXInDirection(unsureDir, startX);
						nextY = getYInDirection(unsureDir, startY);
						lastXHit = nextX;
						lastYHit = nextY;
						return;
					}
				}
			}else {
				unsureDir = mirrorDirection(lastHitDir);
				if(isValidDirection(unsureDir, startX, startY, enemyMap)) {
					nextX = getXInDirection(unsureDir, startX);
					nextY = getYInDirection(unsureDir, startY);
					lastXHit = nextX;
					lastYHit = nextY;
					return;
				}
			}
		}
	}
	
	/**
	 * Gibt zurück, ob es logisch sinnvoll ist, in diese Richtung zu schießen.
	 *
	 * @param dir Die Richtung in die geschossen werden soll.
	 * @param x Die x Koordinate auf die geschossen werden soll.
	 * @param y Die y Koordninate auf die geschossen werden soll.
	 * @param enemyMap Die Karte des Gegners.
	 * @return {@code true}, falls es logisch Sinn macht, sonst {@code false}.
	 */
	private boolean isValidDirection(Direction dir, int x, int y, int[][] enemyMap) {
		int newX = getXInDirection(dir, x);
		int newY = getYInDirection(dir, y);
		return map.isInMap(newX, newY) && enemyMap[newY][newX] == PlayableAI.NOT_SHOT;
	}
	
	/**
	 * Gibt die nächste x Koordiante in der Richtung an.
	 *
	 * @param dir Die Richtung in die gegangen werden soll.
	 * @param x Die x Koordinate von der aus gegangen werden soll.
	 * @return Die nächste x Koordinate in der gegebenen Richtung.
	 */
	private int getXInDirection(Direction dir, int x) {
		switch(dir) {
			case west:
				return x - 1;
			case east:
				return x + 1;
			default:
				return x;
		}
	}
	
	/**
	 * Gibt die nächste y Koordiante in der Richtung an.
	 *
	 * @param dir Die Rcihtung in die gegangen werden soll.
	 * @param y Die y Koordinate von der aus gegangen werden soll.
	 * @return Die nächste y Koordinate in der gegebenen Richtung.
	 */
	private int getYInDirection(Direction dir, int y) {
		switch(dir) {
			case north:
				return y - 1;
			case south:
				return y + 1;
			default:
				return y;
		}
	}
	
	/**
	 * Spiegelt die Richtung.
	 *
	 * @param dir Die zu spiegelnde Richtung.
	 * @return Die gespiegelte Richtung.
	 */
	private Direction mirrorDirection(Direction dir) {
		switch(dir) {
			case north:
				return Direction.south;
			case south:
				return Direction.north;
			case west:
				return Direction.east;
			case east:
				return Direction.west;
		}
		
		return null;
	}
	
	/**
	 * Gibt die x Koordinate des nächsten Schusses zurück.
	 *
	 * @return Die x Koordinate des nächsten Schusses.
	 */
	public int getNextX() {
		return nextX;
	}
	
	/**
	 * Gibt die y Koordinate des nächsten Schusses zurück.
	 *
	 * @return Die y Koordinate des nächsten Schusses.
	 */
	public int getNextY() {
		return nextY;
	}
	
	/**
	 * Bestimmt, ob der letzte Schuss ein treffer war. Muss, falls eine aktuelle Mission besteht, nach jedem Schuss
	 * aufgerufen werden.
	 *
	 * @param hit {@code true}, falls der Schuss ein Treffer war, sonst {@code false}.
	 * @param enemyMap Das Spielfeld des Gegners.
	 */
	public void wasHit(boolean hit, int[][] enemyMap) {
		if(hit && unsureDir != null) {
			lastHitDir = unsureDir;
			unsureDir = null;
		}
		if(state == State.firstHit && hit) {
			state = State.someHits;
		}
		getNextPoint(hit, enemyMap);
	}
}
