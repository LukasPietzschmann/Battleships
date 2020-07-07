package logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Die Klasse Human modelliert einen echten Spieler.
 */
public class Human extends LocalPlayer {
	
	/**
	 * @param l "Zurück-Referenz" auf das Logik Objekt.
	 * @param size Die festgelegte Größe des Spielfelds.
	 * @param name Der vom Spieler festgelegte Name. Dient nur zur Anzeige in der GUI.
	 */
	public Human(Logic l, int size, String name) {
		this(l, new Map(size), name);
	}
	
	public Human(Logic l, Map map, String name){
		super(l, map, name);
	}
	
	/**
	 * Implementierung für einen echten Spieler. {@inheritDoc}
	 *
	 * @return {@inheritDoc}
	 */
	@Override
	public boolean doWhatYouHaveToDo() {
		int x = 0;
		int y = 0;
		try {
			BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("x: ");
			x = Integer.parseInt(bf.readLine());
			System.out.print("y: ");
			y = Integer.parseInt(bf.readLine());
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		return logic.shoot(x, y, this) != null;
	}
	
	/**
	 * Implementierung für einen echten Spieler. {@inheritDoc}
	 */
	@Override
	public void placeShips() {
		randomShipPlacment();
	}
}
