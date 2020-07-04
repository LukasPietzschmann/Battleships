package ai;

import logic.Logic;
import logic.Map;
import logic.Player;

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
	public boolean makeMove() {
		Random rnd = new Random();
		
		return logic.shoot(rnd.nextInt(map.getSize()), rnd.nextInt(map.getSize()), player) != null;
	}
}
