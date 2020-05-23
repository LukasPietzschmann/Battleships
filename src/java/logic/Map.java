package logic;

public class Map {
    MapTile [][] map;

    public void dump() {
        for (MapTile[] mapTiles : map) {
            System.out.println("|");
            for (int j = 0; j < mapTiles.length; j++) {
                String s = "";
                switch (mapTiles[j].stat) {
                    case ship:
                        s = "S";
                        break;
                    case water:
                        s = "~";
                        break;
                    case unscuccHit:
                        s = "/";
                        break;
                    case definitleyNoShip:
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
        if(map[y][x].equals(MapTile.ship)){
            map[y][x] = MapTile.setStat(succHit);
            return true;
        }
        else{
            return false;
        }
    }


    public void Map(int size) {
        //double root = Math.sqrt(size);
        //int mapsize = (int)root;
        map = new MapTile [size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                map[i][j] = new MapTile();
            }

        }

        //new MapTile(0, null);
        //new MapTile(1, new Ship(...));

    }
    static class MapTile{
        int stat;
        public static final int Water = 0;
        public static final int ship = 1;
        public static final int succHit = 2;
        public static final int unsuccHit = 3;
        // kreuze um ein bereits komplett zerstörtes Schiff, da da keins mehr sein darf
        public static final int definitelyNoShip = 4;
        Ship ship;


        private MapTile(){
            stat = Water;
            ship = null;
        }

        private void setStat(int stat){
            this.stat = stat;
        }

        private void setShip(Ship ship){
            this.ship = ship;
        }

        public void setStat(MapStat stat){
            this.stat = stat;
        }
    }
}
