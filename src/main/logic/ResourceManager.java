package logic;

import java.io.*;
import java.util.ArrayList;

public class ResourceManager {
    private static ResourceManager instance;
    private ArrayList<SaveListener> listeners = new ArrayList<>();
    
    public void save (Serializable data, String filename) throws Exception{
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(System.getProperty("user.home") + "/Documents/Battleships_Spielstände/" + filename + ".savegame"))){
            oos.writeObject(data);
            for(SaveListener listener : listeners) listener.OnGameSaved(((SaveData)data).getID());
        }
    }
    
    public Object load (String filename) throws Exception{
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(System.getProperty("user.home") + "\\Documents\\Battleships_Spielstände\\" + filename))){
            return ois.readObject();
        }
    }
    
    public static ResourceManager getInstance(){
        if(instance == null) instance = new ResourceManager();
        return instance;
    }
    
    public void registerSaveListener(SaveListener listener){
        listeners.add(listener);
    }
}
