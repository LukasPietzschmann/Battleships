package logic;

public interface GameListener extends MapListener{
	void OnHit(Map map, int x, int y, boolean hit);
}
