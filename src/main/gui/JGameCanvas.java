package gui;

import java.awt.Color;
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
 
public class JGameCanvas extends JPanel{
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
    	groesse = GuiTester.gridSize + 1;
    	ImageIcon tileset2 = new ImageIcon(new ImageIcon("src/res/tileset6.png").getImage());
    	tileset = tileset2.getImage();
    	map = new Tile[groesse][groesse];
    	initialGrid();
    	addMouseMotionListener(new MouseAdapter() {
			public void mouseMoved(MouseEvent e) {
				int panelsize = getWidth();
				double tilesize = (double)panelsize / (double)groesse;
				int x = e.getX();
				int y = e.getY();
				int xGrid = (int)((double) x / tilesize);
				int yGrid = (int)((double) y / tilesize);
				if (xGrid != 0 && yGrid != 0) {
					setToolTipText(xGrid + ", " + yGrid);
				} else {
					setToolTipText(null);
				}
			}
		});
    }
    
    public void initialGrid() {
    	for (int i = 0; i < groesse; i++) {
    		for (int j = 0; j < groesse; j++) {
    			if (j == 0 || i == 0) {
    				if (j == 0 && i == 0) {
    					map[j][i] = Tile.GREY;
    				}
    				if (j != 0 && i == 0) {
    					map[j][i] = numbers[numberCounterVertical];
    					numberCounterVertical++;
    				}
    				if (j == 0 && i != 0) {
    					map[j][i] = numbers[numberCounterHorizontal];
    					numberCounterHorizontal++;
    				}
    			} else {
    				map[j][i] = Tile.BACKGROUND;
    			}
    		}
    	}
    	numberCounterHorizontal = 0;
		numberCounterVertical = 0;
    }
 
    protected void paintComponent(Graphics g) {
    	int w = getWidth();
    	int h = getHeight();
    	System.out.println(w +" "+ h);
    	int min = Math.min(w, h);
        for(int i=0;i<groesse;i++) {
        	for(int j=0;j<groesse;j++) {
        		drawTile(g, map[j][i], i*h/groesse,j*w/groesse);
            }
        }
 
     }
           
    protected void drawTile(Graphics g, Tile t, int x, int y){
        // map Tile from the tileset
        int mx = t.ordinal()%10;
        int my = t.ordinal()/10;
        int w = getWidth();
    	int h = getHeight();
    	int min = Math.min(w, h);
        g.drawImage(tileset, x, y, x+w/groesse, y+h/groesse,
                mx*tW, my*tH,  mx*tW+tW, my*tH+tH, this); 
    }
    
    public Dimension getPreferredSize() {
    	Dimension d = super.getPreferredSize();
    	Container c = getParent();
    	if (c != null) {
    		d = c.getSize();
    	} else {
    		return new Dimension(10, 10);
    	}
    	int w = (int) d.getHeight();
    	int h = (int) d.getWidth();
    	int s = (w < h ? w : h);
    	return new Dimension(s,s);
    }
    
    public boolean placeShip(int length, boolean isHorizontal, int x, int y) {
    	for (int i = 0; i < length; i++) {
    		if (length == 5) {
    			map[y][x] = fiveElement[i];
    			x++;
    		}
    		if (length == 4) {
    			map[y][x] = fourElement[i];
    			x++;
    		}
    		if (length == 3) {
    			map[y][x] = threeElement[i];
    			x++;
    		}
    		if (length == 2) {
    			map[y][x] = twoElement[i];
    			x++;
    		}
    		repaint();
    	}
    	return true;
    }
    
    public static void main(String[] args) {
    	
    	JFrame frame = new JFrame();
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setPreferredSize(new Dimension(600, 600));
		frame.setMinimumSize(new Dimension(600, 600));
	
		JGameCanvas canvas = new JGameCanvas();
		canvas.setLayout(new GridLayout(0, 1));
		canvas.setVisible(true);
		canvas.placeShip(5, true, 1, 2);
		canvas.placeShip(4, true, 3, 5);
		
		frame.getContentPane().add(canvas);
		frame.setVisible(true);
    	
    }
}