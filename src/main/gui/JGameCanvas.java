package gui;

import logic.Direction;
import logic.Launcher;
import logic.Map;
import logic.Ship;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

//FIXME Direction wird bei der Rotation der Tiles nicht berücksichtigt
public class JGameCanvas extends JPanel implements OnMapChangedListener {
	private static final long serialVersionUID = 1L;
	private static final int tW = 32; // tile width
	private static final int tH = 32; // tile height
	private static Tile[][] map = null;
	
	private static final Tile numbers[] = {Tile.N1, Tile.N2, Tile.N3, Tile.N4, Tile.N5, Tile.N6, Tile.N7, Tile.N8, Tile.N9, Tile.N10,
					Tile.N11, Tile.N12, Tile.N13, Tile.N14, Tile.N15, Tile.N16, Tile.N17, Tile.N18, Tile.N19, Tile.N20,
					Tile.N21, Tile.N22, Tile.N23, Tile.N24, Tile.N25, Tile.N26, Tile.N27, Tile.N28, Tile.N29, Tile.N30};
	private static final Tile fiveElement[] = {Tile.FIVEELEMENTONE, Tile.FIVEELEMENTTWO, Tile.FIVEELEMENTTHREE, Tile.FIVEELEMENTFOUR, Tile.FIVELEMENTFIVE};
	private static final Tile fourElement[] = {Tile.FOURELEMENTONE, Tile.FOURELEMENTTWO, Tile.FOURELEMENTTHREE, Tile.FOURELEMENTFOUR};
	private static final Tile threeElement[] = {Tile.THREEELEMENTONE, Tile.THREEELEMENTTWO, Tile.THREEELEMENTTHREE};
	private static final Tile twoElement[] = {Tile.TWOELEMENTONE, Tile.TWOELEMENTTWO};
	
	private int numberCounterHorizontal = 0;
	private int numberCounterVertical = 0;
	
	private static Image tileset;
	int groesse;
	
	public JGameCanvas() {
		groesse = Launcher.gridSize + 1;
		ImageIcon tileset2 = new ImageIcon(new ImageIcon("src/res/tileset6.png").getImage());
		tileset = tileset2.getImage();
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
	}
	
	public void initialGrid() {
		for(int i = 0; i < groesse; i++) {
			for(int j = 0; j < groesse; j++) {
				if(j == 0 || i == 0) {
					if(j == 0 && i == 0) {
						map[j][i] = Tile.GREY;
					}
					if(j != 0 && i == 0) {
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
		
		/*for(int i = 0; i < groesse; i++) {
			for(int j = 0; j < groesse; j++) {
				map[i][j] = Tile.GREY;
			}
		}*/
	}
	
	protected void paintComponent(Graphics g) {
		int w = getWidth();
		int h = getHeight();
		System.out.println(w + " " + h);
		int min = Math.min(w, h);
		for(int i = 0; i < groesse; i++) {
			for(int j = 0; j < groesse; j++) {
				drawTile(g, map[j][i], i * h / groesse, j * w / groesse);
			}
		}
	}
	
	protected void drawTile(Graphics g, Tile t, int x, int y) {
		// map Tile from the tileset
		int mx = t.ordinal() % 10;
		int my = t.ordinal() / 10;
		int w = getWidth();
		int h = getHeight();
		int min = Math.min(w, h);
		g.drawImage(tileset, x, y, x + w / groesse, y + h / groesse,
						mx * tW, my * tH, mx * tW + tW, my * tH + tH, this);
	}
	
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
		int s = (w < h ? w : h);
		return new Dimension(s, s);
	}
	
	public boolean placeShip(Ship ship) {
		int length = ship.getSize();
		int x = ship.getXPos() + 1;
		int y = ship.getYPos() + 1;
		for(int i = 0; i < length; i++) {
			/*if(length == 5) map[y][x] = fiveElement[i];
			else if(length == 4) map[y][x] = fourElement[i];
			else if(length == 3) map[y][x] = threeElement[i];
			else if(length == 2) map[y][x] = twoElement[i];*/
			map[y][x] = Tile.HIT;
			
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
		return true;
	}
	
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
						this.map[y + 1][x + 1] = Tile.HIT;
						break;
					case Map.UNSUCC_HIT:
						this.map[y + 1][x + 1] = Tile.MISS;
						break;
					case Map.SHIP:
						Ship ship = map.getShip(x,y);
						if(ship.getXPos() == x && ship.getYPos() == y) placeShip(ship);
						break;
				}
			}
		}
		repaint();
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(600, 600));
		frame.setMinimumSize(new Dimension(600, 600));
		
		JGameCanvas canvas = new JGameCanvas();
		canvas.setLayout(new GridLayout(0, 1));
		canvas.setVisible(true);
		
		frame.getContentPane().add(canvas);
		frame.setVisible(true);
		
		Map map = new Map(Launcher.gridSize);
		Ship ship = new Ship(1, 8, Direction.west, 3);
		map.placeShip(ship);
	}
}