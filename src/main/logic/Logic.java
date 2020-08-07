package logic;

import ai.AI;
import ai.Difficulty;
import network.Network;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Die Klasse Logik steuert den kompletten Spielablauf.
 */
public class Logic extends Thread implements Serializable {
	private volatile boolean ownPlayerShipsPlaced = false;
	private volatile boolean oppPlayerShipsPlaced = false;
	private final int MODE;
	public Player ownPlayer;
	public Player oppPlayer;
	private ArrayList<Ship> ships;
	private int size;
	private CopyOnWriteArrayList<SetUpShipsListener> setUpShipsListeners;
	private CopyOnWriteArrayList<GameStartsListener> gameStartsListeners;
	private CopyOnWriteArrayList<GameEndsListener> gameEndsListeners;
	private CopyOnWriteArrayList<GameEventListener> gameEventListeners;
	
	/**
	 * @param MODE Der Modus in dem gespielt wird.
	 */
	private Logic(int MODE) {
		this.MODE = MODE;
		setUpShipsListeners = new CopyOnWriteArrayList<>();
		gameStartsListeners = new CopyOnWriteArrayList<>();
		gameEndsListeners = new CopyOnWriteArrayList<>();
		gameEventListeners = new CopyOnWriteArrayList<>();
	}
	
	/**
	 * @param mode Der Modus in dem gespielt wird.
	 * @param ship2Count Die Anzahl der 2er Schiffe.
	 * @param ship3Count Die Anzahl der 3er Schiffe.
	 * @param ship4Count Die Anzahl der 4er Schiffe.
	 * @param ship5Count Die Anzahl der 5er Schiffe.
	 * @param z Unnötige Variable. Dient zur Unterscheidung von einem Anderen konstruktor mit den seben Datentypen.
	 */
	private Logic(int mode, int ship2Count, int ship3Count, int ship4Count, int ship5Count, int z) {
		this(mode);
		ships = new ArrayList<>();
		int[] ships = {ship2Count, ship3Count, ship4Count, ship5Count};
		
		for(int i = 0; i < ships.length; i++) {
			for(int j = 0; j < ships[i]; j++) {
				this.ships.add(new Ship(0, 0, Direction.north, i + 2));
			}
		}
	}
	
	/**
	 * Konstruktor, falls eine {@link AI} gegen einen Gegner übers Netzwerk spielt und man selbst der Client ist.
	 *
	 * @param difficulty Die Schwierigkeit der AI.
	 * @param IP Die IP Adresse des Servers.
	 * @param port Der Port des Servers.
	 */
	public Logic(Difficulty difficulty, String IP, int port) {
		this(Launcher.NW_CL_AI);
		oppPlayer = new Network(this, IP, port);
		ships = ((Network) oppPlayer).getShips();
		size = ((Network) oppPlayer).getSize();
		ownPlayer = new AI(this, ((Network) oppPlayer).getSize(), difficulty);
	}
	
	/**
	 * Konstruktor, falls eine {@link AI} gegen einen Gegner übers Netzwerk spielt und man selbst der Server ist.
	 *
	 * @param difficulty Die Schwierigkeit der AI.
	 * @param size Die Größe des Spielfelds.
	 * @param ship2Count Anzahl der Schiffe mit Länge 2.
	 * @param ship3Count Anzahl der Schiffe mit Länge 3.
	 * @param ship4Count Anzahl der Schiffe mit Länge 4.
	 * @param ship5Count Anzahl der Schiffe mit Länge 5.
	 * @throws Exception Fehlermeldung bei Erzeugung der Logik.
	 */
	public Logic(Difficulty difficulty, int size, int ship2Count, int ship3Count, int ship4Count, int ship5Count) throws Exception {
		this(Launcher.NW_SV_AI, ship2Count, ship3Count, ship4Count, ship5Count, 0);
		this.size = size;
		ownPlayer = new AI(this, size, difficulty);
		oppPlayer = new Network(this, size);
	}
	
	/**
	 * Konstruktor, falls eine {@link AI} gegen einen {@link Human} spielt.
	 *
	 * @param difficulty Die Schwierigkeit der AI.
	 * @param size Die Größe des Spielfelds.
	 * @param ship2Count Die Anzahl der Schiffe mit Größe 2.
	 * @param ship3Count Die Anzahl der Schiffe mit Größe 3.
	 * @param ship4Count Die Anzahl der Schiffe mit Größe 4.
	 * @param ship5Count Die Anzahl der Schiffe mit Größe 5.
	 */
	public Logic(int size, Difficulty difficulty, int ship2Count, int ship3Count, int ship4Count, int ship5Count) {
		this(Launcher.PL_AI, ship2Count, ship3Count, ship4Count, ship5Count, 0);
		this.size = size;
		ownPlayer = new Human(this, size);
		oppPlayer = new AI(this, size, difficulty);
	}
	
