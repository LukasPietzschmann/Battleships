package network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class NetworkThread extends Thread {
	private enum Type {
		Server,
		Client
	}
	
	private Socket clientSocket;
	private ServerSocket serverSocket;
	private final BlockingQueue<String> recieveQueue;
	private final BlockingQueue<String> sendQueue;
	private final Type type;
	
	public NetworkThread(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
		type = Type.Server;
		recieveQueue = new ArrayBlockingQueue<>(1);
		sendQueue = new ArrayBlockingQueue<>(1);
	}
	
	public NetworkThread(Socket clientSocket) {
		this.clientSocket = clientSocket;
		type = Type.Client;
		recieveQueue = new ArrayBlockingQueue<>(1);
		sendQueue = new ArrayBlockingQueue<>(1);
	}
	
	@Override
	public void run() {
		super.run();
		
		try {
			BufferedReader in;
			OutputStreamWriter out;
			if(type == Type.Server) {
				Socket socket = serverSocket.accept();
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new OutputStreamWriter(socket.getOutputStream());
				while(true) {
					//noinspection DuplicatedCode
					while(true) {
						String msg = sendQueue.take();
						if(msg == null) continue;
						System.out.println("Server sent: Message " + msg.replace("\n", ""));
						out.write(msg);
						out.flush();
						break;
					}
					while(true) {
						String msg = in.readLine();
						if(msg == null) continue;
						System.out.println("Server recieved: Message " + msg.replace("\n", ""));
						recieveQueue.offer(msg);
						break;
					}
				}
			}else {
				in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				out = new OutputStreamWriter(clientSocket.getOutputStream());
				while(true) {
					while(true) {
						String msg = in.readLine();
						if(msg == null) continue;
						System.out.println("Client recieved: Message " + msg.replace("\n", ""));
						recieveQueue.offer(msg);
						break;
					}
					//noinspection DuplicatedCode
					while(true) {
						String msg = sendQueue.take();
						if(msg == null) continue;
						System.out.println("Client sent: Message " + msg.replace("\n", ""));
						out.write(msg);
						out.flush();
						break;
					}
				}
			}
		}catch(Exception e) {
			System.err.println("NE Error");
			e.printStackTrace();
		}
	}
	
	public synchronized boolean sendMessage(String message) {
		return sendQueue.offer(message);
	}
	
	public synchronized String recieveMessage() {
		try {
			return recieveQueue.take();
		}catch(InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}
}
