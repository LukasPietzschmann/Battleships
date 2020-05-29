package logic;

import network.Network;

/**
 * Die Klasse Logik steuert den kompletten Spielablauf.
 */
public class Logic {
	
	/**
	 * Referenz auf einen der beiden Spieler.
	 */
	private Player ownPlayer;
	
	/**
	 * Referenz auf einen der beiden Spieler.
	 */
	private Player oppPlayer;
	
	/**
	 * Konstruktor, falls eine {@link AI} gegen einen Gegner übers Netzwerk spielt und man selbst der Client ist.
	 *
	 * @param nameAI Der Name der AI.
	 * @param nameNW Der Name des Gegners.
	 * @param difficulty Die Schwierigkeit der AI.
	 * @param IP Die IP Adresse des Servers.
	 */
	public Logic(String nameAI, String nameNW, AI.Difficulty difficulty, String IP) {
		oppPlayer = new Network(this, nameNW, IP);
		ownPlayer = new AI(AI.Difficulty.easy, this, ((Network) oppPlayer).getSize(), nameNW);
	}
	
	/**
	 * Konstruktor, falls eine {@link AI} gegen einen Gegner übers Netzwerk spielt und man selbst der Server ist.
	 *
	 * @param nameAI Der Name der AI.
	 * @param nameNW Der Name des Gegners.
	 * @param difficulty Die Schwierigkeit der AI.
	 * @param size Die Größe des Spielfelds.
	 */
	public Logic(String nameAI, String nameNW, AI.Difficulty difficulty, int size) {
		ownPlayer = new AI(difficulty, this, size, nameAI);
		oppPlayer = new Network(this, nameNW, size);
	}
	
	/**
	 * Konstruktor, falls eine {@link AI} gegen einen {@link Human} spielt.
	 *
	 * @param nameAI Der Name der AI.
	 * @param namePL Der Name des Spielers.
	 * @param difficulty Die Schwierigkeit der AI.
	 * @param size Die Größe des Spielfelds.
	 */
	public Logic(String nameAI, String namePL, int size, AI.Difficulty difficulty) {
		ownPlayer = new Human(this, size, namePL);
		oppPlayer = new AI(difficulty, this, size, nameAI);
	}
	
	/**
	 * Konstruktor, falls ein {@link Human} gegen einen Gegner übers Netzwerk spielt und man selbst der Client ist.
	 *
	 * @param namePl Der Name des Spielers.
	 * @param nameNW Der Name des Gegners.
	 * @param IP Die IP Adresse des Servers.
	 */
	public Logic(String namePl, String nameNW, String IP) {
		oppPlayer = new Network(this, nameNW, IP);
		ownPlayer = new Human(this, ((Network) oppPlayer).getSize(), namePl);
	}
	
	/**
	 * Konstruktor, falls ein {@link Human} gegen einen Gegner übers Netzwerk spielt und man selbst der Server ist.
	 *
	 * @param namePl Der Name des Spielers.
	 * @param nameNW Der Name des Gegners.
	 * @param size Die Größe des Spielfelds
	 */
	public Logic(String namePl, String nameNW, int size) {
		ownPlayer = new Human(this, size, namePl);
		oppPlayer = new Network(this, nameNW, size);
	}
	
	/**
	 * Schießt auf einen Spieler.
	 *
	 * @param x x-Koordinate auf die geschossen wird.
	 * @param y y-Koordinate auf die geschossen wird.
	 * @param player Referenz auf den schießenden Spieler.
	 * @return {@code null}, falls nicht getroffen wurde. Das konkrete {@link Ship},falls getroffen wurde.
	 */
	public boolean shoot(int x, int y, Player player) {
		if(player == ownPlayer) {
			return oppPlayer.hit(x, y);
		}else {
			return ownPlayer.hit(x, y);
		}
	}
	
	/**
	 * Startet das Spiel.
	 */
	public void startGame() {
	
	}
}
