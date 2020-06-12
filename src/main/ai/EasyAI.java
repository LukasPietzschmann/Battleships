package main.ai;

import main.logic.Logic;

import java.util.Random;

class EasyAI extends AI {
	/**
	 * @param l "Zurück-Referenz" auf das Logik Objekt.
	 * @param size Die festgelegte Größe des Spielfelds.
	 * @param name Der vom Spieler festgelegte Name. Dient nur zur Anzeige in der GUI.
	 * @param difficulty Die Schwierigkeitsstufe des Computers
	 */
	public EasyAI(Logic l, int size, String name, Difficulty difficulty) {
		super(l, size, name, difficulty);
	}
	
	/**
	 * {@inheritDoc} Die EasyAI schießt komplett zufällig.
	 *
	 * @return {@inheritDoc}
	 */
	@Override
	public boolean doWhatYouHaveToDo() {
		Random rnd = new Random();
		
		return logic.shoot(rnd.nextInt(map.getSize()), rnd.nextInt(map.getSize()), this) != null;
	}
}
