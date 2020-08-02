package ai;

import logic.Direction;
import logic.Logic;
import logic.Map;
import logic.Player;
import logic.Ship;

/**
 * Die Klasse PlayableAI modelliert eine AI. Diese Klasse dient als "zwischen-Klasse", da die
 * AI Klassen nicht von {@link Player} erben können.
 */
public abstract class PlayableAI {
	protected static final int WAIT_TIME = 500;
	protected static final int ALREADY_SHOT = 1;
	protected static final int NOT_SHOT = 0;
	protected Player player;
	protected Logic logic;
	protected Map map;
	protected int[][] enemyMap;
	/**
	 * Die letzte x-Koordinate an der getroffen wurde, oder -1, falls sie nicht existiert.
	 */
	protected int lastXPos = -1;
	/**
	 * Die letzte y-Koordinate an der getroffen wurde, oder -1, falls sie nicht existiert.
	 */
	protected int lastYPos = -1;
	/**
	 * Die letzte Richtung in die gegangen wurde, oder {@code null}, falls sie nicht existiert.
	 */
	protected Direction lastDir = null;
	
	protected Mission currMission = null;
	
	/**
	 * Initialisiert die PlayableAI
	 * @param player Referenz auf den Tatsächlichen Spieler. Immer ein Objekt der Klasse {@link AI}.
	 * @param logic "Zurück-Referenz" auf die {@link Logic}.
	 * @param map Das eigene Spielfeld.
	 */
	public PlayableAI(Player player, Logic logic, Map map) {
		enemyMap = new int[map.getSize()][map.getSize()];
		for(int i = 0; i < map.getSize(); i++) for(int j = 0; j < map.getSize(); j++) enemyMap[i][j] = NOT_SHOT;
		this.player = player;
		this.logic = logic;
		this.map = map;
	}
	
	/**
	 * Wird aufgerufen, sobald die AI dran ist ihren Zug zu machen.
	 * @return Das konkrete Schiff, falls getroffen wurde. {@code null}, falls nicht getroffen wurde.
	 */
	protected abstract Ship makeMove();
}
