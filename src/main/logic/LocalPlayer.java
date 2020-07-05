package logic;

import java.util.ArrayList;

/**
 * Die Klasse LocalPlayer modelliert einen Spieler, der am selben Computer sitzt.
 */
public abstract class LocalPlayer extends Player {
	protected Map map;
	protected ArrayList<OnMapChangedListener> listeners;
	
	/**
	 * @param l "Zurück-Referenz" auf das Logik Objekt.
	 * @param size Die festgelegte Größe des Spielfelds.
	 * @param name Der vom Spieler festgelegte Name. Dient nur zur Anzeige in der GUI.
	 */
	public LocalPlayer(Logic l, int size, String name) {
		super(l, name);
		map = new Map(size);
		listeners = new ArrayList<>();
	}
	
	/**
	 * Implementierung für einen LocalPlayer. {@inheritDoc}
	 *
	 * @param x {@inheritDoc}
	 * @param y {@inheritDoc}
	 * @return {@inheritDoc}
	 */
	@Override
	public Ship hit(int x, int y) {
		Ship hit = map.hit(x, y);
		notifyListeners();
		return hit;
	}
	
	/**
	 * Platziert alle Schiffe zufällig auf dem Spielfeld.
	 *
	 * @return {@code true}, falls die Schiffe erfolgreich platziert werden kommten. Sonst {@code false}.
	 */
	public boolean randomShipPlacment() {//random zahlen x und y erzeugen für alle boote
		map.reset();
		ArrayList<Ship> ships = logic.getAvailableShips();
		if(!solveForShip(ships, 0)) {
			map.reset();
			System.err.print("Fehlerhaftes Schiffsetzen neustarten");
			return false;
		}
		
		notifyListeners();
		return true;
	}
	
	/**
	 * Hilfsmethode für {@link #randomShipPlacment()}
	 *
	 * @param ships Eine Liste an allen zu setzenden Schiffen.
	 * @param index Der Index des Schiffs, das platziert werden soll
	 * @return {@code true}, falls das Schiff platziert werden konnte. Sonst {@code false}.
	 */
	private boolean solveForShip(ArrayList<Ship> ships, int index) {
		if(index >= ships.size()) {
			return true;
		}
		Ship ship = ships.get(index);
		for(int i = 0; i < 100; i++) {
			ship.randomize(map.getSize());
			if(map.canShipBePlaced(ship)) {
				map.placeShip(new Ship(ship));
				if(solveForShip(ships, index + 1)) {
					return true;
				}
				map.removeShip(ship);
			}
		}
		return false;
	}
	
	/**
	 * Implementierung für einen LocalPlayer. {@inheritDoc}
	 *
	 * @return {@inheritDoc}
	 */
	@Override
	public boolean isAlive() {
		return map.shipsNr() > 0;
	}
	
	public void dumpMap() {
		map.dump();
	}
	
	public void registerOnMapChangedListener(OnMapChangedListener listener) {
		listeners.add(listener);
	}
	
	private void notifyListeners(){
		for(OnMapChangedListener listener : listeners) listener.OnMapChanged(map);
	}
}
