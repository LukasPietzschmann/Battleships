package logic;

import ai.Difficulty;
import ai.AI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gui.MainWindow;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Die Klasse Launcher startet das Spiel und frägt Anfangseinstellungen ab
 */
public class Launcher {
	public static boolean soundPlaying = false;
	public static String fileSuffix = ".sg";
	public static String mapPraefix = "map_";
	
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
	
	private static Launcher instance;
	public static int gridSize = 10;
	public static String theme = "Battleships";
	public static String themeIdentifierPlural = "Schiffe";
	public static String themeIdentifierSingular = "Schiff";
	
	public static Logic startGame(int mode, String name01, String name02, int ship2Count, int ship3Count, int ship4Count, int ship5Count, String ip, Difficulty diff01, Difficulty diff02, long id) {
		try {
			switch(mode) {
				case SG:
					return getSaveGame(id);
				case AI_AI:
					return new Logic(name01, name02, diff01, diff02, gridSize, ship2Count, ship3Count, ship4Count, ship5Count);
				case PL_AI:
					return new Logic(name01, name02, gridSize, diff01, ship2Count, ship3Count, ship4Count, ship5Count);
				case NW_CL_AI:
					return new Logic(name01, name02, diff01, ip);
				case NW_SV_AI:
					return new Logic(name01, name02, diff01, gridSize, ship2Count, ship3Count, ship4Count, ship5Count);
				case PL_NW_SV:
					return new Logic(name01, name02, gridSize, ship2Count, ship3Count, ship4Count, ship5Count);
				case PL_NW_CL:
					return new Logic(name01, name02, ip);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static Launcher getInstance() {
		if(instance == null) return Launcher.instance = new Launcher();
		return instance;
	}
	
	public static boolean enoughShips(int ship2Count, int ship3Count, int ship4Count, int ship5Count) {
		return true;
	}
	
	public static void main(String[] args) {
		MainWindow mainWindow = new MainWindow();
		mainWindow.setUpMainWindow();
	}
	
	private static Logic getSaveGame(long id) throws Exception {
		GsonBuilder gb = new GsonBuilder();
		Gson gson = gb.create();
		File mapFile = new File(String.format("%s/%s/%s%d%s", System.getProperty("user.home"), "Documents", mapPraefix, id, fileSuffix));
		Scanner fileScanner;
		if(!mapFile.exists()) return null;
		fileScanner = new Scanner(mapFile);
		SaveGameSetup sgs = gson.fromJson(fileScanner.nextLine(), SaveGameSetup.class);
		Map ownMap = gson.fromJson(fileScanner.nextLine(), Map.class);
		Map oppMap = null;
		switch(sgs.getMode()) {
			case Launcher.PL_AI:
			case Launcher.AI_AI:
				oppMap = gson.fromJson(fileScanner.nextLine(), Map.class);
		}
		
		return new Logic(sgs.getMode(), ownMap, sgs.getOwnPlayerName(), sgs.getOwnDifficulty(), oppMap, sgs.getOppPlayerName(), sgs.getOppDifficulty(), id);
	}
}