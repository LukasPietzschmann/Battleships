package logic;

import java.io.*;

public class ResourceManager {
    public static void save (Serializable data, String filename) throws Exception{
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(System.getProperty("user.home") + "\\Documents\\saveGames\\" + filename + ".savegame"))){
            oos.writeObject(data);
        }
    }
    public static Object load (String filename) throws Exception{
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(System.getProperty("user.home") + "\\Documents\\saveGames\\" + filename))){
            return ois.readObject();
        }
    }
}
