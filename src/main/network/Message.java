package network;

import java.util.Locale;

import static network.Network.*;

/**
 * Die Klasse Message modelliert eine übers Netzwerk gesendete Nachricht.
 */
public class Message {
	public static final int SIZE_POS = 0;
	public static final int SHIPS5_POS = 1;
	public static final int SHIPS4_POS = 2;
	public static final int SHIPS3_POS = 3;
	public static final int SHIPS2_POS = 4;
	public static final int ROW_POS = 0;
	public static final int COL_POS = 1;
	public static final int ANSWER_POS = 0;
	public static final int ID_POS = 0;
	private final String type;
	private int[] args;
	
	/**
	 * Initialisiert die Message
	 *
	 * @param message Der Reine String der übers Netzwerk geschickt wurde.
	 * @throws UnknownMessageException Falls die ankommende Nachricht nicht erkannt wurde.
	 */
	public Message(String message) throws UnknownMessageException {
		String[] splitted = message.split(" ");
		String msgType = splitted[0];
		msgType.toUpperCase(Locale.ROOT);
		if(msgType.contains(SHOOT)) {
			type = SHOOT;
			args = new int[2];
			for(int i = 1; i < 3; i++) args[i - 1] = Integer.parseInt(splitted[i]);
		}else if(msgType.contains(SETUP)) {
			type = SETUP;
			args = new int[5];
			for(int i = 1; i < 6; i++) args[i - 1] = Integer.parseInt(splitted[i]);
		}else if(msgType.contains(CONFIRM)) type = CONFIRM;
		else if(msgType.contains(ANSWER)) {
			type = ANSWER;
			args = new int[1];
			args[0] = Integer.parseInt(splitted[1]);
		}else if(msgType.contains(PASS)) type = PASS;
		else if(msgType.contains(SAVE)) {
			type = SAVE;
			args = new int[1];
			args[0] = Integer.parseInt(splitted[1]);
		}else if(msgType.contains(LOAD)) {
			type = LOAD;
			args = new int[1];
			args[0] = Integer.parseInt(splitted[1]);
		}else throw new UnknownMessageException(message);
	}
	
	/**
	 * Gibt die Art der Nachricht zurück.
	 *
	 * @return Art der Nachricht
	 */
	public String getMessageType() {
		return type;
	}
	
	/**
	 * Gibt die Argumente der Nachricht zurück.
	 *
	 * @return Ein Array an Argumenten.
	 */
	public int[] getArgs() {
		return args;
	}
	
	/**
	 * Gibt die String Repräsentation der Nachricht zurück.
	 *
	 * @return String-Repräsentation der Nachricht.
	 */
	@Override
	public String toString() {
		return type;
	}
}
