package Schiffeversenken.ai;

import Schiffeversenken.logic.*;

/**
 * Die Klasse AI modelliert einen Computer-Spieler.
 */
public class AI extends LocalPlayer {
	/**
	 * Das tatsächlich Spielende AI-Objekt ({@link EasyAI}, {@link MediumAI}, oder {@link HardAI}).
	 */
	private PlayableAI ai;
	
	/**
	 * @param l "Zurück-Referenz" auf das Logik Objekt.
	 * @param size Die festgelegte Größe des Spielfelds.
	 * @param name Der vom Spieler festgelegte Name. Dient nur zur Anzeige in der GUI.
	 * @param difficulty Die Schwierigkeitsstufe des Computers
	 */
	public AI(Logic l, int size, String name, Difficulty difficulty) {
		super(l, size, name);
		
		switch(difficulty) {
			case easy:
				ai = new EasyAI(this, logic, new Map(size));
				break;
			case medium:
				ai = new MediumAI(this, logic, new Map(size));
				break;
			case hard:
				ai = new HardAI(this, logic, new Map(size));
				break;
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void placeShips() {
		//noinspection StatementWithEmptyBody
		while(!randomShipPlacment()) {
		}
		logic.setShipsPlaced(this);
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * @return {@inheritDoc}
	 */
	@Override
	public Ship doWhatYouHaveToDo() {
		return ai.makeMove();
	}
}