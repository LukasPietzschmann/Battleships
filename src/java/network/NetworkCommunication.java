package network;

public interface NetworkCommunication {
	public boolean sendMessage(String message);
	public String recieveMessage();
}
