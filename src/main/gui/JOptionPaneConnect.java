package gui;

import network.Network;
import network.Role;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.*;

/**
 * Diese Klasse zeigt einen Dialog zum Verbinden mit einem Server oder Client an.
 */
public class JOptionPaneConnect {
	private Component parentComponent;
	private Object[] options = {"Bestätigen", "Abbrechen"};
	private JTextField ipTextField;
	private JTextField portTextField;
	private JTextField myIp;
	private JLabel ip;
	private JLabel port;
	private JLabel myIpLable;
	private Role role = Role.server;
	
	/**
	 * Erstellt den Dialog.
	 * @param parentComponent Der Component, über dem der Dialog angezeigt wird.
	 */
	public JOptionPaneConnect(Component parentComponent) {
		this.parentComponent = parentComponent;
	}
	
	/**
	 * Zeigt den tatsächlichen Dialog.
	 * @return Einen Integer, der die Auswahl des Benutzers angibt.
	 */
	public int displayGui() {
		return JOptionPane.showOptionDialog(parentComponent, getPanel(), "Weitere Einstellungen",
				JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
	}
	
	/**
	 * Gibt das JPanel des Dialogs zurück.
	 * @return Das JPanel des Dialogs.
	 */
	private JPanel getPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JRadioButton serverButton = new JRadioButton("Verbinden als Server");
		serverButton.setPreferredSize(new Dimension(210, 30));
		serverButton.setSelected(true);
		serverButton.addActionListener(arg0 -> {
			role = Role.server;
			myIp.setEnabled(true);
			myIpLable.setEnabled(true);
			ipTextField.setEnabled(false);
			ip.setEnabled(false);
			ipTextField.setText("127.0.0.1");
			portTextField.setEnabled(false);
			port.setEnabled(false);
			portTextField.setText(String.valueOf(Network.PORT));
			panel.validate();
			panel.repaint();
		});
		JRadioButton clientButton = new JRadioButton("Verbinden als Client");
		clientButton.setPreferredSize(new Dimension(210, 30));
		clientButton.addActionListener(arg0 -> {
			role = Role.client;
			myIp.setEnabled(false);
			myIpLable.setEnabled(false);
			ipTextField.setEnabled(true);
			portTextField.setEnabled(true);
			ip.setEnabled(true);
			port.setEnabled(true);
			panel.validate();
			panel.repaint();
		});
		
		myIp = new JFormattedTextField();
		myIp.setEditable(false);
		try {
			myIp.setText(String.format("%s:%d", InetAddress.getLocalHost().getHostAddress(), Network.PORT));
		}catch(UnknownHostException e) {
			e.printStackTrace();
		}
		myIpLable = new JLabel("Eigene IP Adresse");
		
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
		portTextField = new JTextField(String.valueOf(Network.PORT));
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
		panel.add(myIpLable);
		panel.add(myIp);
		panel.add(Box.createVerticalGlue());
		return panel;
	}
	
	/**
	 * Gibt die eingetragene IP zurück. Nur valide, falls {@link #getRole()} "client" zurückgibt.
	 * @return Die eingetragene IP Adresse.
	 */
	public String getIP() {
		if (role.equals("client")) {
			return ipTextField.getText();
		}
		return null;
	}
	
	/**
	 * Gibt den eingetragenen Port zurück. Nur valide, falls {@link #getRole()} "client" zurückgibt.
	 * @return Den eingetragenen Port.
	 */
	public int getPort(){
		if (role.equals("client")){
			return Integer.parseInt(portTextField.getText());
		}
		return -1;
	}
	
	/**
	 * Gibt die Rolle des Spielers im Netzwert zurück.
	 *
	 * @return Die Rolle des Spielers im Netzwerl.
	 */
	public Role getRole() {
		return role;
	}
}

