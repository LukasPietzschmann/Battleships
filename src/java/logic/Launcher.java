package logic;

/**
 * Die Klasse Launcher startet das Spiel und frägt Anfangseinstellungen ab
 */
public class Launcher {
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
	
	public static void main(String[] args) {
		// TODO start GUI
		// TODO mode = GUI.getMode();
		int mode = NW_SV_AI;
		
		Logic logic;
		
		switch(mode) {
			case NW_CL_AI:
				logic = new Logic("AI", "NW", AI.Difficulty.easy, "127.0.0.1");
				break;
			case NW_SV_AI:
				logic = new Logic("AI", "NW", AI.Difficulty.easy, 5);
				break;
			case PL_AI:
				logic = new Logic("AI", "Player", 5, AI.Difficulty.easy);
				break;
			case PL_NW_CL:
				logic = new Logic("Player", "NW", "127.0.0.1");
				break;
			case PL_NW_SV:
				logic = new Logic("Player", "NW", 5);
				break;
		}
	}
}