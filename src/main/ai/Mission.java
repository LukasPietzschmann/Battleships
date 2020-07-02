package ai;

import logic.Map;
import logic.Ship;

import java.util.Random;

public class Mission {
	private enum State {
		firstHit,
		someHits
	}
	
	private State state;
	private int lastXHit;
	private int lastYHit;
	private int startX;
	private int startY;
	private int nextX;
	private int nextY;
	private Ship.Direction lastHitDir;
	private Ship.Direction unsureDir;
	private Map map;
	
	public Mission(int startX, int startY, Map map) {
		this.startX = startX;
		this.startY = startY;
		lastXHit = startX;
		lastYHit = startY;
		
		this.map = map;
		
		state = State.firstHit;
		getNextPoint(true);
	}
	
	private boolean getNextPoint(boolean hit) {
		if(state == State.firstHit) {
			Random rnd = new Random();
			do {
				unsureDir = Ship.Direction.values()[rnd.nextInt(Ship.Direction.values().length)];
			}while(!isValidDirection(unsureDir, startX, startY));
			nextX = getXInDirection(unsureDir, startX);
			nextY = getYInDirection(unsureDir, startY);
			lastXHit = nextX;
			lastYHit = nextY;
			return true;
		}
		if(state == State.someHits) {
			if(hit) {
				if(isValidDirection(lastHitDir, lastXHit, lastYHit)) {
					nextX = getXInDirection(lastHitDir, lastXHit);
					nextY = getYInDirection(lastHitDir, lastYHit);
					lastXHit = nextX;
					lastYHit = nextY;
					return true;
				}else {
					unsureDir = mirrorDirection(lastHitDir);
					if(isValidDirection(unsureDir, startX, startY)) {
						nextX = getXInDirection(unsureDir, startX);
						nextY = getYInDirection(unsureDir, startY);
						lastXHit = nextX;
						lastYHit = nextY;
						return true;
					}else return false;
				}
			}else {
				unsureDir = mirrorDirection(lastHitDir);
				if(isValidDirection(unsureDir, startX, startY)) {
					nextX = getXInDirection(unsureDir, startX);
					nextY = getYInDirection(unsureDir, startY);
					lastXHit = nextX;
					lastYHit = nextY;
					return true;
				}else return false;
			}
		}
		
		return false;
	}
	
	private boolean isValidDirection(Ship.Direction dir, int x, int y) {
		return map.isInMap(getXInDirection(dir, x), getYInDirection(dir, y));
	}
	
	private int getXInDirection(Ship.Direction dir, int x) {
		switch(dir) {
			case west:
				return x - 1;
			case east:
				return x + 1;
			default:
				return x;
		}
	}
	
	private int getYInDirection(Ship.Direction dir, int y) {
		switch(dir) {
			case north:
				return y - 1;
			case south:
				return y + 1;
			default:
				return y;
		}
	}
	
	private Ship.Direction mirrorDirection(Ship.Direction dir) {
		switch(dir) {
			case north:
				return Ship.Direction.south;
			case south:
				return Ship.Direction.north;
			case west:
				return Ship.Direction.east;
			case east:
				return Ship.Direction.west;
		}
		
		return null;
	}
	
	public int getNextX() {
		return nextX;
	}
	
	public int getNextY() {
		return nextY;
	}
	
	public void wasHit(boolean hit) {
		if(hit && unsureDir != null) {
			lastHitDir = unsureDir;
			unsureDir = null;
		}
		if(state == State.firstHit && hit) {
			state = State.someHits;
		}
		getNextPoint(hit);
	}
}
