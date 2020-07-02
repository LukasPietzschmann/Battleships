package logic;

import ai.Difficulty;
import ai.AI;

/**
 * Die Klasse Launcher startet das Spiel und frägt Anfangseinstellungen ab
 */
public class Launcher {
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
	
	private static final Launcher instance = null;
	
	public Logic startGame(int mode, int ship2Count, int ship3Count, int ship4Count, int ship5Count, int size) throws Exception{
		switch(mode){
			case SG:
				//TODO id Parameter
				return new Logic(0);
			case AI_AI:
				return new Logic("AI1", "AI2", Difficulty.easy, Difficulty.medium, size, ship2Count, ship3Count, ship4Count, ship5Count);
			case PL_AI:
				return new Logic("AI", "Player", size, Difficulty.easy,  ship2Count, ship3Count, ship4Count, ship5Count);
			case NW_CL_AI:
				return new Logic("Player", "Network", "127.0.0.1");
			case NW_SV_AI:
				return new Logic("AI", "Network", Difficulty.easy, size,  ship2Count, ship3Count, ship4Count, ship5Count);
			case PL_NW_SV:
				return new Logic("Player", "Network", size, ship2Count, ship3Count, ship4Count, ship5Count);
			case PL_NW_CL:
				return new Logic("Player", "Network", "127.0.0.1");
		}
		
		return null;
	}
	
	public static Launcher getInstance(){
		if(instance == null) return new Launcher();
		return instance;
	}
}