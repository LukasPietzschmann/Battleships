package gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

public class MainMenu {
	
	private JFrame frame;
	private JPanel panel = new JPanel();
	private JPanel modes = new JPanel();
	private JPanel settings = new JPanel();
		private JPanel icons = new JPanel();
			private ImageIcon fiveFieldElementIconFromSide;
			private ImageIcon fourFieldElementIconFromSide;
			private ImageIcon threeFieldElementIconFromSide;
			private ImageIcon twoFieldElementIconFromSide;
		private JPanel counters = new JPanel();
			private JPanel fiveFieldElementCountChange = new JPanel();
			private JPanel fourFieldElementCountChange = new JPanel();
			private JPanel threeFieldElementCountChange = new JPanel();
			private JPanel twoFieldElementCountChange = new JPanel();
		private JPanel themes = new JPanel();
	
	ImageIcon themeIcon;
	
	MainMenu(JFrame frame){
		this.frame = frame;
		loadThemeItems(GuiTester.theme);
	}
	
	MainMenu(JFrame frame, String theme){
		this.frame = frame;
		loadThemeItems(theme);
	}
	
	public void setUpMenu() {
		
		// Panel Settings
		panel.setLayout(new BorderLayout());
		panel.setBackground(Color.DARK_GRAY);
		panel.setBorder(BorderFactory.createEmptyBorder(20, 10, 30, 60));
		
		// Panel Layout
		panel.add(modes, BorderLayout.SOUTH);
		panel.add(settings, BorderLayout.EAST);
		
		// Modes Settings
		modes.setLayout(new BoxLayout(modes, BoxLayout.X_AXIS));
		modes.setOpaque(false);
		
		// Modes Elements
			// Player vs. Computer Button
			JButton pvcButton = new JButton("Spieler vs. Computer");
			ImageIcon pvcIcon = new ImageIcon(new ImageIcon("/Users/fabian/Documents/GitHub/Schiffeversenken/src/res/PlayerVsComputer.png").getImage().getScaledInstance(225, 150, Image.SCALE_SMOOTH));
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
					// ...
				}
			});
			
			// Player vs. Player Button
			JButton pvpButton = new JButton("Spieler vs. Spieler");
			ImageIcon pvpIcon = new ImageIcon(new ImageIcon("/Users/fabian/Documents/GitHub/Schiffeversenken/src/res/PlayerVsPlayer.png").getImage().getScaledInstance(225, 150, Image.SCALE_SMOOTH));
			pvpButton.setIcon(pvpIcon);
			pvpButton.setHorizontalAlignment(SwingConstants.LEFT);
			pvpButton.setBorder(null);
			pvpButton.setToolTipText("Spieler vs. Spieler");
			pvpButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			pvpButton.setMinimumSize(new Dimension(225, 150));
			pvpButton.setMaximumSize(new Dimension(225, 150));
			pvpButton.setPreferredSize(new Dimension(225, 150));
			
			// Computer vs. Computer Button
			JButton cvcButton = new JButton("Computer vs. Computer");
			ImageIcon cvcIcon = new ImageIcon(new ImageIcon("/Users/fabian/Documents/GitHub/Schiffeversenken/src/res/ComputerVsComputer.png").getImage().getScaledInstance(225, 150, Image.SCALE_SMOOTH));
			cvcButton.setIcon(cvcIcon);
			cvcButton.setHorizontalAlignment(SwingConstants.LEFT);
			cvcButton.setBorder(null);
			cvcButton.setToolTipText("Computer vs. Computer");
			cvcButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			cvcButton.setMinimumSize(new Dimension(225, 150));
			cvcButton.setMaximumSize(new Dimension(225, 150));
			cvcButton.setPreferredSize(new Dimension(225, 150));
			
			// Information Button
			JButton infoButton = new JButton("Information");
			ImageIcon infoIcon = new ImageIcon(new ImageIcon("/Users/fabian/Documents/GitHub/Schiffeversenken/src/res/info.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
			infoButton.setIcon(infoIcon);
			infoButton.setHorizontalAlignment(SwingConstants.LEFT);
			infoButton.setBorder(null);
			infoButton.setToolTipText("Information");
			infoButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			infoButton.setMinimumSize(new Dimension(50, 50));
			infoButton.setMaximumSize(new Dimension(50, 50));
			infoButton.setPreferredSize(new Dimension(50, 50));
			
			// LoadFile Button
			JButton loadButton = new JButton("Spiel laden");
			ImageIcon loadIcon = new ImageIcon(new ImageIcon("/Users/fabian/Documents/GitHub/Schiffeversenken/src/res/Load_Save_Icon.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
			loadButton.setIcon(loadIcon);
			loadButton.setHorizontalAlignment(SwingConstants.LEFT);
			loadButton.setBorder(null);
			loadButton.setToolTipText("Spiel laden");
			loadButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			loadButton.setMinimumSize(new Dimension(50, 50));
			loadButton.setMaximumSize(new Dimension(50, 50));
			loadButton.setPreferredSize(new Dimension(50, 50));
			
			// Themes Button
			JButton themesButton = new JButton("Spielstil wählen");
			ImageIcon themesIcon = new ImageIcon(new ImageIcon("/Users/fabian/Documents/GitHub/Schiffeversenken/src/res/Theme_Icon.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
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
					} else {
						themes.setVisible(false);
						settings.add(counters, BorderLayout.EAST);
						counters.setVisible(true);
					}
					frame.getContentPane().revalidate();
					frame.getContentPane().repaint();
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
		modes.add(Box.createHorizontalStrut(30));
		modes.add(infoButton);
		modes.add(Box.createHorizontalStrut(15));
		modes.add(loadButton);
		modes.add(Box.createHorizontalStrut(15));
		modes.add(themesButton);
		modes.add(Box.createHorizontalGlue());
		
		// Settings Settings
		settings.setLayout(new BorderLayout());
		settings.setOpaque(true);
		settings.setBackground(Color.cyan);
		
		// Settings Layout
		settings.add(icons, BorderLayout.WEST);
		settings.add(counters, BorderLayout.EAST);
				
		// Icons Settings
		icons.setLayout(new BoxLayout(icons, BoxLayout.Y_AXIS));
		icons.setAlignmentX(Component.RIGHT_ALIGNMENT);
		icons.setOpaque(true);
		
		// Icons Elements
			// fiveFieldElementIcon Label
			JLabel fiveFieldElementIcon = new JLabel(fiveFieldElementIconFromSide);
			fiveFieldElementIcon.setMinimumSize(new Dimension(175, 40));
			fiveFieldElementIcon.setOpaque(true);
			fiveFieldElementIcon.setBackground(Color.green);

			// fourFieldElementIcon Label
			JLabel fourFieldElementIcon = new JLabel(fourFieldElementIconFromSide);
			fourFieldElementIcon.setMinimumSize(new Dimension(160, 40));
			fourFieldElementIcon.setOpaque(true);
			fourFieldElementIcon.setBackground(Color.blue);
			
			// threeFieldElementIcon Label
			JLabel threeFieldElementIcon = new JLabel(threeFieldElementIconFromSide, SwingConstants.RIGHT);
			threeFieldElementIcon.setMinimumSize(new Dimension(145, 40));
			threeFieldElementIcon.setOpaque(true);
			threeFieldElementIcon.setBackground(Color.red);
			
			// twoFieldElementIcon Label
			JLabel twoFieldElementIcon = new JLabel(twoFieldElementIconFromSide, SwingConstants.RIGHT);
			twoFieldElementIcon.setMinimumSize(new Dimension(125, 40));
			
			// gridIcon Label
			JLabel gridIcon = new JLabel();
			ImageIcon gridSymbol = new ImageIcon(new ImageIcon("/Users/fabian/Documents/GitHub/Schiffeversenken/src/res/gridIcon.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
			gridIcon.setIcon(gridSymbol);
			twoFieldElementIcon.setMinimumSize(new Dimension(50, 50));
			
		// Icons Layout
		icons.add(Box.createRigidArea(new Dimension(0, 46)));
		icons.add(fiveFieldElementIcon);
		icons.add(Box.createVerticalStrut(36));
		icons.add(fourFieldElementIcon);
		icons.add(Box.createVerticalStrut(36));
		icons.add(threeFieldElementIcon);
		icons.add(Box.createVerticalStrut(36));
		icons.add(twoFieldElementIcon);
		icons.add(Box.createVerticalStrut(40));
		icons.add(gridIcon);
		icons.add(Box.createRigidArea(new Dimension(20, 1000)));
		
		// Counters Settings
		counters.setLayout(new BoxLayout(counters, BoxLayout.Y_AXIS));
		counters.setOpaque(false);
		
		// Counters Elements
			// countersFont Font
			Font countersFont = new Font("Krungthep", Font.PLAIN, 20);
		
			// fiveFieldElementText Label
			JLabel fiveFieldElementText = new JLabel(GuiTester.fiveFieldElementCount + 
					"x " + GuiTester.fiveFieldElementName, SwingConstants.LEFT);
			fiveFieldElementText.setMinimumSize(new Dimension(200, 20));
			fiveFieldElementText.setPreferredSize(new Dimension(200, 20));
			fiveFieldElementText.setFont(countersFont);
			fiveFieldElementText.setOpaque(true);
			fiveFieldElementText.setBackground(Color.red);
			JPanel fiveFieldElementPanel = new JPanel();
			fiveFieldElementPanel.setLayout(new BoxLayout(fiveFieldElementPanel, BoxLayout.X_AXIS));
			fiveFieldElementPanel.add(fiveFieldElementText);
			
			// fiveFieldElementCountChange Settings
			fiveFieldElementCountChange.setLayout(new BoxLayout(fiveFieldElementCountChange, BoxLayout.X_AXIS));
			fiveFieldElementCountChange.setOpaque(true);
			fiveFieldElementCountChange.setBackground(Color.green);
			
			// fiveFieldElementCountChange Elements
				// fiveFieldElementCountIncrease Button
				JButton fiveFieldElementCountIncrease = new JButton("+");
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
							GuiTester.fiveFieldElementCount = 1;
						}
						fiveFieldElementText.setText(GuiTester.fiveFieldElementCount + 
								"x " + GuiTester.fiveFieldElementName);
					}	
				});
		
				// fiveFieldElementCountDecrease Button
				JButton fiveFieldElementCountDecrease = new JButton("-");
				fiveFieldElementCountDecrease.setMinimumSize(new Dimension(30, 30));
				fiveFieldElementCountDecrease.setMaximumSize(new Dimension(30, 30));
				fiveFieldElementCountDecrease.setPreferredSize(new Dimension(30, 30));
				fiveFieldElementCountDecrease.setToolTipText("Anzahl senken: " + GuiTester.fiveFieldElementName);
				fiveFieldElementCountDecrease.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				fiveFieldElementCountDecrease.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent arg0) {
						if (GuiTester.fiveFieldElementCount > 1){
							GuiTester.fiveFieldElementCount -= 1;
						}else{
							GuiTester.fiveFieldElementCount = GuiTester.fiveFieldElementMaxCount;
						}
						fiveFieldElementText.setText(GuiTester.fiveFieldElementCount + 
								"x " + GuiTester.fiveFieldElementName);
					}	
				});
		
			// fiveFieldElementCountChange Layout
			fiveFieldElementCountChange.add(Box.createRigidArea(new Dimension(60, 30)));
			fiveFieldElementCountChange.add(fiveFieldElementCountIncrease);
			fiveFieldElementCountChange.add(Box.createHorizontalStrut(3));
			fiveFieldElementCountChange.add(fiveFieldElementCountDecrease);
			fiveFieldElementCountChange.add(Box.createHorizontalGlue());
				
			// fourFieldElementText Label
			JLabel fourFieldElementText = new JLabel(GuiTester.fourFieldElementCount + 
					"x " + GuiTester.fourFieldElementName, SwingConstants.LEFT);
