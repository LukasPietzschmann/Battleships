
package main.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

public class MainWindow {
	private JFrame frame;
	private boolean gameRunning;
	public static Music music;
	
	
	public void setUpMainWindow(){
		frame = new JFrame(GuiTester.theme);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(1130, 700));
		frame.setMinimumSize(new Dimension(1130, 700));
		frame.setBackground(Color.DARK_GRAY);
		createMenu();
	}
	
	public void createMenu() {
		gameRunning = true;
		
		MainMenu menu = new MainMenu(frame);
		
		menu.setUpMenu();
		
		String filepath = "src/res/sound.wav";
		music = new Music(filepath);
		if (GuiTester.soundPlaying == true) {
			music.restartMusic();
		}
		
		try {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/res/Krungthep.ttf")));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FontFormatException e) {
			e.printStackTrace();
		}
	}
}
