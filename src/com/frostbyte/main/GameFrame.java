package com.frostbyte.main;

import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class GameFrame extends JFrame implements ComponentListener {
	private static final long serialVersionUID = 1L;
	public static int WIDTH = 800, HEIGHT = 600;

	public GameFrame(final GameManager gameManager) {
		setTitle("Frameshift Alpha v1.0.0");
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setDefaultCloseOperation(3);
		pack();
		setVisible(true);
		addComponentListener(this);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (gameManager.world != null) {
					gameManager.currentMenu = 1;
					gameManager.inGame = false;
					gameManager.world.getWorldSaver().saveWorld();
					gameManager.world.getChunkHandler().update();
				}
			}
		});
	}

	public void componentHidden(ComponentEvent arg0) {

	}

	public void componentMoved(ComponentEvent arg0) {

	}

	public void componentResized(ComponentEvent event) {
		WIDTH = event.getComponent().getWidth();
		HEIGHT = event.getComponent().getHeight();
		setTitle("Frameshift Alpha v1.0.0" + " Sized: " + WIDTH + ", " + HEIGHT);
	}

	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub
	}
}
