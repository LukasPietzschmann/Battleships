package network;

import logic.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Die Klasse Network modelliert einen Spieler an einem anderen Computer.
 */
public class Network extends Player implements SaveListener {
	public static final int PORT = 4444;
	public static final String SHOOT = "shot";
	public static final String SETUP = "setup";
	public static final String CONFIRM = "confirmed";
	public static final String ANSWER = "answer";
	public static final String PASS = "pass";
	public static final String SAVE = "save";
	public static final String LOAD = "load";
	private NetworkThread networkThread;
	private int size;
	private ArrayList<Ship> ships;
	
	private int shipCount;
	
	private Map map;
	
	/**
	 * Der Konstruktor, falls man selbst der Server ist.
	 *
	 * @param logic "Zurück-Referenz" auf das Logik Objekt. Typischerweise {@code this}.
	 * @param size Die Größe des Spielfelds.
	 * @throws IOException Falls der Server nicht erstellt werden kann.
	 */
	public Network(Logic logic, int size) throws IOException {
		super(logic);
		map = new Map(size);
		networkThread = new NetworkThread(new ServerSocket(PORT), logic);
		//networkThread.start();
		logic.registerGameEndListener(networkThread);
		ResourceManager.getInstance().registerSaveListener(this);
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
	 * @param ip Die IP-Adresse des Servers.
	 * @param port Der Port des Servers.
	 */
	public Network(Logic logic, String ip, int port) {
		super(logic);
		try {
			networkThread = new NetworkThread(new Socket(ip, port), logic);
			logic.registerGameEndListener(networkThread);
			ResourceManager.getInstance().registerSaveListener(this);
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
			int id = m.getArgs()[Message.ID_POS];
			//TODO laden
		}else throw new UnexpectedMessageException(m);
	}
	
	/**
	 * Gibt die Größe des Spielfelds zurück. Wird nur verwendet falls man selbst der Client ist.
	 *
	 * @return Die größe des Spielfelds.
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * Gibt alle zu platzierenden Schiffe zurück. Wird nur verwendet falls man selbst der Client ist.
	 *
	 * @return Alle zu platzierenden Schiffe.
	 */
	public ArrayList<Ship> getShips() {
		return ships;
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * @return Rückgabe des Schiffs.
	 */
	@Override
	public Ship yourTurn() {
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
			if(!m.getMessageType().equals(PASS)) throw new UnexpectedMessageException(m);
		}
		return ship;
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * @param x Die x-Koordinate des Schusses
	 * @param y Die y-Koordinate des Schusses
	 * @return {@inheritDoc}
	 */
	@Override
	public Ship hit(int x, int y) {
		networkThread.sendMessage(String.format("%s %d %d\n", SHOOT, y, x));
		Message m = new Message(networkThread.recieveMessage());
		if(!m.getMessageType().equals(ANSWER)) throw new UnexpectedMessageException(m);
		
		// wenn hier false dann pass senden
		int answ = m.getArgs()[Message.ANSWER_POS];
		if(answ == 0) {
			map.setHit(x, y, false);
			notifyOnHit(x, y, false);
			notifyOnEnemyHit(x, y, false);
			networkThread.sendMessage(String.format("%s\n", PASS));
			return null;
		}
		if(answ == 1) {
			map.setHit(x, y, true);
			notifyOnHit(x, y, true);
			notifyOnEnemyHit(x, y, true);
			return Ship.defaultShip(x, y);
		}
		map.setHit(x, y, true);
		map.surroundShip(x, y);
		notifyOnHit(x, y, true);
		notifyOnEnemyHit(x, y, true);
		for(GameListener listener : enemyGameListeners) {
			listener.OnMapChanged(map);
		}
		shipCount -= 1;
		return Ship.defaultSunkenShip(x, y);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void placeShips() {
		Message m = new Message(networkThread.recieveMessage());
		if(!m.getMessageType().equals(CONFIRM)) throw new UnexpectedMessageException(m);
		logic.setShipsPlaced(this);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isAlive() {
		return shipCount > 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void oppPlacedShips() {
		networkThread.sendMessage(String.format("%s\n", CONFIRM));
	}
	
	@Override
	public void OnGameSaved(int id) {
		System.out.println("Ja man");
		networkThread.sendMessage(String.format("%s %d\n", SAVE, id));
	}
}
