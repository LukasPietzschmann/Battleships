package logic;

import ai.Difficulty;

public class SaveGameSetup {
	private int mode;
	private String ownPlayerName;
	private String oppPlayerName;
	private Difficulty ownDifficulty;
	private Difficulty oppDifficulty;
	
	public int getMode() {
		return mode;
	}
	
	public String getOwnPlayerName() {
		return ownPlayerName;
	}
	
	public String getOppPlayerName() {
		return oppPlayerName;
	}
	
	public Difficulty getOwnDifficulty() {
		return ownDifficulty;
	}
	
	public Difficulty getOppDifficulty() {
		return oppDifficulty;
	}
}
