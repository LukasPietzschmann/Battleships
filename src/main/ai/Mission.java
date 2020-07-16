package ai;

import logic.Direction;
import logic.Map;

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
	private Direction lastHitDir;
	private Direction unsureDir;
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
				unsureDir = Direction.values()[rnd.nextInt(Direction.values().length)];
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
	
	private boolean isValidDirection(Direction dir, int x, int y) {
		return map.isInMap(getXInDirection(dir, x), getYInDirection(dir, y));
	}
	
	private int getXInDirection(Direction dir, int x) {
		switch(dir) {
			case west:
				return x - 1;
			case east:
				return x + 1;
			default:
				return x;
		}
	}
	
	private int getYInDirection(Direction dir, int y) {
		switch(dir) {
			case north:
				return y - 1;
			case south:
				return y + 1;
			default:
				return y;
		}
	}
	
	private Direction mirrorDirection(Direction dir) {
		switch(dir) {
			case north:
				return Direction.south;
			case south:
				return Direction.north;
			case west:
				return Direction.east;
			case east:
				return Direction.west;
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
