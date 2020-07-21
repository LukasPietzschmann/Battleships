package Schiffeversenken.ai;

import Schiffeversenken.logic.*;

import java.util.Random;

class EasyAI extends PlayableAI {
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
