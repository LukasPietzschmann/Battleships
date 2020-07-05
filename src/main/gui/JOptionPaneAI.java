package gui;

import ai.Difficulty;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class JOptionPaneAI {
	private Component parentComponent;
	private Object[] options = {"Bestätigen", "Abbrechen"};
	private Difficulty difficulty = Difficulty.medium;
	
	public JOptionPaneAI(Component parentComponent) {
		this.parentComponent = parentComponent;
	}
	
	public int displayGui() {
		return JOptionPane.showOptionDialog(parentComponent, getPanel(), "Weitere Einstellungen",
				JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
	}
	
	private JPanel getPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JLabel aiDifficulty = new JLabel("AI-Schwierigkeit: ");
		
		JRadioButton easyButton = new JRadioButton("Leicht");
		easyButton.addActionListener(arg0 -> difficulty = Difficulty.easy);
		
		JRadioButton mediumButton = new JRadioButton("Mittel");
		mediumButton.setSelected(true);
		mediumButton.addActionListener(arg0 -> difficulty = Difficulty.medium);
		
		JRadioButton hardButton = new JRadioButton("Schwer");
		hardButton.addActionListener(arg0 -> difficulty = Difficulty.hard);
		
		ButtonGroup difficultyGroup = new ButtonGroup();
		difficultyGroup.add(easyButton);
		difficultyGroup.add(mediumButton);
		difficultyGroup.add(hardButton);
		
		panel.add(Box.createVerticalGlue());
		panel.add(aiDifficulty);
		panel.add(easyButton);
		panel.add(mediumButton);
		panel.add(hardButton);
		panel.add(Box.createVerticalGlue());
		return panel;
	}
	
	public Difficulty getDifficulty() {
		return difficulty;
	}
	
	public static void main(String[] args) {
		JOptionPaneAI connect = new JOptionPaneAI(null);
		connect.displayGui();
	}
}

