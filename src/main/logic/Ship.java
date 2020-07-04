package logic;

import java.util.Random;

/**
 * Modelliert ein Schiff.
 */
public class Ship {
	/**
	 * Die Richtung in die das Schiff schaut. Der Kopf des Schiffs schaut immer in dieser Richtung.
	 */
	public enum Direction {
		north,
		south,
		east,
		west
	}
	
	/**
	 * Mögliche Namen des Schiffs. Wird durch die Größe des Schiffs terminiert.
	 */
	private final String[] names = {"U-Boot", "Zerstörer", "Kreuzer", "Schlachtschiff"};
	
	/**
	 * x-Koordinate des Schiffs.
	 */
	private int xPos;
	/**
	 * y-Koordinate des Schiffs.
	 */
	private int yPos;
	/**
	 * Richtung in die das Schiff zeigt.
	 */
	private Direction direction;
	/**
	 * Größe des Schiffs.
	 */
	private final int size;
	/**
	 * Anzahl der bereits erlittenen Treffer.
	 */
	private int hits;
	
	/**
	 * @param xPos x-Koordinate des Schiffs.
	 * @param yPos y-Koordinate des Schiffs.
	 * @param direction Richtung in die das Schiff zeigt.
	 * @param size Größe des Schiffs.
	 */
	public Ship(int xPos, int yPos, Direction direction, int size) {
		hits = 0;
		
		this.xPos = xPos;
		this.yPos = yPos;
		this.direction = direction;
		this.size = size;
	}
	
	/**
	 * Erzeugt ein Standard-{@link Ship}.
	 *
	 * @param x x-Koordinate des Schiffs.
	 * @param y y-Koordinate des Schiffs.
	 * @return Ein bereits versenktes Schiff.
	 */
	public static Ship defaultShip(int x, int y) {
		return new Ship(x, y, Direction.north, 2);
	}
	
	/**
	 * Erzeugt ein {@link Ship} welches bereitzs versenkt wurde.
	 *
	 * @param x x-Koordinate des Schiffs.
	 * @param y y-Koordinate des Schiffs.
	 * @return Ein bereits versenktes Schiff.
	 */
	public static Ship defaultSunkenShip(int x, int y) {
		Ship ship = defaultShip(x, y);
		ship.hits = 2;
		return ship;
	}
	
	/**
	 * Copy-Konstruktor.
	 *
	 * @param ship Das zu kopierende Schiff.
	 */
	public Ship(Ship ship) {
		this(ship.xPos, ship.yPos, ship.direction, ship.size);
	}
	
	/**
	 * Gibt zurück, ob das Schiff bereits versenkt wurde.
	 *
	 * @return {@code true}, falls das Schiff noch nicht versenkt wurde. Sonst {@code false}.
	 */
	public boolean isAlive() {
		return hits < size;
	}
	
	/**
	 * Gibt den Namen des Schiffs zurück. Aus {@link #names}.
	 *
	 * @return Der Name des Schiffs.
	 */
	public String getName() {
		return names[size - 2];
	}
	
	/**
	 * @return Die x-Koordinate des Schiffs. (Koordinaten beziehen sich immer auf den Kopf des Schiffs)
	 */
	public int getXPos() {
		return xPos;
	}
	
	/**
	 * @return Die y-Koordinate des Schiffs. (Koordinaten beziehen sich immer auf den Kopf des Schiffs)
	 */
	public int getYPos() {
		return yPos;
	}
	
	/**
	 * @return Die richtung in die das Schiff zeigt.
	 */
	public Direction getDirection() {
		return direction;
	}
	
	/**
	 * @return Die Größe des Schiffs.
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * @return Die Anzahl der bereits erhaltenen Treffer.
	 */
	public int getHits() {
		return hits;
	}
	
	/**
	 * Setzt die x-Koordinate des Schiffs.
	 *
	 * @param xPos Neue x-Koordinate des Schiffs.
	 */
	public void setXPos(int xPos) {
		this.xPos = xPos;
	}
	
	/**
	 * Setzt die y-Koordinate des Schiffs.
	 *
	 * @param yPos Neue y-Koordinate des Schiffs.
	 */
	public void setYPos(int yPos) {
		this.yPos = yPos;
	}
	
	/**
	 * Wird aufgerufen, wenn das Schiff getroffen wird.
	 */
	public void hit() {
		hits += 1;
	}
	
	/**
	 * Hilfsmethode für {@link LocalPlayer#randomShipPlacment()}. Randomisiert alle Attribute des Schiffs.
	 *
	 * @param mapSize Die größe des Spielfelds.
	 */
	public void randomize(int mapSize) {
		Random rnd = new Random();
		xPos = rnd.nextInt(mapSize);
		yPos = rnd.nextInt(mapSize);
		direction = Direction.values()[rnd.nextInt(Direction.values().length)];
	}
	
	/**
	 * Vergleicht ein Schiff mit einem anderen Objekt.
	 *
	 * @param obj Das zu vergleichende Objekt.
	 * @return {@code true}, falls {@code obj} ein Schiff ist und es diesem Schiff entspricht. Sonst {@code false}.
	 */
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Ship)) return false;
		Ship s = (Ship) obj;
		return (s.xPos == xPos && s.yPos == yPos && s.direction == direction && s.size == size && s.hits == hits);
	}
	
	/**
	 * Hasht das Schiff.
	 *
	 * @return Der Hash des Schiffs.
	 */
	@Override
	public int hashCode() {
		return ((xPos + size + yPos) * direction.hashCode());
	}
	
	/**
	 * Gibt eine String-Repräsentation des Schiffs zurück.
	 *
	 * @return Die String -Repräsentation des Schiffs.
	 */
	@Override
	public String toString() {
		return getName();
	}
}

