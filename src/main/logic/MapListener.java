package logic;

import logic.Map;

public interface MapListener {
	void OnMapChanged(Map map);
	void OnShipPlaced(Ship ship);
	void OnAllShipsPlaced();
	void OnNotAllShipsPlaced();
}
