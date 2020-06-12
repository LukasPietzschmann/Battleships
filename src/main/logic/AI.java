package main.logic;

/**
 * Die Klasse AI modelliert den Computer-Spieler.
 */
public class AI extends LocalPlayer {
	/**
	 * Die Schwierigkeitsstufe des Computers.
	 */
	public enum Difficulty {
		hard, medium, easy
	}
	
	/**
	 * Die Schwierigkeitsstufe des Computers.
	 */
	private Difficulty difficulty;
	
	/**
	 * @param difficulty Die Schwierigkeitsstufe der AI.
	 * @param l "Zurück-Referenz" auf das Logik Objekt.
	 * @param size Die festgelegte Größe des Spielfelds.
	 * @param name Der vom Spieler festgelegte Name. Dient nur zur Anzeige in der GUI.
	 */
	public AI(Difficulty difficulty, Logic l, int size, String name) {
		super(l, size, name);
		this.difficulty = difficulty;
	}
	
	/**
	 * Implementierung für den Computer. {@inheritDoc}
	 *
	 * @return {@inheritDoc}
	 */
	@Override
	public boolean doWhatYouHaveToDo() {
		return false;
	}
	
	/**
	 * Implementierung für den Computer. {@inheritDoc}
	 */
	@Override
	public void placeShips() {
	
	}
}
