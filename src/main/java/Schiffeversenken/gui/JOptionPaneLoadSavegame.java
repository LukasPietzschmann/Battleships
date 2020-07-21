package Schiffeversenken.gui;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class JOptionPaneLoadSavegame {
	private Component parentComponent;
	private Object[] options = {"Laden", "Abbrechen"};
	JTextField textfield;
	
	public JOptionPaneLoadSavegame(Component parentComponent) {
		this.parentComponent = parentComponent;
	}
	
	public int displayGui() {
		return JOptionPane.showOptionDialog(parentComponent, getPanel(), "Spielstand laden",
				JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
	}
	
	private JPanel getPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JLabel saveid = new JLabel("Spielstand-ID eingeben:");
		
		textfield = new JTextField();
		
		panel.add(Box.createVerticalGlue());
		panel.add(saveid);
		panel.add(textfield);
		panel.add(Box.createVerticalGlue());
		return panel;
	}
	
	public String getSavegameId() {
		return textfield.getText();
	}
}

