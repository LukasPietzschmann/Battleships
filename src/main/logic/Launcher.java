package logic;

import ai.Difficulty;
import ai.AI;
import gui.MainWindow;

/**
 * Die Klasse Launcher startet das Spiel und frägt Anfangseinstellungen ab
 */
public class Launcher {
	public static boolean soundPlaying = false;
	
	public static final int SG = 0;
	public static final int NW_CL_AI = 1;
	public static final int NW_SV_AI = 2;
	public static final int PL_AI = 3;
	public static final int PL_NW_CL = 4;
	public static final int PL_NW_SV = 5;
	public static int gridSize = 15;
	public static String theme = "Battleships";
	public static String themeIdentifierPlural = "Schiffe";
	public static String themeIdentifierSingular = "Schiff";
	
	/**
	 * Startet das Spiel.
	 * @param mode Der Modus des Spiels.
	 * @param ship2Count Anzahl der 2er Schiffe.
	 * @param ship3Count Anzahl der 3er Schiffe.
	 * @param ship4Count Anzahl der 4er Schiffe.
	 * @param ship5Count Anzahl der 5er Schiffe.
	 * @param ip Die IP des Servers.
	 * @param port Der Port des Servers.
	 * @param diff Die Schwierigkeit der AI.
	 * @param id Die ID des Save Games.
	 * @return Die Logic, die das Spiel ab sofort steuert.
	 */
	public static Logic startGame(int mode, int ship2Count, int ship3Count, int ship4Count, int ship5Count, String ip, int port, Difficulty diff, long id) {
		try {
			switch(mode) {
				case SG:
					//TODO id Parameter
					return new Logic(id);
				case PL_AI:
					return new Logic(gridSize, diff, ship2Count, ship3Count, ship4Count, ship5Count);
				case NW_CL_AI:
					return new Logic(diff, ip, port);
				case NW_SV_AI:
					return new Logic(diff, gridSize, ship2Count, ship3Count, ship4Count, ship5Count);
				case PL_NW_SV:
					return new Logic(gridSize, ship2Count, ship3Count, ship4Count, ship5Count);
				case PL_NW_CL:
					return new Logic(ip, port);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Überprüft, ob zu wenig, oder zu viele Schiffe platziert wurden.
	 * @param ship2Count Anzahl der 2er Schiffe.
	 * @param ship3Count Anzahl der 3er Schiffe.
	 * @param ship4Count Anzahl der 4er Schiffe.
	 * @param ship5Count Anzahl der 5er Schiffe.
	 * @return {@code true}, falls genau richtig viele Schiffe platziert wurden. Sonst {@code false}.
	 */
	public static boolean enoughShips(int ship2Count, int ship3Count, int ship4Count, int ship5Count) {
		int totalShipParts = 2*ship2Count + 3*ship3Count + 4*ship4Count + 5*ship5Count;
		int totalGridTiles = gridSize*gridSize;
		double fillFactor = (double)totalShipParts/(double)totalGridTiles;
		// Belegungsfaktor darf nicht größer als 25% sein
		return !(fillFactor > 0.25);
	}
	
	/**
	 * Gibt den Quotienten aus den platzierten Schiffen und der Anzahl aller Felder zurück.
	 * @param ship2Count Anzahl der 2er Schiffe.
	 * @param ship3Count Anzahl der 3er Schiffe.
	 * @param ship4Count Anzahl der 4er Schiffe.
	 * @param ship5Count Anzahl der 5er Schiffe.
	 * @return Der Quotienten aus den platzierten Schiffen und der Anzahl aller Felder.
	 */
	public static int getFillFactor(int ship2Count, int ship3Count, int ship4Count, int ship5Count){
		int totalShipParts = 2*ship2Count + 3*ship3Count + 4*ship4Count + 5*ship5Count;
		int totalGridTiles = gridSize*gridSize;
		// Rückgabe des Belegungsfaktors als ganze Zahl
		return (int)(((double)totalShipParts/(double)totalGridTiles)*100);
	}
	
	public static void main(String[] args) {
		MainWindow mainWindow = new MainWindow();
		mainWindow.setUpMainWindow();
	}
}