package logic;

public interface GameListener extends MapListener{
	/**
	 * Wird aufgerufen, sobald ein Spielfeld getroffen wurde.
	 * @param x x Koordinate des Treffers.
	 * @param y y Koordinate des Treffers.
	 * @param hit {@code true}, falls ein Schiff getroffen wurde, sonst {@code false}.
	 */
	void OnHit(int x, int y, boolean hit);
}
