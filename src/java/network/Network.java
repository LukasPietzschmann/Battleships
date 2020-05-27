package network;

import logic.Logic;
import logic.Player;
import logic.Ship;

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
	
	public Network(Logic l, String n) {
		super(l, n);
	}
	
	@Override
	public boolean doWhatYouHaveToDo() {
		// warte bis gegner geschossen hat
		try {
			Message m = new Message(networkPartner.recieveMessage());
			Ship ship = logic.shoot(m.getArgs()[Message.COL_POS], m.getArgs()[Message.ROW_POS], this);
			int a;
			if(ship != null) {
				if(ship.isAlive()) a = 1;
				else a = 2;
			}else a = 0;
			networkPartner.sendMessage(String.format("%s %d\n", ANSWER, a));
			
			//wenn hier false dann muss auf pass gewartet werden
			m = new Message(networkPartner.recieveMessage());
			if(!m.whatKindOfStringIsThis().equals(PASS)) throw new UnexpectedMessageException(m);
			return a != 0;
		}catch(UnknownMessageException | UnexpectedMessageException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public Ship hit(int x, int y) {
		networkPartner.sendMessage(String.format("%s %d %d\n", SHOOT, y, x));
		Message m = null;
		try {
			m = new Message(networkPartner.recieveMessage());
			if(!m.whatKindOfStringIsThis().equals(ANSWER)) throw new UnexpectedMessageException(m);
		}catch(UnknownMessageException | UnexpectedMessageException e) {
			e.printStackTrace();
		}
		
		//wenn hier false dann pass senden
		networkPartner.sendMessage(String.format("%s\n", PASS));
		return m.getArgs()[Message.ANSWER_POS] != 0;
	}
	
	@Override
	public void placeShips() {
		try {
			Message m = new Message(networkPartner.recieveMessage());
			if(!m.whatKindOfStringIsThis().equals(CONFIRM)) throw new UnexpectedMessageException(m);
		}catch(UnknownMessageException | UnexpectedMessageException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean isAlive() {
		return true;
	}
}
