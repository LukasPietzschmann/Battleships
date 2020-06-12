package main.gui;

import java.awt.Graphics;

import javax.swing.JPanel;

public class JGrid extends JPanel {
	int size;
	int grids;
	
	public JGrid(int size) {
		this.grids = size * size;
		this.size = size;
	}
	
//	protected void paintComponent(Graphics g) {
//		// ...
//	}
}
