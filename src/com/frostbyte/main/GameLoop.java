package com.frostbyte.main;

public class GameLoop extends Thread implements Runnable {
	GameManager gameManager;
	
	public GameLoop(GameManager gameManager) {
		this.gameManager = gameManager;
	}

	public void run() {
		while (gameManager.isRunning()) {
			synchronized (this) {
				try {
					wait(15);
					gameManager.drawOffScreen();
					gameManager.update();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}