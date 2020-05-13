package gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class MainWindow {
	private JFrame frame;
	private boolean gameRunning;
	
	public void setUpMainWindow(){
		frame = new JFrame(GuiTester.theme);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(1070, 700));
		frame.setMinimumSize(new Dimension(1070, 700));
		frame.setVisible(true);
		createMenu();
	}
	
	public void createMenu() {
		gameRunning = true;
		
		MainMenu menu = new MainMenu(frame);
		menu.setUpMenu();
		
	}
}