//			fourFieldElementText.setHorizontalAlignment(SwingConstants.LEFT);
			fourFieldElementText.setPreferredSize(new Dimension(240, 30));
			fourFieldElementText.setFont(countersFont);
				
			// fourFieldElementCountChange Settings
			fourFieldElementCountChange.setLayout(new BoxLayout(fourFieldElementCountChange, BoxLayout.X_AXIS));
			fourFieldElementCountChange.setOpaque(false);
				
			// fourFieldElementCountChange Elements
				// fourFieldElementCountIncrease Button
				JButton fourFieldElementCountIncrease = new JButton("+");
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
							GuiTester.fourFieldElementCount = 1;
						}
						fourFieldElementText.setText(GuiTester.fourFieldElementCount + 
								"x " + GuiTester.fourFieldElementName);
					}	
				});
		
				// fourFieldElementCountDecrease Button
				JButton fourFieldElementCountDecrease = new JButton("-");
				fourFieldElementCountDecrease.setMinimumSize(new Dimension(30, 30));
				fourFieldElementCountDecrease.setMaximumSize(new Dimension(30, 30));
				fourFieldElementCountDecrease.setPreferredSize(new Dimension(30, 30));
				fourFieldElementCountDecrease.setToolTipText("Anzahl senken: " + GuiTester.fourFieldElementName);
				fourFieldElementCountDecrease.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				fourFieldElementCountDecrease.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent arg0) {
						if (GuiTester.fourFieldElementCount > 1){
							GuiTester.fourFieldElementCount -= 1;
						}else{
							GuiTester.fourFieldElementCount = GuiTester.fourFieldElementMaxCount;
						}
						fourFieldElementText.setText(GuiTester.fourFieldElementCount + 
								"x " + GuiTester.fourFieldElementName);
					}	
				});
			
			// fourFieldElementCountChange Layout
			fourFieldElementCountChange.add(Box.createRigidArea(new Dimension(60, 30)));
			fourFieldElementCountChange.add(fourFieldElementCountIncrease);
			fourFieldElementCountChange.add(Box.createHorizontalStrut(3));
			fourFieldElementCountChange.add(fourFieldElementCountDecrease);
			fourFieldElementCountChange.add(Box.createHorizontalGlue());
			
			// threeFieldElementText Label
			JLabel threeFieldElementText = new JLabel(GuiTester.threeFieldElementCount + 
					"x " + GuiTester.threeFieldElementName, SwingConstants.LEFT);
