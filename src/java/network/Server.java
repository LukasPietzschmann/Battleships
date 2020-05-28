package network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements NetworkCommunication {
	private final int port;
	private ServerSocket server;
	private Socket socket;

	private DataInputStream in;
	private DataOutputStream out;

	public Server(int port) throws IOException {
		this.port = port;
		server = new ServerSocket(port);
		waitForClient();
	}

	private boolean waitForClient() {
		try {
			socket = server.accept();
			in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			out = new DataOutputStream(socket.getOutputStream());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean sendMessage(String message) {
		try {
			out.writeUTF(message);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public String recieveMessage() {
		try {
			return in.readUTF();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
