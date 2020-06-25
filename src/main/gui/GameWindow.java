package gui;

import javax.swing.*;
import java.awt.*;

public class GameWindow {
	JFrame frame; 
	String mode;
	
	public GameWindow(JFrame frame, String mode) {
		this.frame = frame;
		this.mode = mode;
	}
	
	public void setUpGameWindow() {
		Grid gridPlayer1 = new Grid(GuiTester.gridSize);
		Grid gridPlayer2 = new Grid(GuiTester.gridSize);
		if (mode.equals("pvp") || mode.equals("pvc")) {
			// Methode, um Elemente selbst auf das GridPlayer1 zu setzen
			
		} else {
			// Methode, die die Elemente für beide Spielfelder automatisch füllt
		}
	}
	
	
	public void backToMenu() {
		MainMenu menu = new MainMenu(frame);
		menu.setUpMenu();
	}
}

