package network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
  private final String address;
  private final int port;
  private Socket socket;
  
  public Client(String address, int port) {
    this.address = address;
    this.port = port;
  }
  
  public boolean connect() {
    try {
      socket = new Socket(address, port);
      System.out.println("Connected");
      
      BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
      DataOutputStream out = new DataOutputStream(socket.getOutputStream());
      while (true) out.writeUTF(reader.readLine());
    }catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }
}


class ClientTest {
  public static void main(String[] args) {
    Client client = new Client("127.0.0.1", 4444);
    client.connect();
  }
}

