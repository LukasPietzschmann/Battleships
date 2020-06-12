package main.ai;

import main.logic.Logic;

import main.logic.Ship;
import main.logic.Ship.Direction;

import java.util.Random;

public class MediumAI extends AI {
	/**
	 * @param l "Zurück-Referenz" auf das Logik Objekt.
	 * @param size Die festgelegte Größe des Spielfelds.
	 * @param name Der vom Spieler festgelegte Name. Dient nur zur Anzeige in der GUI.
	 * @param difficulty Die Schwierigkeitsstufe des Computers
	 */
	public MediumAI(Logic l, int size, String name, Difficulty difficulty) {
		super(l, size, name, difficulty);
	}
	
	/**
	 * {@inheritDoc} Die MediumAI schießt zufällig, bis sie ein Schiff getroffen hat. Dann wird sie um die getroffenen
	 * Koordinaten herum schießen.
	 *
	 * @return {@inheritDoc}
	 */
	@Override
	public boolean doWhatYouHaveToDo() {
		Random rnd = new Random();
		int x, y;
		Direction dir;
		
		if(lastDir == null && lastXPos == -1 && lastYPos == -1) {
			// Alles beliebig setzen
			x = rnd.nextInt(map.getSize());
			y = rnd.nextInt(map.getSize());
			
			if(logic.shoot(x, y, this) == null) return false; // Wenn nicht getroffen wurde
			else {
				// Wenn getroffen wurde, wird der Punkt gespeichert
				lastXPos = x;
				lastYPos = y;
				return true;
			}
		}else if(lastDir != null && lastXPos != -1 && lastYPos != -1) {
			x = lastXPos;
			y = lastYPos;
			if(isValidDirection(lastDir, lastXPos, lastYPos)) dir = lastDir;
			else dir = mirrorDirection(lastDir);
			// Falls ich da weiter machen kann
			Ship ship = shootInDirection(dir, x, y);
			if(ship == null) {
				// Richtung drehen, weil nicht getroffen wurde
				lastDir = mirrorDirection(dir);
				return false;
			}
			if(!ship.isAlive()) {
				// Alles löschen weil man das Schiff versenkt hat
				lastDir = null;
				lastXPos = -1;
				lastYPos = -1;
			}else {
				// Alles Speuchern, da man getroffen hat
				lastXPos = getNewXKoord(dir, x);
				lastYPos = getNewYKoord(dir, y);
			}
			return true;
		}else if(lastDir == null && lastXPos != -1 && lastYPos != -1) {
			while(!isValidDirection(dir = Direction.north, lastXPos, lastYPos)) {
				dir = Direction.values()[rnd.nextInt(Direction.values().length)];
			}
			if(shootInDirection(dir, lastXPos, lastYPos) == null) return false;
			else {
				lastXPos = getNewXKoord(dir, lastXPos);
				lastYPos = getNewYKoord(dir, lastYPos);
				lastDir = dir;
				return true;
			}
		}
		
		System.err.println("Fuck die AI geht doch net einwandfrei");
		return false;
	}
}
