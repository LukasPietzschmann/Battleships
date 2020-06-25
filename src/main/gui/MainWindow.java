package gui;


import java.awt.Dimension;

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
		frame.setVisible(true);
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
	}
}
