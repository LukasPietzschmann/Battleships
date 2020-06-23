package ai;

import logic.Logic;

import logic.Map;
import logic.Player;
import logic.Ship;
import logic.Ship.Direction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class MediumAI extends PlayableAI {
	public MediumAI(Player player, Logic logic, Map map) {
		super(player, logic, map);
	}
	
	/**
	 * {@inheritDoc} Die MediumAI schießt zufällig, bis sie ein Schiff getroffen hat. Dann wird sie um die getroffenen
	 * Koordinaten herum schießen.
	 *
	 * @return {@inheritDoc}
	 */
	/*@Override
	public boolean makeMove() {
		Random rnd = new Random();
		int x, y;
		Direction dir;
		
		if(lastDir == null && lastXPos == -1 && lastYPos == -1) {
			// Alles beliebig setzen
			x = rnd.nextInt(map.getSize());
			y = rnd.nextInt(map.getSize());
			
			if(logic.shoot(x, y, player) == null) return false; // Wenn nicht getroffen wurde
			// Wenn getroffen wurde, wird der Punkt gespeichert
			lastXPos = x;
			lastYPos = y;
			return true;
		}else if(lastDir != null && lastXPos != -1 && lastYPos != -1) {
			x = lastXPos;
			y = lastYPos;
			//Hier gehts immer hin und her wenn ich am ende bin, es aber nicht versenkt ist.
			//Ich möchte dann wieder am Anfang anfangen und in die entgegengesetzte Richtung gehen
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
			do {
				dir = Direction.values()[rnd.nextInt(Direction.values().length)];
			}while(!isValidDirection(dir, lastXPos, lastYPos));
			if(shootInDirection(dir, lastXPos, lastYPos) == null) return false;
			lastXPos = getNewXKoord(dir, lastXPos);
			lastYPos = getNewYKoord(dir, lastYPos);
			lastDir = dir;
			return true;
		}
		
		System.err.println("Fuck die AI geht doch net einwandfrei");
		return false;
	}*/
	@Override
	protected boolean makeMove() {
		int x, y;
		if(currMission == null) {
			Random rnd = new Random();
			do {
				x = rnd.nextInt(map.getSize());
				y = rnd.nextInt(map.getSize());
			}while(enemyMap[y][x] == NO_SHIP);
			
			if(logic.shoot(x, y, player) == null) return false;
			currMission = new Mission(x, y, map);
			return true;
		}
		
		x = currMission.getNextX();
		y = currMission.getNextY();
		
		Ship ship = logic.shoot(x, y, player);
		if(ship == null) {
			currMission.wasHit(false);
			return false;
		}
		enemyMap[y][x] = NO_SHIP;
		if(ship.isAlive()) currMission.wasHit(true);
		else currMission = null;
		
		return true;
	}
}
