package main.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;


public class MainMenu {
	
	private JFrame frame;
	private JPanel panel = new JPanel();
	private JPanel themeIconTitle = new JPanel();
		private JPanel themeIconPanel = new JPanel();
	private JPanel modes = new JPanel();
	private JPanel title = new JPanel();
		JLabel themeTitle = new JLabel();
	
	private JPanel settings = new JPanel();
	
		private JPanel icons = new JPanel();
			private ImageIcon fiveFieldElementIconFromSide;
			private ImageIcon fourFieldElementIconFromSide;
			private ImageIcon threeFieldElementIconFromSide;
			private ImageIcon twoFieldElementIconFromSide;
			private JLabel fiveFieldElementIcon = new JLabel();
			private JLabel fourFieldElementIcon = new JLabel();
			private JLabel threeFieldElementIcon = new JLabel();
			private JLabel twoFieldElementIcon = new JLabel();
			private JLabel gridIcon = new JLabel();
			
		private JPanel counters = new JPanel();
			private JLabel fiveFieldElementText = new JLabel();
			private JLabel fourFieldElementText = new JLabel();
			private JLabel threeFieldElementText = new JLabel();
			private JLabel twoFieldElementText = new JLabel();
			private JPanel fiveFieldElementCountChange = new JPanel();
				private JButton fiveFieldElementCountIncrease = new JButton();
				private JButton fiveFieldElementCountDecrease = new JButton();
				private JButton fourFieldElementCountIncrease = new JButton();
				private JButton fourFieldElementCountDecrease = new JButton();
				private JButton threeFieldElementCountIncrease = new JButton();
				private JButton threeFieldElementCountDecrease = new JButton();
				private JButton twoFieldElementCountIncrease = new JButton();
				private JButton twoFieldElementCountDecrease = new JButton();
			private JPanel fourFieldElementCountChange = new JPanel();
			private JPanel threeFieldElementCountChange = new JPanel();
			private JPanel twoFieldElementCountChange = new JPanel();
			
		private JPanel themes = new JPanel();
			JLabel themesHeading = new JLabel("Spielstil\nwählen:");
			JRadioButton battleshipsButton = new JRadioButton("Battleships");
			JRadioButton battlecarsButton = new JRadioButton("Battlecars");
	
	static Color textColor = Color.LIGHT_GRAY;
	static Color backgroundColor = new Color(35,35,35);
	
	ImageIcon themeIcon;
	
	MainMenu(JFrame frame){
		this.frame = frame;
		loadThemeItems(GuiTester.theme);
	}
	
	MainMenu(JFrame frame, String theme){
		this.frame = frame;
		GuiTester.theme = theme;
		loadThemeItems(GuiTester.theme);
	}
	
