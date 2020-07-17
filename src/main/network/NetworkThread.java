package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

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
		recieveQueue = new LinkedBlockingQueue<>();
		sendQueue = new LinkedBlockingQueue<>();
		
		try {
			clientSocket = serverSocket.accept();
		}catch(IOException e) {
			e.printStackTrace();
		}
		Thread serverSendThread = new Thread(() -> {
			try {
				OutputStreamWriter out = new OutputStreamWriter(clientSocket.getOutputStream());
				
				while(true) {
					String msg = sendQueue.take();
					out.write(msg);
					out.flush();
					System.out.println("Server sent " + msg);
				}
			}catch(IOException | InterruptedException e) {
				e.printStackTrace();
			}
		});
		
		Thread serverRecieveThread = new Thread(() -> {
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				
				while(true) {
					String msg = in.readLine();
					recieveQueue.offer(msg);
					System.out.println("Server recieved " + msg);
				}
			}catch(IOException e) {
				e.printStackTrace();
			}
		});
		
		serverSendThread.start();
		serverRecieveThread.start();
	}
	
	public NetworkThread(Socket clientSocket) {
		this.clientSocket = clientSocket;
		type = Type.Client;
		recieveQueue = new LinkedBlockingQueue<>();
		sendQueue = new LinkedBlockingQueue<>();
		
		Thread clientSendThread = new Thread(() -> {
			try {
				OutputStreamWriter out = new OutputStreamWriter(clientSocket.getOutputStream());
				
				while(true) {
					String msg = sendQueue.take();
					out.write(msg);
					out.flush();
					System.out.println("Client sent " + msg);
				}
			}catch(IOException | InterruptedException e) {
				e.printStackTrace();
			}
		});
		
		Thread clientRecieveThread = new Thread(() -> {
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				
				while(true) {
					String msg = in.readLine();
					recieveQueue.offer(msg);
					System.out.println("Client recieved " + msg);
				}
			}catch(IOException e) {
				e.printStackTrace();
			}
		});
		
		clientSendThread.start();
		clientRecieveThread.start();
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
