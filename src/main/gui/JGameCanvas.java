package gui;

import logic.*;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.accessibility.AccessibleContext;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Die Klasse JGameCanvas stellt ein Spielfeld dar.
 */
public class JGameCanvas extends JPanel implements GameListener, MakeMoveListener, GameEndsListener {
	private static final long serialVersionUID = 1L;
	private static final int tW = 32; // tile width
	private static final int tH = 32; // tile height
	private final Tile[][] map;
	private boolean myTurn = false;
	
	private static final Tile[] numbers = {Tile.N1, Tile.N2, Tile.N3, Tile.N4, Tile.N5, Tile.N6, Tile.N7, Tile.N8, Tile.N9, Tile.N10, Tile.N11, Tile.N12, Tile.N13, Tile.N14, Tile.N15, Tile.N16, Tile.N17, Tile.N18, Tile.N19, Tile.N20, Tile.N21, Tile.N22, Tile.N23, Tile.N24, Tile.N25, Tile.N26, Tile.N27, Tile.N28, Tile.N29, Tile.N30};
	private static final Tile[] fiveElementHorizontal = {Tile.FIVEELEMENTONE_HORIZONTAL, Tile.FIVEELEMENTTWO_HORIZONTAL, Tile.FIVEELEMENTTHREE_HORIZONTAL, Tile.FIVEELEMENTFOUR_HORIZONTAL, Tile.FIVELEMENTFIVE_HORIZONTAL};
	private static final Tile[] fourElementHorizontal = {Tile.FOURELEMENTONE_HORIZONTAL, Tile.FOURELEMENTTWO_HORIZONTAL, Tile.FOURELEMENTTHREE_HORIZONTAL, Tile.FOURELEMENTFOUR_HORIZONTAL};
	private static final Tile[] threeElementHorizontal = {Tile.THREEELEMENTONE_HORIZONTAL, Tile.THREEELEMENTTWO_HORIZONTAL, Tile.THREEELEMENTTHREE_HORIZONTAL};
	private static final Tile[] twoElementHorizontal = {Tile.TWOELEMENTONE_HORIZONTAL, Tile.TWOELEMENTTWO_HORIZONTAL};
	private static final Tile[] fiveElementVertical = {Tile.FIVEELEMENTONE_VERTICAL, Tile.FIVEELEMENTTWO_VERTICAL, Tile.FIVEELEMENTTHREE_VERTICAL, Tile.FIVEELEMENTFOUR_VERTICAL, Tile.FIVEELEMENTFIVE_VERTICAL};
	private static final Tile[] fourElementVertical = {Tile.FOURELEMENTONE_VERTICAL, Tile.FOURELEMENTTWO_VERTICAL, Tile.FOURELEMENTTHREE_VERTICAL, Tile.FOURELEMENTFOUR_VERTICAL};
	private static final Tile[] threeElementVertical = {Tile.THREEELEMENTONE_VERTICAL, Tile.THREEELEMENTWO_VERTICAL, Tile.THREEELEMENTHREE_VERTICAL};
	private static final Tile[] twoElementVertical = {Tile.TWOELEMENTONE_VERTICAL, Tile.TWOELEMENTTWO_VERTICAL};
	
	private int numberCounterHorizontal = 0;
	private int numberCounterVertical = 0;
	
	private static Image tileset;
	private int groesse;
	private BlockingQueue<int[]> clickQueue;
	