	public void setUpMenu() {
		
		// Panel Settings
		panel.setLayout(new BorderLayout());
		panel.setOpaque(true);
		panel.setBackground(backgroundColor);
		panel.setBorder(BorderFactory.createEmptyBorder(20, 10, 30, 60));
		
		// Panel Layout
		panel.add(modes, BorderLayout.SOUTH);
		panel.add(settings, BorderLayout.EAST);
		panel.add(themeIconTitle, BorderLayout.CENTER);
		
		// themeIconTitle Panel Settings
		themeIconTitle.setLayout(new BorderLayout());
		themeIconTitle.setOpaque(false);
					
			// themeIcon JPanel
			themeIconPanel = new JPanel(){
				private static final long serialVersionUID = 1L;
				protected void paintComponent(Graphics g) {
					Image themeImage = themeIcon.getImage();
					super.paintComponent(g);
					g.drawImage(themeImage, 0, 0, getWidth(), getHeight(), this);
					}
				};
			themeIconPanel.setOpaque(false);
					
			// Title Settings
			title.setOpaque(false);
					
			// Title Elements
			Font themeTitleFont = new Font("Arial", Font.BOLD, 70);
			themeTitle.setFont(themeTitleFont);
			themeTitle.setText(GuiTester.theme);
			themeTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
			themeTitle.setForeground(textColor);
					
			// Title Layout
			title.add(Box.createHorizontalGlue());
			title.add(themeTitle);
			title.add(Box.createHorizontalStrut(10));
			title.add(Box.createHorizontalGlue());
					
			themeIconTitle.add(themeIconPanel, BorderLayout.CENTER);
			themeIconTitle.add(title, BorderLayout.NORTH);
			themeIconTitle.add(Box.createHorizontalStrut(2000), BorderLayout.SOUTH);
		
		// Modes Settings
		modes.setLayout(new BoxLayout(modes, BoxLayout.X_AXIS));
		modes.setOpaque(false);
		
		// Modes Elements
			// Player vs. Computer Button
			JButton pvcButton = new JButton("Spieler vs. Computer");
			ImageIcon pvcIcon = new ImageIcon(new ImageIcon("src/res/playerVsComputer.png").getImage().getScaledInstance(225, 150, Image.SCALE_SMOOTH));
			pvcButton.setIcon(pvcIcon);
			pvcButton.setHorizontalAlignment(SwingConstants.LEFT);
			pvcButton.setBorder(null);
			pvcButton.setToolTipText("Spieler vs. Computer");
			pvcButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			pvcButton.setMinimumSize(new Dimension(225, 150));
			pvcButton.setMaximumSize(new Dimension(225, 150));
			pvcButton.setPreferredSize(new Dimension(225, 150));
			pvcButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent a) {
//					boolean shipsCanFitOnGrid = Logic.shipsCanFitOnGrid();
					boolean shipsCanFitOnGrid = true;
					if (noElementsSelected() == true || shipsCanFitOnGrid == false) {
						if (noElementsSelected() == true) {
							throwErrorMessage(2);
							return;
						}
						if (shipsCanFitOnGrid == false) {
							throwErrorMessage(1);
							return;
						}
					} else {
						panel.setVisible(false);
						GameWindow game = new GameWindow(frame, "pvc");
						game.setUpGameWindow();
					}
				}
			});
			
			// Player vs. Player Button
			JButton pvpButton = new JButton("Spieler vs. Spieler");
			ImageIcon pvpIcon = new ImageIcon(new ImageIcon("src/res/playerVsPlayer.png").getImage().getScaledInstance(225, 150, Image.SCALE_SMOOTH));
			pvpButton.setIcon(pvpIcon);
			pvpButton.setHorizontalAlignment(SwingConstants.LEFT);
			pvpButton.setBorder(null);
			pvpButton.setToolTipText("Spieler vs. Spieler");
			pvpButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			pvpButton.setMinimumSize(new Dimension(225, 150));
			pvpButton.setMaximumSize(new Dimension(225, 150));
			pvpButton.setPreferredSize(new Dimension(225, 150));
			pvpButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent a) {
//					boolean shipsCanFitOnGrid = Logic.shipsCanFitOnGrid();
					boolean shipsCanFitOnGrid = true;
					if (noElementsSelected() == true || shipsCanFitOnGrid == false) {
						if (noElementsSelected() == true) {
							throwErrorMessage(2);
							return;
						}
						if (shipsCanFitOnGrid == false) {
							throwErrorMessage(1);
							return;
						}
					} else {
						new JOptionPaneConnect(frame).displayGui();
						panel.setVisible(false);
						GameWindow game = new GameWindow(frame, "pvp");
						game.setUpGameWindow();
					}
				}
			});
			
			// Computer vs. Computer Button
			JButton cvcButton = new JButton("Computer vs. Computer");
			ImageIcon cvcIcon = new ImageIcon(new ImageIcon("src/res/computerVsComputer.png").getImage().getScaledInstance(225, 150, Image.SCALE_SMOOTH));
			cvcButton.setIcon(cvcIcon);
			cvcButton.setHorizontalAlignment(SwingConstants.LEFT);
			cvcButton.setBorder(null);
			cvcButton.setToolTipText("Computer vs. Computer");
			cvcButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			cvcButton.setMinimumSize(new Dimension(225, 150));
			cvcButton.setMaximumSize(new Dimension(225, 150));
			cvcButton.setPreferredSize(new Dimension(225, 150));
			cvcButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent a) {
//					boolean shipsCanFitOnGrid = Logic.shipsCanFitOnGrid();
					boolean shipsCanFitOnGrid = false;
					if (noElementsSelected() == true || shipsCanFitOnGrid == false) {
						if (noElementsSelected() == true) {
							throwErrorMessage(2);
							return;
						}
						if (shipsCanFitOnGrid == false) {
							throwErrorMessage(1);
							return;
						}
					} else {
						panel.setVisible(false);
						GameWindow game = new GameWindow(frame, "cvc");
						game.setUpGameWindow();
					}
				}
			});
			
			// Information Button
			JButton infoButton = new JButton("Information");
			ImageIcon infoIcon = new ImageIcon(new ImageIcon("src/res/info.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
			infoButton.setIcon(infoIcon);
			infoButton.setHorizontalAlignment(SwingConstants.LEFT);
			infoButton.setBorder(null);
			infoButton.setToolTipText("Information");
			infoButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			infoButton.setMinimumSize(new Dimension(50, 50));
			infoButton.setMaximumSize(new Dimension(50, 50));
			infoButton.setPreferredSize(new Dimension(50, 50));
			infoButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					showInfo();
				}	
			});
			
			// LoadFile Button
			JButton loadButton = new JButton("Spiel laden");
			ImageIcon loadIcon = new ImageIcon(new ImageIcon("src/res/loadSaveIcon.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
			loadButton.setIcon(loadIcon);
			loadButton.setHorizontalAlignment(SwingConstants.LEFT);
			loadButton.setBorder(null);
			loadButton.setToolTipText("Spiel laden");
			loadButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			loadButton.setMinimumSize(new Dimension(50, 50));
			loadButton.setMaximumSize(new Dimension(50, 50));
			loadButton.setPreferredSize(new Dimension(50, 50));
			loadButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					FileFilter filter = new FileNameExtensionFilter("Textdatei", "txt");
					JFileChooser chooser = new JFileChooser();
					chooser.setDialogTitle("Spielstand laden");
					chooser.addChoosableFileFilter(filter);
					int returnValue = chooser.showOpenDialog(frame);
					if (returnValue == JFileChooser.APPROVE_OPTION) {
						// Logic.verarbeiteDatei(chooser.getSelectedFile());
					}
				}	
			});
			
			// Themes Button
			JButton themesButton = new JButton("Spielstil wählen");
			ImageIcon themesIcon = new ImageIcon(new ImageIcon("src/res/themeIcon.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
			themesButton.setIcon(themesIcon);
			themesButton.setHorizontalAlignment(SwingConstants.LEFT);
			themesButton.setBorder(null);
			themesButton.setToolTipText("Spielstil wählen");
			themesButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			themesButton.setMinimumSize(new Dimension(50, 50));
			themesButton.setMaximumSize(new Dimension(50, 50));
			themesButton.setPreferredSize(new Dimension(50, 50));
			themesButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if (counters.isVisible() == true) {
						counters.setVisible(false);
						settings.add(themes, BorderLayout.EAST);
						themes.setVisible(true);
						selectCurrentThemeButton();
					} else {
						themes.setVisible(false);
						settings.add(counters, BorderLayout.EAST);
						counters.setVisible(true);
					}
					frame.getContentPane().revalidate();
					frame.getContentPane().repaint();
				}	
			});
			
			// Sound Button
			JButton soundButton = new JButton("Lautstärke anpassen");
			Icon soundOnIcon = new ImageIcon(new ImageIcon("src/res/soundOnIcon.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
			Icon soundOffIcon = new ImageIcon(new ImageIcon("src/res/soundOffIcon.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
			if (GuiTester.soundPlaying == true) {
				soundButton.setIcon(soundOnIcon);
			} else {
				soundButton.setIcon(soundOffIcon);
			}
			soundButton.setHorizontalAlignment(SwingConstants.LEFT);
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
		
		// Modes Layout
		modes.add(Box.createHorizontalGlue());
		modes.add(Box.createHorizontalStrut(40));
		modes.add(pvcButton);
		modes.add(Box.createHorizontalStrut(20));
		modes.add(pvpButton);
		modes.add(Box.createHorizontalStrut(20));
		modes.add(cvcButton);
		modes.add(Box.createHorizontalStrut(40));
		modes.add(infoButton);
		modes.add(Box.createHorizontalStrut(20));
		modes.add(loadButton);
		modes.add(Box.createHorizontalStrut(20));
		modes.add(themesButton);
		modes.add(Box.createHorizontalStrut(20));
		modes.add(soundButton);
		modes.add(Box.createHorizontalGlue());
		
		// Settings Settings
		settings.setLayout(new BorderLayout(15, 0));
		settings.setOpaque(false);
		
		// Settings Layout
		settings.add(icons, BorderLayout.WEST);
		settings.add(counters, BorderLayout.EAST);
				
		// Icons Settings
		icons.setLayout(new BoxLayout(icons, BoxLayout.Y_AXIS));
		icons.setOpaque(false);
		
		// Icons Elements
			// fiveFieldElementIcon Label
			fiveFieldElementIcon.setIcon(fiveFieldElementIconFromSide);
			fiveFieldElementIcon.setMinimumSize(new Dimension(250, 50));
			fiveFieldElementIcon.setOpaque(false);
			fiveFieldElementIcon.setBackground(Color.green);
			fiveFieldElementIcon.setAlignmentX(Component.RIGHT_ALIGNMENT);

			// fourFieldElementIcon Label
			fourFieldElementIcon.setIcon(fourFieldElementIconFromSide);
			fourFieldElementIcon.setMinimumSize(new Dimension(200, 50));
			fourFieldElementIcon.setOpaque(false);
			fourFieldElementIcon.setBackground(Color.blue);
			fourFieldElementIcon.setAlignmentX(Component.RIGHT_ALIGNMENT);
			
			// threeFieldElementIcon Label
			threeFieldElementIcon.setIcon(threeFieldElementIconFromSide);
			threeFieldElementIcon.setMinimumSize(new Dimension(150, 50));
			threeFieldElementIcon.setOpaque(false);
			threeFieldElementIcon.setBackground(Color.red);
			threeFieldElementIcon.setAlignmentX(Component.RIGHT_ALIGNMENT);
			
			// twoFieldElementIcon Label
			twoFieldElementIcon.setIcon(twoFieldElementIconFromSide);
			twoFieldElementIcon.setMinimumSize(new Dimension(100, 50));
			twoFieldElementIcon.setAlignmentX(Component.RIGHT_ALIGNMENT);
			
			// gridIcon Label
			ImageIcon gridSymbol = new ImageIcon(new ImageIcon("src/res/gridIcon.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
			gridIcon.setIcon(gridSymbol);
			gridIcon.setMinimumSize(new Dimension(50, 50));
			gridIcon.setAlignmentX(Component.RIGHT_ALIGNMENT);
			
		// Icons Layout
		icons.add(Box.createRigidArea(new Dimension(0, 35)));
		icons.add(fiveFieldElementIcon);
		icons.add(Box.createVerticalStrut(25));
		icons.add(fourFieldElementIcon);
		icons.add(Box.createVerticalStrut(25));
		icons.add(threeFieldElementIcon);
		icons.add(Box.createVerticalStrut(25));
		icons.add(twoFieldElementIcon);
		icons.add(Box.createVerticalStrut(55));
		icons.add(gridIcon);
		icons.add(Box.createRigidArea(new Dimension(0, 500)));
		
		// Counters Settings
		counters.setLayout(new BoxLayout(counters, BoxLayout.Y_AXIS));
		counters.setOpaque(false);
		
		// Counters Elements
			// countersFont Font
			Font countersFont = new Font("Krungthep", Font.PLAIN, 20);
		
			// fiveFieldElementText Label
			fiveFieldElementText.setText(GuiTester.fiveFieldElementCount + 
					"x " + GuiTester.fiveFieldElementName);
			fiveFieldElementText.setPreferredSize(new Dimension(210, 30));
			fiveFieldElementText.setFont(countersFont);
			fiveFieldElementText.setAlignmentX(Component.LEFT_ALIGNMENT);
			fiveFieldElementText.setForeground(textColor);
			
			// fiveFieldElementCountChange Settings
			fiveFieldElementCountChange.setLayout(new BoxLayout(fiveFieldElementCountChange, BoxLayout.X_AXIS));
			fiveFieldElementCountChange.setOpaque(false);
			fiveFieldElementCountChange.setAlignmentX(Component.LEFT_ALIGNMENT);
			
			// fiveFieldElementCountChange Elements
				// fiveFieldElementCountIncrease Button
				fiveFieldElementCountIncrease.setText("+");
				fiveFieldElementCountIncrease.setMinimumSize(new Dimension(30, 30));
				fiveFieldElementCountIncrease.setMaximumSize(new Dimension(30, 30));
				fiveFieldElementCountIncrease.setPreferredSize(new Dimension(30, 30));
				fiveFieldElementCountIncrease.setToolTipText("Anzahl erhöhen: " + GuiTester.fiveFieldElementName);
				fiveFieldElementCountIncrease.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				fiveFieldElementCountIncrease.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent arg0) {
						if (GuiTester.fiveFieldElementCount < GuiTester.fiveFieldElementMaxCount){
							GuiTester.fiveFieldElementCount += 1;
						}else{
							GuiTester.fiveFieldElementCount = 0;
						}
						fiveFieldElementText.setText(GuiTester.fiveFieldElementCount + 
								"x " + GuiTester.fiveFieldElementName);
					}	
				});
		
				// fiveFieldElementCountDecrease Button
				fiveFieldElementCountDecrease.setText("-");
				fiveFieldElementCountDecrease.setMinimumSize(new Dimension(30, 30));
				fiveFieldElementCountDecrease.setMaximumSize(new Dimension(30, 30));
				fiveFieldElementCountDecrease.setPreferredSize(new Dimension(30, 30));
				fiveFieldElementCountDecrease.setToolTipText("Anzahl senken: " + GuiTester.fiveFieldElementName);
				fiveFieldElementCountDecrease.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				fiveFieldElementCountDecrease.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent arg0) {
						if (GuiTester.fiveFieldElementCount > 0){
							GuiTester.fiveFieldElementCount -= 1;
						}else{
							GuiTester.fiveFieldElementCount = GuiTester.fiveFieldElementMaxCount;
						}
						fiveFieldElementText.setText(GuiTester.fiveFieldElementCount + 
								"x " + GuiTester.fiveFieldElementName);
					}	
				});
		
			// fiveFieldElementCountChange Layout
			fiveFieldElementCountChange.add(Box.createRigidArea(new Dimension(30, 30)));
			fiveFieldElementCountChange.add(fiveFieldElementCountIncrease);
			fiveFieldElementCountChange.add(Box.createHorizontalStrut(3));
			fiveFieldElementCountChange.add(fiveFieldElementCountDecrease);
			fiveFieldElementCountChange.add(Box.createHorizontalGlue());
				
			// fourFieldElementText Label
			fourFieldElementText.setText(GuiTester.fourFieldElementCount + 
					"x " + GuiTester.fourFieldElementName);
			fourFieldElementText.setPreferredSize(new Dimension(210, 30));
			fourFieldElementText.setFont(countersFont);
			fourFieldElementText.setAlignmentX(Component.LEFT_ALIGNMENT);
			fourFieldElementText.setForeground(textColor);
				
			// fourFieldElementCountChange Settings
			fourFieldElementCountChange.setLayout(new BoxLayout(fourFieldElementCountChange, BoxLayout.X_AXIS));
			fourFieldElementCountChange.setOpaque(false);
			fourFieldElementCountChange.setAlignmentX(Component.LEFT_ALIGNMENT);
				
			// fourFieldElementCountChange Elements
				// fourFieldElementCountIncrease Button
				fourFieldElementCountIncrease.setText("+");
				fourFieldElementCountIncrease.setMinimumSize(new Dimension(30, 30));
				fourFieldElementCountIncrease.setMaximumSize(new Dimension(30, 30));
				fourFieldElementCountIncrease.setPreferredSize(new Dimension(30, 30));
				fourFieldElementCountIncrease.setToolTipText("Anzahl erhöhen: " + GuiTester.fourFieldElementName);
				fourFieldElementCountIncrease.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				fourFieldElementCountIncrease.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent arg0) {
						if (GuiTester.fourFieldElementCount < GuiTester.fourFieldElementMaxCount){
							GuiTester.fourFieldElementCount += 1;
						}else{
							GuiTester.fourFieldElementCount = 0;
						}
						fourFieldElementText.setText(GuiTester.fourFieldElementCount + 
								"x " + GuiTester.fourFieldElementName);
					}	
				});
		
				// fourFieldElementCountDecrease Button
				fourFieldElementCountDecrease.setText("-");
				fourFieldElementCountDecrease.setMinimumSize(new Dimension(30, 30));
				fourFieldElementCountDecrease.setMaximumSize(new Dimension(30, 30));
				fourFieldElementCountDecrease.setPreferredSize(new Dimension(30, 30));
				fourFieldElementCountDecrease.setToolTipText("Anzahl senken: " + GuiTester.fourFieldElementName);
				fourFieldElementCountDecrease.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				fourFieldElementCountDecrease.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent arg0) {
						if (GuiTester.fourFieldElementCount > 0){
							GuiTester.fourFieldElementCount -= 1;
						}else{
							GuiTester.fourFieldElementCount = GuiTester.fourFieldElementMaxCount;
						}
						fourFieldElementText.setText(GuiTester.fourFieldElementCount + 
								"x " + GuiTester.fourFieldElementName);
					}	
				});
			
			// fourFieldElementCountChange Layout
			fourFieldElementCountChange.add(Box.createRigidArea(new Dimension(30, 30)));
			fourFieldElementCountChange.add(fourFieldElementCountIncrease);
			fourFieldElementCountChange.add(Box.createHorizontalStrut(3));
			fourFieldElementCountChange.add(fourFieldElementCountDecrease);
			fourFieldElementCountChange.add(Box.createHorizontalGlue());
			
			// threeFieldElementText Label
			threeFieldElementText.setText(GuiTester.threeFieldElementCount + 
					"x " + GuiTester.threeFieldElementName);
			threeFieldElementText.setPreferredSize(new Dimension(210, 30));
			threeFieldElementText.setFont(countersFont);
			threeFieldElementText.setAlignmentX(Component.LEFT_ALIGNMENT);
			threeFieldElementText.setForeground(textColor);
			
			// threeFieldElementCountChange Settings
			threeFieldElementCountChange.setLayout(new BoxLayout(threeFieldElementCountChange, BoxLayout.X_AXIS));
			threeFieldElementCountChange.setOpaque(false);
			threeFieldElementCountChange.setAlignmentX(Component.LEFT_ALIGNMENT);
			
			// threeFieldElementCountChange Elements
				// threeFieldElementCountIncrease Button
				threeFieldElementCountIncrease.setText("+");
				threeFieldElementCountIncrease.setMinimumSize(new Dimension(30, 30));
				threeFieldElementCountIncrease.setMaximumSize(new Dimension(30, 30));
				threeFieldElementCountIncrease.setPreferredSize(new Dimension(30, 30));
				threeFieldElementCountIncrease.setToolTipText("Anzahl erhöhen: " + GuiTester.threeFieldElementName);
				threeFieldElementCountIncrease.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				threeFieldElementCountIncrease.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent arg0) {
						if (GuiTester.threeFieldElementCount < GuiTester.threeFieldElementMaxCount){
							GuiTester.threeFieldElementCount += 1;
						}else{
							GuiTester.threeFieldElementCount = 0;
						}
						threeFieldElementText.setText(GuiTester.threeFieldElementCount + 
								"x " + GuiTester.threeFieldElementName);
					}	
				});
				
				//threeFieldElementCountDecrease Button
				threeFieldElementCountDecrease.setText("-");
				threeFieldElementCountDecrease.setMinimumSize(new Dimension(30, 30));
				threeFieldElementCountDecrease.setMaximumSize(new Dimension(30, 30));
				threeFieldElementCountDecrease.setPreferredSize(new Dimension(30, 30));
				threeFieldElementCountDecrease.setToolTipText("Anzahl senken: " + GuiTester.threeFieldElementName);
				threeFieldElementCountDecrease.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				threeFieldElementCountDecrease.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent arg0) {
						if (GuiTester.threeFieldElementCount > 0){
							GuiTester.threeFieldElementCount -= 1;
						}else{
							GuiTester.threeFieldElementCount = GuiTester.threeFieldElementMaxCount;
						}
						threeFieldElementText.setText(GuiTester.threeFieldElementCount + 
								"x " + GuiTester.threeFieldElementName);
					}	
				});
				
			// threeFieldElementCountChange Layout
			threeFieldElementCountChange.add(Box.createRigidArea(new Dimension(30, 30)));
			threeFieldElementCountChange.add(threeFieldElementCountIncrease);
			threeFieldElementCountChange.add(Box.createHorizontalStrut(3));
			threeFieldElementCountChange.add(threeFieldElementCountDecrease);
			threeFieldElementCountChange.add(Box.createHorizontalGlue());
			
			// twoFieldElementText Label
			twoFieldElementText.setText(GuiTester.twoFieldElementCount + 
					"x " + GuiTester.twoFieldElementName);
			twoFieldElementText.setPreferredSize(new Dimension(210, 30));
			twoFieldElementText.setFont(countersFont);
			twoFieldElementText.setAlignmentX(Component.LEFT_ALIGNMENT);
			twoFieldElementText.setForeground(textColor);
			
			// twoFieldElementCountChange Setting
			twoFieldElementCountChange.setLayout(new BoxLayout(twoFieldElementCountChange, BoxLayout.X_AXIS));
			twoFieldElementCountChange.setOpaque(false);
			twoFieldElementCountChange.setAlignmentX(Component.LEFT_ALIGNMENT);
			
			// twoFieldElementCountChange Elements
				// twoFieldElementCountIncrease Button
				twoFieldElementCountIncrease.setText("+");
				twoFieldElementCountIncrease.setMinimumSize(new Dimension(30, 30));
				twoFieldElementCountIncrease.setMaximumSize(new Dimension(30, 30));
				twoFieldElementCountIncrease.setPreferredSize(new Dimension(30, 30));
				twoFieldElementCountIncrease.setToolTipText("Anzahl erhöhen: " + GuiTester.twoFieldElementName);
				twoFieldElementCountIncrease.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				twoFieldElementCountIncrease.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent arg0) {
						if (GuiTester.twoFieldElementCount < GuiTester.twoFieldElementMaxCount){
							GuiTester.twoFieldElementCount += 1;
						}else{
							GuiTester.twoFieldElementCount = 0;
						}
						twoFieldElementText.setText(GuiTester.twoFieldElementCount + 
								"x " + GuiTester.twoFieldElementName);
					}	
				});
				
				//twoFieldElementCountDecrease Button
				twoFieldElementCountDecrease.setText("-");
				twoFieldElementCountDecrease.setMinimumSize(new Dimension(30, 30));
				twoFieldElementCountDecrease.setMaximumSize(new Dimension(30, 30));
				twoFieldElementCountDecrease.setPreferredSize(new Dimension(30, 30));
				twoFieldElementCountDecrease.setToolTipText("Anzahl senken: " + GuiTester.twoFieldElementName);
				twoFieldElementCountDecrease.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				twoFieldElementCountDecrease.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent arg0) {
						if (GuiTester.twoFieldElementCount > 0){
							GuiTester.twoFieldElementCount -= 1;
						}else{
							GuiTester.twoFieldElementCount = GuiTester.twoFieldElementMaxCount;
						}
						twoFieldElementText.setText(GuiTester.twoFieldElementCount + 
								"x " + GuiTester.twoFieldElementName);
					}	
				});
				
			// twoFieldElementCountChange Layout
			twoFieldElementCountChange.add(Box.createRigidArea(new Dimension(30, 30)));
			twoFieldElementCountChange.add(twoFieldElementCountIncrease);
			twoFieldElementCountChange.add(Box.createHorizontalStrut(3));
			twoFieldElementCountChange.add(twoFieldElementCountDecrease);
			twoFieldElementCountChange.add(Box.createHorizontalGlue());
			
			// gridText Label
			JLabel gridText = new JLabel("Feldgröße: " + GuiTester.gridSize + "*" + GuiTester.gridSize);
			gridText.setForeground(textColor);
			gridText.setMinimumSize(new Dimension(210, 30));
			gridText.setAlignmentX(Component.LEFT_ALIGNMENT);
			gridText.setFont(countersFont);
			
			// gridSlider Slider
			JSlider gridSlider = new JSlider();
			gridSlider.setForeground(textColor);
			gridSlider.setMinimum(5);
			gridSlider.setMaximum(30);
			gridSlider.setMajorTickSpacing(5);
			gridSlider.setMinorTickSpacing(1);
			gridSlider.createStandardLabels(1);
			gridSlider.setPaintTicks(true);
			gridSlider.setPaintLabels(true);
			gridSlider.setValue(GuiTester.gridSize);
			gridSlider.setSnapToTicks(true);
			gridSlider.setMaximumSize(new Dimension(210, 40));
			gridSlider.setAlignmentX(Component.LEFT_ALIGNMENT);
			gridSlider.setToolTipText("Spielfeldgröße auswählen");
			gridSlider.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			gridSlider.addChangeListener(new ChangeListener() {
			      public void stateChanged(ChangeEvent event) {
			        GuiTester.gridSize = gridSlider.getValue();
			        gridText.setText("Feldgröße: " + gridSlider.getValue() + "*" + gridSlider.getValue());
			    }});
				
		// Counters Layout
		counters.add(Box.createRigidArea(new Dimension(0, 30)));
		counters.add(fiveFieldElementText);
		counters.add(Box.createVerticalStrut(3));
		counters.add(fiveFieldElementCountChange);
		counters.add(Box.createVerticalStrut(15));
		counters.add(fourFieldElementText);
		counters.add(Box.createVerticalStrut(3));
		counters.add(fourFieldElementCountChange);
		counters.add(Box.createVerticalStrut(15));
		counters.add(threeFieldElementText);
		counters.add(Box.createVerticalStrut(3));
		counters.add(threeFieldElementCountChange);
		counters.add(Box.createVerticalStrut(15));
		counters.add(twoFieldElementText);
		counters.add(Box.createVerticalStrut(3));
		counters.add(twoFieldElementCountChange);
		counters.add(Box.createVerticalStrut(35));
		counters.add(gridText);
		counters.add(Box.createVerticalStrut(0));
		counters.add(gridSlider);
		counters.add(Box.createRigidArea(new Dimension(0, 1000)));
		
		// Themes Settings
		themes.setLayout(new BoxLayout(themes, BoxLayout.Y_AXIS));
		themes.setOpaque(false);
		themes.setVisible(false);
		
		// Themes Elements
		Font themesFont = new Font("Krungthep", Font.PLAIN, 20);
		
		themesHeading.setFont(themesFont);
		themesHeading.setForeground(textColor);
		
		battleshipsButton.setPreferredSize(new Dimension(210, 30));
		battleshipsButton.setFont(themesFont);
		battleshipsButton.setForeground(textColor);
		battleshipsButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!GuiTester.theme.equals("Battleships")) {
					GuiTester.theme = "Battleships";
					loadThemeItems("Battleships");
					updateThemeItems();
				}
				themes.setVisible(false);
				settings.add(counters, BorderLayout.EAST);
				counters.setVisible(true);
			}	
		});
		battlecarsButton.setPreferredSize(new Dimension(210, 30));
		battlecarsButton.setFont(themesFont);
		battlecarsButton.setForeground(textColor);
		battlecarsButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!GuiTester.theme.equals("Battlecars")) {
					GuiTester.theme = "Battlecars";
					loadThemeItems("Battlecars");
					updateThemeItems();
				}
				themes.setVisible(false);
				settings.add(counters, BorderLayout.EAST);
				counters.setVisible(true);
			}	
		});
		ButtonGroup themesGroup = new ButtonGroup();
		themesGroup.add(battleshipsButton);
		themesGroup.add(battlecarsButton);
		
		// Themes Layout
		themes.add(Box.createVerticalGlue());
		themes.add(themesHeading);
		themes.add(Box.createVerticalStrut(10));
		themes.add(battleshipsButton);
		themes.add(battlecarsButton);
		themes.add(Box.createVerticalGlue());
		
		// Frame Settings
		frame.getContentPane().add(panel);
		frame.setVisible(true);
	}
	
	public void selectCurrentThemeButton() {
		if (GuiTester.theme.equals("Battleships")) {
			battleshipsButton.setSelected(true);
		}
		if (GuiTester.theme.equals("Battlecars")) {
			battlecarsButton.setSelected(true);
		}
	}
	
	public void throwErrorMessage(int i) {
		// Too many ships for the specific grid
		if (i==1) {
			JOptionPane.showMessageDialog(panel, "Zu viele " + GuiTester.themeIdentifierPlural + " für das gewählte Spielfeld!\nAnzahl der "
					+ GuiTester.themeIdentifierPlural + " senken oder das Spielfeld vergrößern.",
				      "Fehlermeldung: Zu viele Schiffe für das Spielfeld", JOptionPane.ERROR_MESSAGE);
			return;
		}
		// No ships selected at all
		if (i==2) {
			JOptionPane.showMessageDialog(panel, "Es wurden keine " + GuiTester.themeIdentifierPlural + " ausgewählt!\nMindestens ein "
					+ GuiTester.themeIdentifierSingular + " auswählen, um fortzufahren.",
				      "Fehlermeldung: Keine Schiffe ausgewählt", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
	}
	
	public void showInfo() {
		ImageIcon hsaalenIcon = new ImageIcon(new ImageIcon("src/res/hsaalen.png").getImage().getScaledInstance(100, 70, Image.SCALE_SMOOTH));
		JOptionPane.showOptionDialog(panel, "Dieses Spiel wurde als "
				+ "Teil eines Programmierpraktikums\n"
				+ "an der Hochschule Aalen geschrieben von:\n\n"
				+ "Fabian Schwarz, Lukas Pietzschmann und Vincent Ugrai", 
				"Super wichtige Information", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, hsaalenIcon, new String[] {"Wirklich toll", "Mega", "Ich bin begeistert"}, null);
	}
	
	public void loadThemeItems(String theme) {
		
		if (theme.equals("Battleships")) {
			
			frame.setTitle("Battleships");
			
			GuiTester.themeIdentifierSingular = "Schiff";
			GuiTester.themeIdentifierPlural = "Schiffe";
			
			GuiTester.fiveFieldElementName = "Flugzeugträger";
			GuiTester.fourFieldElementName = "Schlachtschiff";
			GuiTester.threeFieldElementName = "Zerstörer";
			GuiTester.twoFieldElementName = "U-Boot";
			
			fiveFieldElementIconFromSide = new ImageIcon(new ImageIcon("src/res/carrier.png").getImage().getScaledInstance(250, 50, Image.SCALE_SMOOTH));
			fourFieldElementIconFromSide = new ImageIcon(new ImageIcon("src/res/battleship.png").getImage().getScaledInstance(200, 50, Image.SCALE_SMOOTH));
			threeFieldElementIconFromSide = new ImageIcon(new ImageIcon("src/res/destroyer.png").getImage().getScaledInstance(150, 50, Image.SCALE_SMOOTH));
			twoFieldElementIconFromSide = new ImageIcon(new ImageIcon("src/res/submarine.png").getImage().getScaledInstance(100, 50, Image.SCALE_SMOOTH));
			
			themeIcon = new ImageIcon(new ImageIcon("src/res/battleshipsThemeIcon.png").getImage());
//			themeIcon = new ImageIcon(new ImageIcon("src/res/battleshipsThemeIcon2.png").getImage());
			
		}
		
		if (theme.equals("Battlecars")) {
			
			frame.setTitle("Battlecars");
			
			GuiTester.themeIdentifierSingular = "Fahrzeug";
			GuiTester.themeIdentifierPlural = "Fahrzeuge";
			
			GuiTester.fiveFieldElementName = "Bus";
			GuiTester.fourFieldElementName = "Truck";
			GuiTester.threeFieldElementName = "Sportwagen";
			GuiTester.twoFieldElementName = "Kombi";
			
			fiveFieldElementIconFromSide = new ImageIcon(new ImageIcon("src/res/bus.png").getImage().getScaledInstance(250, 50, Image.SCALE_SMOOTH));
			fourFieldElementIconFromSide = new ImageIcon(new ImageIcon("src/res/truck.png").getImage().getScaledInstance(200, 50, Image.SCALE_SMOOTH));
			threeFieldElementIconFromSide = new ImageIcon(new ImageIcon("src/res/sportscar.png").getImage().getScaledInstance(150, 50, Image.SCALE_SMOOTH));
			twoFieldElementIconFromSide = new ImageIcon(new ImageIcon("src/res/kombi.png").getImage().getScaledInstance(100, 50, Image.SCALE_SMOOTH));
			
			themeIcon = new ImageIcon(new ImageIcon("src/res/battlecarsThemeIcon2.png").getImage());
		}
		
		//...
	}
	
	public boolean noElementsSelected() {
		if (GuiTester.fiveFieldElementCount + GuiTester.fourFieldElementCount + GuiTester.threeFieldElementCount + GuiTester.twoFieldElementCount == 0) {
			return true;
		}
		return false;
	}
	
	public void updateThemeItems() {
		
		// Update Title
		themeTitle.setText(GuiTester.theme);
		
		// Update ThemeIcon
		themeIconPanel.repaint();
		themeIconPanel.revalidate();
		
		// Update Icons
		fiveFieldElementIcon.setIcon(fiveFieldElementIconFromSide);
		fourFieldElementIcon.setIcon(fourFieldElementIconFromSide);
		threeFieldElementIcon.setIcon(threeFieldElementIconFromSide);
		twoFieldElementIcon.setIcon(twoFieldElementIconFromSide);
		
		// Update Element Names
		fiveFieldElementText.setText(GuiTester.fiveFieldElementCount + "x " + GuiTester.fiveFieldElementName);
		fourFieldElementText.setText(GuiTester.fourFieldElementCount + "x " + GuiTester.fourFieldElementName);
		threeFieldElementText.setText(GuiTester.threeFieldElementCount + "x " + GuiTester.threeFieldElementName);
		twoFieldElementText.setText(GuiTester.twoFieldElementCount + "x " + GuiTester.twoFieldElementName);
		
		// Update Buttons
		fiveFieldElementCountIncrease.setToolTipText("Anzahl erhöhen: " + GuiTester.fiveFieldElementName);
		fiveFieldElementCountDecrease.setToolTipText("Anzahl senken: " + GuiTester.fiveFieldElementName);
		fourFieldElementCountIncrease.setToolTipText("Anzahl erhöhen: " + GuiTester.fourFieldElementName);
		fourFieldElementCountDecrease.setToolTipText("Anzahl senken: " + GuiTester.fourFieldElementName);
		threeFieldElementCountIncrease.setToolTipText("Anzahl erhöhen: " + GuiTester.threeFieldElementName);
		threeFieldElementCountDecrease.setToolTipText("Anzahl senken: " + GuiTester.threeFieldElementName);
		twoFieldElementCountIncrease.setToolTipText("Anzahl erhöhen: " + GuiTester.twoFieldElementName);
		twoFieldElementCountDecrease.setToolTipText("Anzahl senken: " + GuiTester.twoFieldElementName);
		
	}
	
}
