package network;

import logic.GameEndsListener;
import logic.Logic;
import logic.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Der NetworkThread empfängt und sendet durchgehend Nachrichten.
 */
public class NetworkThread implements GameEndsListener {
	private Logic logic;
	
	private Socket clientSocket;
	private ServerSocket serverSocket;
	private final BlockingQueue<String> recieveQueue;
	private final BlockingQueue<String> sendQueue;
	
	/**
	 * Konstruktor, falls man selbst der Server ist.
	 * @param serverSocket Der Server Socket.
	 * @param logic "Zurück-Referenz" auf das Logic Objekt.
	 */
	public NetworkThread(ServerSocket serverSocket, Logic logic) {
		this.logic = logic;
		this.serverSocket = serverSocket;
		recieveQueue = new LinkedBlockingQueue<>();
		sendQueue = new LinkedBlockingQueue<>();
		
		Thread serverSendThread = new Thread(() -> {
			try {
				OutputStreamWriter out = new OutputStreamWriter(clientSocket.getOutputStream());
				
				while(!serverSocket.isClosed()) {
					try {
						String msg = sendQueue.take();
						out.write(msg);
						out.flush();
						System.out.println("Server sent " + msg);
					}catch(Exception e) {
						break;
					}
				}
				
				out.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
		});
		
		Thread serverRecieveThread = new Thread(() -> {
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				
				while(!serverSocket.isClosed()) {
					try {
						String msg = in.readLine();
						if(msg == null){
							logic.notifyOppLeftListener();
							break;
						}
						recieveQueue.offer(msg);
						System.out.println("Server recieved " + msg);
					}catch(SocketException e) {
						break;
					}
				}
				
				in.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
		});
		
		Thread acceptT = new Thread(() -> {
			try {
				clientSocket = serverSocket.accept();
			}catch(IOException e) {
				e.printStackTrace();
			}
			serverSendThread.start();
			serverRecieveThread.start();
			
		});
		
		acceptT.start();
	}
	
	/**
	 * Konstruktor, falls man selbst der Client ist.
	 * @param clientSocket Der Socket.
	 * @param logic "Zurück-Referenz" auf das Logic Objekt.
	 */
	public NetworkThread(Socket clientSocket, Logic logic) {
		this.logic = logic;
		this.clientSocket = clientSocket;
		recieveQueue = new LinkedBlockingQueue<>();
		sendQueue = new LinkedBlockingQueue<>();
		
		Thread clientSendThread = new Thread(() -> {
			try {
				OutputStreamWriter out = new OutputStreamWriter(clientSocket.getOutputStream());
				
				while(!clientSocket.isClosed()) {
					try {
						String msg = sendQueue.take();
						out.write(msg);
						out.flush();
						System.out.println("Client sent " + msg);
					}catch(Exception e) {
						break;
					}
				}
				
				out.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
		});
		
		Thread clientRecieveThread = new Thread(() -> {
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				
				while(!clientSocket.isClosed()) {
					try {
						String msg = in.readLine();
						if(msg == null){
							logic.notifyOppLeftListener();
							break;
						}
						recieveQueue.offer(msg);
						System.out.println("Client recieved " + msg);
					}catch(SocketException e) {
						break;
					}
				}
				
				in.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
		});
		
		clientSendThread.start();
		clientRecieveThread.start();
	}
	
	/**
	 * Sendet eine Nachricht. Es ist nicht gewährleistet, dass die Nachricht direkt gesendet wird.
	 * @param message Die zu sendende Nachricht.
	 * @return {@code true}, falls gesendet werden kann, sonst {@code false}.
	 */
	public synchronized boolean sendMessage(String message) {
		return sendQueue.offer(message);
	}
	
	/**
	 * Empfängt eine Nachricht. Es ist nicht gewährleistet, dass beim Aufruf schon eine NAchricht zur verfügung steht.
	 * @return Die empfangene Nachricht.
	 */
	public synchronized String recieveMessage() {
		try {
			return recieveQueue.take();
		}catch(InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @param winningPlayer {@inheritDoc}
	 */
	@Override
	public void OnGameEnds(Player winningPlayer) {
		try {
			if(clientSocket != null)clientSocket.close();
			if(serverSocket != null)serverSocket.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void OnOpponentLeft() {
		OnGameEnds(null);
	}
}
