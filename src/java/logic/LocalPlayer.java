package logic;

import java.util.ArrayList;

public abstract class  LocalPlayer extends Player {
        protected Map map;


    public LocalPlayer(Logic l, int size, String name){
        super(l, name);
        map = new Map(size);
    }

    @Override
    public Ship hit(int x, int y){
        return map.hit(x, y);
    }

    public void randomShipPlacment(){//random zahlen x und y erzeugen für alle boote
        map.reset();
        ArrayList <Ship> ships = new ArrayList<>();
        if(!solveForShip(ships, 0)){
            map.reset();
            System.err.print("Fehlerhaftes Schiffsetzen neustarten");
        }
    }

    private boolean solveForShip(ArrayList<Ship> ships, int index){
        if(index >= ships.size()){
            return true;
        }
        Ship ship = ships.get(index);
        for (int i = 0; i < 100; i++) {
            ship.randomize(map.getSize());
            if (map.canShipBePlaced(ship)) {
                map.placeShip(new Ship(ship));
                if (solveForShip(ships, index + 1)) {
                    return true;
                }
                map.removeShip(ship);
            }
        }
        return false;
    }

    @Override
    public boolean isAlive() {
        return map.shipsNr() > 0;
    }
}
