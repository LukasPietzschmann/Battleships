package logic;
import java.util.Random;

public class Ship {
    public enum Direction {
        north, south, east, west
    }

    private String[] names = {"U-Boot", "Zerstörer", "Kreuzer", "Schlachtschiff"};

    private int xPos;
    private int yPos;
    private Direction direction;
    private int size;
    private int hits;

    public Ship(int xPos, int yPos, Direction direction, int size) {
        hits = 0;

        this.xPos = xPos;
        this.yPos = yPos;
        this.direction = direction;
        this.size = size;
    }

    public Ship(Ship ship) {
        this(ship.xPos, ship.yPos, ship.direction, ship.size);
    }

    public boolean isAlive() {
        return hits < size;
    }

    public String getName() {
        return names[size - 2];
    }

    /**
     * @return the x Position of the ships head
     */
    public int getXPos() {
        return xPos;
    }

    /**
     * @return the y Position of the ships head
     */
    public int getYPos() {
        return yPos;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getSize() {
        return size;
    }

    public int getHits() {
        return hits;
    }

    public void setXPos(int xPos) {
        this.xPos = xPos;
    }

    public void setYPos(int yPos) {
        this.yPos = yPos;
    }

    public void hit() {
        hits += 1;
    }

    // Die ist fürs zufällige platzieren aller Schiffe
    public void randomize(int mapSize) {
        Random rnd = new Random();
        xPos = rnd.nextInt(mapSize);
        yPos = rnd.nextInt(mapSize);
        direction = Direction.values()[rnd.nextInt(Direction.values().length)];
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Ship)) return false;
        Ship s = (Ship) obj;
        return (s.xPos == xPos && s.yPos == yPos && s.direction == direction && s.size == size && s.hits == hits);
    }

    @Override
    public int hashCode() {
        return ((xPos + size + yPos) * direction.hashCode());
    }

    @Override
    public String toString() {
        return getName();
    }
}

