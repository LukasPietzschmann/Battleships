package network;

import java.io.IOException;

class ClientTest{
	public static void main(String[] args) {
		new Network(null,"Client", "127.0.0.1");
	}
}

class ServerTest{
	public static void main(String[] args) {
		try {
			new Network(null,"Server",5);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
