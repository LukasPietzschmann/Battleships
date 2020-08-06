package logic;

public interface GameListener extends MapListener{
	void OnHit(int x, int y, boolean hit);
}
