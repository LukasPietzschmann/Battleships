package Schiffeversenken.logic;

public interface GameEventListener {
	public static final int HIT = 0;
	public static final int MISS = 1;
	public static final int HIT_DEAD = 2;
	
	void OnEventFired(int event);
	void OnPlayersTurn(Player player);
}
