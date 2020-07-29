package gui;

import ai.Difficulty;
import network.Network;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.*;

public class JOptionPaneConnectAI {
	private Component parentComponent;
	private Object[] options = {"Bestätigen", "Abbrechen"};
	private JTextField ipTextField;
	private JTextField myIp;
	private JTextField portTextField;
	private JLabel ip;
	private JLabel port;
	private JLabel myIpLable;
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
			ipTextField.setEnabled(false);
			myIp.setEnabled(true);
			myIpLable.setEnabled(true);
			ip.setEnabled(false);
			ipTextField.setText("127.0.0.1");
			portTextField.setEnabled(false);
			port.setEnabled(false);
			portTextField.setText(String.valueOf(Network.PORT));
			panel.validate();
			panel.repaint();
		});
		JRadioButton clientButton = new JRadioButton("Verbinden als Client");
		clientButton.addActionListener(arg0 -> {
			role = "client";
			ipTextField.setEnabled(true);
			myIp.setEnabled(false);
			myIpLable.setEnabled(false);
			ip.setEnabled(true);
			portTextField.setEnabled(true);
			port.setEnabled(true);
			panel.validate();
			panel.repaint();
		});
		
		ButtonGroup connectGroup = new ButtonGroup();
		connectGroup.add(serverButton);
		connectGroup.add(clientButton);
		
		myIp = new JFormattedTextField();
		myIp.setEditable(false);
		try {
			myIp.setText(String.format("%s:%d",InetAddress.getLocalHost().getHostAddress(), Network.PORT));
		}catch(UnknownHostException e) {
			e.printStackTrace();
		}
		
		ip = new JLabel("IP-Adresse");
		ipTextField = new JTextField("127.0.0.1");
		ipTextField.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (ipTextField.isEnabled()) ipTextField.setText("");
			}
		});
		myIpLable = new JLabel("Eigene IP Adresse");
		
		ipTextField.setEnabled(false);
		ip.setEnabled(false);
	
		
		port = new JLabel("Port");
		portTextField = new JTextField(String.valueOf(Network.PORT));
		portTextField.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (portTextField.isEnabled()) portTextField.setText("");
			}
		});

		portTextField.setEnabled(false);
		port.setEnabled(false);

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
		panel.add(serverButton);
		panel.add(clientButton);
		panel.add(Box.createVerticalStrut(15));
		panel.add(ip);
		panel.add(ipTextField);
		panel.add(port);
		panel.add(portTextField);
		panel.add(myIpLable);
		panel.add(myIp);
		panel.add(Box.createVerticalStrut(15));
		panel.add(aiDifficulty);
		panel.add(easyButton);
		panel.add(mediumButton);
		panel.add(hardButton);
		panel.add(Box.createVerticalGlue());
		return panel;
	}
	
	public String getIP() {
		if (role.equals("client")) {
			return ipTextField.getText();
		}
		return null;
	}

	public String getPort(){
		if (role.equals("client")){
			return portTextField.getText();
		}
		return null;
	}
	
	public String getRole() {
		return role;
	}
	
	public Difficulty getDifficulty() {
		return difficulty;
	}
}