//			threeFieldElementText.setHorizontalAlignment(SwingConstants.LEFT);
			threeFieldElementText.setPreferredSize(new Dimension(240, 30));
			threeFieldElementText.setFont(countersFont);
			
			// threeFieldElementCountChange Settings
			threeFieldElementCountChange.setLayout(new BoxLayout(threeFieldElementCountChange, BoxLayout.X_AXIS));
			threeFieldElementCountChange.setOpaque(false);
			
			// threeFieldElementCountChange Elements
				// threeFieldElementCountIncrease Button
				JButton threeFieldElementCountIncrease = new JButton("+");
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
							GuiTester.threeFieldElementCount = 1;
						}
						threeFieldElementText.setText(GuiTester.threeFieldElementCount + 
								"x " + GuiTester.threeFieldElementName);
					}	
				});
				
				//threeFieldElementCountDecrease Button
				JButton threeFieldElementCountDecrease = new JButton("-");
				threeFieldElementCountDecrease.setMinimumSize(new Dimension(30, 30));
				threeFieldElementCountDecrease.setMaximumSize(new Dimension(30, 30));
				threeFieldElementCountDecrease.setPreferredSize(new Dimension(30, 30));
				threeFieldElementCountDecrease.setToolTipText("Anzahl senken: " + GuiTester.threeFieldElementName);
				threeFieldElementCountDecrease.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				threeFieldElementCountDecrease.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent arg0) {
						if (GuiTester.threeFieldElementCount > 1){
							GuiTester.threeFieldElementCount -= 1;
						}else{
							GuiTester.threeFieldElementCount = GuiTester.threeFieldElementMaxCount;
						}
						threeFieldElementText.setText(GuiTester.threeFieldElementCount + 
								"x " + GuiTester.threeFieldElementName);
					}	
				});
				
			// threeFieldElementCountChange Layout
			threeFieldElementCountChange.add(Box.createRigidArea(new Dimension(60, 30)));
			threeFieldElementCountChange.add(threeFieldElementCountIncrease);
			threeFieldElementCountChange.add(Box.createHorizontalStrut(3));
			threeFieldElementCountChange.add(threeFieldElementCountDecrease);
			threeFieldElementCountChange.add(Box.createHorizontalGlue());
			
			// twoFieldElementText Label
			JLabel twoFieldElementText = new JLabel(GuiTester.twoFieldElementCount + 
					"x " + GuiTester.twoFieldElementName, SwingConstants.LEFT);
