package logic;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class saveGame {
//    private Map map;
//    private String saveGameId;
//    private LocalPlayer player;
//    public saveGame(Map map, String saveGameId, Player player;) {
//        this.map = map;
//        this.saveGameId = saveGameId;
//        this.player = player;
//    }

    public String saveGame(Map map){
        String cache;
        Gson mapSave = new Gson();
        cache = String.valueOf(mapSave.toJson(map));
        return cache;
    }
}