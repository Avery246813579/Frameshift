package com.frostbyte.main;

public class GameLoop extends Thread implements Runnable {
	GameManager gameManager;
	int i = 0;
	
	public GameLoop(GameManager gameManager) {
		this.gameManager = gameManager;
	}

	public void run() {
		while (gameManager.isRunning()) {
			synchronized (this) {
				try {
					wait(50);
					System.out.println(i);
					i++;
					gameManager.drawOffScreen();
					gameManager.update();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
