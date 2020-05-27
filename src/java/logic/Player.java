package logic;

import java.util.Random;

public abstract class Player {

    protected Logic logic;
    protected String name;


    public Player(Logic l, String n) {
        logic = l;
        name = n;
    }

    public abstract boolean doWhatYouHaveToDo();

    public abstract Ship hit(int x, int y);

    public abstract void placeShips();

    public abstract boolean isAlive();

}
