package logic;

public interface SaveListener {
	/**
	 * Wird aufgerufen, sobald das Spiel gespeichert wurde.
	 *
	 * @param id Die ID des gespeicherten Spiels.
	 */
	void OnGameSaved(int id);
}
