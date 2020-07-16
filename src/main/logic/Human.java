package logic;

/**
 * Die Klasse Human modelliert einen echten Spieler.
 */
public class Human extends LocalPlayer {
	
	/**
	 * @param l "Zurück-Referenz" auf das Logik Objekt.
	 * @param size Die festgelegte Größe des Spielfelds.
	 * @param name Der vom Spieler festgelegte Name. Dient nur zur Anzeige in der GUI.
	 */
	public Human(Logic l, int size, String name) {
		super(l, size, name);
	}
	
	/**
	 * Implementierung für einen echten Spieler. {@inheritDoc}
	 *
	 * @return {@inheritDoc}
	 */
	@Override
	public boolean doWhatYouHaveToDo() {
		notifyMakeMove();
		int x = 0;
		int y = 0;
		
		return logic.shoot(x, y, this) != null;
	}
	
	/**
	 * Implementierung für einen echten Spieler. {@inheritDoc}
	 */
	@Override
	public void placeShips() {
	}
}
