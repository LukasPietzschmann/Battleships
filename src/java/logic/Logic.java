package logic;

public class Logic {

    private Player player01;
    private Player player02;

    public void Logic(Difficulty difficulty01, Difficulty difficulty02, int size){

    }

    public void Logic(Difficulty difficulty, int size){

    }

    public void Logic(int size){

    }

    public Ship shoot(int x, int y, Player player){
        if(player == player01){
            return player02.hit(x, y);
        }
        else{
            return player01.hit(x, y);
        }

    }

    public void startGame(){

    }
}
