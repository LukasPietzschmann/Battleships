package network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class NetworkThread extends Thread {
	private enum Type {
		Host, Client
	}
	
	Socket clientSocket;
	ServerSocket serverSocket;
	BlockingQueue<String> recieveQueue;
	BlockingQueue<String> sendQueue;
	private BufferedReader in;
	private OutputStreamWriter out;
	private Type type;
	private int size;
	
	public NetworkThread(ServerSocket serverSocket, int size) {
		this.serverSocket = serverSocket;
		type = Type.Host;
		this.size = size;
		recieveQueue = new SynchronousQueue<>();
		sendQueue = new SynchronousQueue<>();
	}
	
	public NetworkThread(Socket clientSocket) {
		this.clientSocket = clientSocket;
		type = Type.Client;
		recieveQueue = new SynchronousQueue<>();
		sendQueue = new SynchronousQueue<>();
	}
	
	@Override
	public void run() {
		super.run();
		
		try {
			if(type == Type.Host) {
			
			}
		}catch(Exception e) {
			System.err.println("NE Error");
			e.printStackTrace();
		}
	}
	
	public boolean sendMessage(String message) {
		return sendQueue.offer(message);
	}
	
	public String recieveMessage() {
		try {
			return recieveQueue.take();
		}catch(InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}
}
