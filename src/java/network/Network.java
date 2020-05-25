package network;

import logic.Logic;
import logic.Player;

import java.io.IOException;

public class Network extends Player {
	private static final int PORT = 1234;
	public static final String SHOOT = "SHOT";
	public static final String SETUP = "SETUP";
	public static final String CONFIRM = "CONFIRMED";
	public static final String ANSWER = "ANSWER";
	public static final String PASS = "PASS";
	public static final String SAVE = "SAVE";
	public static final String LOAD = "LOAD";
	private NetworkCommunication networkPartner;
	
	@Override
	public boolean doWhatYouHaveToDo() {
		return false;
	}
	
	@Override
	public boolean hit(int x, int y) {
		networkPartner.sendMessage(String.format("%s %d %d\n", SHOOT, y, x));
		try {
			Message m = new Message(networkPartner.recieveMessage());
			if(!m.whatKindOfStringIsThis().equals(ANSWER)) throw new UnexpectedMessageException(m);
			return m.getArgs()[Message.ANSWER_POS] != 0;
		}catch(UnknownMessageException | UnexpectedMessageException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public void placeShips() {
	
	}
	
	@Override
	public void ready() {
	
	}
	
	/**
	 * Client-Konstruktor
	 *
	 * @param logic Referenz auf die das Logik Objekt
	 * @param name  Name des Spielers
	 * @param ip    Die Ip des Gegners (Hosts)
	 */
	public Network(Logic logic, String name, String ip) {
		super.Player(logic, name);
		
		networkPartner = new Client(ip, PORT);
		if(!((Client) networkPartner).connect()) return; //TODO Exception
		
		try {
			//TODO was mache ich mit der Größe?
			Message m = new Message(networkPartner.recieveMessage());
			if(!m.whatKindOfStringIsThis().equals(SETUP)) throw new UnexpectedMessageException(m);
		}catch(UnknownMessageException | UnexpectedMessageException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Server-Konstruktor
	 *
	 * @param logic Referenz auf die das Logik Objekt
	 * @param name  Name des Spielers
	 * @param size  Größe des Spielfelds
	 */
	public Network(Logic logic, String name, int size) throws IOException {
		super.Player(logic, name);
		
		networkPartner = new Server(PORT);
		if(!((Server) networkPartner).waitForClient()) return; //TODO Exception
		
		//TODO get ships
		//networkPartner.sendMessage(String.format("%s %d %d %d %d %d", SETUP));
	}
}
