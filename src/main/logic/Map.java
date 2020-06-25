package logic;

import java.awt.font.LineBreakMeasurer;

/**
 * Die Klasse Map modelliert das Spielfeld eines {@link LocalPlayer}.
 */
public class Map {
	/**
	 * Die Datenstruktur zur modellierung des Spielfelds.
	 */
	private MapTile[][] map;
	/**
	 * Die Anzahl an noch nicht zerstörten Schiffen.
	 */
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
	 * Gibt das Spielfeld auf der Konsole aus.
	 */
	public void dump() {
		for(MapTile[] mapTiles : map) {
			System.out.println("|");
			for(MapTile tile : mapTiles) {
				String s = "";
				switch(tile.stat) {
					case MapTile.SHIP:
						s = "S";
						break;
					case MapTile.WATER:
						s = "~";
						break;
					case MapTile.UNSUCC_HIT:
						s = "/";
						break;
					case MapTile.DEFINITELY_NO_SHIP:
						s = "#";
						break;
					case MapTile.SUCC_HIT:
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
		if(map[y][x].stat == MapTile.WATER) {
			map[y][x].stat = MapTile.UNSUCC_HIT;
			return null;
		}else if(map[y][x].stat == MapTile.SHIP) {
			map[y][x].stat = MapTile.SUCC_HIT;
			map[y][x].ship.hit();
			
			if(!map[y][x].ship.isAlive()) {
				shipsNr -= 1;
				Ship ship = map[y][x].ship;
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
							map[start][sx].stat = MapTile.DEFINITELY_NO_SHIP;
						}
						//Kucken ob unten Platz ist
						if(end < map.length - 1) {
							end -= 1;
							map[end][sx].stat = MapTile.DEFINITELY_NO_SHIP;
						}
						//Kucken ob links Platz ist
						if(sx > 0) {
							//Links setzen, wenn oben Platz ist, auch oben linkts, wenn unten Platz ist auch unten links
							for(start = start; start <= end; start++) map[start][sx - 1].stat = MapTile.DEFINITELY_NO_SHIP;
						}
						//Kucken ob rechts Platz ist
						if(sx < map.length - 1) {
							//Rechts setzen, wenn oben Platz ist, auch oben rechts, wenn unten Platz ist auch unten rechts
							for(start = start; start <= end; start++) map[start][sx + 1].stat = MapTile.DEFINITELY_NO_SHIP;
						}
						break;
					case south:
						start = sy;
						end = (sy - ship.getSize()) + 1;
						//Kucken on oben Platz ist
						if(start < map.length - 1) {
							start += 1;
							map[start][sx].stat = MapTile.DEFINITELY_NO_SHIP;
						}
						//Kucken ob unten Platz ist
						if(end > 0) {
							end -= 1;
							map[end][sx].stat = MapTile.DEFINITELY_NO_SHIP;
						}
						//Kucken ob links Platz ist
						if(sx > 0) {
							//Links setzen, wenn oben Platz ist, auch oben linkts, wenn unten Platz ist auch unten links
							for(start = start; start <= end; start--) map[start][sx - 1].stat = MapTile.DEFINITELY_NO_SHIP;
						}
						//Kucken ob rechts Platz ist
						if(sx < map.length - 1) {
							//Rechts setzen, wenn oben Platz ist, auch oben rechts, wenn unten Platz ist auch unten rechts
							for(start = start; start <= end; start--) map[start][sx + 1].stat = MapTile.DEFINITELY_NO_SHIP;
						}
						break;
					case west:
						start = sx;
						end = (sx + ship.getSize()) - 1;
						//Kucken ob links Platz ist und links mittig setzen
						if(start > 0) {
							start -= 1;
							map[sy][start].stat = MapTile.DEFINITELY_NO_SHIP;
						}
						//Kucken ob rechts Platz ist und rechts mittig setzen
						if(end < map.length - 1) {
							end += 1;
							map[sy][end].stat = MapTile.DEFINITELY_NO_SHIP;
						}
						//Kucken ob oben Platz ist
						if(sy > 0) {
							//Oben setzen, wenn links Platz ist, auch oben linkts, wenn rechts Platz ist auch oben Rechts
							for(start = start; start <= end; start++) map[sy - 1][start].stat = MapTile.DEFINITELY_NO_SHIP;
						}
						//Kucken ob unten Platz ist
						if(sy < map.length - 1) {
							//Unten setzen, wenn links Platz ist, auch oben linkts, wenn rechts Platz ist auch oben Rechts
							for(start = start; start <= end; start++) map[sy + 1][start].stat = MapTile.DEFINITELY_NO_SHIP;
						}
						break;
					case east:
						start = sx;
						end = (sx - ship.getSize()) + 1;
						//Kucken ob rechts Platz ist und links mittig setzen
						if(start < map.length - 1) {
							start += 1;
							map[sy][start].stat = MapTile.DEFINITELY_NO_SHIP;
						}
						//Kucken ob links Platz ist und rechts mittig setzen
						if(end > 0) {
							end -= 1;
							map[sy][end].stat = MapTile.DEFINITELY_NO_SHIP;
						}
						//Kucken ob oben Platz ist
						if(sy > 0) {
							//Oben setzen, wenn links Platz ist, auch oben linkts, wenn rechts Platz ist auch oben Rechts
							for(start = start; start <= end; start--) map[sy - 1][start].stat = MapTile.DEFINITELY_NO_SHIP;
						}
						//Kucken ob unten Platz ist
						if(sy < map.length - 1) {
							//Unten setzen, wenn links Platz ist, auch unten linkts, wenn rechts Platz ist auch unten Rechts
							for(start = start; start <= end; start--) map[sy + 1][start].stat = MapTile.DEFINITELY_NO_SHIP;
						}
						break;
				}
			}
			
			return map[y][x].ship;
		}else if(map[y][x].stat == MapTile.SUCC_HIT) {
			return null;
		}else if(map[y][x].stat == MapTile.UNSUCC_HIT) {
			return null;
		}else if(map[y][x].stat == MapTile.DEFINITELY_NO_SHIP) {
			return null;
		}
		return null;
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
					map[ship.getYPos()][ship.getXPos() - i].stat = MapTile.SHIP;
					map[ship.getYPos()][ship.getXPos() - i].ship = ship;
				}
				break;
			case west:
				for(int i = 0; i < ship.getSize(); i++) {
					map[ship.getYPos()][ship.getXPos() + i].stat = MapTile.SHIP;
					map[ship.getYPos()][ship.getXPos() + i].ship = ship;
				}
				break;
			case north:
				for(int i = 0; i < ship.getSize(); i++) {
					map[ship.getYPos() + i][ship.getXPos()].stat = MapTile.SHIP;
					map[ship.getYPos() + i][ship.getXPos()].ship = ship;
				}
				break;
			case south:
				for(int i = 0; i < ship.getSize(); i++) {
					map[ship.getYPos() - i][ship.getXPos()].stat = MapTile.SHIP;
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
					map[ship.getYPos()][ship.getXPos() - i].stat = MapTile.WATER;
					map[ship.getYPos()][ship.getXPos() - i].ship = null;
				}
				break;
			case west:
				for(int i = 0; i < ship.getSize(); i++) {
					map[ship.getYPos()][ship.getXPos() + i].stat = MapTile.WATER;
					map[ship.getYPos()][ship.getXPos() + i].ship = null;
				}
				break;
			case north:
				for(int i = 0; i < ship.getSize(); i++) {
					map[ship.getYPos() + i][ship.getXPos()].stat = MapTile.WATER;
					map[ship.getYPos() + i][ship.getXPos()].ship = null;
				}
				break;
			case south:
				for(int i = 0; i < ship.getSize(); i++) {
					map[ship.getYPos() - i][ship.getXPos()].stat = MapTile.WATER;
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
				if(x < 0 || x >= map.length)
					return false;
				if(y < 0 || y + shipSize - 1 >= map.length)
					return false;
				
				try {
					if(y - 1 >= 0 && map[y - 1][x].stat == MapTile.SHIP)
						return false;
					if(y + shipSize < map.length && map[y + shipSize][x].stat == MapTile.SHIP)
						return false;
					
					if(y - 1 >= 0 && map[y - 1][x + 1].stat == MapTile.SHIP && map[y - 1][x - 1].stat == MapTile.SHIP) {
						return false;
					}
					if(y + shipSize < map.length && map[y + shipSize][x + 1].stat == MapTile.SHIP && map[y + shipSize][x - 1].stat == MapTile.SHIP) {
						return false;
					}
					
					for(int i = 0; i < shipSize; i++) {
						for(int j = 0; j < 2; j++) {
							if(x + j < map.length && map[y + i][x + j].stat == MapTile.SHIP)
								return false;
							if(x - j >= 0 && map[y + i][x - j].stat == MapTile.SHIP)
								return false;
						}
					}
				}catch(ArrayIndexOutOfBoundsException e) {
					// System.out.println("Yes schon wieder Codingarbeit gespart hehe :)");
				}
				break;
			case south:
				if(x < 0 || x >= map.length)
					return false;
				if(y - shipSize + 1 < 0 || y >= map.length)
					return false;
				
				try {
					if(y - shipSize >= 0 && map[y - shipSize][x].stat == MapTile.SHIP)
						return false;
					if(y + 1 < map.length && map[y + 1][x].stat == MapTile.SHIP)
						return false;
					
					if(y - 1 >= 0 && map[y + 1][x + 1].stat == MapTile.SHIP && map[y + 1][x - 1].stat == MapTile.SHIP) {
						return false;
					}
					if(y + 1 < map.length && map[y + 1][x + 1].stat == MapTile.SHIP && map[y + 1][x - 1].stat == MapTile.SHIP) {
						return false;
					}
					for(int i = 0; i < shipSize; i++) {
						for(int j = 0; j < 2; j++) {
							if(x + j < map.length && map[y - i][x + j].stat == MapTile.SHIP)
								return false;
							if(x - j >= 0 && map[y - i][x - j].stat == MapTile.SHIP)
								return false;
						}
					}
				}catch(ArrayIndexOutOfBoundsException e) {
					// System.out.println("Yes schon wieder Codingarbeit gespart hehe :)");
				}
				break;
			case west:
				if(x < 0 || x + shipSize - 1 >= map.length)
					return false;
				if(y < 0 || y >= map.length)
					return false;
				
				try {
					if(x - 1 >= 0 && map[y][x - 1].stat == MapTile.SHIP)
						return false;
					if(x + shipSize < map.length && map[y][x + shipSize].stat == MapTile.SHIP)
						return false;
					
					if(x - 1 >= 0 && map[y + 1][x - 1].stat == MapTile.SHIP && map[y - 1][x - 1].stat == MapTile.SHIP) {
						return false;
					}
					if(x + shipSize < map.length && map[y + 1][x + shipSize].stat == MapTile.SHIP && map[y - 1][x + shipSize].stat == MapTile.SHIP) {
						return false;
					}
					for(int i = 0; i < shipSize; i++) {
						for(int j = 0; j < 2; j++) {
							if(y + j < map.length && map[y + j][x + i].stat == MapTile.SHIP)
								return false;
							if(y - j >= 0 && map[y - j][x + i].stat == MapTile.SHIP)
								return false;
						}
					}
				}catch(ArrayIndexOutOfBoundsException e) {
					// System.out.println("Yes schon wieder Codingarbeit gespart hehe :)");
				}
				break;
			case east:
				if(x - shipSize + 1 < 0 || x >= map.length)
					return false;
				if(y < 0 || y >= map.length)
					return false;
				
				try {
					if(x - shipSize >= 0 && map[y][x - shipSize].stat == MapTile.SHIP)
						return false;
					if(x + 1 < map.length && map[y][x + 1].stat == MapTile.SHIP)
						return false;
					
					if(x - shipSize >= 0 && map[y + 1][x - shipSize].stat == MapTile.SHIP && map[y - 1][x - shipSize].stat == MapTile.SHIP) {
						return false;
					}
					if(x + 1 < map.length && map[y + 1][x + 1].stat == MapTile.SHIP && map[y - 1][x + 1].stat == MapTile.SHIP) {
						return false;
					}
					for(int i = 0; i < shipSize; i++) {
						for(int j = 0; j < 2; j++) {
							if(y + j < map.length && map[y + j][x - i].stat == MapTile.SHIP)
								return false;
							if(y - j >= 0 && map[y - j][x - i].stat == MapTile.SHIP)
								return false;
						}
					}
				}catch(ArrayIndexOutOfBoundsException e) {
					// System.out.println("Yes schon wieder Codingarbeit gespart hehe :)");
				}
				break;
		}
		
		return true;
		//TODO
	}
	
