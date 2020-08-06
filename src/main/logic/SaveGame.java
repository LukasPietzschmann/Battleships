package logic;

//import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

public class SaveGame {
    private Player ownPlayer;
    private Player oppPlayer;
    private int mode;
    
//    public String saveGame(Map map){
//        String cache;
//        Gson mapSave = new Gson();
//        cache = String.valueOf(mapSave.toJson(map));
//        return cache;
//    }
    
    public static SaveGame fromId(long id){
        File file = new File(System.getProperty("user.home") + "\\Documents\\saveGames\\" + id);
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        }catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public int getMode(){
        return mode;
    }
    
    public Player getOwnPlayer(){
        return ownPlayer;
    }
    
    public Player getOppPlayer(){
        return oppPlayer;
    }
}