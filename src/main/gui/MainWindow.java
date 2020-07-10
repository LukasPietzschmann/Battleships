
package gui;

import logic.Launcher;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

/**
 * Die Klasse MainWindow bildet das Grundgerüst für die weiteren GUI-Klassen.
 */
public class MainWindow {
	private JFrame frame;
	public static Music music;

	/**
	 * Erstellung des JFrames.
	 */
	public void setUpMainWindow(){
		frame = new JFrame(Launcher.theme);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(1130, 700));
		frame.setMinimumSize(new Dimension(1130, 700));
		frame.setBackground(Color.DARK_GRAY);
		createMenu();
	}

	/**
	 * Erstellung und Aufruf des Hauptmenüs.
	 * Einbindung der Schriftart "Krungthep".
	 * Laden der Musikdatei.
	 */
	public void createMenu() {
		
		MainMenu menu = new MainMenu(frame);
		
		menu.setUpMenu();
		
		String filepath = "src/res/sound.wav";
		music = new Music(filepath);
		if (Launcher.soundPlaying) {
			music.restartMusic();
		}
		
		try {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/res/Krungthep.ttf")));
		} catch (IOException | FontFormatException e) {
			e.printStackTrace();
		}
	}
}
