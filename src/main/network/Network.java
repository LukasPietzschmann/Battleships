package network;

import logic.Direction;
import logic.GameListener;
import logic.Logic;
import logic.Map;
import logic.Player;
import logic.Ship;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Die Klasse Network modelliert einen Spieler an einem anderen Computer.
 */
public class Network extends Player {
	/**
	 * Der über den man sich verbindet.
	 */
	private static final int PORT = 1234;
	/**
	 * Das Keyword für eine Schuss.
	 */
	public static final String SHOOT = "SHOT";
	/**
	 * Das Keyword für das Setup.
	 */
	public static final String SETUP = "SETUP";
	/**
	 * Das Keyword für die Bestätigung von {@value SETUP}.
	 */
	public static final String CONFIRM = "CONFIRMED";
	/**
	 * Das Keyword zum zurückgeben, ob getroffen wurde, oder nicht.
	 */
	public static final String ANSWER = "ANSWER";
	/**
	 * Das Keyword, ob man seinen Zug überspringt.
	 */
	public static final String PASS = "PASS";
	/**
	 * Das Keyword zum speichern des Spielstands.
	 */
	public static final String SAVE = "SAVE";
	/**
	 * Das Keyword zum laden des Spielspands.
	 */
	public static final String LOAD = "LOAD";
	/**
	 * Die Verbindung zum anderen Spieler.
	 */
	private NetworkThread networkThread;
	/**
	 * Die Größe des Spielfelds. Wird nur gesetzt, falls man selbst der Client ist.
	 */
	private int size;
	/**
	 * Alle zu platzierenden Shiffe. Werden nur gesetzt, falls man selbst der Client ist.
	 */
	private ArrayList<Ship> ships;
	
	/**
	 * Die Anzahl aller noch lebender Schiffe.
	 */
	private int shipCount;
	
	private Map map;
	
	public Network(Logic logic, String name, long id) throws IOException {
		super(logic, name);
		networkThread = new NetworkThread(new ServerSocket(PORT));
		//networkThread.start();
		logic.registerGameEndListener(networkThread);
		networkThread.sendMessage(String.format("%s %d\n", LOAD, id));
		Message m = new Message(networkThread.recieveMessage());
		if(!m.getMessageType().equals(CONFIRM)) throw new UnexpectedMessageException(m);
	}
	
	/**
	 * Der Konstruktor, falls man selbst der Server ist.
	 *
	 * @param logic "Zurück-Referenz" auf das Logik Objekt. Typischerweise {@code this}.
	 * @param name Der vom Spieler festgelegte Name. Dient nur zur Anzeige in der GUI.
	 * @param size Die Größe des Spielfelds.
	 * @throws IOException Falls der Server nicht erstellt werden kann.
	 */
	public Network(Logic logic, String name, int size) throws IOException {
		super(logic, name);
		map = new Map(size);
		networkThread = new NetworkThread(new ServerSocket(PORT));
		//networkThread.start();
		logic.registerGameEndListener(networkThread);
		shipCount = logic.getAvailableShips().size();
		int[] shipCount = new int[4];
		for(int i = 0; i < logic.getAvailableShips().size(); i++)
			shipCount[logic.getAvailableShips().get(i).getSize() - 2] += 1;
		networkThread.sendMessage(String.format("%s %d %d %d %d %d\n", SETUP, size, shipCount[3], shipCount[2], shipCount[1], shipCount[0]));
	}
	
	/**
	 * Konstruktor, falls man selbst der Client ist.
	 *
	 * @param logic "Zurück-Referenz" auf das Logik Objekt. Typischerweise {@code this}.
	 * @param name Der vom Spieler festgelegte Name. Dient nur zur Anzeige in der GUI.
	 * @param ip Die IP-Adresse des Servers.
	 */
	public Network(Logic logic, String name, String ip) {
		super(logic, name);
		try {
			networkThread = new NetworkThread(new Socket(ip, PORT));
			logic.registerGameEndListener(networkThread);
		}catch(IOException e) {
			e.printStackTrace();
			System.err.println("NW Error");
			return;
		}
		//networkThread.start();
		Message m = new Message(networkThread.recieveMessage());
		
		if(m.getMessageType().equals(SETUP)) {
			size = m.getArgs()[Message.SIZE_POS];
			map = new Map(size);
			
			ships = new ArrayList<>();
			int[] posis = new int[] {Message.SHIPS2_POS, Message.SHIPS3_POS, Message.SHIPS4_POS, Message.SHIPS5_POS};
			for(int i = 0; i < posis.length; i++) {
				for(int j = 0; j < m.getArgs()[posis[i]]; j++) ships.add(new Ship(0, 0, Direction.north, i + 2));
			}
			shipCount = ships.size();
		}else if(m.getMessageType().equals(LOAD)) {
			//TODO implement
		}else throw new UnexpectedMessageException(m);
	}
	
	public int getSize() {
		return size;
	}
	
	public ArrayList<Ship> getShips() {
		return ships;
	}
	
	@Override
	public Ship doWhatYouHaveToDo() {
		// warte bis gegner geschossen hat
		
		Message m = new Message(networkThread.recieveMessage());
		Ship ship = logic.shoot(m.getArgs()[Message.COL_POS], m.getArgs()[Message.ROW_POS], this);
		int a;
		if(ship != null) {
			if(ship.isAlive()) a = 1;
			else {
				a = 2;
				//shipCount -= 1;
			}
		}else a = 0;
		networkThread.sendMessage(String.format("%s %d\n", ANSWER, a));
		
		// wenn hier false dann muss auf pass gewartet werden
		if(a == 0) {
			m = new Message(networkThread.recieveMessage());
			if(!m.getMessageType().equals(PASS))
				throw new UnexpectedMessageException(m);
		}
		return ship;
	}
	
	@Override
	public Ship hit(int x, int y) {
		networkThread.sendMessage(String.format("%s %d %d\n", SHOOT, y, x));
		Message m = new Message(networkThread.recieveMessage());
		if(!m.getMessageType().equals(ANSWER))
			throw new UnexpectedMessageException(m);
		
		// wenn hier false dann pass senden
		int answ = m.getArgs()[Message.ANSWER_POS];
		if(answ == 0) {
			map.setHit(x,y,false);
			notifyOnHit(x,y,false);
			notifyOnEnemyHit(x,y,false);
			networkThread.sendMessage(String.format("%s\n", PASS));
			return null;
		}
		if(answ == 1) {
			map.setHit(x,y,true);
			notifyOnHit(x,y,true);
			notifyOnEnemyHit(x,y,true);
			return Ship.defaultShip(x, y);
		}
		map.setHit(x,y,true);
		map.surroundShip(x,y);
		notifyOnHit(x,y,true);
		notifyOnEnemyHit(x,y,true);
		for(GameListener listener : enemyGameListeners) {
			listener.OnMapChanged(map);
		}
		shipCount -= 1;
		return Ship.defaultSunkenShip(x, y);
	}
	
	@Override
	public void placeShips() {
		Message m = new Message(networkThread.recieveMessage());
		if(!m.getMessageType().equals(CONFIRM))
			throw new UnexpectedMessageException(m);
		logic.setShipsPlaced(this);
	}
	
	@Override
	public boolean isAlive() {
		return shipCount > 0;
	}
	
	@Override
	public void oppPlacedShips() {
		networkThread.sendMessage(String.format("%s\n", CONFIRM));
	}
}
