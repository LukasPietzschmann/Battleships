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
	 * Initialisiet den LocalPlayer
	 * @param l "Zurück-Referenz" auf das Logik Objekt.
	 * @param size Die festgelegte Größe des Spielfelds.
	 */
	public LocalPlayer(Logic l, int size) {
		super(l);
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
	
	/**
	 * Registriert einen {@link MapListener}.
	 * @param listener Der zu registrierende Listener.
	 */
	public void registerOnMapChangedListener(MapListener listener) {
		mapListeners.add(listener);
	}
	
	/**
	 * Registriert einen {@link GameListener}.
	 * @param listener Der zu registrierende Listener.
	 */
	@Override
	public void registerGameListener(GameListener listener) {
		super.registerGameListener(listener);
		registerOnMapChangedListener(listener);
		notifyOnMapChangedListeners();
	}
	
	/**
	 * Registriert einen {@link MakeMoveListener}.
	 * @param listener Der zu registrierende Listener.
	 * @param clickQueue Die Queue, in der Die Koordinaten von Maus Klicks gespeichert werden.
	 */
	public void registerMakeMove(MakeMoveListener listener, BlockingQueue<int[]> clickQueue) {
		if(clickQueue != null) this.clickQueue = clickQueue;
		makeMoveListeners.add(listener);
	}
	
	/**
	 * Benachrichtigt einen registrierten {@link MapListener}, dass sich das Spielfeld geändert hat.
	 */
	private void notifyOnMapChangedListeners() {
		for(MapListener listener : mapListeners) listener.OnMapChanged(map);
	}
	
	/**
	 * Benachrichtigt einen registrierten {@link MapListener}, dass sich das Spielfeld des Gegners geändert hat.
	 */
	private void notifyOnEnemyMapChangedListener(){
		for(GameListener listener : enemyGameListeners) {
			listener.OnMapChanged(map.getEnemyPerspective());
		}
	}
	
	/**
	 * Benachrichtigt einen registrierten {@link MapListener}, dass ein Schiff platziert wurde.
	 * @param ship Das zu platziernde Schiff
	 */
	private void notifyOnShipPlacedListeners(Ship ship) {
		for(MapListener listener : mapListeners) listener.OnShipPlaced(ship);
	}
	
	/**
	 * Benachrichtigt einen registrierten {@link MapListener}, dass alle Schiffe platziert wurden.
	 */
	private void notifyOnAllShipsPlacedListeners() {
		for(MapListener listener : mapListeners) listener.OnAllShipsPlaced();
	}
	
	/**
	 * Benachrichtigt einen registrierten {@link MapListener}, dass noch nicht alle Schiffe platziert worden
	 * sind und das Spiel ergo noch nicht begonnen werden kann.
	 */
	protected void notifyOnNotAllShipsPlaced(){
		for(MapListener listener : mapListeners) {
			listener.OnNotAllShipsPlaced();
		}
	}
	
	/**
	 * Benachrichtigt einen registrierten {@link MakeMoveListener}, dass er mit seinem Zug an der Reihe ist.
	 */
	protected void notifyMakeMove() {
		for(MakeMoveListener makeMoveListener : makeMoveListeners) {
			makeMoveListener.makeMove();
		}
	}
	
	/**
	 * Gibt an, ob ein Schiff dort platziert werden kann. Koordinaten müssen nicht extra angegeben werden,
	 * da das {@link Ship} diese enthält.
	 * @param ship Das zu platzierende Schiff.
	 * @return {@code true}, falls das Shiff platziert werden darf. Sonst {@code false}.
	 */
	public boolean canShipBePlaced(Ship ship) {
		return map.canShipBePlaced(ship);
	}
	
	/**
	 * Platziert ein Schiff. Es wird nicht überprüft, ob das Schiff platziert werden darf.
	 * Das vorherige Aufrufen von {@link #canShipBePlaced(Ship)} wird vorrausgesetzt.
	 * @param ship Das zu platzierende Shiff.
	 */
	public void placeShip(Ship ship) {
		if(!canShipBePlaced(ship)) return;
		map.placeShip(ship);
		notifyOnShipPlacedListeners(ship);
	}

	public Map getMap(){
		return map;
	}
}
