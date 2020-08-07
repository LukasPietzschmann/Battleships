package gui;

import logic.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.Objects;

/**
 * Die Klasse SetUpMenu bildet die Nutzeroberfläche, auf dem der lokale Spieler seine eigenen Schiffe platzieren kann.
 */
public class SetUpMenu implements GameStartsListener, MapListener, Serializable {
	private final JFrame frame;
	private final String mode;
	private final JPanel mainPanel = new JPanel();
	private final JPanel gridHolder = new JPanel(new GridBagLayout());
	private JGameCanvas grid;
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
	
	private int fiveRemaining;
	private int fourRemaining;
	private int threeRemaining;
	private int twoRemaining;
	
	private JLabel fiveFieldElementCountLabel;
	private JLabel fourFieldElementCountLabel;
	private JLabel threeFieldElementCountLabel;
	private JLabel twoFieldElementCountLabel;
	
	private static Color textColor = MainMenu.textColor;
	private static Color backgroundColor = MainMenu.backgroundColor;
	private Font font = new Font("Krungthep", Font.PLAIN, 20);
	private int elementSelected;
	private Direction direction = Direction.west;
	
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
		this.frame.setResizable(true);
		this.mode = mode;
		this.logic = logic;
		grid = new JGameCanvas(logic.getSize());
		player = logic.getOwnPlayer();
		player.registerOnMapChangedListener(grid);
		player.registerOnMapChangedListener(this);
		logic.registerGameStartsListener(this);
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
				double tilesize = (double) panelsize / (double) grid.getGroesse();
				int x = e.getX();
				int y = e.getY();
				int xGrid = (int) ((double) x / tilesize) - 1;
				int yGrid = (int) ((double) y / tilesize) - 1;
				player.placeShip(new Ship(xGrid, yGrid, direction, elementSelected));
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
		Icon directionRight = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("direction_right.png"))).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
		Icon directionDown = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("direction_down.png"))).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
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
		ImageIcon randomPutIcon = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("random.png"))).getImage().getScaledInstance(130, 60, Image.SCALE_SMOOTH));
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
		
		// startButton Button Settings
		startButton.setText("Start");
		ImageIcon startIcon = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("start.png"))).getImage().getScaledInstance(130, 60, Image.SCALE_SMOOTH));
		startButton.setIcon(startIcon);
		startButton.setHorizontalAlignment(SwingConstants.LEFT);
		startButton.setBorder(null);
		startButton.setToolTipText("Spiel starten");
		startButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		startButton.setMinimumSize(new Dimension(130, 60));
		startButton.setMaximumSize(new Dimension(130, 60));
		startButton.setPreferredSize(new Dimension(130, 60));
		startButton.setFocusable(false);
		startButton.addActionListener(arg0 -> logic.setShipsPlaced(player));
		
		// soundButton Button Settings
		Icon soundOnIcon = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("soundOnIcon.png"))).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
		Icon soundOffIcon = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("soundOffIcon.png"))).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
		if(Launcher.soundPlaying) {
			soundButton.setIcon(soundOnIcon);
		}else {
			soundButton.setIcon(soundOffIcon);
		}
		soundButton.setHorizontalAlignment(SwingConstants.LEFT);
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
	 * {@inheritDoc}
	 */
	@Override
	public void OnStartGame() {
		frame.remove(mainPanel);
		GameWindow game = new GameWindow(frame, mode, logic);
		game.setUpGameWindow();
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * @param map Die geänderte Map.
	 */
	@Override
	public void OnMapChanged(Map map) {
		return;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void OnAllShipsPlaced() {
		fiveRemaining = 0;
		fourRemaining = 0;
		threeRemaining = 0;
		twoRemaining = 0;
		
		fiveFieldElementCountLabel.setText(fiveRemaining + "x");
		fourFieldElementCountLabel.setText(fiveRemaining + "x");
		threeFieldElementCountLabel.setText(fiveRemaining + "x");
		twoFieldElementCountLabel.setText(fiveRemaining + "x");
		
		fiveFieldElementIcon.setEnabled(false);
		fiveFieldElementIcon.setBorder(new EmptyBorder(3, 3, 3, 3));
		fourFieldElementIcon.setEnabled(false);
		fourFieldElementIcon.setBorder(new EmptyBorder(3, 3, 3, 3));
		threeFieldElementIcon.setEnabled(false);
		threeFieldElementIcon.setBorder(new EmptyBorder(3, 3, 3, 3));
		twoFieldElementIcon.setEnabled(false);
		twoFieldElementIcon.setBorder(new EmptyBorder(3, 3, 3, 3));
		
		elementSelected = 0;
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * @param ship Das platzierte Schiff.
	 */
	@Override
	public void OnShipPlaced(Ship ship) {
		switch(ship.getSize()) {
			case 5:
				fiveRemaining--;
				fiveFieldElementCountLabel.setText(fiveRemaining + "x");
				if(fiveRemaining == 0) {
					fiveFieldElementIcon.setEnabled(false);
					fiveFieldElementIcon.setBorder(new EmptyBorder(3, 3, 3, 3));
					elementSelected = 0;
				}
				break;
			case 4:
				fourRemaining--;
				fourFieldElementCountLabel.setText(fourRemaining + "x");
				if(fourRemaining == 0) {
					fourFieldElementIcon.setEnabled(false);
					fourFieldElementIcon.setBorder(new EmptyBorder(3, 3, 3, 3));
					elementSelected = 0;
				}
				break;
			case 3:
				threeRemaining--;
				threeFieldElementCountLabel.setText(threeRemaining + "x");
				if(threeRemaining == 0) {
					threeFieldElementIcon.setEnabled(false);
					threeFieldElementIcon.setBorder(new EmptyBorder(3, 3, 3, 3));
					elementSelected = 0;
				}
				break;
			case 2:
				twoRemaining--;
				twoFieldElementCountLabel.setText(twoRemaining + "x");
				if(twoRemaining == 0) {
					twoFieldElementIcon.setEnabled(false);
					twoFieldElementIcon.setBorder(new EmptyBorder(3, 3, 3, 3));
					elementSelected = 0;
				}
				break;
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void OnNotAllShipsPlaced() {
		JOptionPane.showMessageDialog(frame, "Um zu starten müssen erst alle " + Launcher.themeIdentifierPlural + " platziert werden!", "Platzierung nicht abgeschlossen", JOptionPane.ERROR_MESSAGE);
	}
}