//			twoFieldElementText.setHorizontalAlignment(SwingConstants.LEFT);
			twoFieldElementText.setPreferredSize(new Dimension(240, 30));
			twoFieldElementText.setFont(countersFont);
			
			// twoFieldElementCountChange Setting
			twoFieldElementCountChange.setLayout(new BoxLayout(twoFieldElementCountChange, BoxLayout.X_AXIS));
			twoFieldElementCountChange.setOpaque(false);
			
			// twoFieldElementCountChange Elements
				// twoFieldElementCountIncrease Button
				JButton twoFieldElementCountIncrease = new JButton("+");
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
							GuiTester.twoFieldElementCount = 1;
						}
						twoFieldElementText.setText(GuiTester.twoFieldElementCount + 
								"x " + GuiTester.twoFieldElementName);
					}	
				});
				
				//twoFieldElementCountDecrease Button
				JButton twoFieldElementCountDecrease = new JButton("-");
				twoFieldElementCountDecrease.setMinimumSize(new Dimension(30, 30));
				twoFieldElementCountDecrease.setMaximumSize(new Dimension(30, 30));
				twoFieldElementCountDecrease.setPreferredSize(new Dimension(30, 30));
				twoFieldElementCountDecrease.setToolTipText("Anzahl senken: " + GuiTester.twoFieldElementName);
				twoFieldElementCountDecrease.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				twoFieldElementCountDecrease.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent arg0) {
						if (GuiTester.twoFieldElementCount > 1){
							GuiTester.twoFieldElementCount -= 1;
						}else{
							GuiTester.twoFieldElementCount = GuiTester.twoFieldElementMaxCount;
						}
						twoFieldElementText.setText(GuiTester.twoFieldElementCount + 
								"x " + GuiTester.twoFieldElementName);
					}	
				});
				
			// twoFieldElementCountChange Layout
			twoFieldElementCountChange.add(Box.createRigidArea(new Dimension(60, 30)));
			twoFieldElementCountChange.add(twoFieldElementCountIncrease);
			twoFieldElementCountChange.add(Box.createHorizontalStrut(3));
			twoFieldElementCountChange.add(twoFieldElementCountDecrease);
			twoFieldElementCountChange.add(Box.createHorizontalGlue());
			
			// gridText Label
			JLabel gridText = new JLabel("Feldgröße: " + GuiTester.gridSize + "*" + GuiTester.gridSize, SwingConstants.LEFT);
			gridText.setMinimumSize(new Dimension(200, 30));
			gridText.setHorizontalAlignment(SwingConstants.LEFT);
			gridText.setFont(countersFont);
			
			// gridSlider Slider
			JSlider gridSlider = new JSlider();
			gridSlider.setMinimum(5);
			gridSlider.setMaximum(30);
			gridSlider.setMajorTickSpacing(5);
			gridSlider.setMinorTickSpacing(1);
			gridSlider.createStandardLabels(1);
			gridSlider.setPaintTicks(true);
			gridSlider.setPaintLabels(true);
			gridSlider.setValue(GuiTester.gridSize);
			gridSlider.setSnapToTicks(true);
			gridSlider.setMaximumSize(new Dimension(200, 40));
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
		counters.add(fiveFieldElementPanel);
