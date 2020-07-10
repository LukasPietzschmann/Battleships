package gui;

import logic.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Die Klasse SetUpMenu bildet die Nutzeroberfläche, auf dem der lokale Spieler seine eigenen
 * Schiffe platzieren kann.
 */
public class SetUpMenu implements GameStartsListener {
	private final JFrame frame;
	private final String mode;
	private final JPanel mainPanel = new JPanel();
	private final JPanel gridHolder = new JPanel(new GridBagLayout());
	JGameCanvas grid = new JGameCanvas();
	private final JPanel options = new JPanel();
	private final JLabel title = new JLabel();
	private final JPanel elements = new JPanel();
	private final JPanel elementsCounter = new JPanel();
	private final JPanel buttonsHolder = new JPanel();
	private final JPanel buttons = new JPanel();
	private final JButton directionButton = new JButton();
	private final JButton randomButton = new JButton();
	private final JButton startButton = new JButton();
	private final JButton soundButton = new JButton();
	private final JLabel fiveFieldElementIcon = MainMenu.fiveFieldElementIcon;
	private final JLabel fourFieldElementIcon = MainMenu.fourFieldElementIcon;
	private final JLabel threeFieldElementIcon = MainMenu.threeFieldElementIcon;
	private final JLabel twoFieldElementIcon = MainMenu.twoFieldElementIcon;
	
	private int fiveFieldElementCount = 0;
	private int fourFieldElementCount = 0;
	private int threeFieldElementCount = 0;
	private int twoFieldElementCount = 0;
	
	int fiveRemaining;
	int fourRemaining;
	int threeRemaining;
	int twoRemaining;
	
	JLabel fiveFieldElementCountLabel;
	JLabel fourFieldElementCountLabel;
	JLabel threeFieldElementCountLabel;
	JLabel twoFieldElementCountLabel;
	
	static Color textColor = MainMenu.textColor;
	static Color backgroundColor = MainMenu.backgroundColor;
	Font font = new Font("Krungthep", Font.PLAIN, 20);
	int elementSelected;
	Direction direction = Direction.west;
	boolean readyToPlay = false;
	
	private final Logic logic;
	private final LocalPlayer player;

	/**
	 * Konstruktor, erstellt ein SetUpMenu-Objekt in welchem der lokale Spieler seine Schiffe platzieren kann
	 *
	 * @param frame Der übergebene Frame des MainWindow
	 * @param mode Der Spielmodus
	 * @param logic Rückverweis auf die Logik
	 */
	public SetUpMenu(JFrame frame, String mode, Logic logic) {
		this.frame = frame;
		this.mode = mode;
		this.logic = logic;
		player = logic.getOwnPlayer();
		player.registerOnMapChangedListener(grid);
		logic.registerGameStartsListener(this);
	}

	/**
	 * Rücksprung ins Hauptmenü
	 * (wird derzeit nicht genutzt)
	 */
	public void backToMenu() {
		frame.remove(mainPanel);
		MainMenu menu = new MainMenu(frame);
		menu.setUpMenu();
	}

