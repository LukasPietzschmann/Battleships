package ai;

import logic.Logic;
import logic.Map;
import logic.Player;
import logic.Ship;

import java.util.Random;

/**
 * Die Klasse EasyAI modelliert die einfachste AI.
 */
class EasyAI extends PlayableAI {
	/**
	 * Initialisiert die EasyAI
	 * @param player Referenz auf die von {@link Player} erbende Klasse. In diesem Fall meistens {@link AI}.
	 * @param logic "zurück-Referenz" auf die {@link Logic}.
	 * @param map Das eigene Spielfeld.
	 */
	public EasyAI(Player player, Logic logic, Map map) {
		super(player, logic, map);
	}
	
	/**
	 * {@inheritDoc} Die EasyAI schießt komplett zufällig.
	 *
	 * @return {@inheritDoc}
	 */
	@Override
	public Ship makeMove() {
		Random rnd = new Random();
		try {
			Thread.sleep(WAIT_TIME);
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		return logic.shoot(rnd.nextInt(map.getSize()), rnd.nextInt(map.getSize()), player);
	}
}
