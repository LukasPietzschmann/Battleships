package logic;

import java.io.Serializable;

public class SaveData implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private int mode;
    private int gridSize;
    private Map map1;
    private Map map2;
    private Player ownPlayer;
    private Player oppPlayer;

    public Map getMap1() {
        return map1;
    }

    public void setMap1(Map map1) {
        this.map1 = map1;
    }

    public Map getMap2() {
        return map2;
    }

    public void setMap2(Map map2) {
        this.map2 = map2;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getGridSize() {
        return gridSize;
    }

    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }

    public Player getOwnPlayer() {
        return ownPlayer;
    }

    public void setOwnPlayer(Player ownPlayer) {
        this.ownPlayer = ownPlayer;
    }

    public Player getOppPlayer() {
        return oppPlayer;
    }

    public void setOppPlayer(Player oppPlayer) {
        this.oppPlayer = oppPlayer;
    }

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }
}
