package network;

public interface NetworkCommunication {
	boolean sendMessage(String message);
	String recieveMessage();
}
