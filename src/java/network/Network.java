package network;

import logic.Logic;
import logic.Player;
import logic.Ship;

import java.io.IOException;
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
	private NetworkCommunication networkPartner;
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
		networkPartner = new Server(PORT);
		//TODO echte Zahlen als Schiff anzahl nehmen
		networkPartner.sendMessage(String.format("%s %d %d %d %d %d", SETUP, size, 0, 0, 0, 0));
		Message m = new Message(networkPartner.recieveMessage());
		if(!m.getMessageType().equals(CONFIRM)) throw new UnexpectedMessageException(m);
		
		// jetzt sind wir dran mit Schiffe platzieren
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
		networkPartner = new Client(ip, PORT);
		Message m = new Message(networkPartner.recieveMessage());
		if(!m.getMessageType().equals(SETUP)) throw new UnexpectedMessageException(m);
		
		size = m.getArgs()[Message.SIZE_POS];
		
		ships = new ArrayList<>();
		int[] posis = new int[] {Message.SHIPS2_POS, Message.SHIPS3_POS, Message.SHIPS4_POS, Message.SHIPS5_POS};
		for(int i = 0; i < posis.length; i++) {
			for(int j = 0; j < m.getArgs()[posis[i]]; j++) {
				//FIXME gibt vllt Fehler, da die selben Schiffe eingefügt werden. (equals und hasCode in Ship)
				ships.add(new Ship(0, 0, Ship.Direction.north, i + 2));
			}
		}
		shipCount = ships.size();
		networkPartner.sendMessage(CONFIRM);
	}
	
	public int getSize() {
		return size;
	}
	
	public ArrayList<Ship> getShips() {
		return ships;
	}
	
	@Override
	public boolean doWhatYouHaveToDo() {
		// warte bis gegner geschossen hat
		
		Message m = new Message(networkPartner.recieveMessage());
		Ship ship = logic.shoot(m.getArgs()[Message.COL_POS], m.getArgs()[Message.ROW_POS], this);
		int a;
		if(ship != null) {
			if(ship.isAlive()) a = 1;
			else a = 2;
		}else a = 0;
		networkPartner.sendMessage(String.format("%s %d", ANSWER, a));
		
		// wenn hier false dann muss auf pass gewartet werden
		m = new Message(networkPartner.recieveMessage());
		if(!m.getMessageType().equals(PASS))
			throw new UnexpectedMessageException(m);
		return a != 0;
	}
	
	@Override
	public Ship hit(int x, int y) {
		networkPartner.sendMessage(String.format("%s %d %d", SHOOT, y, x));
		Message m = new Message(networkPartner.recieveMessage());
		if(!m.getMessageType().equals(ANSWER))
			throw new UnexpectedMessageException(m);
		
		// wenn hier false dann pass senden
		int answ = m.getArgs()[Message.ANSWER_POS];
		if(answ == 0) networkPartner.sendMessage(String.format("%s", PASS));
		
		return Ship.sunkenShip(x, y);
	}
	
	@Override
	public void placeShips() {
		Message m = new Message(networkPartner.recieveMessage());
		if(!m.getMessageType().equals(CONFIRM))
			throw new UnexpectedMessageException(m);
	}
	
	@Override
	public boolean isAlive() {
		return shipCount > 0;
	}
}
