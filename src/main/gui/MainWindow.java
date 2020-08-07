package gui;

import logic.Launcher;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.net.URL;

import javax.swing.JFrame;

/**
 * Die Klasse MainWindow bildet das Grundgerüst für die weiteren GUI-Klassen.
 */
public class MainWindow {
	private JFrame frame;
	public static Music music;
	public static Font font;
	
	/**
	 * Erstellung des JFrames.
	 */
	public void setUpMainWindow() {
		frame = new JFrame(Launcher.theme);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(1130, 728));
		frame.setMinimumSize(new Dimension(1130, 728));
		frame.setBackground(Color.DARK_GRAY);
		createMenu();
	}
	
	/**
	 * Erstellung und Aufruf des Hauptmenüs. Einbindung der Schriftart "Krungthep". Laden der Musikdatei.
	 */
	public void createMenu() {
		URL musicURL = getClass().getClassLoader().getResource("sound.wav");
		music = new Music(musicURL);
		if(Launcher.soundPlaying) {
			music.restartMusic();
		}
		
		try {
			URL fontURL = getClass().getClassLoader().getResource("Krungthep.ttf");
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, fontURL.openStream()));
		}catch(IOException | FontFormatException e) {
			e.printStackTrace();
		}
		
		font = new Font("Krungthep", Font.PLAIN, 20);
		
		MainMenu menu = new MainMenu(frame);
		
		menu.setUpMenu();
	}
}
