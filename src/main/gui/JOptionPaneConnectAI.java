package gui;

import ai.Difficulty;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class JOptionPaneConnectAI {
	private Component parentComponent;
	private Object[] options = {"Bestätigen", "Abbrechen"};
	private JTextField textfield;
	private JLabel ip;
	private String role = "server";
	private Difficulty difficulty = Difficulty.medium;
	
	public JOptionPaneConnectAI(Component parentComponent) {
		this.parentComponent = parentComponent;
	}
	
	public int displayGui() {
		return JOptionPane.showOptionDialog(parentComponent, getPanel(), "Weitere Einstellungen",
				JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
	}
	
	private JPanel getPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JRadioButton serverButton = new JRadioButton("Verbinden als Server");
		serverButton.setSelected(true);
		serverButton.addActionListener(arg0 -> {
			role = "server";
			textfield.setEnabled(false);
			ip.setEnabled(false);
			textfield.setText("192.168.21.2");
			panel.validate();
			panel.repaint();
		});
		JRadioButton clientButton = new JRadioButton("Verbinden als Client");
		clientButton.addActionListener(arg0 -> {
			role = "client";
			textfield.setEnabled(true);
			ip.setEnabled(true);
			panel.validate();
			panel.repaint();
		});
		
		ButtonGroup connectGroup = new ButtonGroup();
		connectGroup.add(serverButton);
		connectGroup.add(clientButton);
		
		ip = new JLabel("IP-Adresse");
		textfield = new JTextField("192.168.21.2");
		textfield.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (textfield.isEnabled()) textfield.setText("");
			}
		});
		
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
		
		textfield.setEnabled(false);
		ip.setEnabled(false);
		panel.add(Box.createVerticalGlue());
		panel.add(serverButton);
		panel.add(clientButton);
		panel.add(Box.createVerticalStrut(15));
		panel.add(ip);
		panel.add(textfield);
		panel.add(Box.createVerticalStrut(15));
		panel.add(aiDifficulty);
		panel.add(easyButton);
		panel.add(mediumButton);
		panel.add(hardButton);
		panel.add(Box.createVerticalGlue());
		return panel;
	}
	
	public String getIP() {
		if (role.equals("string")) {
			return null;
		}
		if (role.equals("client")) {
			return textfield.getText();
		}
		return null;
	}
	
	public String getRole() {
		return role;
	}
	
	public Difficulty getDifficulty() {
		return difficulty;
	}
	
	public static void main(String[] args) {
		JOptionPaneConnectAI connect = new JOptionPaneConnectAI(null);
		connect.displayGui();
	}
}

