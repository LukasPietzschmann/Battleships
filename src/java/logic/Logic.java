package logic;

/**
 * Die Klasse Logik steuert den kompletten Spielablauf.
 */
public class Logic {
	
	/**
	 * Referenz auf einen der beiden Spieler.
	 */
	private Player player01;
	
	/**
	 * Referenz auf einen der beiden Spieler.
	 */
	private Player player02;
	
	/**
	 * Konstruktor, falls zwei {@link AI} gegeneinander spielen.
	 *
	 * @param difficulty01 Die Schwierigkeitsstufe der einen AI.
	 * @param name01 Der Name der ersten AI.
	 * @param difficulty02 Die Schwierigkeitsstufe der anderen AI.
	 * @param name02 Der Name der zweiten AI.
	 * @param size Die Größe des Spielfelds.
	 */
	public Logic(AI.Difficulty difficulty01, String name01, AI.Difficulty difficulty02, String name02, int size) {
	
	}
	
	/**
	 * Konstruktor, falls zwei eine {@link AI} gegeneinander einen {@link Human} spielt.
	 *
	 * @param difficulty Die Schwierigkeitsstufe der AI.
	 * @param nameAI Der Name der AI.
	 * @param nameHuman Der Name des Spielers.
	 * @param size Die Größe des Spielfelds.
	 */
	public Logic(AI.Difficulty difficulty, String nameAI, String nameHuman, int size) {
	
	}
	
	/**
	 * Konstruktor, falls zwei {@link Human} gegeneinander spielen.
	 *
	 * @param name01 Der Name des ersten Spielers.
	 * @param name02 Der Name des zweiten Spielers.
	 * @param size Die Größe des Spielfelds.
	 */
	public Logic(String name01, String name02, int size) {
	
	}
	
	/**
	 * Schießt auf einen Spieler.
	 *
	 * @param x x-Koordinate auf die geschossen wird.
	 * @param y y-Koordinate auf die geschossen wird.
	 * @param player Referenz auf den schießenden Spieler.
	 * @return {@code null}, falls nicht getroffen wurde. Das konkrete {@link Ship}, falls getroffen wurde.
	 */
	public Ship shoot(int x, int y, Player player) {
		if(player == player01) {
			return player02.hit(x, y);
		}else {
			return player01.hit(x, y);
		}
	}
	
	/**
	 * Startet das Spiel.
	 */
	public void startGame() {
	
	}
}
