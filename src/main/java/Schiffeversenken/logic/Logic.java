package Schiffeversenken.logic;

import Schiffeversenken.ai.AI;
import Schiffeversenken.ai.Difficulty;
import Schiffeversenken.network.Network;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Die Klasse Logik steuert den kompletten Spielablauf.
 */
public class Logic extends Thread {
	private volatile boolean ownPlayerShipsPlaced = false;
	private volatile boolean oppPlayerShipsPlaced = false;
	private final int MODE;
	/**
	 * Referenz auf einen der beiden Spieler.
	 */
	public Player ownPlayer;
	/**
	 * Referenz auf einen der beiden Spieler.
	 */
	public Player oppPlayer;
	private ArrayList<Ship> ships;
	private int size;
	private CopyOnWriteArrayList<SetUpShipsListener> setUpShipsListeners;
	private CopyOnWriteArrayList<GameStartsListener> gameStartsListeners;
	private CopyOnWriteArrayList<GameEndsListener> gameEndsListeners;
	private CopyOnWriteArrayList<GameEventListener> gameEventListeners;
	
	private Logic(int MODE) {
		this.MODE = MODE;
		setUpShipsListeners = new CopyOnWriteArrayList<>();
		gameStartsListeners = new CopyOnWriteArrayList<>();
		gameEndsListeners = new CopyOnWriteArrayList<>();
		gameEventListeners = new CopyOnWriteArrayList<>();
	}
	
	/**
	 * Konstruktor, falls ein Save-Game geladen wird.
	 *
	 * @param id Die ID des Save-Games.
	 */
	public Logic(long id) {
		this(Launcher.SG);
		//TODO implement
	}
	
	private Logic(int mode, int ship2Count, int ship3Count, int ship4Count, int ship5Count) {
		this(mode);
		ships = new ArrayList<>();
		int[] ships = {ship2Count, ship3Count, ship4Count, ship5Count};
		
		for(int i = 0; i < ships.length; i++) {
			for(int j = 0; j < ships[i]; j++) {
				this.ships.add(new Ship(0, 0, Direction.north, i + 2));
			}
		}
	}
	
	public Logic(String nameAI1, String nameAI2, Difficulty difficultyAI1, Difficulty difficultyAI2, int size, int ship2Count, int ship3Count, int ship4Count, int ship5Count) {
		this(Launcher.AI_AI, ship2Count, ship3Count, ship4Count, ship5Count);
		this.size = size;
		ownPlayer = new AI(this, size, nameAI1, difficultyAI1);
		oppPlayer = new AI(this, size, nameAI2, difficultyAI2);
	}
	
	/**
	 * Konstruktor, falls eine {@link AI} gegen einen Gegner übers Netzwerk spielt und man selbst der Client ist.
	 *
	 * @param nameAI Der Name der AI.
	 * @param nameNW Der Name des Gegners.
	 * @param difficulty Die Schwierigkeit der AI.
	 * @param IP Die IP Adresse des Servers.
	 */
	public Logic(String nameAI, String nameNW, Difficulty difficulty, String IP) {
		this(Launcher.NW_CL_AI);
		oppPlayer = new Network(this, nameNW, IP);
		ships = ((Network) oppPlayer).getShips();
		size = ((Network) oppPlayer).getSize();
		ownPlayer = new AI(this, ((Network) oppPlayer).getSize(), nameNW, difficulty);
	}
	
