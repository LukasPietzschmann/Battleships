package logic;

public interface GameEndsListener {
	/**
	 * Wird aufgerufen, sobald das Spiel zuende ist.
	 * @param winningPlayer Referenz auf den Spieler der gewonnen hat.
	 */
	void OnGameEnds(Player winningPlayer);
	/**
	 * Wird aufgerufen, sobald der Gegenspieler das Spiel verlassen hat.
	 */
	void OnOpponentLeft();
}
