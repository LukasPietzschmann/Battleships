package logic;

import java.util.ArrayList;

/**
 * Die Klasse Map modelliert das Spielfeld eines {@link LocalPlayer}.
 */
public class Map {
	public static final int WATER = 0;
	public static final int SHIP = 1;
	public static final int SUCC_HIT = 2;
	public static final int UNSUCC_HIT = 3;
	public static final int DEFINITELY_NO_SHIP = 4;
	private MapTile[][] map;
	private int shipsNr;

	/**
	 * Erstellt ein Spielfeld mit der spezifizierten Größe.
	 *
	 * @param size Die Größe des Spielfelds.
	 */
	public Map(int size) {
		map = new MapTile[size][size];
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				map[i][j] = new MapTile();
			}
		}
	}
	
	/**
	 * Copy-Constructor
	 * @param map Die zu kopierende Map.
	 */
	public Map(Map map){
		this.shipsNr = map.shipsNr;
		this.map = new MapTile[map.getSize()][map.getSize()];
		for(int i = 0; i < this.map.length; i++) {
			for(int j = 0; j < this.map.length; j++) {
				this.map[i][j] = new MapTile(map.map[i][j]);
			}
		}
	}

	/**
	 * Gibt das Spielfeld auf der Konsole aus.
	 */
	public void dump() {
		for(MapTile[] mapTiles : map) {
			System.out.print("|");
			for(MapTile tile : mapTiles) {
				String s = "";
				switch(tile.stat) {
					case SHIP:
						s = "S";
						break;
					case WATER:
						s = "~";
						break;
					case UNSUCC_HIT:
						s = "/";
						break;
					case DEFINITELY_NO_SHIP:
						s = "#";
						break;
					case SUCC_HIT:
						s = "X";
						break;
				}
				System.out.print(String.format("%s|", s));
			}
			System.out.print("\n");
		}
	}

	/**
	 * @return die Größe des Spielfelds.
	 */
	public int getSize() {
		return map.length;
	}

	/**
	 * Schießt auf ein spezifiziertes Feld der Map.
	 *
	 * @param x Die x-Koordinate des Schusses.
	 * @param y Die y-Koordinate des Schusses.
	 * @return {@code null}, falls nicht getroffen wurde. Das konkrete {@link Ship}, falls getroffen wurde.
	 */
	public Ship hit(int x, int y) {
		if(map[y][x].stat == WATER) {
			map[y][x].stat = UNSUCC_HIT;
			return null;
		}else if(map[y][x].stat == SHIP) {
			map[y][x].stat = SUCC_HIT;
			map[y][x].ship.hit();

			if(!map[y][x].ship.isAlive()) {
				shipsNr -= 1;
				setDefinitelyNoShip(map[y][x].ship);
			}

			return map[y][x].ship;
		}else if(map[y][x].stat == SUCC_HIT) {
			return null;
		}else if(map[y][x].stat == UNSUCC_HIT) {
			return null;
		}else if(map[y][x].stat == DEFINITELY_NO_SHIP) {
			return null;
		}
		return null;
	}
	
	/**
	 * Umrandet ein versenktes Schiff mit Markierungen.
	 * @param ship Das zu umrandende Schiff.
	 */
	private void setDefinitelyNoShip(Ship ship){
		int sx, sy, start, end;
		sx = ship.getXPos();
		sy = ship.getYPos();
		
		switch(ship.getDirection()) {
			case north:
				start = sy;
				end = (sy + ship.getSize()) - 1;
				//Kucken on oben Platz ist
				if(start > 0) {
					start -= 1;
					map[start][sx].stat = DEFINITELY_NO_SHIP;
				}
				//Kucken ob unten Platz ist
				if(end < map.length - 1) {
					end += 1;
					map[end][sx].stat = DEFINITELY_NO_SHIP;
				}
				//Kucken ob links Platz ist
				if(sx > 0) {
					//Links setzen, wenn oben Platz ist, auch oben linkts, wenn unten Platz ist auch unten links
					for(int i = start; i <= end; i++) map[i][sx - 1].stat = DEFINITELY_NO_SHIP;
				}
				//Kucken ob rechts Platz ist
				if(sx < map.length - 1) {
					//Rechts setzen, wenn oben Platz ist, auch oben rechts, wenn unten Platz ist auch unten rechts
					for(int i = start; i <= end; i++) map[i][sx + 1].stat = DEFINITELY_NO_SHIP;
				}
				break;
			case south:
				start = sy;
				end = (sy - ship.getSize()) + 1;
				//Kucken on oben Platz ist
				if(start < map.length - 1) {
					start += 1;
					map[start][sx].stat = DEFINITELY_NO_SHIP;
				}
				//Kucken ob unten Platz ist
				if(end > 0) {
					end -= 1;
					map[end][sx].stat = DEFINITELY_NO_SHIP;
				}
				//Kucken ob links Platz ist
				if(sx > 0) {
					//Links setzen, wenn oben Platz ist, auch oben linkts, wenn unten Platz ist auch unten links
					for(int i = start; i >= end; i--) map[i][sx - 1].stat = DEFINITELY_NO_SHIP;
				}
				//Kucken ob rechts Platz ist
				if(sx < map.length - 1) {
					//Rechts setzen, wenn oben Platz ist, auch oben rechts, wenn unten Platz ist auch unten rechts
					for(int i = start; i >= end; i--) map[i][sx + 1].stat = DEFINITELY_NO_SHIP;
				}
				break;
			case west:
				start = sx;
				end = (sx + ship.getSize()) - 1;
				//Kucken ob links Platz ist und links mittig setzen
				if(start > 0) {
					start -= 1;
					map[sy][start].stat = DEFINITELY_NO_SHIP;
				}
				//Kucken ob rechts Platz ist und rechts mittig setzen
				if(end < map.length - 1) {
					end += 1;
					map[sy][end].stat = DEFINITELY_NO_SHIP;
				}
				//Kucken ob oben Platz ist
				if(sy > 0) {
					//Oben setzen, wenn links Platz ist, auch oben linkts, wenn rechts Platz ist auch oben Rechts
					for(int i = start; i <= end; i++) map[sy - 1][i].stat = DEFINITELY_NO_SHIP;
				}
				//Kucken ob unten Platz ist
				if(sy < map.length - 1) {
					//Unten setzen, wenn links Platz ist, auch oben linkts, wenn rechts Platz ist auch oben Rechts
					for(int i = start; i <= end; i++) map[sy + 1][i].stat = DEFINITELY_NO_SHIP;
				}
				break;
			case east:
				start = sx;
				end = (sx - ship.getSize()) + 1;
				//Kucken ob rechts Platz ist und links mittig setzen
				if(start < map.length - 1) {
					start += 1;
					map[sy][start].stat = DEFINITELY_NO_SHIP;
				}
				//Kucken ob links Platz ist und rechts mittig setzen
				if(end > 0) {
					end -= 1;
					map[sy][end].stat = DEFINITELY_NO_SHIP;
				}
				//Kucken ob oben Platz ist
				if(sy > 0) {
					//Oben setzen, wenn links Platz ist, auch oben linkts, wenn rechts Platz ist auch oben Rechts
					for(int i = start; i >= end; i--) map[sy - 1][i].stat = DEFINITELY_NO_SHIP;
				}
				//Kucken ob unten Platz ist
				if(sy < map.length - 1) {
					//Unten setzen, wenn links Platz ist, auch unten linkts, wenn rechts Platz ist auch unten Rechts
					for(int i = start; i >= end; i--) map[sy + 1][i].stat = DEFINITELY_NO_SHIP;
				}
				break;
		}
	}
	
	/**
	 * Markiert die Zelle auf die geschossen wurde,
	 * @param x Die x Korrdinate der Zelle.
	 * @param y Die y Korrdinate der Zelle.
	 * @param succ {@code true}, falls getroffen wurde. Sonst {@code false}.
	 */
	public void setHit(int x, int y, boolean succ){
		//map[y][x].stat = succ ? SUCC_HIT : UNSUCC_HIT;
		if(succ) {
			map[y][x].stat = SUCC_HIT;
			if(map[y][x].ship == null) map[y][x].ship = Ship.defaultShip(x,y);
		}else map[y][x].stat = UNSUCC_HIT;
	}
	
	/**
	 * Umrandet ein Schiff.
	 * @param x Eine x Koordinate des versenkten Schiffs.
	 * @param y Eine y Koordinate des versenkten Schiffs.
	 */
	public void surroundShip(int x, int y){
		ArrayList<int[]> points = new ArrayList<>();
		recFindShip(x,y, null, points);
		
		Ship ship;
		//ist x koordinate gleich
		if(points.get(0)[0] == points.get(1)[0]){
			int min = Integer.MAX_VALUE;
			for(int[] point : points) if(point[1] < min) min = point[1];
			ship = new Ship(points.get(0)[0], min, Direction.north, points.size());
		}else/*ist y Koordinate gleich*/{
			int min = Integer.MAX_VALUE;
			for(int[] point : points) if(point[0] < min) min = point[0];
			ship = new Ship(min, points.get(0)[1], Direction.west, points.size());
		}
		
		setDefinitelyNoShip(ship);
	}
	
	/**
	 * Hilfsmethode für {@link #surroundShip(int, int)} zum finden aller Koordinaten eines Schiffs.
	 * @param x Aktuelle x Koordinate.
	 * @param y Aktuelle y Koordinate.
	 * @param from Die Richtung aus der man kommt.
	 * @param result Liste aller gefundenen Koordinaten.
	 */
	private void recFindShip(int x, int y, Direction from, ArrayList<int[]> result){
		result.add(new int[]{x, y});
		//oben kucken
		if(isInMap(x, y - 1) && map[y - 1][x].ship != null && from != Direction.north){
			recFindShip(x,y - 1, Direction.south, result);
		}
		
		//unten kucken
		if(isInMap(x, y + 1) && map[y + 1][x].ship != null && from != Direction.south){
			recFindShip(x,y + 1, Direction.north, result);
		}
		
		//links kucken
		if(isInMap(x - 1, y) && map[y][x - 1].ship != null && from != Direction.west){
			recFindShip(x - 1,y, Direction.east, result);
		}
		
		//rechts kucken
		if(isInMap(x + 1, y) && map[y][x + 1].ship != null && from != Direction.east){
			recFindShip(x + 1,y, Direction.west, result);
		}
	}

	/**
	 * Gibt die Anzahl der noch nicht versenkten Schiffe zurück.
	 *
	 * @return Anzahl der noch nicht versenkten Schiffe.
	 */
	public int shipsNr() {
		return shipsNr;
	}

	/**
	 * Gibt das Schiff an der spezifizierten Position zurück.
	 *
	 * @param x Die x-Koordinate des Schusses.
	 * @param y Die y-Koordinate des Schusses.
	 * @return {@code null}, falls an dieser Stelle kein Schiff vorhanden ist. Sonst das konkrete {@link Ship}.
	 */
	public Ship getShip(int x, int y) {
		return map[y][x].ship;
	}
	
	/**
	 * Gibt den Zustand der Zelle zurück.
	 * @param x x Koordinate der Zelle.
	 * @param y y Koordinate der Zelle.
	 * @return Der Zustand der Zelle.
	 */
	public int getStat(int x, int y) {
		return map[y][x].stat;
	}

	/**
	 * Platziert ein Schiff auf der Karte. Es wird nicht überprüft, ob das platzieren dieses Schiffs möglich, oder erlaubt
	 * ist. Das muss der Benutzer selbst mit {@link #canShipBePlaced(Ship)} überprüfen.
	 *
	 * @param ship Das zu platzierende Schiff.
	 */
	public void placeShip(Ship ship) {
		shipsNr++;
		switch(ship.getDirection()) {
			case east:
				for(int i = 0; i < ship.getSize(); i++) {
					map[ship.getYPos()][ship.getXPos() - i].stat = SHIP;
					map[ship.getYPos()][ship.getXPos() - i].ship = ship;
				}
				break;
			case west:
				for(int i = 0; i < ship.getSize(); i++) {
					map[ship.getYPos()][ship.getXPos() + i].stat = SHIP;
					map[ship.getYPos()][ship.getXPos() + i].ship = ship;
				}
				break;
			case north:
				for(int i = 0; i < ship.getSize(); i++) {
					map[ship.getYPos() + i][ship.getXPos()].stat = SHIP;
					map[ship.getYPos() + i][ship.getXPos()].ship = ship;
				}
				break;
			case south:
				for(int i = 0; i < ship.getSize(); i++) {
					map[ship.getYPos() - i][ship.getXPos()].stat = SHIP;
					map[ship.getYPos() - i][ship.getXPos()].ship = ship;
				}
				break;
		}
	}

	/**
	 * Entfernt ein Schiff aus der Map. Überprüft nicht, ob das Schiff tatsächlich auf der Karte vorhanden ist.
	 *
	 * @param ship Das zu löschende Schiff.
	 */
	public void removeShip(Ship ship) {
		shipsNr--;
		switch(ship.getDirection()) {
			case east:
				for(int i = 0; i < ship.getSize(); i++) {
					map[ship.getYPos()][ship.getXPos() - i].stat = WATER;
					map[ship.getYPos()][ship.getXPos() - i].ship = null;
				}
				break;
			case west:
				for(int i = 0; i < ship.getSize(); i++) {
					map[ship.getYPos()][ship.getXPos() + i].stat = WATER;
					map[ship.getYPos()][ship.getXPos() + i].ship = null;
				}
				break;
			case north:
				for(int i = 0; i < ship.getSize(); i++) {
					map[ship.getYPos() + i][ship.getXPos()].stat = WATER;
					map[ship.getYPos() + i][ship.getXPos()].ship = null;
				}
				break;
			case south:
				for(int i = 0; i < ship.getSize(); i++) {
					map[ship.getYPos() - i][ship.getXPos()].stat = WATER;
					map[ship.getYPos() - i][ship.getXPos()].ship = null;
				}
				break;
		}
	}

	/**
	 * Überprüft, ob das Schiff regelkonform auf dem Spielfeld platziert werden darf. Muss vor der verwendung von {@link
	 * #placeShip(Ship)} aufgerufen werden.
	 *
	 * @param ship Das zu überprüfende Schiff.
	 * @return {@code true}, falls das Schiff platziert werden darf. Sonst {@code false}.
	 */
	public boolean canShipBePlaced(Ship ship) {
		int x = ship.getXPos();
		int y = ship.getYPos();
		int shipSize = ship.getSize();

		switch(ship.getDirection()) {
			case north:
				for(int i = 0; i < shipSize; i++) if(!isInMap(x, y + i)|| map[y + i][x].stat == SHIP) return false;
				for(int i = 0; i < shipSize; i++) {
					//links
					try {
						if(map[y + i][x - 1].stat == SHIP) return false;
					}catch(ArrayIndexOutOfBoundsException e) {
						break;
					}
				}
				for(int i = 0; i < shipSize; i++) {
					//rechts
					try {
						if(map[y + i][x + 1].stat == SHIP) return false;
					}catch(ArrayIndexOutOfBoundsException e) {
						break;
					}
				}
				for(int i = -1; i <= 1; i++) {
					//oben
					try {
						if(map[y - 1][x + i].stat == SHIP) return false;
					}catch(ArrayIndexOutOfBoundsException e) {
						continue;
					}
				}
				for(int i = -1; i <= 1; i++) {
					//unten
					try {
						if(map[y + shipSize][x + i].stat == SHIP) return false;
					}catch(ArrayIndexOutOfBoundsException e) {
						continue;
					}
				}
				break;
			case south:
				for(int i = 0; i < shipSize; i++) if(!isInMap(x, y - i)|| map[y - i][x].stat == SHIP) return false;
				for(int i = 0; i < shipSize; i++) {
					//links
					try {
						if(map[y - i][x - 1].stat == SHIP) return false;
					}catch(ArrayIndexOutOfBoundsException e) {
						break;
					}
				}
				for(int i = 0; i < shipSize; i++) {
					//rechts
					try {
						if(map[y - i][x + 1].stat == SHIP) return false;
					}catch(ArrayIndexOutOfBoundsException e) {
						break;
					}
				}
				for(int i = -1; i <= 1; i++) {
					//unten
					try {
						if(map[y + 1][x + i].stat == SHIP) return false;
					}catch(ArrayIndexOutOfBoundsException e) {
						continue;
					}
				}
				for(int i = -1; i <= 1; i++) {
					//oben
					try {
						if(map[y - shipSize][x + i].stat == SHIP) return false;
					}catch(ArrayIndexOutOfBoundsException e) {
						continue;
					}
				}
				break;
			case west:
				for(int i = 0; i < shipSize; i++) if(!isInMap(x + i, y)|| map[y][x + i].stat == SHIP) return false;
				for(int i = 0; i < shipSize; i++) {
					//oben
					try {
						if(map[y - 1][x + i].stat == SHIP) return false;
					}catch(Exception e) {
						break;
					}
				}
				for(int i = 0; i < shipSize; i++) {
					//unten
					try {
						if(map[y + 1][x + i].stat == SHIP) return false;
					}catch(Exception e) {
						break;
					}
				}
				for(int i = -1; i <= 1; i++) {
					//links
					try {
						if(map[y + i][x - 1].stat == SHIP) return false;
					}catch(Exception e) {
						continue;
					}
				}
				for(int i = -1; i <= 1; i++) {
					//rechts
					try {
						if(map[y + i][x + shipSize].stat == SHIP) return false;
					}catch(Exception e) {
						continue;
					}
				}
				break;
			case east:
				for(int i = 0; i < shipSize; i++) if(!isInMap(x - i, y) || map[y][x - i].stat == SHIP) return false;
				for(int i = 0; i < shipSize; i++) {
					//oben
					try {
						if(map[y - 1][x - i].stat == SHIP) return false;
					}catch(Exception e) {
						break;
					}
				}
				for(int i = 0; i < shipSize; i++) {
					//unten
					try {
						if(map[y + 1][x - i].stat == SHIP) return false;
					}catch(Exception e) {
						break;
					}
				}
				for(int i = -1; i <= 1; i++) {
					//rechts
					try {
						if(map[y + i][x + 1].stat == SHIP) return false;
					}catch(Exception e) {
						continue;
					}
				}
				for(int i = -1; i <= 1; i++) {
					//links
					try {
						if(map[y + i][x - shipSize].stat == SHIP) return false;
					}catch(Exception e) {
						continue;
					}
				}
				break;
		}

		return true;
	}

	/**
	 * Setzt das Spielfeld auf den Anfangszustand zurück. (Auf jedem Feld Wasser)
	 */
	public void reset() {
		shipsNr = 0;
		map = new MapTile[map.length][map.length];
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map.length; j++) {
				map[i][j] = new MapTile();
				map[i][j].stat = WATER;
				map[i][j].ship = null;
			}
		}
	}
	
	/**
	 * Gibt an ob sich Koordinaten innerhalb des Spielfelds befinden.
	 * @param x Die zu untersuchende x Koordinate.
	 * @param y Die zu untersuchende y Koordinate.
	 * @return {@code true}, falls die Koordinate innerhalb des Spielfelds ist. Sonst {@code false}.
	 */
	public boolean isInMap(int x, int y) {
		return x >= 0 && x < map.length && y >= 0 && y < map.length;
	}
	
	/**
	 * Gibt das Spielfelds des Gegners zurück, ohne die Position der Schiffe preiszugeben.
	 * @return Das Spielfeld des Gegners.
	 */
	public Map getEnemyPerspective(){
		Map enemyMap = new Map(this);
		for(int i = 0; i < enemyMap.map.length; i++) {
			for(int j = 0; j < enemyMap.map.length; j++) {
				if(enemyMap.map[i][j].stat == SHIP) {
					enemyMap.map[i][j].stat = WATER;
				}
			}
		}
		
		return enemyMap;
	}
	
	/**
	 * Gibt die aktuelle Anzahl der noch nicht versenkten Schiffe zurück.
	 * @return Die aktuelle Anzahl der noch nicht versenkten Schiffe.
	 */
	public int getShipsNr() {
		return shipsNr;
	}
	
	/**
	 * Nach Außen nicht sichtbare Hilfsklasse zur modellierung des Spielfelds.
	 */
	static class MapTile {
		private int stat;
		private Ship ship;

		/**
		 * Erstellt einen Platz, der nur Wasser enthält.
		 */
		private MapTile() {
			stat = WATER;
			ship = null;
		}
		
		/**
		 * Copy-Constructor
		 * @param tile Das zu kopierende MapTile.
		 */
		private MapTile(MapTile tile){
			this.stat = tile.stat;
			this.ship = tile.ship;
		}
	}
}