	/**
	 * Konstruktor, falls ein {@link Human} gegen einen Gegner übers Netzwerk spielt und man selbst der Client ist.
	 *
	 * @param IP Die IP Adresse des Servers.
	 * @param port Der Port des Servers.
	 */
	public Logic(String IP, int port) {
		this(Launcher.PL_NW_CL);
		oppPlayer = new Network(this, IP, port);
		ships = ((Network) oppPlayer).getShips();
		size = ((Network) oppPlayer).getSize();
		ownPlayer = new Human(this, ((Network) oppPlayer).getSize());
	}
	
	/**
	 * Konstruktor, falls ein {@link Human} gegen einen Gegner übers Netzwerk spielt und man selbst der Server ist.
	 *
	 * @param size Die Größe des Spielfelds
	 * @param ship2Count Die Anzahl der Schiffe mit Größe 2.
	 * @param ship3Count Die Anzahl der Schiffe mit Größe 3.
	 * @param ship4Count Die Anzahl der Schiffe mit Größe 4.
	 * @param ship5Count Die Anzahl der Schiffe mit Größe 5.
	 * @throws Exception Fehlermeldung bei Erzeugung der Logik.
	 */
	public Logic(int size, int ship2Count, int ship3Count, int ship4Count, int ship5Count) throws Exception {
		this(Launcher.PL_NW_SV, ship2Count, ship3Count, ship4Count, ship5Count, 0);
		this.size = size;
		ownPlayer = new Human(this, size);
		oppPlayer = new Network(this, size);
	}
	
	public static Logic fromSaveGame(SaveData save) {
		//		SaveGame saveGame = SaveGame.fromId(id);
		//Hier den Modus setzen
		Logic logic = new Logic(save.getMode());
		logic.ownPlayer = save.getOwnPlayer();
		logic.oppPlayer = save.getOppPlayer();
		
		return logic;
	}
	
	/**
	 * Schießt auf einen Spieler.
	 *
	 * @param x x-Koordinate auf die geschossen wird.
	 * @param y y-Koordinate auf die geschossen wird.
	 * @param player Referenz auf den schießenden Spieler.
	 * @return {@code null}, falls nicht getroffen wurde. Das konkrete {@link Ship},falls getroffen wurde.
	 */
	public Ship shoot(int x, int y, Player player) {
		if(player == ownPlayer) {
			return oppPlayer.hit(x, y);
		}else {
			return ownPlayer.hit(x, y);
		}
	}
	
	/**
	 * Startet das Spiel.
	 *
	 * @param loaded Spiel geladen oder nicht.
	 */
	public void startGame(boolean loaded) {
		Thread t = new Thread(() -> {
			Ship hit;
			
			Player currPlayer = ownPlayer, otherPlayer = oppPlayer;
			if(!loaded) {
				switch(MODE) {
					case Launcher.NW_CL_AI:
					case Launcher.PL_NW_CL:
						currPlayer = oppPlayer;
						otherPlayer = ownPlayer;
						break;
				}
				if(!(MODE == Launcher.NW_SV_AI || MODE == Launcher.NW_CL_AI)) notifyPlaceShips();
				currPlayer.placeShips();
				if(currPlayer == oppPlayer) {
					while(!oppPlayerShipsPlaced) {
					}
				}else {
					while(!ownPlayerShipsPlaced) {
					}
				}
				otherPlayer.oppPlacedShips();
				otherPlayer.placeShips();
				if(otherPlayer == oppPlayer) {
					while(!oppPlayerShipsPlaced) {
					}
				}else {
					while(!ownPlayerShipsPlaced) {
					}
				}
				currPlayer.oppPlacedShips();
			}
			notifyGameStarts();
			
			while(true) {
				notifyPlayersTurnListener(currPlayer);
				hit = Ship.defaultShip(0, 0);
				while(hit != null) {
					if(!otherPlayer.isAlive()) {
						notifyGameEndsListener(currPlayer);
						return;
					}
					
					hit = currPlayer.yourTurn();
					if(hit == null) notifyGameEventListener(GameEventListener.MISS);
					else if(hit.isAlive()) notifyGameEventListener(GameEventListener.HIT);
					else notifyGameEventListener(GameEventListener.HIT_DEAD);
				}
				
				Player temp = currPlayer;
				currPlayer = otherPlayer;
				otherPlayer = temp;
			}
		});
		t.start();
	}
	
	/**
	 * Gibt alle Schiffe zurück, die platziert werden dürfen.
	 *
	 * @return Eine Liste an Schiffen, die platziert werden können.
	 */
	public ArrayList<Ship> getAvailableShips() {
		return ships;
	}
	
