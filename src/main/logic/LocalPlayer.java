package logic;

import java.security.KeyFactory;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

/**
 * Die Klasse LocalPlayer modelliert einen Spieler, der am selben Computer sitzt.
 */
public abstract class LocalPlayer extends Player {
	protected Map map;
	protected ArrayList<MapListener> mapListeners;
	protected ArrayList<MakeMoveListener> makeMoveListeners;
	protected BlockingQueue<int[]> clickQueue;
	
	/**
	 * @param l "Zurück-Referenz" auf das Logik Objekt.
	 * @param size Die festgelegte Größe des Spielfelds.
	 * @param name Der vom Spieler festgelegte Name. Dient nur zur Anzeige in der GUI.
	 */
	public LocalPlayer(Logic l, int size, String name) {
		super(l, name);
		map = new Map(size);
		mapListeners = new ArrayList<>();
		makeMoveListeners = new ArrayList<>();
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
		if(hit == null) {
			//notifyOnHit(x, y, false);
			//notifyOnEnemyHit(x, y,false);
			notifyOnMapChangedListeners();
			notifyOnEnemyMapChangedListener();
		}
		else if(!hit.isAlive()) {
			notifyOnMapChangedListeners();
			notifyOnEnemyMapChangedListener();
		}
		else {
			//notifyOnHit(x, y, true);
			//notifyOnEnemyHit(x,y,true);
			notifyOnMapChangedListeners();
			notifyOnEnemyMapChangedListener();
		}
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
		
		notifyOnMapChangedListeners();
		notifyOnAllShipsPlacedListeners();
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
	
	public void registerOnMapChangedListener(MapListener listener) {
		mapListeners.add(listener);
	}
	
	@Override
	public void registerGameListener(GameListener listener) {
		super.registerGameListener(listener);
		registerOnMapChangedListener(listener);
		notifyOnMapChangedListeners();
	}
	
	public void registerMakeMove(MakeMoveListener listener, BlockingQueue<int[]> clickQueue) {
		if(clickQueue != null) this.clickQueue = clickQueue;
		makeMoveListeners.add(listener);
	}
	
	private void notifyOnMapChangedListeners() {
		for(MapListener listener : mapListeners) listener.OnMapChanged(map);
	}
	
	private void notifyOnEnemyMapChangedListener(){
		for(GameListener listener : enemyGameListeners) {
			listener.OnMapChanged(map.getEnemyPerspective());
		}
	}
	
	private void notifyOnShipPlacedListeners(Ship ship) {
		for(MapListener listener : mapListeners) listener.OnShipPlaced(ship);
	}
	
	private void notifyOnAllShipsPlacedListeners() {
		for(MapListener listener : mapListeners) listener.OnAllShipsPlaced();
	}
	
	protected void notifyOnNotAllShipsPlaced(){
		for(MapListener listener : mapListeners) {
			listener.OnNotAllShipsPlaced();
		}
	}
	
	protected void notifyMakeMove() {
		for(MakeMoveListener makeMoveListener : makeMoveListeners) {
			makeMoveListener.makeMove();
		}
	}
	
	public boolean canShipBePlaced(Ship ship) {
		return map.canShipBePlaced(ship);
	}
	
	public void placeShip(Ship ship) {
		if(!canShipBePlaced(ship)) return;
		map.placeShip(ship);
		notifyOnShipPlacedListeners(ship);
	}
}
