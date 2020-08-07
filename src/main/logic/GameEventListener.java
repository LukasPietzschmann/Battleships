package logic;

public interface GameEventListener {
	int HIT = 0;
	int MISS = 1;
	int HIT_DEAD = 2;
	/**
	 * Wird aufgerufen sobald ein Event ausgelöst wurde.
	 * @param event Die Art des Events. Entweder {@link #HIT}, {@link #MISS}, oder {@link #HIT_DEAD}.
	 */
	void OnEventFired(int event);
	/**
	 * Wird aufgerufen sobald der nächste Spieler am Zug ist.
	 * @param player Der Spieler der nun am Zug ist.
	 */
	void OnPlayersTurn(Player player);
}