	/**
	 * Gibt die Größe des Spielfelds zurück.
	 *
	 * @return Die Größe des Spielfelds.
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * Gibt den eigenen Spieler zurück.
	 *
	 * @return Der eigene Spieler.
	 */
	public LocalPlayer getOwnPlayer() {
		return (LocalPlayer) ownPlayer;
	}
	
	/**
	 * Gibt den Gegenspieler zurück.
	 *
	 * @return Der Gegenspieler.
	 */
	public Player getOppPlayer() {
		return oppPlayer;
	}
	
	/**
	 * Registriert einen {@link SetUpShipsListener}.
	 *
	 * @param listener Der zu registrierende Listener.
	 */
	public void registerSetupShipsListener(SetUpShipsListener listener) {
		setUpShipsListeners.add(listener);
	}
	
	/**
	 * Hebt die Registrierung für einen {@link SetUpShipsListener} wierder auf.
	 *
	 * @param listener Der zu entferndende Listener.
	 */
	public void unregisterSetupShipsListener(SetUpShipsListener listener) {
		setUpShipsListeners.remove(listener);
	}
	
	/**
	 * Registriert einen {@link GameStartsListener}.
	 *
	 * @param listener Der zu registrierende Listener.
	 */
	public void registerGameStartsListener(GameStartsListener listener) {
		gameStartsListeners.add(listener);
	}
	
	/**
	 * Hebt die Registrierung für einen {@link GameStartsListener} wierder auf.
	 *
	 * @param listener Der zu entferndende Listener.
	 */
	public void unregisterGameStartsListener(GameStartsListener listener) {
		gameStartsListeners.remove(listener);
	}
	
	/**
	 * Registriert einen {@link GameEndsListener}.
	 *
	 * @param listener Der zu registrierende Listener.
	 */
	public void registerGameEndListener(GameEndsListener listener) {
		gameEndsListeners.add(listener);
	}
	
	/**
	 * Registriert einen {@link GameEventListener}.
	 *
	 * @param listener Der zu registrierende Listener.
	 */
	public void registerGameEventListener(GameEventListener listener) {
		gameEventListeners.add(listener);
	}
	
	/**
	 * Beachrichtigt alle registrierten {@link GameEventListener}, dass ein Event ausgelöst wurde.
	 *
	 * @param event Das ausgelöste Event.
	 */
	private void notifyGameEventListener(int event) {
		for(GameEventListener listener : gameEventListeners) {
			listener.OnEventFired(event);
		}
	}
	
	/**
	 * Benachrichtigt alle registrierten {@link GameEndsListener}, dass der Gegner das Spiel verlassen hat.
	 */
	public void notifyOppLeftListener() {
		for(GameEndsListener listener : gameEndsListeners) {
			listener.OnOpponentLeft();
		}
	}
	
	/**
	 * Benachrichtigt alle registrierten {@link GameEventListener}, welcher Spieler nun dran ist.
	 *
	 * @param player Der SPieler, der nun am Zug ist.
	 */
	private void notifyPlayersTurnListener(Player player) {
		for(GameEventListener listener : gameEventListeners) {
			listener.OnPlayersTurn(player);
		}
	}
	
	/**
	 * Benachrichtigt alle registrierten {@link SetUpShipsListener}, dass ein Schiff platziert wurde.
	 */
	private void notifyPlaceShips() {
		for(SetUpShipsListener listener : setUpShipsListeners) listener.onPlaceShips();
	}
	
	/**
	 * Benachrichtigt alle registrierten {@link GameStartsListener}, dass das Spiel gestartet wurde.
	 */
	private void notifyGameStarts() {
		for(GameStartsListener listener : gameStartsListeners) {
			listener.OnStartGame();
		}
	}
	
	/**
	 * Benachrichtigt alle registrierten {@link GameEndsListener}, dass das Spiel zuende ist.
	 *
	 * @param winningPlayer Der Spieler der gewonnen hat.
	 */
	private void notifyGameEndsListener(Player winningPlayer) {
		for(GameEndsListener listener : gameEndsListeners) {
			listener.OnGameEnds(winningPlayer);
		}
	}
	
	/**
	 * Muss von einem {@link Player} aufgerufen werden, falls all seine Schiffe platziert hat.
	 *
	 * @param player Spieler, der alle seine Schiffe platziert hat.
	 */
	public synchronized void setShipsPlaced(Player player) {
		if(player instanceof LocalPlayer && ((LocalPlayer) player).map.getShipsNr() != getAvailableShips().size()) {
			//nicht alle wurden platziert
			((LocalPlayer) player).notifyOnNotAllShipsPlaced();
			return;
		}
		if(player == ownPlayer) ownPlayerShipsPlaced = true;
		else oppPlayerShipsPlaced = true;
	}
	
	public int getMODE() {
		return MODE;
	}
}
