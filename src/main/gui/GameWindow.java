package main.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameWindow {
	private JFrame frame; 
	private String mode;
	private JPanel mainPanel = new JPanel();
//	private JPanel grid = new JPanel();
	JGameCanvas grid = new JGameCanvas();
	private JPanel options = new JPanel();
	private JLabel title = new JLabel();
	private JPanel elements = new JPanel();
	private JPanel elementsCounter = new JPanel();
	private JPanel buttons = new JPanel();
		private JButton randomButton = new JButton();
		private JButton startButton = new JButton();
		private JButton soundButton = new JButton();
	
	static Color textColor = MainMenu.textColor;
	static Color backgroundColor = MainMenu.backgroundColor;
	Font font = new Font("Krungthep", Font.PLAIN, 20);
	
	
	public GameWindow(JFrame frame, String mode) {
		this.frame = frame;
		this.mode = mode;
	}
	
	public void setUpGameWindow() {
//		Grid gridPlayer1 = new Grid(GuiTester.gridSize);
//		Grid gridPlayer2 = new Grid(GuiTester.gridSize);
		if (mode.equals("pvp") || mode.equals("pvc")) {
			setUpPlaceWindow();
			
		} else {
			// Methode, die die Elemente für beide Spielfelder automatisch füllt
		}
	}
	
	
	public void backToMenu() {
		MainMenu menu = new MainMenu(frame);
		menu.setUpMenu();
	}
	
	public void setUpPlaceWindow() {
		
		// mainPanel Panel Settings
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setOpaque(true);
		mainPanel.setBackground(backgroundColor);
		mainPanel.setBorder(BorderFactory.createEmptyBorder(30,70,30,30));
		
		// mainPanel Panel Layout
		mainPanel.add(grid, BorderLayout.CENTER);
		mainPanel.add(options, BorderLayout.EAST);
		
		// grid Panel Settings
		grid.setOpaque(true);
		
		// options Panel Settings
		options.setLayout(new BorderLayout());
		options.setOpaque(false);
		options.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		
		// options Panel Layout
		options.add(title, BorderLayout.NORTH);
		options.add(elements, BorderLayout.WEST);
		options.add(elementsCounter, BorderLayout.EAST);
		options.add(buttons, BorderLayout.SOUTH);
		
		// elements Panel Settings
		elements.setLayout(new BoxLayout(elements, BoxLayout.Y_AXIS));
		elements.setOpaque(false);
		
		// elementsCounter Panel Settings
		elementsCounter.setLayout(new BoxLayout(elementsCounter, BoxLayout.Y_AXIS));
		elementsCounter.setOpaque(false);
		
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
		title.setText(GuiTester.themeIdentifierPlural + " platzieren");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setForeground(textColor);
		title.setFont(font);
		
		// randomButton Button Settings
		randomButton.setText("Zufällig");
//		ImageIcon randomPutIcon = new ImageIcon(new ImageIcon("src/res/randomPutIcon.png").getImage().getScaledInstance(200, 60, Image.SCALE_SMOOTH));
//		randomButton.setIcon(randomPutIcon);
//		randomButton.setHorizontalAlignment(SwingConstants.LEFT);
//		randomButton.setBorder(null);
		randomButton.setToolTipText("Zufällige Platzierung der " + GuiTester.themeIdentifierPlural);
		randomButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		randomButton.setMinimumSize(new Dimension(130, 60));
		randomButton.setMaximumSize(new Dimension(130, 60));
		randomButton.setPreferredSize(new Dimension(130, 60));
		
		// startButton Button Settings
		startButton.setText("Start");
//		ImageIcon startIcon = new ImageIcon(new ImageIcon("src/res/startIcon.png").getImage().getScaledInstance(200, 60, Image.SCALE_SMOOTH));
//		startButton.setIcon(startIcon);
//		startButton.setHorizontalAlignment(SwingConstants.LEFT);
//		startButton.setBorder(null);
		startButton.setToolTipText("Spiel starten");
		startButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		startButton.setMinimumSize(new Dimension(130, 60));
		startButton.setMaximumSize(new Dimension(130, 60));
		startButton.setPreferredSize(new Dimension(130, 60));
		
		// soundButton Button Settings
		soundButton.setText("Lautstärke anpassen");
		Icon soundOnIcon = new ImageIcon(new ImageIcon("src/res/soundOnIcon.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
		Icon soundOffIcon = new ImageIcon(new ImageIcon("src/res/soundOffIcon.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
		if (GuiTester.soundPlaying == true) {
			soundButton.setIcon(soundOnIcon);
		} else {
			soundButton.setIcon(soundOffIcon);
		}
//		soundButton.setHorizontalAlignment(SwingConstants.LEFT);
		soundButton.setBorder(null);
		soundButton.setToolTipText("Lautstärke anpassen");
		soundButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		soundButton.setMinimumSize(new Dimension(50, 50));
		soundButton.setMaximumSize(new Dimension(50, 50));
		soundButton.setPreferredSize(new Dimension(50, 50));
		soundButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (GuiTester.soundPlaying == true) {
					soundButton.setIcon(soundOffIcon);
					soundButton.setBorder(null);
					MainWindow.music.stopMusic();
					GuiTester.soundPlaying = false;
				} else {
					soundButton.setIcon(soundOnIcon);
					soundButton.setBorder(null);
					MainWindow.music.restartMusic();
					GuiTester.soundPlaying = true;
				}
			}	
		});			
	}
	
}

