package logic;

public class Player {

    protected Logic logic;
    protected String name;

    public boolean doWhatYouHaveToDo(){
        return false;
    }

    public boolean hit(int x, int y){
        return false;
    }

    public void placeShips(){//in gui aufrufen

    }
    
    public void ready(){
    
    }

    public void Player(Logic logic, String name){
        this.logic = logic;
        this.name = name;
    }
}