	/**
	 * Setzt das Spielfeld auf den Anfangszustand zurück. (Auf jedem Feld Wasser)
	 */
	public void reset() {
		map = new MapTile[map.length][map.length];
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map.length; j++) {
				map[i][j].stat = MapTile.WATER;
				map[i][j].ship = null;
			}
		}
	}
	
	public boolean isInMap(int x, int y) {
		return x >= 0 && x < map.length && y >= 0 && y < map.length;
	}
	
	/**
	 * Nach Außen nicht sichtbare Hilfsklasse zur modellierung des Spielfelds.
	 */
	static class MapTile {
		/**
		 * Konstante zum Anzeigen von Wasser.
		 */
		public static final int WATER = 0;
		/**
		 * Konstante zum Anzeigen eines Schiffs.
		 */
		public static final int SHIP = 1;
		/**
		 * Konstante zum Anzeigen eines erfolgreichen Treffers.
		 */
		public static final int SUCC_HIT = 2;
		/**
		 * Konstante zum Anzeigen eines nicht erfolgreichen Treffers.
		 */
		public static final int UNSUCC_HIT = 3;
		/**
		 * Konstante zum Anzeigen des Bereichs um ein bereits versenktes Schiff. Dort kann auf Grund der Regeln devinitiv
		 * kein Schiff liegen.
		 */
		public static final int DEFINITELY_NO_SHIP = 4;
		/**
		 * Zeigt an was sich auf dem Feld befindet. Entweder {@value WATER}, {@value SHIP}, {@value SUCC_HIT},{@value
		 * UNSUCC_HIT}, oder {@value DEFINITELY_NO_SHIP}.
		 */
		private int stat;
		/**
		 * Falls {@link #stat} {@value SHIP}, oder {@value SUCC_HIT} ist, wird das konkrete {@link Ship} auf diesem Platz
		 * referenziert.
		 */
		private Ship ship;
		
		/**
		 * Erstellt einen Platz, der nur Wasser enthält.
		 */
		private MapTile() {
			stat = WATER;
			ship = null;
		}
	}
}
