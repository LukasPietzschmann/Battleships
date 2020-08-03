package logic;

import ai.Difficulty;
import ai.AI;
import gui.MainWindow;

import java.io.File;

/**
 * Die Klasse Launcher startet das Spiel und frägt Anfangseinstellungen ab
 */
public class Launcher {
	public static boolean soundPlaying = false;
	
	public static final int SG = 0;
	/**
	 * Die {@link AI} gegen einen Gegner übers {@link network.Network}, wobei man selbst der Client ist.
	 */
	public static final int NW_CL_AI = 1;
	/**
	 * Die {@link AI} gegen einen Gegner übers {@link network.Network}, wobei man selbst der Server ist.
	 */
	public static final int NW_SV_AI = 2;
	/**
	 * Ein {@link Human} gegen die {@link AI}.
	 */
	public static final int PL_AI = 3;
	/**
	 * Ein {@link Human} gegen einen Gegner übers {@link network.Network}, wobei man selbst der Client ist.
	 */
	public static final int PL_NW_CL = 4;
	/**
	 * Ein {@link Human} gegen einen Gegner übers {@link network.Network}, wobei man selbst der Server ist.
	 */
	public static final int PL_NW_SV = 5;
	public static final int AI_AI = 6;
	public static int gridSize = 15;
	public static String theme = "Battleships";
	public static String themeIdentifierPlural = "Schiffe";
	public static String themeIdentifierSingular = "Schiff";
	
	public static Logic startGame(int mode, String name01, String name02, int ship2Count, int ship3Count, int ship4Count, int ship5Count, String ip, int port, Difficulty diff01, Difficulty diff02, long id) {
		try {
			switch(mode) {
//				case SG:
//					return Logic.fromSaveGame(id);
				case AI_AI:
					return new Logic(name01, name02, diff01, diff02, gridSize, ship2Count, ship3Count, ship4Count, ship5Count);
				case PL_AI:
					return new Logic(name01, name02, gridSize, diff01, ship2Count, ship3Count, ship4Count, ship5Count);
				case NW_CL_AI:
					return new Logic(name01, name02, diff01, ip, port);
				case NW_SV_AI:
					return new Logic(name01, name02, diff01, gridSize, ship2Count, ship3Count, ship4Count, ship5Count);
				case PL_NW_SV:
					return new Logic(name01, name02, gridSize, ship2Count, ship3Count, ship4Count, ship5Count);
				case PL_NW_CL:
					return new Logic(name01, name02, ip, port);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static boolean enoughShips(int ship2Count, int ship3Count, int ship4Count, int ship5Count) {
		int totalShipParts = 2*ship2Count + 3*ship3Count + 4*ship4Count + 5*ship5Count;
		int totalGridTiles = gridSize*gridSize;
		double fillFactor = (double)totalShipParts/(double)totalGridTiles;
		// Belegungsfaktor darf nicht größer als 25% sein
		return !(fillFactor > 0.25);
	}


	public static int getFillFactor(int ship2Count, int ship3Count, int ship4Count, int ship5Count){
		int totalShipParts = 2*ship2Count + 3*ship3Count + 4*ship4Count + 5*ship5Count;
		int totalGridTiles = gridSize*gridSize;
		// Rückgabe des Belegungsfaktors als ganze Zahl
		return (int)(((double)totalShipParts/(double)totalGridTiles)*100);
	}
	
	public static void main(String[] args) {
		MainWindow mainWindow = new MainWindow();
		mainWindow.setUpMainWindow();
		createSaveDirectory();
	}

	public static void createSaveDirectory(){
		File dir = new File(System.getProperty("user.home") + "\\Documents\\saveGames");
		dir.mkdir();
	}
}