package gui;

import logic.Launcher;
import logic.LocalPlayer;
import logic.Logic;
import logic.Ship;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameWindow {
	private JFrame frame;
	private String mode;
	private JPanel mainPanel = new JPanel();
	private JPanel gridHolder = new JPanel(new GridBagLayout());
	JGameCanvas grid = new JGameCanvas();
	private JPanel options = new JPanel();
	private JLabel title = new JLabel();
	private JPanel elements = new JPanel();
	private JPanel elementsCounter = new JPanel();
	private JPanel buttons = new JPanel();
	private JButton randomButton = new JButton();
	private JButton startButton = new JButton();
	private JButton soundButton = new JButton();
	private JLabel fiveFieldElementIcon = MainMenu.fiveFieldElementIcon;
	private JLabel fourFieldElementIcon = MainMenu.fourFieldElementIcon;
	private JLabel threeFieldElementIcon = MainMenu.threeFieldElementIcon;
	private JLabel twoFieldElementIcon = MainMenu.twoFieldElementIcon;
	
	private int fiveFieldElementCount = 0;
	private int fourFieldElementCount = 0;
	private int threeFieldElementCount = 0;
	private int twoFieldElementCount = 0;
	
	static Color textColor = MainMenu.textColor;
	static Color backgroundColor = MainMenu.backgroundColor;
	Font font = new Font("Krungthep", Font.PLAIN, 20);
	String elementSelected = null;
	
	private Logic logic;
	private LocalPlayer player;
	
	public GameWindow(JFrame frame, String mode, Logic logic) {
		this.frame = frame;
		this.mode = mode;
		this.logic = logic;
		player = logic.getOwnPlayer();
		player.registerOnMapChangedListener(grid);
	}
	
	public void setUpGameWindow() {
		//		Grid gridPlayer1 = new Grid(GuiTester.gridSize);
		//		Grid gridPlayer2 = new Grid(GuiTester.gridSize);
		if(mode.equals("pvp") || mode.equals("pvc")) {
			setUpPlaceWindow();
		}else {
			// Methode, die die Elemente für beide Spielfelder automatisch füllt
		}
	}
	
	public void backToMenu() {
		frame.remove(mainPanel);
		MainMenu menu = new MainMenu(frame);
		menu.setUpMenu();
	}
	
	public void setUpPlaceWindow() {
		
		// mainPanel Panel Settings
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setOpaque(true);
		mainPanel.setBackground(backgroundColor);
		mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 70, 30, 30));
		
		// mainPanel Panel Layout
		mainPanel.add(gridHolder, BorderLayout.CENTER);
		mainPanel.add(options, BorderLayout.EAST);
		
		// gridHolder Panel Settings & Layout
		gridHolder.setOpaque(false);
		gridHolder.add(grid);
		
		// grid Panel Settings
		grid.setOpaque(false);
		grid.setSize(grid.getPreferredSize());
		grid.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int panelsize = grid.getWidth();
				int height = grid.getHeight(); // zu löschen
				int width = grid.getWidth(); // zu löschen
				System.out.println("Panelsize: " + panelsize + " ( width: " + width + ", height: " + height + ")"); // zu löschen
				double tilesize = (double) panelsize / (double) grid.groesse;
				System.out.println("Tilesize: " + tilesize); // zu löschen
				int x = e.getX();
				int y = e.getY();
				System.out.println("X: " + x + ", Y: " + y); // zu löschen
				int xGrid = (int) ((double) x / tilesize) - 1;
				int yGrid = (int) ((double) y / tilesize) - 1;
				System.out.println("XGrid: " + xGrid + ", YGrid: " + yGrid); // zu löschen
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
		
		// options Panel Settings
		options.setLayout(new BorderLayout());
		options.setOpaque(false);
		options.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		// options Panel Layout
		options.add(title, BorderLayout.NORTH);
		options.add(elements, BorderLayout.WEST);
		options.add(elementsCounter, BorderLayout.EAST);
		options.add(buttons, BorderLayout.SOUTH);
		
		// elements Panel Settings
		elements.setLayout(new BoxLayout(elements, BoxLayout.Y_AXIS));
		elements.setOpaque(false);
		
		for(Ship ship : logic.getAvailableShips()) {
			switch(ship.getSize()) {
				case 2:
					twoFieldElementCount++;
					break;
				case 3:
					threeFieldElementCount++;
					break;
				case 4:
					fourFieldElementCount++;
					break;
				case 5:
					fiveFieldElementCount++;
					break;
			}
		}
		
		// elements Elements
		if(fiveFieldElementCount == 0) fiveFieldElementIcon.setEnabled(false);
		if(fourFieldElementCount == 0) fourFieldElementIcon.setEnabled(false);
		if(threeFieldElementCount == 0) threeFieldElementIcon.setEnabled(false);
		if(twoFieldElementCount == 0) twoFieldElementIcon.setEnabled(false);
		
		EmptyBorder emptyBorder = new EmptyBorder(3, 3, 3, 3);
		fiveFieldElementIcon.setBorder(emptyBorder);
		fiveFieldElementIcon.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if(fiveFieldElementIcon.isEnabled()) {
					fourFieldElementIcon.setBorder(emptyBorder);
					threeFieldElementIcon.setBorder(emptyBorder);
					twoFieldElementIcon.setBorder(emptyBorder);
					fiveFieldElementIcon.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
					elementSelected = "five";
				}
			}
		});
		fourFieldElementIcon.setBorder(emptyBorder);
		fourFieldElementIcon.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if(fourFieldElementIcon.isEnabled()) {
					fiveFieldElementIcon.setBorder(emptyBorder);
					threeFieldElementIcon.setBorder(emptyBorder);
					twoFieldElementIcon.setBorder(emptyBorder);
					fourFieldElementIcon.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
					elementSelected = "four";
				}
			}
		});
		threeFieldElementIcon.setBorder(emptyBorder);
		threeFieldElementIcon.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if(threeFieldElementIcon.isEnabled()) {
					fiveFieldElementIcon.setBorder(emptyBorder);
					fourFieldElementIcon.setBorder(emptyBorder);
					twoFieldElementIcon.setBorder(emptyBorder);
					threeFieldElementIcon.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
					elementSelected = "three";
				}
			}
		});
		twoFieldElementIcon.setBorder(emptyBorder);
		twoFieldElementIcon.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if(twoFieldElementIcon.isEnabled()) {
					fiveFieldElementIcon.setBorder(emptyBorder);
					fourFieldElementIcon.setBorder(emptyBorder);
					threeFieldElementIcon.setBorder(emptyBorder);
					twoFieldElementIcon.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
					elementSelected = "two";
				}
			}
		});
		elements.add(Box.createVerticalGlue());
		elements.add(fiveFieldElementIcon);
		elements.add(Box.createVerticalStrut(40));
		elements.add(fourFieldElementIcon);
		elements.add(Box.createVerticalStrut(40));
		elements.add(threeFieldElementIcon);
		elements.add(Box.createVerticalStrut(40));
		elements.add(twoFieldElementIcon);
		elements.add(Box.createVerticalGlue());
		
		// elementsCounter Panel Settings
		elementsCounter.setLayout(new BoxLayout(elementsCounter, BoxLayout.Y_AXIS));
		elementsCounter.setOpaque(false);
		
		// elementsCounter Elements
		int fiveRemaining = fiveFieldElementCount;
		JLabel fiveFieldElementCount = new JLabel(fiveRemaining + "x");
		fiveFieldElementCount.setFont(font);
		fiveFieldElementCount.setForeground(textColor);
		int fourRemaining = fourFieldElementCount;
		JLabel fourFieldElementCount = new JLabel(fourRemaining + "x");
		fourFieldElementCount.setFont(font);
		fourFieldElementCount.setForeground(textColor);
		int threeRemaining = threeFieldElementCount;
		JLabel threeFieldElementCount = new JLabel(threeRemaining + "x");
		threeFieldElementCount.setFont(font);
		threeFieldElementCount.setForeground(textColor);
		int twoRemaining = twoFieldElementCount;
		JLabel twoFieldElementCount = new JLabel(twoRemaining + "x");
		twoFieldElementCount.setFont(font);
		twoFieldElementCount.setForeground(textColor);
		
		//elementsCounter Layout
		elementsCounter.add(Box.createVerticalGlue());
		elementsCounter.add(fiveFieldElementCount);
		elementsCounter.add(Box.createVerticalStrut(70));
		elementsCounter.add(fourFieldElementCount);
		elementsCounter.add(Box.createVerticalStrut(70));
		elementsCounter.add(threeFieldElementCount);
		elementsCounter.add(Box.createVerticalStrut(70));
		elementsCounter.add(twoFieldElementCount);
		elementsCounter.add(Box.createVerticalGlue());
		
		// buttons Panel Settings
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
		buttons.setOpaque(false);
		
		// buttons Panel Layout
		buttons.add(Box.createHorizontalGlue());
		buttons.add(randomButton);
		buttons.add(Box.createHorizontalStrut(10));
		buttons.add(startButton);
		buttons.add(Box.createHorizontalStrut(10));
		buttons.add(soundButton);
		buttons.add(Box.createHorizontalGlue());
		
		// frame Settings
		frame.getContentPane().add(mainPanel);
		frame.setVisible(true);
		
		// Elements in option Panel
		// title JPanel Settings
		title.setText(Launcher.themeIdentifierPlural + " platzieren");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setForeground(textColor);
		title.setFont(font);
		
		// randomButton Button Settings
		randomButton.setText("Zufällig");
		ImageIcon randomPutIcon = new ImageIcon(new ImageIcon("src/res/random.png").getImage().getScaledInstance(130, 60, Image.SCALE_SMOOTH));
		randomButton.setIcon(randomPutIcon);
		//		randomButton.setHorizontalAlignment(SwingConstants.LEFT);
		randomButton.setBorder(null);
		randomButton.setToolTipText("Zufällige Platzierung der " + Launcher.themeIdentifierPlural);
		randomButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		randomButton.setMinimumSize(new Dimension(130, 60));
		randomButton.setMaximumSize(new Dimension(130, 60));
		randomButton.setPreferredSize(new Dimension(130, 60));
		randomButton.addActionListener(a -> player.randomShipPlacment());
		
		// startButton Button Settings
		startButton.setText("Start");
		ImageIcon startIcon = new ImageIcon(new ImageIcon("src/res/start.png").getImage().getScaledInstance(130, 60, Image.SCALE_SMOOTH));
		startButton.setIcon(startIcon);
		//		startButton.setHorizontalAlignment(SwingConstants.LEFT);
		startButton.setBorder(null);
		startButton.setToolTipText("Spiel starten");
		startButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		startButton.setMinimumSize(new Dimension(130, 60));
		startButton.setMaximumSize(new Dimension(130, 60));
		startButton.setPreferredSize(new Dimension(130, 60));
		startButton.addActionListener(arg0 -> backToMenu());
		
		// soundButton Button Settings
		soundButton.setText("Lautstärke anpassen");
		Icon soundOnIcon = new ImageIcon(new ImageIcon("src/res/soundOnIcon.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
		Icon soundOffIcon = new ImageIcon(new ImageIcon("src/res/soundOffIcon.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
		if(Launcher.soundPlaying) {
			soundButton.setIcon(soundOnIcon);
		}else {
			soundButton.setIcon(soundOffIcon);
		}
		//		soundButton.setHorizontalAlignment(SwingConstants.LEFT);
		soundButton.setBorder(null);
		soundButton.setToolTipText("Lautstärke anpassen");
		soundButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		soundButton.setMinimumSize(new Dimension(50, 50));
		soundButton.setMaximumSize(new Dimension(50, 50));
		soundButton.setPreferredSize(new Dimension(50, 50));
		soundButton.addActionListener(arg0 -> {
			if(Launcher.soundPlaying) {
				soundButton.setIcon(soundOffIcon);
				soundButton.setBorder(null);
				MainWindow.music.stopMusic();
				Launcher.soundPlaying = false;
			}else {
				soundButton.setIcon(soundOnIcon);
				soundButton.setBorder(null);
				MainWindow.music.restartMusic();
				Launcher.soundPlaying = true;
			}
		});
	}
}