//		counters.add(fiveFieldElementText);
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
		counters.add(Box.createVerticalStrut(30));
		counters.add(gridText);
		counters.add(Box.createVerticalStrut(3));
		counters.add(gridSlider);
		counters.add(Box.createRigidArea(new Dimension(0, 1000)));
		
		// Themes Settings
		themes.setLayout(new BoxLayout(themes, BoxLayout.Y_AXIS));
		themes.setOpaque(false);
		themes.setVisible(false);
		
		// Themes Elements
		JRadioButton auswahl1 = new JRadioButton("gelb");
		auswahl1.setMinimumSize(new Dimension(240, 30));
		auswahl1.setMaximumSize(new Dimension(240, 30));
		auswahl1.setPreferredSize(new Dimension(240, 30));
        JRadioButton auswahl2 = new JRadioButton("blau");
		ButtonGroup themesGroup = new ButtonGroup();
		themesGroup.add(auswahl1);
		themesGroup.add(auswahl2);
		
		// Themes Layout
		themes.add(auswahl1);
		themes.add(auswahl2);
		
		// Frame Settings
		frame.getContentPane().add(panel);
		frame.setVisible(true);
	}
	
	
	public void loadThemeItems(String theme) {
		
		if (theme.equals("Battleships")) {
			
			frame.setTitle("Battleships");
			
			GuiTester.fiveFieldElementName = "Flugzeugträger";
			GuiTester.fourFieldElementName = "Schlachtschiff";
			GuiTester.threeFieldElementName = "Zerstörer";
			GuiTester.twoFieldElementName = "U-Boot";
			
			fiveFieldElementIconFromSide = new ImageIcon(new ImageIcon("/Users/fabian/Documents/GitHub/Schiffeversenken/src/res/bla.png").getImage().getScaledInstance(175, 60, Image.SCALE_SMOOTH));
			fourFieldElementIconFromSide = new ImageIcon(new ImageIcon("/Users/fabian/Documents/GitHub/Schiffeversenken/src/res/bla2.png").getImage().getScaledInstance(160, 40, Image.SCALE_SMOOTH));
			threeFieldElementIconFromSide = new ImageIcon(new ImageIcon("/Users/fabian/Documents/GitHub/Schiffeversenken/src/res/bla3.png").getImage().getScaledInstance(145, 40, Image.SCALE_SMOOTH));
			twoFieldElementIconFromSide = new ImageIcon(new ImageIcon("/Users/fabian/Documents/GitHub/Schiffeversenken/src/res/bla4.png").getImage().getScaledInstance(125, 40, Image.SCALE_SMOOTH));
			
//			themeIcon = new ImageIcon(...)
			
		}
		
		if (theme.equals("Battlecars")) {
			
			frame.setTitle("Battlecars");
			
			GuiTester.fiveFieldElementName = "Bus";
			GuiTester.fourFieldElementName = "Limousine";
			GuiTester.threeFieldElementName = "Sportwagen";
			GuiTester.twoFieldElementName = "Mini";
			
//			fiveFieldElementIconFromSide = new ImageIcon(...)
//			fourFieldElementIconFromSide = new ImageIcon(...)
//			threeFieldElementIconFromSide = new ImageIcon(...)
//			twoFieldElementIconFromSide = new ImageIcon(...)
			
//			themeIcon = new ImageIcon(...)
		}
		
		//...
	}
}
