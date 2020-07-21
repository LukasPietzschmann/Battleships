package gui;

import java.awt.Component;
import java.awt.Dimension;
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
	private JTextField ipTextField;
	private JTextField portTextField;
	private JLabel ip;
	private JLabel port;
	private String role = "server";
	
	public JOptionPaneConnect(Component parentComponent) {
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
		serverButton.setPreferredSize(new Dimension(210, 30));
		serverButton.setSelected(true);
		serverButton.addActionListener(arg0 -> {
			role = "server";
			ipTextField.setEnabled(false);
			ip.setEnabled(false);
			ipTextField.setText("127.0.0.1");
			portTextField.setEnabled(false);
			port.setEnabled(false);
			portTextField.setText("4444");
			panel.validate();
			panel.repaint();
		});
		JRadioButton clientButton = new JRadioButton("Verbinden als Client");
		clientButton.setPreferredSize(new Dimension(210, 30));
		clientButton.addActionListener(arg0 -> {
			role = "client";
			ipTextField.setEnabled(true);
			portTextField.setEnabled(true);
			ip.setEnabled(true);
			port.setEnabled(true);
			panel.validate();
			panel.repaint();
		});
		
		ButtonGroup connectGroup = new ButtonGroup();
		connectGroup.add(serverButton);
		connectGroup.add(clientButton);
		ip = new JLabel("IP-Adresse");
		ipTextField = new JTextField("127.0.0.1");
		ipTextField.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (ipTextField.isEnabled()) ipTextField.setText("");
			}
		});
		ipTextField.setEnabled(false);
		ip.setEnabled(false);

		port = new JLabel("Port");
		portTextField = new JTextField("4444");
		portTextField.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (portTextField.isEnabled()) portTextField.setText("");
			}
		});
		portTextField.setEnabled(false);
		port.setEnabled(false);


		panel.add(Box.createVerticalGlue());
		panel.add(serverButton);
		panel.add(clientButton);
		panel.add(Box.createVerticalStrut(15));
		panel.add(ip);
		panel.add(ipTextField);
		panel.add(port);
		panel.add(portTextField);
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
}

