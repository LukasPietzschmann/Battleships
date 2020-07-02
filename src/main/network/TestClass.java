package network;

import logic.Logic;

class ClientTest {
	public static void main(String[] args) {
		Logic logic = new Logic("AI", "NW", "127.0.0.1");
		logic.ownPlayer.placeShips();
		logic.startGame();
	}
}

class ServerTest {
	public static void main(String[] args) throws Exception{
		Logic logic = new Logic("AI", "AW", Difficulty.easy, 10, 1,1,1,1);
		logic.ownPlayer.placeShips();
		logic.startGame();
	}
}
