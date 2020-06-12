package main.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class JOptionPaneConnect {
	private Component parentComponent;
	private Object[] options = {"Bestätigen", "Abbrechen"};
	private JTextField textfield;
	private JLabel ip;
	private char role = 's';
	
	public JOptionPaneConnect(Component parentComponent) {
		this.parentComponent = parentComponent;
	}
	
	public int displayGui() {
		int n = JOptionPane.showOptionDialog(parentComponent, getPanel(), "Verbindungseinstellungen",
				JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		return n;
	}
	
	private JPanel getPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JRadioButton serverButton = new JRadioButton("Verbinden als Server");
		serverButton.setPreferredSize(new Dimension(210, 30));
		serverButton.setSelected(true);
		serverButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				role = 's';
				textfield.setEnabled(false);
				ip.setEnabled(false);
				textfield.setText("192.168.21.2");
				panel.validate();
				panel.repaint();
			}
		});
		JRadioButton clientButton = new JRadioButton("Verbinden als Client");
		clientButton.setPreferredSize(new Dimension(210, 30));
		clientButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				role = 'c';
				textfield.setEnabled(true);
				ip.setEnabled(true);
				panel.validate();
				panel.repaint();
			}
		});
		
		ButtonGroup connectGroup = new ButtonGroup();
		connectGroup.add(serverButton);
		connectGroup.add(clientButton);
		ip = new JLabel("IP-Adresse");
		textfield = new JTextField("192.168.21.2");
		textfield.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (textfield.isEnabled() == true) textfield.setText("");
			}
		});
		textfield.setEnabled(false);
		ip.setEnabled(false);
		panel.add(Box.createVerticalGlue());
		panel.add(serverButton);
		panel.add(clientButton);
		panel.add(Box.createVerticalStrut(15));
		panel.add(ip);
		panel.add(textfield);
		panel.add(Box.createVerticalGlue());
		return panel;
	}
	
	public String getIP() {
		if (role == 's') {
			return null;
		}
		if (role == 'c') {
			return textfield.getText();
		}
		return null;
	}
	
	public char getRole() {
		return role;
	}
	
}

