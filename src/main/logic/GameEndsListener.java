package logic;

public interface GameEndsListener {
	void OnGameEnds(Player winningPlayer);
	void OnOpponentLeft();
}
