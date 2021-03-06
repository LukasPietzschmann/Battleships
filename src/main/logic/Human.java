package logic;

/**
 * Die Klasse Human modelliert einen echten Spieler.
 */
public class Human extends LocalPlayer {
	
	/**
	 * @param l "Zurück-Referenz" auf das Logik Objekt.
	 * @param size Die festgelegte Größe des Spielfelds.
	 */
	public Human(Logic l, int size) {
		super(l, size);
	}
	
	/**
	 * Implementierung für einen echten Spieler. {@inheritDoc}
	 *
	 * @return {@inheritDoc}
	 */
	@Override
	public Ship yourTurn() {
		notifyMakeMove();
		int x = 0;
		int y = 0;
		try {
			int[] click = clickQueue.take();
			x = click[0];
			y = click[1];
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		return logic.shoot(x, y, this);
	}
	
	/**
	 * Implementierung für einen echten Spieler. {@inheritDoc}
	 */
	@Override
	public void placeShips() {
	}
}
