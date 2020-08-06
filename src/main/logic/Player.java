package logic;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Die Klasse Player modelliert einen Spieler auf der höchsten Abstraktionsebene.
 */
public abstract class Player implements Serializable {
	protected Logic logic;
	protected ArrayList<GameListener> gameListeners;
	protected ArrayList<GameListener> enemyGameListeners;
	
	/**
	 * Initialisiert den Player.
	 * @param logic "Zurück-Referenz" auf das Logik Objekt. Typischerweise {@code this}.
	 */
	public Player(Logic logic) {
		this.logic = logic;
		gameListeners = new ArrayList<>();
		enemyGameListeners = new ArrayList<>();
	}
	
	/**
	 * Registriert einen {@link GameListener}.
	 * @param listener Der zu registrierende Listener.
	 */
	public void registerGameListener(GameListener listener){
		gameListeners.add(listener);
	}
	
	/**
	 * Registriert einen {@link GameListener} für den Gegner.
	 * @param listener Der zu registrierende Listener.
	 */
	public void registerEnemyGameListener(GameListener listener){
		enemyGameListeners.add(listener);
	}
	
	/**
	 * Wird aufgerufen, falls auf den Spieler geschossen wurde.
	 * @param x Die x Koordinate des Schusses.
	 * @param y Die y Koordinate des Schusses.
	 * @param hit {@code true}, falls es ein Treffer war, sonst {@code false}.
	 */
	protected void notifyOnHit(int x, int y, boolean hit){
		for (GameListener g: gameListeners) {
			g.OnHit(x, y, hit);
		}
	}
	
	/**
	 * Wird aufgerufen, falls auf den Gegner geschossen wurde.
	 * @param x Die x Koordinate des Schusses.
	 * @param y Die y Koordinate des Schusses.
	 * @param hit {@code true}, falls es ein Treffer war, sonst {@code false}.
	 */
	protected void notifyOnEnemyHit(int x, int y, boolean hit){
		for (GameListener listener: enemyGameListeners) {
			listener.OnHit(x, y, hit);
		}
	}
	
	/**
	 * Weißt den Spieler darauf hin, dass er an der Reihe ist, seinen Zug zu machen.
	 *
	 * @return {@code true}, falls der Spieler erfolgreich getroffen hat. Sonst {@code false}.
	 */
	public abstract Ship yourTurn();
	
	/**
	 * Wird von der Klasse {@link Logic} aufgerufen, um auf den Spieler zu schießen.
	 *
	 * @param x Die x-Koordinate des Schusses
	 * @param y Die y-Koordinate des Schusses
	 * @return {@code null}, falls nicht getroffen wurde. Das konkrete {@link Ship}, falls getroffen wurde.
	 */
	public abstract Ship hit(int x, int y);
	
	/**
	 * Weißt den Spieler darauf hin, dass er an der Reihe ist seine Schiffe zu platzieren.
	 */
	public abstract void placeShips();
	
	/**
	 * Gibt zurück, ob der Spieler noch Schiffe besitzt.
	 *
	 * @return {@code true}, falls der Spieler noch "lebende" Schiffe hat. Sonst {@code false}.
	 */
	public abstract boolean isAlive();
	
	/**
	 * Wird aufgerufen, sobald der Gegner alle Schiffe platziert hat.
	 */
	public void oppPlacedShips(){
		return;
	}
}