	/**
	 * Konstruktor, falls eine {@link AI} gegen einen Gegner übers Netzwerk spielt und man selbst der Server ist.
	 *
	 * @param nameAI Der Name der AI.
	 * @param nameNW Der Name des Gegners.
	 * @param difficulty Die Schwierigkeit der AI.
	 * @param size Die Größe des Spielfelds.
	 */
	public Logic(String nameAI, String nameNW, Difficulty difficulty, int size, int ship2Count, int ship3Count, int ship4Count, int ship5Count) throws Exception {
		this(Launcher.NW_SV_AI, ship2Count, ship3Count, ship4Count, ship5Count);
		this.size = size;
		ownPlayer = new AI(this, size, nameAI, difficulty);
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
	public Logic(String nameAI, String namePL, int size, Difficulty difficulty, int ship2Count, int ship3Count, int ship4Count, int ship5Count) {
		this(Launcher.PL_AI, ship2Count, ship3Count, ship4Count, ship5Count);
		this.size = size;
		ownPlayer = new Human(this, size, namePL);
		oppPlayer = new AI(this, size, nameAI, difficulty);
	}
	
	/**
	 * Konstruktor, falls ein {@link Human} gegen einen Gegner übers Netzwerk spielt und man selbst der Client ist.
	 *
	 * @param namePl Der Name des Spielers.
	 * @param nameNW Der Name des Gegners.
	 * @param IP Die IP Adresse des Servers.
	 */
	public Logic(String namePl, String nameNW, String IP) {
		this(Launcher.PL_NW_CL);
		oppPlayer = new Network(this, nameNW, IP);
		ships = ((Network) oppPlayer).getShips();
		size = ((Network) oppPlayer).getSize();
		ownPlayer = new Human(this, ((Network) oppPlayer).getSize(), namePl);
	}
	
	/**
	 * Konstruktor, falls ein {@link Human} gegen einen Gegner übers Netzwerk spielt und man selbst der Server ist.
	 *
	 * @param namePl Der Name des Spielers.
	 * @param nameNW Der Name des Gegners.
	 * @param size Die Größe des Spielfelds
	 */
	public Logic(String namePl, String nameNW, int size, int ship2Count, int ship3Count, int ship4Count, int ship5Count) throws Exception {
		this(Launcher.PL_NW_SV, ship2Count, ship3Count, ship4Count, ship5Count);
		this.size = size;
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
	public Ship shoot(int x, int y, Player player) {
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
		Thread t = new Thread(() -> {
			Ship hit;
			
			Player currPlayer, otherPlayer;
			switch(MODE) {
				case Launcher.NW_CL_AI:
				case Launcher.PL_NW_CL:
					currPlayer = oppPlayer;
					otherPlayer = ownPlayer;
					break;
				default: //PL_AI, NW_SV_AI, PL_NW_SV
					currPlayer = ownPlayer;
					otherPlayer = oppPlayer;
			}
			if(!(MODE == Launcher.AI_AI || MODE == Launcher.NW_SV_AI || MODE == Launcher.NW_CL_AI)) notifyPlaceShips();
			//oppPlayerShipsPlaced = true;
			//ownPlayerShipsPlaced = true;
			//TODO netwokr listener
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
			notifyGameStarts();
			
			while(true) {
				notifyPlayersTurnListener(currPlayer);
				hit = Ship.defaultShip(0,0);
				while(hit != null) {
					if(!otherPlayer.isAlive()) {
						System.out.println(String.format("%s hat gewonnen!!", currPlayer.name));
						notifyGameEndsListener(currPlayer);
						return;
					}
					
					hit = currPlayer.doWhatYouHaveToDo();
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
	
	public int getSize() {
		return size;
	}
	
	public LocalPlayer getOwnPlayer() {
		return (LocalPlayer) ownPlayer;
	}
	
	public Player getOppPlayer() {
		return oppPlayer;
	}
	
	public void registerSetupShipsListener(SetUpShipsListener listener) {
		setUpShipsListeners.add(listener);
	}
	
	public void unregisterSetupShipsListener(SetUpShipsListener listener) {
		setUpShipsListeners.remove(listener);
	}
	
	public void registerGameStartsListener(GameStartsListener listener) {
		gameStartsListeners.add(listener);
	}
	
	public void unregisterGameStartsListener(GameStartsListener listener) {
		gameStartsListeners.remove(listener);
	}
	
	public void registerGameEndListener(GameEndsListener listener) {
		gameEndsListeners.add(listener);
	}
	
	public void registerGameEventListener(GameEventListener listener){
		gameEventListeners.add(listener);
	}
	
	private void notifyGameEventListener(int event){
		for(GameEventListener listener : gameEventListeners) {
			listener.OnEventFired(event);
		}
	}
	
	public void notifyOppLeftListener(){
		for(GameEndsListener listener : gameEndsListeners) {
			listener.OnOpponentLeft();
		}
	}
	
	private void notifyPlayersTurnListener(Player player){
		for(GameEventListener listener : gameEventListeners) {
			listener.OnPlayersTurn(player);
		}
	}
	
	private void notifyPlaceShips() {
		for(SetUpShipsListener listener : setUpShipsListeners) listener.onPlaceShips();
	}
	
	private void notifyGameStarts() {
		for(GameStartsListener listener : gameStartsListeners) {
			listener.OnStartGame();
		}
	}
	
	private void notifyGameEndsListener(Player winningPlayer) {
		for(GameEndsListener listener : gameEndsListeners) {
			listener.OnGameEnds(winningPlayer);
		}
	}
	
	public synchronized void setShipsPlaced(Player player) {
		if(player instanceof LocalPlayer && ((LocalPlayer) player).map.getShipsNr() != getAvailableShips().size()) {
			//nicht alle wurden platziert
			((LocalPlayer) player).notifyOnNotAllShipsPlaced();
			return;
		}
		if(player == ownPlayer) ownPlayerShipsPlaced = true;
		else oppPlayerShipsPlaced = true;
	}
}
