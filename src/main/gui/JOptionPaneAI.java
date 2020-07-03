package main.gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	private String difficulty = "medium";
	
	public JOptionPaneAI(Component parentComponent) {
		this.parentComponent = parentComponent;
	}
	
	public int displayGui() {
		int n = JOptionPane.showOptionDialog(parentComponent, getPanel(), "Weitere Einstellungen",
				JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		return n;
	}
	
	private JPanel getPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JLabel aiDifficulty = new JLabel("AI-Schwierigkeit: ");
		
		JRadioButton easyButton = new JRadioButton("Leicht");
		easyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				difficulty = "easy";
			}
		});
		
		JRadioButton mediumButton = new JRadioButton("Mittel");
		mediumButton.setSelected(true);
		mediumButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				difficulty = "medium";
			}
		});
		
		JRadioButton hardButton = new JRadioButton("Schwer");
		hardButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				difficulty = "hard";
			}
		});
		
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
	
	public String getDifficulty() {
		return difficulty;
	}
	
	public static void main(String[] args) {
		JOptionPaneAI connect = new JOptionPaneAI(null);
		connect.displayGui();
	}
}

