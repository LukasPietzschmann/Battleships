package logic;

public class Map {
    MapTile [][] map;
    private int shipsNr;

    public Map(int size) {
        map = new MapTile [size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                map[i][j] = new MapTile();
            }

        }
    }

    public void dump() {
        for (MapTile[] mapTiles : map) {
            System.out.println("|");
            for (int j = 0; j < mapTiles.length; j++) {
                String s = "";
                switch (mapTiles[j].stat) {
                    case MapTile.Ship:
                        s = "S";
                        break;
                    case MapTile.Water:
                        s = "~";
                        break;
                    case MapTile.unsuccHit:
                        s = "/";
                        break;
                    case MapTile.definitelyNoShip:
                        s = "#";
                        break;
                    case MapTile.succHit:
                        s = "X";
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

    public Ship hit(int x, int y) {
        if(map[y][x].stat == MapTile.Water){
           map[y][x].stat = MapTile.unsuccHit;
           return null;
        }
        else if(map[y][x].stat == MapTile.Ship){
            map[y][x].stat = MapTile.succHit;
            map[y][x].ship.hit();


            if(!map[y][x].ship.isAlive()){
                    shipsNr -= 1;
                    Ship ship = map[y][x].ship;
                    int sx, sy;
                    sx = ship.getXPos();
                    sy = ship.getYPos();
                    switch (ship.getDirection()){
                        case south:
                            if(sx > 0){
                                for(int i = 0; i < ship.getSize(); i++){
                                    map[sy - i][sx - 1].stat = MapTile.definitelyNoShip;
                                }
                            }
                            if(sx < map.length - 1){
                                for(int i = 0; i < ship.getSize(); i++){
                                    map[sy - i][sx + 1].stat = MapTile.definitelyNoShip;
                                }
                            }
                            if(sy < map.length - 1){
                                map[sy + 1][sx].stat = MapTile.definitelyNoShip;
                            }
                            if((sy - ship.getSize()) + 1  > 0){
                                map[sy - ship.getSize()][sx].stat = MapTile.definitelyNoShip;
                            }
                            break;
                        case north:
                            break;
                        case west:
                            break;
                        case east:
                            break;
                    }//TODO Fertig machen
            }


            return map[y][x].ship;
        }
        else if(map[y][x].stat == MapTile.succHit){
            return null;
        }
        else if(map[y][x].stat == MapTile.unsuccHit){
            return null;
        }
        else if(map[y][x].stat == MapTile.definitelyNoShip){
            return null;
        }
        return null;
    }

    public int shipsNr(){
        return shipsNr;
    }

    public Ship getShip(int x, int y){
        return map[y][x].ship;
    }

    public void placeShip(Ship ship){
        shipsNr++;
        switch (ship.getDirection()){
            case east:
                for(int i = 0; i < ship.getSize(); i++){
                    map[ship.getYPos()][ship.getXPos() - i].stat = MapTile.Ship;
                    map[ship.getYPos()][ship.getXPos() - i].ship = ship;
                }
                break;
            case west:
                for(int i = 0; i < ship.getSize(); i++){
                    map[ship.getYPos()][ship.getXPos() + i].stat = MapTile.Ship;
                    map[ship.getYPos()][ship.getXPos() + i].ship = ship;
                }
                break;
            case north:
                for(int i = 0; i < ship.getSize(); i++){
                    map[ship.getYPos() + i][ship.getXPos()].stat = MapTile.Ship;
                    map[ship.getYPos() + i][ship.getXPos()].ship = ship;
                }
                break;
            case south:
                for(int i = 0; i < ship.getSize(); i++){
                    map[ship.getYPos() - i][ship.getXPos()].stat = MapTile.Ship;
                    map[ship.getYPos() - i][ship.getXPos()].ship = ship;
                }
                break;
        }
    }

    public void removeShip(Ship ship){
        shipsNr--;
        switch (ship.getDirection()){
            case east:
                for(int i = 0; i < ship.getSize(); i++){
                    map[ship.getYPos()][ship.getXPos() - i].stat = MapTile.Water;
                    map[ship.getYPos()][ship.getXPos() - i].ship = null;
                }
                break;
            case west:
                for(int i = 0; i < ship.getSize(); i++){
                    map[ship.getYPos()][ship.getXPos() + i].stat = MapTile.Water;
                    map[ship.getYPos()][ship.getXPos() + i].ship = null;
                }
                break;
            case north:
                for(int i = 0; i < ship.getSize(); i++){
                    map[ship.getYPos() + i][ship.getXPos()].stat = MapTile.Water;
                    map[ship.getYPos() + i][ship.getXPos()].ship = null;
                }
                break;
            case south:
                for(int i = 0; i < ship.getSize(); i++){
                    map[ship.getYPos() - i][ship.getXPos()].stat = MapTile.Water;
                    map[ship.getYPos() - i][ship.getXPos()].ship = null;
                }
                break;
        }
    }

    public boolean canShipBePlaced(Ship ship){
        int x = ship.getXPos();
        int y = ship.getYPos();
        int shipSize = ship.getSize();

        switch (ship.getDirection()) {
            case north:
                if (x < 0 || x >= map.length)
                    return false;
                if (y < 0 || y + shipSize - 1 >= map.length)
                    return false;

                try {
                    if (y - 1 >= 0 && map[y - 1][x].stat == MapStat.ship)
                        return false;
                    if (y + shipSize < map.length && map[y + shipSize][x].stat == MapStat.ship)
                        return false;

                    for (int i = 0; i < shipSize; i++) {
                        for (int j = 0; j < 2; j++) {
                            if (x + j < map.length && map[y + i][x + j].stat == MapStat.ship)
                                return false;
                            if (x - j >= 0 && map[y + i][x - j].stat == MapStat.ship)
                                return false;
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    // System.out.println("Yes schon wieder Codingarbeit gespart hehe :)");
                }
                break;
            case south:
                if (x < 0 || x >= map.length)
                    return false;
                if (y - shipSize + 1 < 0 || y >= map.length)
                    return false;

                try {
                    if (y - shipSize >= 0 && map[y - shipSize][x].stat == MapStat.ship)
                        return false;
                    if (y + 1 < map.length && map[y + 1][x].stat == MapStat.ship)
                        return false;

                    for (int i = 0; i < shipSize; i++) {
                        for (int j = 0; j < 2; j++) {
                            if (x + j < map.length && map[y - i][x + j].stat == MapStat.ship)
                                return false;
                            if (x - j >= 0 && map[y - i][x - j].stat == MapStat.ship)
                                return false;
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    // System.out.println("Yes schon wieder Codingarbeit gespart hehe :)");
                }
                break;
            case west:
                if (x < 0 || x + shipSize - 1 >= map.length)
                    return false;
                if (y < 0 || y >= map.length)
                    return false;

                try {
                    if (x - 1 >= 0 && map[y][x - 1].stat == MapStat.ship)
                        return false;
                    if (x + shipSize < map.length && map[y][x + shipSize].stat == MapStat.ship)
                        return false;

                    for (int i = 0; i < shipSize; i++) {
                        for (int j = 0; j < 2; j++) {
                            if (y + j < map.length && map[y + j][x + i].stat == MapStat.ship)
                                return false;
                            if (y - j >= 0 && map[y - j][x + i].stat == MapStat.ship)
                                return false;
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    // System.out.println("Yes schon wieder Codingarbeit gespart hehe :)");
                }
                break;
            case east:
                if (x - shipSize + 1 < 0 || x >= map.length)
                    return false;
                if (y < 0 || y >= map.length)
                    return false;

                try {
                    if (x - shipSize >= 0 && map[y][x - shipSize].stat == MapStat.ship)
                        return false;
                    if (x + 1 < map.length && map[y][x + 1].stat == MapStat.ship)
                        return false;

                    for (int i = 0; i < shipSize; i++) {
                        for (int j = 0; j < 2; j++) {
                            if (y + j < map.length && map[y + j][x - i].stat == MapStat.ship)
                                return false;
                            if (y - j >= 0 && map[y - j][x - i].stat == MapStat.ship)
                                return false;
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    // System.out.println("Yes schon wieder Codingarbeit gespart hehe :)");
                }
                break;
        }

        return true;
        //TODO
    }
    public void reset(){
        map = new MapTile [map.length][map.length];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                map[i][j].stat = MapTile.Water;
                map[i][j].ship = null;
            }
        }
    }





    static class MapTile{
        private int stat;
        public static final int Water = 0;
        public static final int Ship = 1;
        public static final int succHit = 2;
        public static final int unsuccHit = 3;
        // kreuze um ein bereits komplett zerstörtes Schiff, da da keins mehr sein darf
        public static final int definitelyNoShip = 4;
        private Ship ship;


        private MapTile(){
            stat = Water;
            ship = null;
        }
    }
}
