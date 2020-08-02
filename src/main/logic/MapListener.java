package logic;

import logic.Map;

public interface MapListener {
	/**
	 * Wird aufgerufen, wenn sich die Map verändert.
	 * @param map Die geänderte Map.
	 */
	void OnMapChanged(Map map);
	/**
	 * Wird aufgerufen, wenn ein Schiff platziert wurde.
	 * @param ship Das platzierte Schiff.
	 */
	void OnShipPlaced(Ship ship);
	/**
	 * Wird aufgerufen, wenn alle Schiffe platziert worden sind.
	 */
	void OnAllShipsPlaced();
	/**
	 * Wird aufgerufen, wenn das Spiel gestartet werden soll, aber noch nicht alle Schiffe platziert worden sind.
	 */
	void OnNotAllShipsPlaced();
}
