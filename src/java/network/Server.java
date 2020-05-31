package network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements NetworkCommunication {
	private final int port;
	private ServerSocket server;
	private Socket socket;
	
	private BufferedReader in;
	private OutputStreamWriter out;

	public Server(int port) throws IOException {
		this.port = port;
		server = new ServerSocket(port);
		waitForClient();
	}

	private boolean waitForClient() {
		try {
			socket = server.accept();
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new OutputStreamWriter(socket.getOutputStream());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean sendMessage(String message) {
		try {
			out.write(message);
			out.flush();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public String recieveMessage() {
		try {
			return in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
