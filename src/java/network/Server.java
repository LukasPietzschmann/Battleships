package network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
  private final int port;
  private ServerSocket server;
  private Socket socket;
  
  public Server(int port) {
    this.port = port;
    
    try {
      server = new ServerSocket(port);
      socket = server.accept();
      System.out.println("Connected");
      
      DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
      while (true) System.out.println(in.readUTF());
    }catch (IOException e) {
      e.printStackTrace();
    }
  }
}


class ServerTest {
  public static void main(String[] args) {
    Server server = new Server(4444);
  }
}
