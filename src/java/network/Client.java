package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client implements NetworkCommunication{
	private final String address;
	private final int port;
	private Socket socket;
	
	private DataInputStream in;
	private DataOutputStream out;
	
	public Client(String address, int port) {
		this.address = address;
		this.port = port;
	}
	
	public boolean connect() {
		try {
			socket = new Socket(address, port);
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public boolean sendMessage(String message) {
		try {
			out.writeUTF(message);
			return true;
		}catch(IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public String recieveMessage() {
		try {
			return in.readUTF();
		}catch(IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
