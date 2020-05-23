package logic;

public class Map {
	private MapTile[][] map;
	
	public void Map(int size) {
		map = new MapTile[size][size];
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				map[i][j] = new MapTile();
			}
		}
	}
	
	public void dump() {
		for(MapTile[] mapTiles : map) {
			System.out.println("|");
			for(int j = 0; j < mapTiles.length; j++) {
				String s = "";
				switch(mapTiles[j].stat) {
					case MapTile.SHIP:
						s = "S";
						break;
					case MapTile.WATER:
						s = "~";
						break;
					case MapTile.UNSUCCHIT:
						s = "/";
						break;
					case MapTile.DEFINITELYNOSHIP:
						s = "#";
						break;
				}
				System.out.print(String.format("%s|", s));
			}
			System.out.print("\n");
		}
	}
	
	public int getSize() {
		return map.length;
	}
	
	//public MapStat getStat(int a, int b) {
	
	//}
	
	public boolean hit(int x, int y) {
		if(map[y][x].stat == MapTile.WATER) {
			map[y][x].stat = MapTile.UNSUCCHIT;
			return false;
		}
		if(map[y][x].stat == MapTile.UNSUCCHIT) return false;
		if(map[y][x].stat == MapTile.SUCCHIT) return false;
		if(map[y][x].stat == MapTile.DEFINITELYNOSHIP) return false;
		if(map[y][x].stat == MapTile.SHIP) {
			map[y][x].stat = MapTile.SUCCHIT;
			map[y][x].ship.hit();
			
			if(!map[y][x].ship.isAlive()) {
				// TODO hier muss dann noch überprüft werden ob das Schiff versenkt wurde
				// Falls ja muss die Variable die speichert wie viele Schiffe noch
				// "leben" verringert werden
			}
			
			return true;
		}
		
		return false;
	}
	
	static class MapTile {
		public static final int WATER = 0;
		public static final int SHIP = 1;
		public static final int SUCCHIT = 2;
		public static final int UNSUCCHIT = 3;
		// kreuze um ein bereits komplett zerstörtes Schiff, da da keins mehr sein darf
		public static final int DEFINITELYNOSHIP = 4;
		private Ship ship;
		private int stat;
		
		private MapTile() {
			stat = WATER;
			ship = null;
		}
		
		// Die setter hab ich doch raus gemacht weil mir eingefallen ist,
		// dass man auf die Attribute auch so zugreifen kann (da es eine
		// innere Klasse ist)
	}
}
