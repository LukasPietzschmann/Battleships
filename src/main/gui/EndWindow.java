package gui;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * Das EndWindow wird angezeigt, sobald das Spiel zuende ist.
 */
public class EndWindow {
	public static final int WIN = 0;
	public static final int LOSE = 1;
	public static final int OPP_LEFT = 2;
	private JFrame frame;
	private GameWindow gw;
	private JDialog dialog = new JDialog();
	private JPanel mainPanel = new JPanel();
	private JPanel rightPanel = new JPanel();
	private ImageIcon icon = new ImageIcon();
	private JLabel message = new JLabel();
	
	/**
	 * Erstellt ein EndWindow.
	 * @param result Gibt an, wieso das Spiel beendet wurde. Entweder {@link #WIN}, {@link #LOSE}, oder {@link #OPP_LEFT}.
	 * @param frame Das Fenster über dem das EndWindow angezeigt werden soll.
	 * @param gw Referenz auf das aktuelle GameWindow.
	 */
	public EndWindow(int result, JFrame frame, GameWindow gw) {
		this.frame = frame;
		this.gw = gw;
		switch(result) {
			case (WIN):
				icon = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("win.png"))).getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH));
				message.setText("<html>Glückwunsch! <p/>Du hast gewonnen!</html>");
				break;
			case (LOSE):
				icon = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("lose.png"))).getImage().getScaledInstance(190, 180, Image.SCALE_SMOOTH));
				message.setText("<html>Schade! <p/>Du hast verloren!</html>");
				break;
			case (OPP_LEFT):
				icon = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("win.png"))).getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH));
				message.setText("<html>Dein Gegner hat aufgegeben! <p/>Du hast gewonnen!</html>");
				break;
		}
	}
	
	/**
	 * Initialisiert das EndWindow und zeigt es an.
	 */
	public void setUpMainWindow() {
		dialog.setSize(500, 300);
		dialog.setResizable(false);
		dialog.getContentPane().setBackground(MainMenu.backgroundColor);
		dialog.setUndecorated(true);
		dialog.setLocationRelativeTo(frame);
		//dialog.setAlwaysOnTop(true);
		dialog.getRootPane().
						setBorder(BorderFactory.createLineBorder(MainMenu.textColor, 2, true));
		
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		mainPanel.setOpaque(false);
		mainPanel.setBackground(MainMenu.backgroundColor);
		
		JLabel iconHolder = new JLabel();
		iconHolder.setIcon(icon);
		
		mainPanel.add(Box.createHorizontalStrut(40));
		mainPanel.add(iconHolder);
		mainPanel.add(Box.createHorizontalStrut(30));
		mainPanel.add(rightPanel);
		mainPanel.add(Box.createHorizontalStrut(30));
		
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		rightPanel.setBackground(MainMenu.backgroundColor);
		
		message.setForeground(MainMenu.textColor);
		message.setFont(MainWindow.font);
		
		ImageIcon menu = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("hauptmenue.png"))).getImage().getScaledInstance(160, 60, Image.SCALE_SMOOTH));
		JButton button = new JButton();
		button.setIcon(menu);
		button.setBorder(null);
		button.setToolTipText("Zurück zum Hauptmenü");
		button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		button.addActionListener(arg0 -> {
			dialog.dispose();
			gw.backToMenu();
		});
		
		rightPanel.add(message);
		rightPanel.add(Box.createVerticalStrut(20));
		rightPanel.add(button);
		
		dialog.add(mainPanel);
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialog.setVisible(true);
	}
}