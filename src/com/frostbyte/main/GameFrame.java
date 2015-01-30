package com.frostbyte.main;

import java.awt.Dimension;

import javax.swing.JFrame;

public class GameFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	public static int WIDTH = 800, HEIGHT = 600;

	public GameFrame() {
		setTitle("Frameshift Alpha v1.0.0");
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setDefaultCloseOperation(3);
		setResizable(false);
		pack();
		setVisible(true);
	}
}
