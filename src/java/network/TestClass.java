package network;

class ClientTest {
	public static void main(String[] args) {
		Network nwC = new Network(null, "NWC", "127.0.0.1");
	}
}

class ServerTest {
	public static void main(String[] args) {
		Network nwS = new Network(null, "NWS", 10);
	}
}