	/**
	 * Konstruktor, erstellt ein Spielfeld-Objekt.
	 *
	 * @param size Größe des Spielfelds.
	 */
	public JGameCanvas(int size) {
		clickQueue = new LinkedBlockingQueue<>();
		groesse = size + 1;
		loadTileSet();
		map = new Tile[groesse][groesse];
		initialGrid();
		addMouseMotionListener(new MouseAdapter() {
			public void mouseMoved(MouseEvent e) {
				int panelsize = getWidth();
				double tilesize = (double) panelsize / (double) groesse;
				int x = e.getX();
				int y = e.getY();
				int xGrid = (int) ((double) x / tilesize);
				int yGrid = (int) ((double) y / tilesize);
				if(xGrid != 0 && yGrid != 0) {
					setToolTipText(xGrid + ", " + yGrid);
				}else {
					setToolTipText(null);
				}
			}
		});
		
		addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int panelsize = getWidth();
				double tilesize = (double) panelsize / (double) groesse;
				int x = e.getX();
				int y = e.getY();
				int xGrid = (int) ((double) x / tilesize);
				int yGrid = (int) ((double) y / tilesize);
				if(xGrid != 0 && yGrid != 0 && myTurn) {
					clickQueue.offer(new int[] {xGrid - 1, yGrid - 1});
					myTurn = false;
				}
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
			
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
			
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
			
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
			
			}
		});
	}
	
	/**
	 * Füllt die Map nur mit Koordinaten und Hintergrund-Tiles. Es werden keine Schiffe platziert.
	 */
	public void initialGrid() {
		for(int i = 0; i < groesse; i++) {
			for(int j = 0; j < groesse; j++) {
				if(j == 0 || i == 0) {
					if(j == 0 && i == 0) {
						map[j][i] = Tile.GREY;
					}
					if(j != 0) {
						map[j][i] = numbers[numberCounterVertical];
						numberCounterVertical++;
					}
					if(j == 0 && i != 0) {
						map[j][i] = numbers[numberCounterHorizontal];
						numberCounterHorizontal++;
					}
				}else {
					map[j][i] = Tile.BACKGROUND;
				}
			}
		}
		numberCounterHorizontal = 0;
		numberCounterVertical = 0;
	}
	
	/**
	 * Zeichnet das Spielfeld
	 *
	 * @param g Graphic_Objekt
	 */
	protected void paintComponent(Graphics g) {
		int w = getWidth();
		int h = getHeight();
		for(int i = 0; i < groesse; i++) {
			for(int j = 0; j < groesse; j++) {
				drawTile(g, map[j][i], i * h / groesse, j * w / groesse);
			}
		}
	}
	
	/**
	 * Zeichnet die einzelnen Tiles des Spielfelds
	 *
	 * @param g Graphics-Objekt.
	 * @param t zu zeichnender Tile.
	 * @param x x-Koordinate an der gezeichnet werden soll.
	 * @param y y-Koordinate an der gezeichnet werden soll.
	 */
	protected void drawTile(Graphics g, Tile t, int x, int y) {
		// map Tile from the tileset
		int mx = t.ordinal() % 10;
		int my = t.ordinal() / 10;
		int w = getWidth();
		int h = getHeight();
		g.drawImage(tileset, x, y, x + w / groesse, y + h / groesse, mx * tW, my * tH, mx * tW + tW, my * tH + tH, this);
	}
	
	/**
	 * Liefert die präferrierte Größe des Spielfelds zurück
	 *
	 * @return {@link Dimension}
	 */
	public Dimension getPreferredSize() {
		Dimension d = super.getPreferredSize();
		Container c = getParent();
		if(c != null) {
			d = c.getSize();
		}else {
			return new Dimension(10, 10);
		}
		int w = (int) d.getHeight();
		int h = (int) d.getWidth();
		int s = (Math.min(w, h));
		return new Dimension(s, s);
	}
	
	/**
	 * Zeigt ein neues Schiff an.
	 *
	 * @param ship Das neu platzierte Schiff.
	 */
	private void placeShip(Ship ship) {
		int length = ship.getSize();
		Direction direction = ship.getDirection();
		int x = ship.getXPos() + 1;
		int y = ship.getYPos() + 1;
		for(int i = 0; i < length; i++) {
			
			if(direction == Direction.north) {
				if(length == 5) map[y][x] = fiveElementVertical[i];
				if(length == 4) map[y][x] = fourElementVertical[i];
				if(length == 3) map[y][x] = threeElementVertical[i];
				if(length == 2) map[y][x] = twoElementVertical[i];
			}else if(direction == Direction.south) {
				if(length == 5) map[y][x] = fiveElementVertical[4 - i];
				if(length == 4) map[y][x] = fourElementVertical[3 - i];
				if(length == 3) map[y][x] = threeElementVertical[2 - i];
				if(length == 2) map[y][x] = twoElementVertical[1 - i];
			}else if(direction == Direction.west) {
				if(length == 5) map[y][x] = fiveElementHorizontal[i];
				if(length == 4) map[y][x] = fourElementHorizontal[i];
				if(length == 3) map[y][x] = threeElementHorizontal[i];
				if(length == 2) map[y][x] = twoElementHorizontal[i];
			}else if(direction == Direction.east) {
				if(length == 5) map[y][x] = fiveElementHorizontal[4 - i];
				if(length == 4) map[y][x] = fourElementHorizontal[3 - i];
				if(length == 3) map[y][x] = threeElementHorizontal[2 - i];
				if(length == 2) map[y][x] = twoElementHorizontal[1 - i];
			}
			
			switch(ship.getDirection()) {
				case north:
					y++;
					break;
				case south:
					y--;
					break;
				case east:
					x--;
					break;
				case west:
					x++;
					break;
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @param ship Das platzierte Schiff.
	 */
	@Override
	public void OnShipPlaced(Ship ship) {
		placeShip(ship);
		repaint();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void OnAllShipsPlaced() {
		return;
	}
	
	/**
	 * {@inheritDoc}
	 * @param map Die geänderte Map.
	 */
	@Override
	public void OnMapChanged(Map map) {
		for(int x = 0; x < map.getSize(); x++) {
			for(int y = 0; y < map.getSize(); y++) {
				int stat = map.getStat(x, y);
				switch(stat) {
					case Map.WATER:
						this.map[y + 1][x + 1] = Tile.BACKGROUND;
						break;
					case Map.SUCC_HIT:
						this.map[y + 1][x + 1] = Tile.HIT_WATER;
						break;
					case Map.UNSUCC_HIT:
						this.map[y + 1][x + 1] = Tile.MISS;
						break;
					case Map.SHIP:
						Ship ship = map.getShip(x, y);
						if(ship.getXPos() == x && ship.getYPos() == y) placeShip(ship);
						break;
					case Map.DEFINITELY_NO_SHIP:
						this.map[y + 1][x + 1] = Tile.DEFINITELY_NO_SHIP;
						break;
				}
			}
		}
		repaint();
	}
	
	/**
	 * {@inheritDoc}
	 * @param x x Koordinate des Treffers.
	 * @param y y Koordinate des Treffers.
	 * @param hit {@code true}, falls ein Schiff getroffen wurde, sonst {@code false}.
	 */
	@Override
	public void OnHit(int x, int y, boolean hit) {
		if(hit) this.map[y + 1][x + 1] = Tile.HIT_WATER;
		else this.map[y + 1][x + 1] = Tile.MISS;
		repaint();
		//repaint(100, (x + 1) * (getWidth() / groesse), (y + 1) * (getHeight() / groesse), 50, 50);
	}
	
	/**
	 * Lädt das Tileset des ausgewählten Spielthemas.
	 */
	public void loadTileSet() {
		String theme = Launcher.theme;
		
		switch(theme) {
			case "Battleships":
				ImageIcon tilesetIcon = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("tileset_battleships.png"))).getImage());
				tileset = tilesetIcon.getImage();
				break;
			case "Battlecars":
				tilesetIcon = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("tileset_battlecars.png"))).getImage());
				tileset = tilesetIcon.getImage();
				break;
		}
	}
	
	/**
	 * Gibt die größe des Spielfelds zurück.
	 * @return
	 */
	public int getGroesse() {
		return groesse;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void makeMove() {
		myTurn = true;
	}
	
	/**
	 * Gibt die Queue mit den gemachten Mausklicks zurück.
	 * @return Die Queue mit den gemachten Mausklicks.
	 */
	public BlockingQueue<int[]> getClickQueue() {
		return clickQueue;
	}
	
	/**
	 * {@inheritDoc}
	 * @param winningPlayer Referenz auf den Spieler der gewonnen hat.
	 */
	@Override
	public void OnGameEnds(Player winningPlayer) {
		myTurn = false;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void OnNotAllShipsPlaced() {
		return;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void OnOpponentLeft() {
		myTurn = false;
	}
}