	/**
	 * Erstellt die grafische Benutzeroberfläche für das Set-Up-Menü
	 */
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
				double tilesize = (double) panelsize / (double) grid.groesse;
				int x = e.getX();
				int y = e.getY();
				int xGrid = (int) ((double) x / tilesize) - 1;
				int yGrid = (int) ((double) y / tilesize) - 1;
				Ship ship = new Ship(xGrid, yGrid, direction, elementSelected);
				if(player.canShipBePlaced(ship)) {
					player.placeShip(ship);
					grid.placeShip(ship);
					grid.repaint();
					if(elementSelected == 5) {
						fiveRemaining--;
						fiveFieldElementCountLabel.setText(fiveRemaining + "x");
						if(fiveRemaining == 0) {
							fiveFieldElementIcon.setEnabled(false);
							fiveFieldElementIcon.setBorder(new EmptyBorder(3, 3, 3, 3));
							elementSelected = 0;
						}
					}
					if(elementSelected == 4) {
						fourRemaining--;
						fourFieldElementCountLabel.setText(fourRemaining + "x");
						if(fourRemaining == 0) {
							fourFieldElementIcon.setEnabled(false);
							fourFieldElementIcon.setBorder(new EmptyBorder(3, 3, 3, 3));
							elementSelected = 0;
						}
					}
					if(elementSelected == 3) {
						threeRemaining--;
						threeFieldElementCountLabel.setText(threeRemaining + "x");
						if(threeRemaining == 0) {
							threeFieldElementIcon.setEnabled(false);
							threeFieldElementIcon.setBorder(new EmptyBorder(3, 3, 3, 3));
							elementSelected = 0;
						}
					}
					if(elementSelected == 2) {
						twoRemaining--;
						twoFieldElementCountLabel.setText(twoRemaining + "x");
						if(twoRemaining == 0) {
							twoFieldElementIcon.setEnabled(false);
							twoFieldElementIcon.setBorder(new EmptyBorder(3, 3, 3, 3));
							elementSelected = 0;
						}
					}
					if(fiveRemaining + fourRemaining + threeRemaining + twoRemaining == 0) readyToPlay = true;
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
		
		// options Panel Settings
		options.setLayout(new BorderLayout());
		options.setOpaque(false);
		options.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		// options Panel Layout
		options.add(title, BorderLayout.NORTH);
		options.add(elements, BorderLayout.WEST);
		options.add(elementsCounter, BorderLayout.EAST);
		options.add(buttonsHolder, BorderLayout.SOUTH);
		
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
					elementSelected = 5;
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
					elementSelected = 4;
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
					elementSelected = 3;
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
					elementSelected = 2;
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
		fiveRemaining = fiveFieldElementCount;
		fiveFieldElementCountLabel = new JLabel(fiveRemaining + "x");
		fiveFieldElementCountLabel.setFont(font);
		fiveFieldElementCountLabel.setForeground(textColor);
		fourRemaining = fourFieldElementCount;
		fourFieldElementCountLabel = new JLabel(fourRemaining + "x");
		fourFieldElementCountLabel.setFont(font);
		fourFieldElementCountLabel.setForeground(textColor);
		threeRemaining = threeFieldElementCount;
		threeFieldElementCountLabel = new JLabel(threeRemaining + "x");
		threeFieldElementCountLabel.setFont(font);
		threeFieldElementCountLabel.setForeground(textColor);
		twoRemaining = twoFieldElementCount;
		twoFieldElementCountLabel = new JLabel(twoRemaining + "x");
		twoFieldElementCountLabel.setFont(font);
		twoFieldElementCountLabel.setForeground(textColor);
		
		//elementsCounter Layout
		elementsCounter.add(Box.createVerticalGlue());
		elementsCounter.add(fiveFieldElementCountLabel);
		elementsCounter.add(Box.createVerticalStrut(70));
		elementsCounter.add(fourFieldElementCountLabel);
		elementsCounter.add(Box.createVerticalStrut(70));
		elementsCounter.add(threeFieldElementCountLabel);
		elementsCounter.add(Box.createVerticalStrut(70));
		elementsCounter.add(twoFieldElementCountLabel);
		elementsCounter.add(Box.createVerticalGlue());
		
		// buttonsHolder Panel Settings
		buttonsHolder.setLayout(new BoxLayout(buttonsHolder, BoxLayout.Y_AXIS));
		buttonsHolder.setOpaque(false);
		
		// Elements in buttonsHolder
		Font tipFont = new Font("Krungthep", Font.PLAIN, 15);
		JLabel tip = new JLabel("<html><body>STRG gedrückt halten, um die<br>Platzierungsrichtung zu ändern</body></html>");
		tip.setHorizontalAlignment(SwingConstants.CENTER);
		tip.setFont(tipFont);
		tip.setForeground(textColor);
		JPanel tipHolder = new JPanel();
		tipHolder.setLayout(new BorderLayout(0, 0));
		tipHolder.setOpaque(false);
		tipHolder.add(tip, BorderLayout.CENTER);
		
		// buttonsHolder Panel Layout
		buttonsHolder.add(tipHolder);
		buttonsHolder.add(Box.createVerticalStrut(10));
		buttonsHolder.add(buttons);
		
		// buttons Panel Settings
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
		buttons.setOpaque(false);
		
		// buttons Panel Layout
		buttons.add(Box.createHorizontalGlue());
		buttons.add(directionButton);
		buttons.add(Box.createHorizontalStrut(10));
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
		
		// directionButton
		directionButton.setText("Platzierungsrichtung");
		Icon directionRight = new ImageIcon(new ImageIcon("src/res/direction_right.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
		Icon directionDown = new ImageIcon(new ImageIcon("src/res/direction_down.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
		directionButton.setIcon(directionRight);
		directionButton.setBorder(null);
		directionButton.setHorizontalAlignment(SwingConstants.LEFT);
		directionButton.setContentAreaFilled(false);
		directionButton.setToolTipText("Platzierungsrichtung");
		directionButton.setMinimumSize(new Dimension(50, 50));
		directionButton.setMaximumSize(new Dimension(50, 50));
		directionButton.setPreferredSize(new Dimension(50, 50));
		
		frame.setFocusable(true);
		frame.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent evt) {
			}
			
			@Override
			public void keyPressed(KeyEvent evt) {
				if(evt.isControlDown()) {
					direction = Direction.north;
					directionButton.setIcon(directionDown);
					directionButton.setBorder(null);
				}
			}
			
			@Override
			public void keyReleased(KeyEvent evt) {
				if(!evt.isControlDown()) {
					direction = Direction.west;
					directionButton.setIcon(directionRight);
					directionButton.setBorder(null);
				}
			}
		});
		
		// randomButton Button Settings
		randomButton.setText("Zufällig");
		ImageIcon randomPutIcon = new ImageIcon(new ImageIcon("src/res/random.png").getImage().getScaledInstance(130, 60, Image.SCALE_SMOOTH));
		randomButton.setIcon(randomPutIcon);
		randomButton.setHorizontalAlignment(SwingConstants.LEFT);
		randomButton.setBorder(null);
		randomButton.setToolTipText("Zufällige Platzierung der " + Launcher.themeIdentifierPlural);
		randomButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		randomButton.setMinimumSize(new Dimension(130, 60));
		randomButton.setMaximumSize(new Dimension(130, 60));
		randomButton.setPreferredSize(new Dimension(130, 60));
		randomButton.setFocusable(false);
		randomButton.addActionListener(a -> player.randomShipPlacment());
		randomButton.addActionListener(a -> disableShipPlacement());
		randomButton.addActionListener(a -> readyToPlay = true);
		
		// startButton Button Settings
		startButton.setText("Start");
		ImageIcon startIcon = new ImageIcon(new ImageIcon("src/res/start.png").getImage().getScaledInstance(130, 60, Image.SCALE_SMOOTH));
		startButton.setIcon(startIcon);
		startButton.setHorizontalAlignment(SwingConstants.LEFT);
		startButton.setBorder(null);
		startButton.setToolTipText("Spiel starten");
		startButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		startButton.setMinimumSize(new Dimension(130, 60));
		startButton.setMaximumSize(new Dimension(130, 60));
		startButton.setPreferredSize(new Dimension(130, 60));
		startButton.setFocusable(false);
		//		startButton.addActionListener(arg0 -> backToMenu());
		// TODO: Muss überarbeitet werden, sorgt so für fehlerhaftes resizen in der GameWindow-Klasse
		startButton.addActionListener(arg0 -> logic.setShipsPlaced(player));
//		startButton.addActionListener(arg0 -> OnStartGame());
		
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
		soundButton.setContentAreaFilled(false);
		soundButton.setToolTipText("Lautstärke anpassen");
		soundButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		soundButton.setMinimumSize(new Dimension(50, 50));
		soundButton.setMaximumSize(new Dimension(50, 50));
		soundButton.setPreferredSize(new Dimension(50, 50));
		soundButton.setFocusable(false);
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

	/**
	 * Sperrt die Möglichkeit Schiffe zu platzieren, graut die Schiffsicons aus und setzt die verbleibende
	 * Anzahl der zu platzierenden Schiffe auf 0
	 */
	public void disableShipPlacement() {
		elementSelected = 0;
		fiveFieldElementCountLabel.setText(0 + "x");
		fiveFieldElementIcon.setEnabled(false);
		fiveFieldElementIcon.setBorder(new EmptyBorder(3, 3, 3, 3));
		fourFieldElementCountLabel.setText(0 + "x");
		fourFieldElementIcon.setEnabled(false);
		fourFieldElementIcon.setBorder(new EmptyBorder(3, 3, 3, 3));
		threeFieldElementCountLabel.setText(0 + "x");
		threeFieldElementIcon.setEnabled(false);
		threeFieldElementIcon.setBorder(new EmptyBorder(3, 3, 3, 3));
		twoFieldElementCountLabel.setText(0 + "x");
		twoFieldElementIcon.setEnabled(false);
		twoFieldElementIcon.setBorder(new EmptyBorder(3, 3, 3, 3));
	}

	/**
	 * Startet das eigentliche Spielfenster
	 */
	@Override
	public void OnStartGame() {
		frame.remove(mainPanel);
		GameWindow game = new GameWindow(frame, mode, logic);
	}
}


