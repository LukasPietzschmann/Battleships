package network;

import logic.Logic;

class ClientTest {
	public static void main(String[] args) {
		Logic logic = new Logic("Pl", "Nw", "127.0.0.1");
	}
}

class ServerTest {
	public static void main(String[] args) throws Exception{
		Logic logic = new Logic("PL", "NW", 10);
	}
}
